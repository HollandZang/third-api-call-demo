package com.holland.demo.wechat.offiaccount;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.holland.demo.exception.NetExceptionEnum;
import com.holland.demo.util.Action;
import com.holland.demo.wechat.offiaccount.exception.WeChatException;
import com.holland.net.common.PairBuilder;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Net {

    private final com.holland.net.Net net;
    private final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";

    public Net() {
        this.net = new com.holland.net.Net();
    }

    /**
     * https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     */
    private synchronized Conf.AccessToken getAccessToken(final long callTime) {
        final boolean isCache = Conf.token != null && callTime <= Conf.token.createTime + 50;
        if (isCache)
            return Conf.token;

        final Optional<String> o = net.sync.get(BASE_URL + "/token"
                , null
                , new PairBuilder()
                        .add("grant_type", "client_credential")
                        .add("appid", Conf.APPID)
                        .add("secret", Conf.APPSECRET));
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();

        final JsonObject json = new Gson().fromJson(o.get(), JsonObject.class);
        if (WeChatException.checkNullErr(json)) {
            Conf.token = new Conf.AccessToken(Conf.token.access_token, Conf.token.expires_in);
            throw new WeChatException(json);
        }

        final String access_token = json.get("access_token").getAsString();
        final Integer expires_in = json.get("expires_in").getAsInt();
        return Conf.token = new Conf.AccessToken(access_token, expires_in);
    }

    /**
     * 自定义菜单 /创建接口
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
     */
    public boolean menu_create(String jsonStr) {
        return retryAction(() -> {
            final JsonObject json = postJson("/menu/create", jsonStr);
            return toRetry(json, true, false);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /查询接口
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Querying_Custom_Menus.html
     */
    public JsonObject get_current_selfmenu_info() {
        return retryAction(() -> {
            final JsonObject json = get("/get_current_selfmenu_info");
            return toRetry(json, json);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /删除接口
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Deleting_Custom-Defined_Menu.html
     */
    public boolean menu_delete() {
        return retryAction(() -> {
            final JsonObject json = get("/menu/delete");
            return toRetry(json, true, false);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /个性化菜单接口 创建个性化菜单
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html
     */
    public JsonObject menu_addconditional(String jsonStr) {
        return retryAction(() -> {
            final JsonObject json = postJson("/menu/addconditional", jsonStr);
            return toRetry(json, json);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /个性化菜单接口 删除个性化菜单
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html
     */
    public JsonObject menu_delconditional(String jsonStr) {
        return retryAction(() -> {
            final JsonObject json = postJson("/menu/delconditional", jsonStr);
            return toRetry(json, json);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /个性化菜单接口 测试个性化菜单匹配结果
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html
     */
    public JsonObject menu_trymatch(String jsonStr) {
        return retryAction(() -> {
            final JsonObject json = postJson("/menu/trymatch", jsonStr);
            return toRetry(json, json);
        }, Retry::predicate)
                .data;
    }

    /**
     * 自定义菜单 /获取自定义菜单配置
     * https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Getting_Custom_Menu_Configurations.html
     */
    public JsonObject menu_get() {
        return retryAction(() -> {
            final JsonObject json = get("/menu/get");
            return toRetry(json, json);
        }, Retry::predicate)
                .data;
    }

    private JsonObject get(String path) {
        final Optional<String> o = net.sync.get(BASE_URL + path + "?access_token=" + Conf.token.access_token
                , null
                , null);
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        return new Gson().fromJson(o.get(), JsonObject.class);
    }

    private JsonObject postJson(String path, String jsonStr) {
        final Optional<String> o = net.sync.postJson(BASE_URL + path + "?access_token=" + Conf.token.access_token
                , null
                , jsonStr);
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        return new Gson().fromJson(o.get(), JsonObject.class);
    }

    private <T> T retryAction(final Supplier<T> action, final Predicate<T> predicate) {
        return Action.retry(
                action
                , predicate
                , 50
                , 1
                , () -> {
                    try {
                        final Conf.AccessToken token = getAccessToken(System.currentTimeMillis());
                        return new Retry<>(true, token);
                    } catch (Exception e) {
                        return new Retry<>(false, null);
                    }
                }
                , Retry::predicate);
    }

    private <T> Retry<T> toRetry(JsonObject json, T res) {
        return toRetry(json, res, res);
    }

    private <T> Retry<T> toRetry(JsonObject json, T success, T failed) {
        System.out.println(json);
        if (WeChatException.checkCodeErr(json)) {
            if (WeChatException.checkTokenErr(json)) {
                return new Retry<>(true, failed);
            }
            return new Retry<>(false, failed);
        }
        return new Retry<>(false, success);
    }

    private static class Retry<T> {
        public final boolean needRetry;
        public final T data;

        public Retry(boolean needRetry, T data) {
            this.needRetry = needRetry;
            this.data = data;
        }

        public boolean predicate() {
            return !needRetry;
        }
    }
}
