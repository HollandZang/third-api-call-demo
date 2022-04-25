package com.holland.demo.eth;

import com.google.gson.Gson;
import com.holland.http.conf.DefaultZnHttpConf;
import com.holland.http.conf.ZnHttpConf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class EthNet {
    private final String KEY;
    public final Sync sync;
    public final Async async;

    public EthNet(String key) {
        KEY = key;
        final DefaultZnHttpConf httpConf = new DefaultZnHttpConf();
        this.sync = new Sync(httpConf);
        this.async = new Async(httpConf);
    }

    public class Sync extends com.holland.http.core.Sync {
        public Sync(ZnHttpConf conf) {
            super(conf);
        }

        public <T> T postJson(String method, Object... params) {
            final String p = getParamStr(method, params);
            final Optional<String> s = super.postJson("https://eth-mainnet.alchemyapi.io/v2/" + KEY, null, p);

            System.out.println("> method: " + method + "\n  param:  " + p + "\n  result: " + s.orElse(null));
            if (!s.isPresent())
                throw new RuntimeException("Response body is null");

            final Map<?, ?> data = new Gson().fromJson(s.get(), Map.class);
            final Object error = data.get("error");
            if (error != null)
                throw new RuntimeException(error.toString());

            //noinspection unchecked
            return (T) data.get("result");
        }
    }

    public class Async extends com.holland.http.core.Async {
        public Async(ZnHttpConf conf) {
            super(conf);
        }

        public <T> void postJson(String method, Consumer<T> onResponse, Object... params) {
            final String p = getParamStr(method, params);
            super.postJson("https://eth-mainnet.alchemyapi.io/v2/" + KEY, null, p
                    , response -> {
                        if (response.body() == null)
                            throw new RuntimeException("Response body is null");

                        final String s;
                        try {
                            s = response.body().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("> method: " + method + "\n  param:  " + p + "\n  result: " + s);

                        final Map<?, ?> data = new Gson().fromJson(s, Map.class);
                        final Object error = data.get("error");
                        if (error != null)
                            throw new RuntimeException(error.toString());

                        //noinspection unchecked
                        final T result = (T) data.get("result");
                        onResponse.accept(result);
                    });
        }
    }

    private String getParamStr(String method, Object[] params) {
        final Map<String, Object> map = new HashMap<>(8);
        //noinspection SpellCheckingInspection
        map.put("jsonrpc", "2.0");
        map.put("id", "0");
        map.put("method", method);
        map.put("params", params);
        return new Gson().toJson(map);
    }
}
