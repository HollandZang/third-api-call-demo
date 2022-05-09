package com.holland.demo.cloud;

public class Server {
    public final String serverName;
    public final String url;
    /* millisecond */
    public final long registerTime;
    /* millisecond */
    public long lastConnectTime;
    /* millisecond */
    public long networkLatency;

    public Server(String serverName, String url, long registerTime, long lastConnectTime, long networkLatency) {
        this.serverName = serverName;
        this.url = url;
        this.registerTime = registerTime;
        this.lastConnectTime = lastConnectTime;
        this.networkLatency = networkLatency;
    }
}
