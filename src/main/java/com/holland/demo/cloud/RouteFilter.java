package com.holland.demo.cloud;

import com.google.gson.reflect.TypeToken;
import com.holland.demo.util.Requests;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Order(100)
@Configuration
public class RouteFilter implements Filter {
    private static final TypeToken<Map<String, String>> MAP_STR_TYPE = new TypeToken<Map<String, String>>() {
    };

    @Resource
    private CloudCenter cloudCenter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String bodyStr = Requests.getBodyStr(request);
        request = Requests.REQ_TREAD_LOCAL.get();

        final List<Server> servers = cloudCenter.findRoute((HttpServletRequest) request);
        if (servers.size() > 0) {
            System.out.println("> forward: " + ((HttpServletRequest) request).getRequestURI());
            final ServletOutputStream outputStream = response.getOutputStream();
            for (Server server : servers) {
                final Response resp = cloudCenter.route(server, (HttpServletRequest) request, bodyStr);
                if (!resp.isSuccessful())
                    continue;

                // TODO: 2022/5/10 更新服务状态参数

                //            server.lastConnectTime = timestamp;
                //            server.networkLatency = networkLatency;
                final String respBody = resp.body().string();
                outputStream.write(respBody.getBytes(StandardCharsets.UTF_8));
                return;
            }
            outputStream.write("> failed to reach forwarding service".getBytes(StandardCharsets.UTF_8));
            return;
        }
        System.out.println("> accept: " + ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
