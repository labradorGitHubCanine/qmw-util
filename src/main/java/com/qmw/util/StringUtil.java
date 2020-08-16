package com.qmw.util;

import java.util.Random;

/**
 * 字符串相关工具类
 *
 * @author qmw
 * @since 1.00
 */
public class StringUtil {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static boolean isEmpty(Object object) {
        return object == null || object.toString().trim().isEmpty();
    }

    public static String ifEmptyThen(Object object, String string) {
        return isEmpty(object) ? string : object.toString().trim();
    }

    public static String trim(Object object) {
        return ifEmptyThen(object, "");
    }

    /**
     * 驼峰转下划线
     *
     * @param s s
     * @return s
     */
    public static String camel2under(String s) {
        if (isEmpty(s))
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c) && i != 0) // 首字母不加下划线
                sb.append("_");
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param s s
     * @return s
     */
    public static String under2camel(String s) {
        if (isEmpty(s))
            return "";
        String[] arr = s.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String c = arr[i];
            if (!isEmpty(c)) {
                if (i == 0) // 首字母不转大写
                    sb.append(arr[i].toLowerCase());
                else
                    sb.append(Character.toUpperCase(c.charAt(0))).append(c.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 生成一个随机scale位数字
     * 不足scale位会用0补齐
     *
     * @param scale 几位
     * @return string
     */
    public static String randomNumber(int scale) {
        if (scale <= 0)
            throw new RuntimeException("scale必须大于0");
        return new StringBuilder()
                .append(repeat("0", scale - 1))
                .append(new Random().nextInt((int) Math.pow(10, scale)))
                .reverse().substring(0, scale);
    }


    /**
     * 将一个字符串重复拼接times次
     *
     * @param string string
     * @param times  次数
     * @return string
     */
    public static String repeat(String string, int times) {
        if (string == null)
            return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++)
            builder.append(string);
        return builder.toString();
    }

}
