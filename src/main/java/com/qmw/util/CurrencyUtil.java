package com.qmw.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 货币相关工具类
 * 2020-08-14
 *
 * @author qmw
 * @since 1.03
 */
public class CurrencyUtil {

    // 汉语中的数字大写
    private static final char[] CN_UPPER_NUMBER = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};

    // 汉语中的货币单位大写，类似于占位符
    private static final char[] CN_UPPER_CURRENCY_UNIT = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '兆', '拾', '佰', '仟'};

    // 金额精度，默认为2
    private static final int CURRENCY_PRECISION = 2;

    /**
     * 将数字转换为中文大写货币
     *
     * @param currency 货币金额
     * @return 中文大写货币金额
     */
    public static String convertToChineseUppercase(Number currency) {
        long number = new BigDecimal(currency.toString()).movePointRight(CURRENCY_PRECISION).setScale(0, RoundingMode.HALF_UP).longValue();
        // 零元整的情况
        if (number == 0)
            return "零元整";
        boolean negative = number < 0; // 判断前面是否需要加“负”
        boolean full = number % 10 == 0; // 没有精确到分都需要加“整”
        number = Math.abs(number);

        int unitIndex = 0;
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            builder.insert(0, CN_UPPER_CURRENCY_UNIT[unitIndex++]);
            builder.insert(0, CN_UPPER_NUMBER[(int) (number % 10)]);
            number = number / 10;
        }
        if (negative)
            builder.insert(0, "负");
        if (full)
            builder.append("整");

        return builder.toString()
                .replaceAll("零[仟佰拾]", "零")
                .replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿")
                .replaceAll("亿万", "亿零")
                .replaceAll("零+", "零")
                .replaceAll("零元", "元")
                .replaceAll("零[角分]", "");
    }

}
