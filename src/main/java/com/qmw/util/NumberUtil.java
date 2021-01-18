package com.qmw.util;

/**
 * 数字相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class NumberUtil {

    /**
     * 判断是否数字
     *
     * @param object object
     * @return boolean
     */
    public static boolean isNumber(Object object) {
        try {
            Double.parseDouble(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为整数
     *
     * @param object object
     * @return boolean
     */
    public static boolean isInteger(Object object) {
        try {
            Integer.parseInt(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为长整数
     *
     * @param object object
     * @return boolean
     */
    public static boolean isLong(Object object) {
        try {
            Long.parseLong(object.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将字符串转换未纯数字（剔除非数字的部分）
     *
     * @param string string
     * @return String
     */
    public static String convertToPureNumber(String string) {
        if (StringUtil.isEmpty(string))
            return "";
        StringBuilder builder = new StringBuilder();
        for (char c : string.trim().toCharArray())
            if (Character.isDigit(c))
                builder.append(c);
        return builder.toString();
    }

}
