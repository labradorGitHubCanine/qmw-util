package com.qmw.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期工具类
 *
 * @author qmw
 * @since 1.05
 */
public class DateUtil {

    /**
     * 判断一个字符串是否指定格式的日期
     *
     * @param date   日期字符串
     * @param format 日期格式字符串
     * @return boolean
     */
    public static boolean isTargetFormat(String date, String format) {
        try {
            new SimpleDateFormat(format).parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 将字符串转换为java.sql.Date
     *
     * @param date 字符串
     * @return java.sql.Date
     */
    public static java.sql.Date parseAsSqlDate(String date) {
        try {
            long timeMillis = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
            return new java.sql.Date(timeMillis);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误：" + date + "，正确格式应为yyyy-MM-dd");
        }
    }

}
