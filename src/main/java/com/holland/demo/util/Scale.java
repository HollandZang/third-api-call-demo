package com.holland.demo.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Scale {
    private static final List<Character> HEX_DICT = new ArrayList<>(32);
    private static final String HEX_HEAD = "0x";
    private static final BigDecimal HEX_EXPONENT = BigDecimal.valueOf(16);

    static {
        HEX_DICT.add('0');
        HEX_DICT.add('1');
        HEX_DICT.add('2');
        HEX_DICT.add('3');
        HEX_DICT.add('4');
        HEX_DICT.add('5');
        HEX_DICT.add('6');
        HEX_DICT.add('7');
        HEX_DICT.add('8');
        HEX_DICT.add('9');
        HEX_DICT.add('a');
        HEX_DICT.add('b');
        HEX_DICT.add('c');
        HEX_DICT.add('d');
        HEX_DICT.add('e');
        HEX_DICT.add('f');
    }

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
