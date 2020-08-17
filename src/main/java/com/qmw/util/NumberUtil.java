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
     * 将字符串转换未纯数字（剔除非数字的部分）
     *
     * @param string string
     * @return String
     */
    public static String convertToPureNumber(String string) {
        if (StringUtil.isEmpty(string))
            return "";
        StringBuilder builder = new StringBuilder();
        for (char c : string.toCharArray())
            if (Character.isDigit(c))
                builder.append(c);
        return builder.toString();
    }

}
