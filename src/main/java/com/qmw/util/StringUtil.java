package com.qmw.util;

public class StringUtil {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static boolean isEmpty(Object o) {
        return o == null || o.toString().trim().isEmpty();
    }

}
