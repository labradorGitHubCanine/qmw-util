package com.qmw.util;

import com.qmw.exception.CustomException;

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
        return object == null ||
                object.toString().trim().isEmpty() ||
                object.toString().trim().equalsIgnoreCase("null") ||
                object.toString().trim().equalsIgnoreCase("undefined");
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
            if (i != 0 && Character.isUpperCase(c)) // 首字母不加下划线
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
            throw new CustomException("scale必须大于0");
        return new StringBuilder()
                .append(repeat("0", scale - 1))
                .append(new Random().nextInt((int) Math.pow(10, scale)))
                .reverse().substring(0, scale);
    }

    /**
     * 生成一个scale位的随机字母串
     *
     * @param scale 几位
     * @return string
     */
    public static String randomLetter(int scale) {
        if (scale <= 0)
            throw new CustomException("scale必须大于0");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < scale; i++) {
            if (random.nextBoolean())
                sb.append((char) (random.nextInt('z' - 'a' + 1) + 'a'));
            else
                sb.append((char) (random.nextInt('Z' - 'A' + 1) + 'A'));
        }
        return sb.toString();
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
