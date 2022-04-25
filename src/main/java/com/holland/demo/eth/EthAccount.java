package com.holland.demo.eth;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigInteger;

public class EthAccount {
    /**
     * 0xfe1e11c4aeb47b5710f61a6d4fec520c6ac5e02bd22ef57de422a897f20ee87
     * 0xfe0cbd6ec16cf7afbc033c8f0cafffb8a4272efbe64f9a59f3e5ece58006e947c36b57eeee2ff37f9832f3ac79e37cac102cacc54813cd10f8d37e46f926fdc3
     * 0xba15d75b851d2ce86a8254c2e2815bc63faf386c
     */
    public static void main(String[] args) {
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            final BigInteger privateKey = keyPair.getPrivateKey();
            final String s = privateKey.toString(16);
            final String s1 = keyPair.getPublicKey().toString(16);
            final String address = Keys.getAddress(s1);
            System.out.println("0x" + s + " 0x" + s1 + " 0x" + address);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
