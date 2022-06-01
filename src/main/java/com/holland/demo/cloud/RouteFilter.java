package com.holland.demo.cloud;

import com.holland.demo.util.Requests;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Order(100)
@Configuration
public class RouteFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RouteFilter.class);

    @Resource
    private CloudCenter cloudCenter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing RouteFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String bodyStr = Requests.getBodyStr(request);
        request = ThreadLocals.REQUEST.get();

        final List<Server> servers = cloudCenter.findRoute((HttpServletRequest) request);
        if (servers.size() > 0) {
            logger.debug("> forward: " + ((HttpServletRequest) request).getRequestURI());
            final ServletOutputStream outputStream = response.getOutputStream();
            for (Server server : servers) {
                final Response resp = cloudCenter.route(server, (HttpServletRequest) request, bodyStr);
                /* When IOException happened */
                if (resp == null) {
                    server.networkLatency = Long.MAX_VALUE;
                    server.visitNum++;
                    continue;
                }

                updateServerState((HttpServletRequest) request, server, resp);
                if (resp.body() == null) {
                    outputStream.close();
                    return;
                }
                final String respBody = resp.body().string();
                outputStream.write(respBody.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                return;
            }
            outputStream.write("> failed to reach forwarding service".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            return;
        }
        logger.debug("> accept: " + ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(request, response);
    }

    private void updateServerState(HttpServletRequest request, Server server, Response resp) {
        final String timestamp = resp.header("timestamp");
        if (timestamp != null) {
            server.networkLatency = Long.parseLong(timestamp) - Long.parseLong(request.getHeader("timestamp"));
            server.lastConnectTime = Long.parseLong(timestamp);
        } else {
            server.networkLatency = System.currentTimeMillis() - Long.parseLong(request.getHeader("timestamp"));
            server.lastConnectTime = System.currentTimeMillis();
        }
        server.visitNum++;
    }

    @Override
    public void destroy() {
        logger.info("Destroying RouteFilter");
    }
}
