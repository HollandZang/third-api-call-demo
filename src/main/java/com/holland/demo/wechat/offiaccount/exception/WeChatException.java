package com.holland.demo.wechat.offiaccount.exception;

import com.google.gson.JsonObject;

public class WeChatException extends RuntimeException {

    public WeChatException(JsonObject json) {
        super(json.get("errcode").getAsString() + ':' + json.get("errmsg").getAsString());
    }

    public static boolean checkNullErr(JsonObject json) {
        final Object errcode = json.get("errcode");
        return errcode != null;
    }

    public static boolean checkCodeErr(JsonObject json) {
        final int errcode = json.get("errcode").getAsInt();
        return errcode != 0;
    }

    /**
     * {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest rid: 62946ac6-028ee51a-70bee675"}
     */
    public static boolean checkTokenErr(JsonObject json) {
        final int errcode = json.get("errcode").getAsInt();
        return errcode == 40001;
    }
}
