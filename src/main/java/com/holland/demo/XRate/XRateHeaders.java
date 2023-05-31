package com.holland.demo.XRate;

/**
 * Resp: 429 Too Many Requests
 */
public enum XRateHeaders {
    /**
     * <p>The policy that applies to this request.</p>
     * <p>Policies may be the same across different API endpoints but are treated the same for rate limiting purposes.</p>
     */
    XRateLimitPolicy("X-Rate-Limit-Policy"),
    /**
     * <p>A comma-delimited list of applicable rules.</p>
     * <p>This can be used to determine the header key for the next two headers.</p>
     * <p>Common rules are: ip, account, and client.</p>
     */
    XRateLimitRules("X-Rate-Limit-Rules"),

    /* on [X-Rate-Limit-Rules: ip] */
    XRateLimitIp("X-Rate-Limit-Ip"),
    XRateLimitIpState("X-Rate-Limit-Ip-State"),

    /* on [X-Rate-Limit-Rules: account] */
    XRateLimitAccount("X-Rate-Limit-Account"),
    XRateLimitAccountState("X-Rate-Limit-Account-State"),

    /* on [X-Rate-Limit-Rules: client] */
    XRateLimitClient("X-Rate-Limit-Client"),
    XRateLimitClientState("X-Rate-Limit-Client-State"),

    /**
     * Time to wait (in seconds) until the rate limit expires.
     */
    RetryAfter("Retry-After"),
    ;

    public final String headerName;

    XRateHeaders(String headerName) {
        this.headerName = headerName;
    }
}
