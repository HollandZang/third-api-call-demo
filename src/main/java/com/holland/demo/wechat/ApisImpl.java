package com.holland.demo.wechat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.holland.demo.exception.NetExceptionEnum;
import com.holland.demo.wechat.exception.WeChatException;
import com.holland.net.Net;
import com.holland.net.common.PairBuilder;

import java.util.Optional;

import static com.holland.demo.wechat.Conf.APPID;
import static com.holland.demo.wechat.Conf.APPSECRET;

public class ApisImpl implements Apis {

    private final Net net;

    public ApisImpl() {
        this.net = new Net();
    }

    @Override
    public Conf.AccessToken getAccessToken() {
        final Optional<String> o = net.sync.get("https://api.weixin.qq.com/cgi-bin/token"
                , null
                , new PairBuilder()
                        .add("grant_type", "client_credential")
                        .add("appid", APPID)
                        .add("secret", APPSECRET));
        if (!o.isPresent())
            throw NetExceptionEnum.EMPTY_BODY.e();

        final JsonObject json = new Gson().fromJson(o.get(), JsonObject.class);
        final Object errcode = json.get("errcode");
        if (errcode != null)
            throw new WeChatException(json);

        final String access_token = json.get("access_token").getAsString();
        final Integer expires_in = json.get("expires_in").getAsInt();
        return Conf.token = new Conf.AccessToken(access_token, expires_in);
    }

}
