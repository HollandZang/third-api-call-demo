package com.holland.demo.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Scale {
    public static final List<Character> HEX_DICT = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

    public static BigDecimal a2Dec(String a, List<Character> dictA) {
        final BigDecimal aExponent = BigDecimal.valueOf(dictA.size());

        BigDecimal b = BigDecimal.ZERO;
        final char[] chars = a.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            final char c = chars[i];

            final int val = dictA.indexOf(c);
            if (val == -1)
                throw new NumberFormatException("Invalid string '" + a + "', invalid character is " + c);

            BigDecimal pow = BigDecimal.ONE;
            for (int j = 1; j < (a.length() - i); j++) {
                pow = pow.multiply(aExponent);
            }
            final BigDecimal bigDecimal = BigDecimal.valueOf(val).multiply(pow);
            b = b.add(bigDecimal);
        }
        return b;
    }

    public static String dec2A(BigDecimal dec, List<Character> dictA) {
        final StringBuilder a = new StringBuilder();
        final BigDecimal[] curr = {BigDecimal.ZERO};
        final BigDecimal pow = BigDecimal.valueOf(dictA.size());

        final List<Character> subList = dictA.subList(1, dictA.size());

//        while (true) {
//            dec.divide(pow)
//        }

        return a.toString();
    }

    private static class Pair {
        public final Character c;
        public final BigDecimal bigDecimal;

        private Pair(Character c, BigDecimal bigDecimal) {
            this.c = c;
            this.bigDecimal = bigDecimal;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }
    }

    public static void main(String[] args) {
        final BigDecimal abc = Scale.a2Dec("ab1", Scale.HEX_DICT);
        System.out.println(abc);
        final String s = Scale.dec2A(abc, Scale.HEX_DICT);
        System.out.println(s);
    }
}
