package com.qmw.util;

import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean isMobile(String string) {
        return Pattern.compile("^[1]\\d{10}$").matcher(string).matches();
    }

}
