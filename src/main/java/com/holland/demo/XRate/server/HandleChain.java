package com.holland.demo.XRate.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleChain {
    private final ThreadLocal<Integer> pos = new ThreadLocal<>();
    private final List<Handler> handlers = new ArrayList<>();

    // TODO: 2023/5/19 统一管理周期变化
    /* [handleName, 当前的周期: 转64位] */
    private final Map<String, String> currPeriod = new HashMap<>();

    void doHandle(String ip, String account, String client) {
        Integer p = pos.get();
        int n = handlers.size();
        if (p < n) {
            pos.set(++p);
            Handler handler = handlers.get(p);
            try {
                handler.handle(this, ip, account, client);
            } catch (XRateServerException xRateServerException) {

            }
        }
    }

    public String getCurrPeriod(String handleName) {
        return currPeriod.get(handleName);
    }
}
