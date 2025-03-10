package com.vjuliano.asciiArt.util;

public class Assert {

    public static void isTrue(boolean cond, String errorMsg) {
        if (!cond) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void isNotNull(Object obj, String errorMsg) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
