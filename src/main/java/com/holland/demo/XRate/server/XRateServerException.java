package com.holland.demo.XRate.server;

public class XRateServerException extends RuntimeException {
    public final String respRule;
    public final String respRuleState;

    public XRateServerException(String respRule, String respRuleState) {
        super("限流");
        this.respRule = respRule;
        this.respRuleState = respRuleState;
    }
}
