package com.holland.demo.cloud;

import com.holland.demo.util.Requests;
import com.holland.net.Net;
import com.holland.net.conf.DefaultHttpConf;
import okhttp3.Request;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cloud/center")
public class CloudCenter {
    private final Map<String, List<Server>> servers;
    private final long maxFreeTime;
    private final ScheduledThreadPoolExecutor heathCheckPool;
    private final Net net;

    {
        /* 1 minute */
        this.maxFreeTime = 1000 * 60;
        this.servers = new HashMap<>();
        this.heathCheckPool = new ScheduledThreadPoolExecutor(1, r -> {
            final Thread thread = new Thread(r);
            thread.setName("heathCheck_thread");
            return thread;
        });
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
        // TODO: 2022/5/9 validate parameters

        servers.computeIfPresent(serverName, (name, list) -> {
            final Optional<Server> o = list.stream().filter(server -> server.url.equals(url)).findFirst();
            if (o.isPresent()) {
                final Server server = o.get();
                server.lastConnectTime = timestamp;
                server.networkLatency = networkLatency;
            } else {
                list.add(new Server(serverName, url, timestamp, timestamp, networkLatency));
            }
            return list;
        });
        servers.computeIfAbsent(serverName, name -> {
            final List<Server> list = new ArrayList<>();
            list.add(new Server(serverName, url, timestamp, timestamp, networkLatency));
            return list;
        });
        return "OK";
    }

    private void heathCheckTask() {
        heathCheckPool.scheduleWithFixedDelay(() -> {
            final long currentTimeMillis = System.currentTimeMillis();
            servers.forEach((name, list) -> {
                list.removeIf(server -> currentTimeMillis - server.lastConnectTime > maxFreeTime);
            });

            System.out.println("> heathCheck: \n"
                    + servers.entrySet().stream()
                    .map(e -> String.format("[%s] online: %s", e.getKey(), e.getValue().size()))
                    .collect(Collectors.joining("\n"))
            );
        }, 1, 3, TimeUnit.SECONDS);
    }

    public String router(HttpServletRequest request, Map<String, String> body) {
        final Map<String, String> headers = Requests.headers(request);
        final String forwardServer = request.getHeader("forward");
        final List<Server> servers = this.servers.get(forwardServer);
        final long currentTimeMillis = System.currentTimeMillis();
        if (servers != null && !servers.isEmpty()) {
            final Optional<Server> o = servers.stream().filter(server -> currentTimeMillis - server.lastConnectTime > maxFreeTime)
                    .findFirst();
            if (o.isPresent()) {
                final Server server = o.get();
                return route(server, headers, body);
            }
        }
        System.err.println("No route");
        return null;
    }

    private String route(Server server, Map<String, String> headers, Map<String, String> body) {
        final Optional<String> o = net.sync.postJson(server.url, headers, body);
        //            server.lastConnectTime = timestamp;
        //            server.networkLatency = networkLatency;
        return o.orElse(null);
    }


}
