package com.holland.demo.XRate;

/**
 * <p>A comma-delimited list of text representing the request's current state.</p>
 * <p>these are made up of three numbers separated by a colon with each representing:</p>
 * <p>1. the current hit count</p>
 * <p>2. the period (in seconds) tested</p>
 * <p>3. the active time restricted in seconds (this will be 0 if the request is not rate limited)</p>
 */
public class XRateLimitRulesState {
    /**
     * on [X-Rate-Limit-Rules: ip]
     */
    public class Ip extends XRateLimitRulesState {
    }

    /**
     * on [X-Rate-Limit-Rules: account]
     */
    public class Account extends XRateLimitRulesState {
    }

    /**
     * on [X-Rate-Limit-Rules: client]
     */
    public class Client extends XRateLimitRulesState {
    }
}
