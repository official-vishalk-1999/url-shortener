package com.vishalk.urlshortener.util;

public class Base62 {

    private static final String CHARS =
        "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) { sb.append(CHARS.charAt((int) (num % 62))); num /= 62; }
        return sb.reverse().toString();
    }

    public static long decode(String code) {
        long num = 0;
        for (char c : code.toCharArray()) num = num * 62 + CHARS.indexOf(c);
        return num;
    }
}
