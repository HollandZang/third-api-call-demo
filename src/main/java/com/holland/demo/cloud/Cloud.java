package com.holland.demo.cloud;

import com.holland.net.Net;
import com.holland.net.common.PairBuilder;
import com.holland.net.conf.DefaultHttpConf;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Cloud {
    private final Net net;
    private final ScheduledThreadPoolExecutor registerPool;

    private final String url;
    private final String serverName;

    public Cloud(String url, String serverName) {
        this.url = url;
        this.serverName = serverName;
    }

    {
        final DefaultHttpConf conf = new DefaultHttpConf() {
            @Override
            public Request.Builder getRequest(Map<String, ?> headers) {
                Request.Builder builder = new Request.Builder()
                        .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*");
                if (headers != null) {
                    headers.forEach((name, value) -> builder.addHeader(name, value == null ? "" : value.toString()));
                }
                return builder;
            }
        };
        this.net = new Net(conf);
        this.registerPool = new ScheduledThreadPoolExecutor(1, r -> {
            final Thread thread = new Thread(r);
            thread.setName("heathCheck_thread");
            return thread;
        });
    }

    public void register() {
        registerPool.scheduleWithFixedDelay(() -> {
            net.async.postJson(url + "/register", null
                    , new PairBuilder()
                            .add("server", serverName)
                            .add("url", "http://localhost:9001")
                    , response -> {
                        try {
                            final ResponseBody body = response.body();
                            if (body != null) {
                                final String string = body.string();
                                if ("OK".equals(string)) {
                                    System.out.println("Register server success");
                                    return;
                                }
                            }
                            System.err.println("Register server failed");
                        } catch (IOException e) {
                            System.err.println("Register server failed");
                            e.printStackTrace();
                        }
                    });
        }, 0, 1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        final Cloud cloud = new Cloud("http://localhost:9001/cloud/center", "test_server");
        cloud.register();
    }
}
