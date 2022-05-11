package com.holland.demo.cloud;

public class Server {
    public final String serverName;
    public final String url;
    /* regular expression */
    public final String forwardRule;
    /* millisecond */
    public final long registerTime;
    /* millisecond */
    public long lastConnectTime;
    /* millisecond */
    public long networkLatency;

    public long visitNum;
    public long weights;
    public long hash;

    public Server(String serverName, String url, String forwardRule, long registerTime, long lastConnectTime, long networkLatency) {
        this.serverName = serverName;
        this.url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
        this.forwardRule = forwardRule;
        this.registerTime = registerTime;
        this.lastConnectTime = lastConnectTime;
        this.networkLatency = networkLatency;
    }
}
