package com.holland.demo.eth;

import com.holland.demo.util.Scale;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class EthConf {
    private static final String KEY;
    private static final EthNet NET;


    static {
        KEY = System.getenv("ETH_KEY");
        if (KEY == null)
            throw new RuntimeException("ETH_KEY is null");

        NET = new EthNet(KEY);
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            System.exit(0);
        });

        final String blockNumber = NET.sync.postJson("eth_blockNumber");

        final Map<String, Object> eth_getBlockByNumber = NET.sync.postJson("eth_getBlockByNumber"
                , blockNumber
                , false);
        final List<String> transactions = (List<String>) eth_getBlockByNumber.get("transactions");
        System.out.println("> count transactions: " + transactions.size());

        final Map<String, Object> eth_getTransactionByHash = NET.sync.postJson("eth_getTransactionByHash"
                , transactions.get(0));
        final Object to = eth_getTransactionByHash.get("to");

        final String eth_getBalance = NET.sync.postJson("eth_getBalance"
                , to
                , "latest");

        final BigDecimal wei = Scale.hex2Dec(eth_getBalance);
        System.out.println(wei + " wei");

        final BigDecimal eth = wei.divide(BigDecimal.valueOf(1000000000000000000L), 18, RoundingMode.HALF_DOWN);
        System.out.println(eth.doubleValue() + " eth");

        System.exit(0);
    }
}
