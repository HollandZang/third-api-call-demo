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
    private final String centerUrl;
    /* second */
    private final long pingTime;

    private final String serverName;
    private final String serverUrl;
    private final String serverForwardRule;

    public Cloud(String centerUrl, String serverName, String serverUrl, String serverForwardRule) {
        this.centerUrl = centerUrl;
        this.serverName = serverName;
        this.serverUrl = serverUrl;
        this.serverForwardRule = serverForwardRule;
    }

    {
        final DefaultHttpConf conf = new DefaultHttpConf() {
            @Override
            public Request.Builder getRequest(Map<String, ?> headers) {
                final Request.Builder builder = new Request.Builder()
                        .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*");
                if (headers != null)
                    headers.forEach((name, value) -> builder.addHeader(name, value == null ? "" : value.toString()));
                return builder;
            }
        };
        this.net = new Net(conf);
        this.registerPool = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "heathCheck_thread"));
        this.pingTime = 5;
        register();
    }

    private void register() {
        registerPool.scheduleWithFixedDelay(() -> {
            try {
                net.async.postJson(centerUrl + "/register", null
                        , new PairBuilder()
                                .add("server", serverName)
                                .add("url", serverUrl)
                                .add("forwardRule", serverForwardRule)
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, pingTime, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        final Cloud cloud = new Cloud("http://localhost:9001/cloud/center", "test_server", "http://localhost:9001", "^/?cloud/center/w.*");
        Thread.sleep(1000);
//        final Optional<String> s = cloud.net.sync.get("http://localhost:9001/cloud/center/watch", null
//                , new PairBuilder().add("a", 1));
//        s.ifPresent(System.out::println);

        System.exit(0);
    }
}
