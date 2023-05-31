package com.holland.demo.XRate;

/**
 * <p>A comma-delimited list of text representing the rule.</p>
 * <p>These are made up of three numbers separated by a colon with each representing:</p>
 * <p>1. the maximum hits</p>
 * <p>2. the period (in seconds) tested</p>
 * <p>3. the time restricted (in seconds) if the rule is broken</p>
 */
public abstract class XRateLimitRules {
    public abstract String getRules(String ip, String account, String client);

    /**
     * on [X-Rate-Limit-Rules: ip]
     */
    public class Ip extends XRateLimitRules {
        @Override
        public String getRules(String ip, String account, String client) {
            return ip;
        }
    }

    /**
     * on [X-Rate-Limit-Rules: account]
     */
    public class Account extends XRateLimitRules {
        @Override
        public String getRules(String ip, String account, String client) {
            return account;
        }
    }

    /**
     * on [X-Rate-Limit-Rules: client]
     */
    public class Client extends XRateLimitRules {
        @Override
        public String getRules(String ip, String account, String client) {
            return client;
        }
    }
}
