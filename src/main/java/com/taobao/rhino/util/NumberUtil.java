package com.taobao.rhino.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author xueshengguo
 * @date 2018/11/21
 */
public class NumberUtil {

    static String[] map = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String toHex(Long num) {
        if (num == 0) {
            return "0";
        }
        String result = "";
        while (num != 0) {
            long x = num & 0xF;
            result = map[(int)x] + result;
            num = num >>> 4;
        }
        return result;
    }

    /**
     * 十进制转成十六进制，前面自动填充0
     *
     * @param digit
     * @param num
     * @return
     */
    public static String toHex(Integer digit, Long num) {
        String hex = Long.toHexString(num);
        if (hex.length() < digit) {
            int r = digit - hex.length();
            String[] fill = new String[r];
            Arrays.fill(fill, "0");
            hex = Arrays.stream(fill).collect(Collectors.joining()) + hex;
        }
        return hex;
    }

    public static Integer hexToDecimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    public static boolean isNumber(char num) {
        try {
            Integer.parseInt(String.valueOf(num));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static char[] intArrayToCharArray(int[] ints) {
        char[] chars = new char[ints.length];
        for (int i = 0; i < ints.length; i++) {
            chars[i] = (char)ints[i];
        }
        return chars;
    }

    public static void main(String[] args) {
        String str = "000000000000123400a10002";
        String strings = str.replaceAll("\r|\n", "").replaceAll("([\\da-fA-F]{2})?", "0x$1 ").replace("/+$/", "");

        System.out.println(strings);
    }
}
