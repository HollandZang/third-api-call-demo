package com.holland.demo.XRate.server;

import com.holland.demo.XRate.XRateLimitRules;
import com.holland.demo.XRate.XRateLimitRulesState;

import java.util.concurrent.ConcurrentHashMap;

public class Handler {
    private final String handleName;
    private final XRateLimitRules xRateLimitRules;
    private final XRateLimitRulesState xRateLimitRulesState;
    private final int max;
    private final int period;
    private final int retryAfter;
    private String respRule;
    private final ConcurrentHashMap<String, String> data = new ConcurrentHashMap<>();

    public Handler(String handleName, XRateLimitRules xRateLimitRules, XRateLimitRulesState xRateLimitRulesState, int max, int period, int retryAfter) {
        this.handleName = handleName;
        this.xRateLimitRules = xRateLimitRules;
        this.xRateLimitRulesState = xRateLimitRulesState;
        this.max = max;
        this.period = period;
        this.retryAfter = retryAfter;
        respRule = String.valueOf(max) + ':' + period + ':' + retryAfter;
    }

    void handle(HandleChain chain, String ip, String account, String client) {
        String currPeriod = chain.getCurrPeriod(handleName);
        String rules = xRateLimitRules.getRules(ip, account, client);
        String userData = data.get(rules);
        if (null == userData) {
            // 新用户
            data.put(rules, currPeriod + ':' + 1);
            chain.doHandle(ip, account, client);
        } else {
            int i = userData.indexOf(':');
            String userPeriod = userData.substring(0, i);
            if (userPeriod.equals(currPeriod)) {
                // 当前周期
                int userCount = Integer.parseInt(userData.substring(i + 1)) + 1;
                data.put(rules, currPeriod + ':' + userCount);

                if (userCount < max) {
                    // 未限流
                    chain.doHandle(ip, account, client);
                } else {
                    String respRuleState = String.valueOf(userCount) + ':' + period + ':' + retryAfter;
                    throw new XRateServerException(respRule, respRuleState);
                }
            } else {
                // 新周期
                data.put(rules, currPeriod + ':' + 1);
                chain.doHandle(ip, account, client);
            }
        }
    }
}
