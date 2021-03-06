package com.holland.demo.wechat.offiaccount;

public class Conf {
    public static final String APPID = "";
    public static final String APPSECRET = "";

    public static AccessToken token;

    static {
        // TODO: get from cache
        token = new AccessToken("test", -1);
    }

    public static class AccessToken {
        public final String access_token;
        public final Integer expires_in;
        public final long createTime;

        public AccessToken(String access_token, Integer expires_in) {
            this.access_token = access_token;
            this.expires_in = expires_in;
            this.createTime = System.currentTimeMillis();
        }

        public boolean isExpires() {
            return System.currentTimeMillis() - this.createTime > this.expires_in * 1000;
        }
    }
}
