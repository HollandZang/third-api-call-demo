package com.holland.demo.cloud;

import com.holland.demo.util.Requests;
import com.holland.demo.util.Validator;
import com.holland.net.conf.DefaultHttpConf;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cloud/center")
public class CloudCenter {
    public final Map<String, List<Server>> servers;
    private final long maxFreeTime;
    private final ScheduledThreadPoolExecutor heathCheckPool;
    private final DefaultHttpConf conf;

    {
        /* 6 second */
        this.maxFreeTime = 1000 * 6;
        this.servers = new HashMap<>();
        this.heathCheckPool = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "heathCheck_thread"));
        this.conf = new DefaultHttpConf() {
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

        heathCheckTask();
    }

    @GetMapping("/watch")
    public Map<String, List<Server>> watch() {
        return servers;
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, @RequestBody Map<String, String> body) {
        final long timestamp = Long.parseLong(request.getHeader("timestamp"));
        final long networkLatency = System.currentTimeMillis() - timestamp;
        final String serverName = body.get("server");
        final String url = body.get("url");
        final String forwardRule = body.get("forwardRule");

        Validator.test(serverName, "serverName").notEmpty();
        Validator.test(url, "url").notEmpty();
        Validator.test(forwardRule, "forwardRule").notEmpty();

//        request.getRemoteHost();
//        request.getRemotePort();
        servers.computeIfPresent(serverName, (name, list) -> {
            final Optional<Server> o = list.stream().filter(server -> server.url.equals(url)).findFirst();
            if (o.isPresent()) {
                final Server server = o.get();
                server.lastConnectTime = timestamp;
                server.networkLatency = networkLatency;
            } else {
                list.add(new Server(serverName, url, forwardRule, timestamp, timestamp, networkLatency));
            }
            return list;
        });
        servers.computeIfAbsent(serverName, name -> {
            final List<Server> list = new ArrayList<>();
            list.add(new Server(serverName, url, forwardRule, timestamp, timestamp, networkLatency));
            return list;
        });
        return "OK";
    }

    private void heathCheckTask() {
        heathCheckPool.scheduleWithFixedDelay(() -> {
            try {
                final long currentTimeMillis = System.currentTimeMillis();
                servers.forEach((name, list) -> {
                    list.removeIf(server -> currentTimeMillis - server.lastConnectTime > maxFreeTime);
                });

                System.out.println("> heathCheck: \n"
                        + servers.entrySet().stream()
                        .map(e -> String.format("\t[%s] online: %s", e.getKey(), e.getValue().size()))
                        .collect(Collectors.joining("\n"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, maxFreeTime, maxFreeTime, TimeUnit.MILLISECONDS);
    }

    public List<Server> findRoute(HttpServletRequest request) {
        final long currentTimeMillis = System.currentTimeMillis();
        final String uri = request.getRequestURI();
        for (Map.Entry<String, List<Server>> entry : servers.entrySet()) {
            final List<Server> value = entry.getValue();
            if (value.size() > 0 && uri.matches(value.get(0).forwardRule)) {
                return value.stream()
                        .filter(server -> currentTimeMillis - server.lastConnectTime < maxFreeTime)
                        .sorted(LoadBalance::latency)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    public Response route(Server server, HttpServletRequest request, String bodyStr) {
        final Map<String, String> headers = Requests.headers(request);

        final Request req = this.conf.getRequest(headers)
                .url(server.url + request.getRequestURI())
                .method(request.getMethod(), okhttp3.RequestBody.create(MediaType.parse(headers.get("Content-Type")), bodyStr))
                .build();
        try {
            return this.conf.getClient().newCall(req).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
