package com.holland.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Requests {
    public static Map<String, String> headers(HttpServletRequest request) {
        final Map<String, String> map = new HashMap<>();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            map.put(key, request.getHeader(key));
        }
        return map;
    }
}
