package com.holland.demo.wechat.offiaccount.net;

import com.google.gson.JsonObject;

import static com.holland.demo.wechat.offiaccount.net.Net.Retry.toRetry;

public class Menu {
    private final Net net;

    public Menu(Net net) {
        this.net = net;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html">
     * 自定义菜单 /创建接口</a>
     */
    public boolean menu_create(String jsonStr) {
        return net.retryAction(() -> {
            final JsonObject json = net.postJson("/menu/create", jsonStr);
            return toRetry(json, true, false);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Querying_Custom_Menus.html">
     * 自定义菜单 /查询接口</a>
     */
    public JsonObject get_current_selfmenu_info() {
        return net.retryAction(() -> {
            final JsonObject json = net.get("/get_current_selfmenu_info");
            return toRetry(json, json);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Deleting_Custom-Defined_Menu.html">
     * 自定义菜单 /删除接口</a>
     */
    public boolean menu_delete() {
        return net.retryAction(() -> {
            final JsonObject json = net.get("/menu/delete");
            return toRetry(json, true, false);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html">
     * 自定义菜单 /个性化菜单接口 创建个性化菜单</a>
     */
    public JsonObject menu_addconditional(String jsonStr) {
        return net.retryAction(() -> {
            final JsonObject json = net.postJson("/menu/addconditional", jsonStr);
            return toRetry(json, json);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html">
     * 自定义菜单 /个性化菜单接口 删除个性化菜单</a>
     */
    public JsonObject menu_delconditional(String jsonStr) {
        return net.retryAction(() -> {
            final JsonObject json = net.postJson("/menu/delconditional", jsonStr);
            return toRetry(json, json);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Personalized_menu_interface.html">
     * 自定义菜单 /个性化菜单接口 测试个性化菜单匹配结果</a>
     */
    public JsonObject menu_trymatch(String jsonStr) {
        return net.retryAction(() -> {
            final JsonObject json = net.postJson("/menu/trymatch", jsonStr);
            return toRetry(json, json);
        }, Net.Retry::predicate)
                .data;
    }

    /**
     * <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Getting_Custom_Menu_Configurations.html">
     * 自定义菜单 /获取自定义菜单配置</a>
     */
    public JsonObject menu_get() {
        return net.retryAction(() -> {
            final JsonObject json = net.get("/menu/get");
            return toRetry(json, json);
        }, Net.Retry::predicate)
                .data;
    }
}
