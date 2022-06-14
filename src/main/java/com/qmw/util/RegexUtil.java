package com.qmw.util;

import java.util.regex.Pattern;

/**
 * 正则表达式相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class RegexUtil {

    public static boolean isMobile(String string) {
        return Pattern.compile("^1\\d{10}$").matcher(string).matches();
    }

}
