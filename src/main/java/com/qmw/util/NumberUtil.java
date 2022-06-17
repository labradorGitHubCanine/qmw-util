package com.qmw.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 判断前者是否后面那个数字
    public static boolean equals(Object o, Number number) {
        if (!isNumber(o))
            return false;
        return new BigDecimal(o.toString()).doubleValue() == number.doubleValue();
    }

    /**
     * 将字符串转换未纯数字（剔除非数字的部分）
     *
     * @param string  字符串
     * @param exclude 不排除的字符串
     * @return 转换后的数字
     */
    public static String convertToPureNumber(String string, char... exclude) {
        if (StringUtil.isEmpty(string))
            return "";
        List<Character> excludeList = new ArrayList<>();
        for (char c : exclude)
            excludeList.add(c);
        StringBuilder builder = new StringBuilder();
        for (char c : string.trim().toCharArray())
            if (Character.isDigit(c) || excludeList.contains(c))
                builder.append(c);
        return builder.toString();
    }

    public static BigDecimal sumUp(Number... numbers) {
        return sumUp(Arrays.asList(numbers));
    }

    public static BigDecimal sumUp(Iterable<? extends Number> numbers) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Number number : numbers)
            sum = sum.add(new BigDecimal(number.toString()));
        return sum;
    }

    public static String zeroFill(int number, int length) {
        return String.format("%0" + length + "d", number);
    }

    public static String incrementRate(Number newVal, Number oldVal, int scale) {
        if (oldVal.doubleValue() == 0)
            return "";
        double rate = (newVal.doubleValue() / oldVal.doubleValue() - 1) * 100;
        String neg = rate < 0 ? "" : "+";
        return neg + new BigDecimal(rate).setScale(scale, RoundingMode.HALF_UP).toPlainString() + "%";
    }

    public static String incrementRate(Number newVal, Number oldVal) {
        return incrementRate(newVal, oldVal, 2);
    }

}
