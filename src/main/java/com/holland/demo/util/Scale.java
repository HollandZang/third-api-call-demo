package com.holland.demo.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Scale {
    private static final List<Character> HEX_DICT = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
    private static final String HEX_HEAD = "0x";
    private static final BigDecimal HEX_EXPONENT = BigDecimal.valueOf(16);

    public static BigDecimal hex2Dec(String hex) {
        BigDecimal dec = BigDecimal.ZERO;
        if (hex.startsWith(HEX_HEAD))
            hex = hex.substring(2);
        hex = hex.toLowerCase();

        final char[] chars = hex.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            final char c = chars[i];
            if (c == 'x')
                break;

            final int val = HEX_DICT.indexOf(c);
            if (val == -1)
                throw new NumberFormatException("Invalid hex is " + hex + ", invalid character is " + c);

            BigDecimal pow = BigDecimal.ONE;
            for (int j = 1; j < (hex.length() - i); j++) {
                pow = pow.multiply(HEX_EXPONENT);
            }
            final BigDecimal bigDecimal = BigDecimal.valueOf(val).multiply(pow);
            dec = dec.add(bigDecimal);
        }
        return dec;
    }

}
