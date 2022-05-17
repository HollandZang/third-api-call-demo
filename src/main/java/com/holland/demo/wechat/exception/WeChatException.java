package com.holland.demo.wechat.exception;

import com.google.gson.JsonObject;

public class WeChatException extends RuntimeException {

    public WeChatException(JsonObject json) {
        super(json.get("errcode").getAsString() + ':' + json.get("errmsg").getAsString());
    }
}
