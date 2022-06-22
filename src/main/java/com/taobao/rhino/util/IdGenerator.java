package com.taobao.rhino.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成唯一id
 */
public class IdGenerator {
    static Integer counter = 0;

    String baseHex = "";
    String tableStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    String[] table = tableStr.split("");

    public void setBaseHex(String hex) {
        this.baseHex = hex;
    }

    public String atob(String base64) {
        if (base64.matches(".*={3,}|.*=[^=]+")) {
            throw new Error("String contains an invalid character");
        }
        base64 = base64.replaceAll("=", "");
        Integer n = base64.length() & 3;
        if (n == 1) {
            throw new Error("String contains an invalid character");
        }
        List<String> base64Split = Arrays.asList(base64.split(""));
        int len = base64.length() / 4;
        int[] bin = new int[len * 3];
        for (Integer i = 0, j = 0; i < len; ++i) {
            int a = this.tableStr.indexOf(base64Split.size() < j ? base64Split.get(j++) : "A");
            int b = this.tableStr.indexOf(base64Split.size() < j ? base64Split.get(j++) : "A");
            int c = this.tableStr.indexOf(base64Split.size() < j ? base64Split.get(j++) : "A");
            int d = this.tableStr.indexOf(base64Split.size() < j ? base64Split.get(j++) : "A");
            if ((a | b | c | d) < 0) {
                throw new Error("String contains an invalid character");
            }
            bin[bin.length] = ((a << 2) | (b >> 4)) & 255;
            bin[bin.length] = ((b << 4) | (c >> 2)) & 255;
            bin[bin.length] = ((c << 6) | d) & 255;
        }
        return String.valueOf(NumberUtil.intArrayToCharArray(bin)).substring(0, bin.length + n - 4);
    }

    public String btoa(String bin) {
        int len = bin.length() / 3;
        String[] base64 = new String[len];
        for (Integer i = 0, j = 0; i < len; ++i) {
            char a,b,c;
            try {
                a = bin.charAt(j++);
            } catch (StringIndexOutOfBoundsException e) {
                a = (char) -1;
            }
            try {
                b = bin.charAt(j++);
            } catch (StringIndexOutOfBoundsException e) {
                b = (char) -1;
            }
            try {
                c = bin.charAt(j++);
            } catch (StringIndexOutOfBoundsException e) {
                c = (char) -1;
            }
            if ((a | b | c) > 255) {
                throw new Error("String contains an invalid character");
            }
            base64[i] =
                table[a >> 2] +
                    table[((a << 4) & 63) | (b >> 4)] +
                    (b == 0xFFFF ? "=" : table[((b << 2) & 63) | (c >> 6)]) +
                    (b == 0xFFFF || c == 0xFFFF ? "=" : table[c & 63]);
        }
        return Arrays.stream(base64).collect(Collectors.joining());
    }

    public String hexToBase64(String str) {
        String[] hexs = str.replaceAll("\r|\n", "").replaceAll("([\\da-fA-F]{2})", "$1 ").replaceAll("\\s+$", "").split(
            " ");
        int[] strings = new int[hexs.length];
        for (int i = 0; i < hexs.length; i++) {
            strings[i] = Integer.parseInt(hexs[i], 16);
        }
        return btoa(String.valueOf(NumberUtil.intArrayToCharArray(strings)));
    }

    public String generate() {
        String base64, hex, counterHex, timestampHex, randomHex;
        Long timestamp;
        Integer counter;
        Double random;
        timestamp = (new Date()).getTime();
        counter = IdGenerator.counter;
        IdGenerator.counter++;
        if (IdGenerator.counter > 65535) {
            IdGenerator.counter = 0;
        }
        random = Math.floor(Math.random() * 65536);
        timestampHex = NumberUtil.toHex(16, timestamp);
        counterHex = NumberUtil.toHex(4, (long)counter);
        randomHex = NumberUtil.toHex(4, random.longValue());
        hex = this.baseHex + timestampHex + counterHex + randomHex;
        base64 = this.hexToBase64(hex);
        return base64;
    }

    public static String newId() {
        IdGenerator idGenerator = new IdGenerator();
        return idGenerator.generate();
    }
}