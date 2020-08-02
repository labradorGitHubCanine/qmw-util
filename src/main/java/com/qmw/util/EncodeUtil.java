package com.qmw.util;

import java.util.Base64;

/**
 * 转码工具类
 * 2020-08-01
 *
 * @author qmw
 * @since 1.00
 */
public class EncodeUtil {

    public static String Base64Encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    public static String Base64Decode(String string) {
        return new String(Base64.getDecoder().decode(string));
    }

}
