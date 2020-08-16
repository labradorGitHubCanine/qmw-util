package com.qmw.util;

/**
 * 数字相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class NumberUtil {

    public static boolean isNumber(Object object) {
        try {
            Double.parseDouble(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
