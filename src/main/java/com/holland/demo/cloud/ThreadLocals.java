package com.holland.demo.cloud;

import javax.servlet.ServletRequest;

public class ThreadLocals {
    public static final ThreadLocal<String> REQUEST_BODY = new ThreadLocal<>();
    public static final ThreadLocal<ServletRequest> REQUEST = new ThreadLocal<>();
}
