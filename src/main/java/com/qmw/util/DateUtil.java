package com.qmw.util;

import com.qmw.exception.CustomException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
            throw new CustomException("日期格式错误：" + date + "，正确格式应为yyyy-MM-dd");
        }
    }

    public static List<String> getCompareYearRange(String start, String end, String format) {
        try {
            List<String> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date1 = sdf.parse(start), date2 = sdf.parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.YEAR, -1);
            list.add(sdf.format(calendar.getTime()));

            calendar.setTime(date2);
            calendar.add(Calendar.YEAR, -1);
            list.add(sdf.format(calendar.getTime()));
            return list;

        } catch (ParseException e) {
            throw new CustomException("日期解析失败");
        }
    }

    public static List<String> getCompareMonthRange(String start, String end, String format) {
        try {
            List<String> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date1 = sdf.parse(start), date2 = sdf.parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, -1);
            list.add(sdf.format(calendar.getTime()));

            calendar.setTimeInMillis(calendar.getTimeInMillis() - (date2.getTime() - date1.getTime()));
            list.add(0, sdf.format(calendar.getTime()));
            return list;
        } catch (ParseException e) {
            throw new CustomException("日期解析失败");
        }
    }

}
