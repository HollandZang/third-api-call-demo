package com.holland.demo.util;

import com.holland.demo.cloud.ThreadLocals;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    /**
     * @param request need reset request from thread local
     */
    public static String getBodyStr(ServletRequest request) throws IOException {
        final String body = ThreadLocals.REQUEST_BODY.get();
        if (body != null)
            return body;

        long length = request.getContentLength();
        if (length <= 0) length = request.getContentLengthLong();

        final ServletInputStream inputStream = request.getInputStream();
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final byte[] bytes;
        if (length > 0) {
            bytes = new byte[(int) length];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(bytes);
            bos.write(bytes);
        } else {
            bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        }
        bos.close();
        final String s = bos.toString();
        request = new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                // noinspection override,SpellCheckingInspection
                return new ServletInputStream() {
                    private final InputStream delegate = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));

                    public boolean isFinished() {
                        return false;
                    }

                    public boolean isReady() {
                        return true;
                    }

                    public void setReadListener(ReadListener readListener) {
                        throw new UnsupportedOperationException();
                    }

                    public int read() throws IOException {
                        return this.delegate.read();
                    }

                    public int read(byte[] b, int off, int len) throws IOException {
                        return this.delegate.read(b, off, len);
                    }

                    public int read(byte[] b) throws IOException {
                        return this.delegate.read(b);
                    }

                    public long skip(long n) throws IOException {
                        return this.delegate.skip(n);
                    }

                    public int available() throws IOException {
                        return this.delegate.available();
                    }

                    public void close() throws IOException {
                        this.delegate.close();
                    }

                    public synchronized void mark(int readlimit) {
                        this.delegate.mark(readlimit);
                    }

                    public synchronized void reset() throws IOException {
                        this.delegate.reset();
                    }

                    public boolean markSupported() {
                        return this.delegate.markSupported();
                    }
                };
            }
        };
        ThreadLocals.REQUEST_BODY.set(s);
        ThreadLocals.REQUEST.set(request);
        return s;
    }
}
