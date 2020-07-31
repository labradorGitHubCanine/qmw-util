package com.qmw.util;

import java.util.Base64;

/**
 * 转码工具类
 *
 * @author qmw
 * @since 2020-06-18
 */
public class EncodeUtil {

    public static String encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static String decode(String s) {
        return new String(Base64.getDecoder().decode(s));
    }

}
