package com.holland.demo.wechat.offiaccount.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.holland.demo.exception.NetExceptionEnum;
import com.holland.demo.util.Action;
import com.holland.demo.wechat.offiaccount.Conf;
import com.holland.demo.wechat.offiaccount.exception.WeChatException;
import com.holland.net.common.PairBuilder;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Net {

    private final com.holland.net.Net net;
    private final String BASE_URL;

    public Net() {
        this.net = new com.holland.net.Net();
        this.BASE_URL = "https://api.weixin.qq.com/cgi-bin";
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">
     * 开始开发 /获取Access token</a>
     */
    synchronized Conf.AccessToken getAccessToken(final long callTime) {
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

    JsonObject get(String path) {
        final Optional<String> o = net.sync.get(BASE_URL + path + "?access_token=" + Conf.token.access_token
                , null
                , null);
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        return new Gson().fromJson(o.get(), JsonObject.class);
    }

    JsonObject postJson(String path, String jsonStr) {
        final Optional<String> o = net.sync.postJson(BASE_URL + path + "?access_token=" + Conf.token.access_token
                , null
                , jsonStr);
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();
        return new Gson().fromJson(o.get(), JsonObject.class);
    }

    <T> T retryAction(final Supplier<T> action, final Predicate<T> predicate) {
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

    static class Retry<T> {
        public final boolean needRetry;
        public final T data;

        public Retry(boolean needRetry, T data) {
            this.needRetry = needRetry;
            this.data = data;
        }

        public boolean predicate() {
            return !needRetry;
        }

        static <T> Retry<T> toRetry(JsonObject json, T res) {
            return toRetry(json, res, res);
        }

        static <T> Retry<T> toRetry(JsonObject json, T success, T failed) {
            System.out.println(json);
            if (WeChatException.checkCodeErr(json)) {
                if (WeChatException.checkTokenErr(json)) {
                    return new Retry<>(true, failed);
                }
                return new Retry<>(false, failed);
            }
            return new Retry<>(false, success);
        }
    }
}
