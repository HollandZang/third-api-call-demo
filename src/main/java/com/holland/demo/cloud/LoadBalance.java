package com.holland.demo.cloud;

public class LoadBalance {

    public static int random(Server s1, Server s2) {
        return (int) (s1.visitNum - s2.visitNum);
    }

    public static int weights(Server s1, Server s2) {
        return 0;
    }

    public static int hash(Server s1, Server s2) {
        return 0;
    }

    public static int latency(Server s1, Server s2) {
        return (int) (s1.networkLatency - s2.networkLatency);
    }
}
