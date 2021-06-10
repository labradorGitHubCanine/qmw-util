package com.qmw.util;

import com.qmw.exception.CustomException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    /**
     * 获取同比区间
     *
     * @param start 起始日期
     * @param end   截止日期
     * @return 同比区间
     */
    public static List<YearMonth> getCompareYearRange(YearMonth start, YearMonth end) {
        List<YearMonth> list = new ArrayList<>(Arrays.asList(start.minusYears(1), end.minusYears(1)));
        Collections.sort(list);
        return list;
    }

    /**
     * 获取环比区间
     *
     * @param start 日期起始
     * @param end   日期截止
     * @return 环比区间
     */
    public static List<YearMonth> getCompareMonthRange(YearMonth start, YearMonth end) {
        long diff = Math.abs(start.until(end, ChronoUnit.MONTHS));
        if (end.isBefore(start))
            start = end;
        return new ArrayList<>(Arrays.asList(start.minusMonths(1 + diff), start.minusMonths(1)));
    }

    /**
     * 生成一个最近count天的日期列表，正序排列
     *
     * @param timePoint 时间点
     * @param count     最近几天，负数过去，正数未来
     * @return 列表
     */
    public static List<String> latestDays(long timePoint, int count) {
        List<String> days = new ArrayList<>();
        java.sql.Date date = new java.sql.Date(timePoint);
        do {
            days.add(count > 0 ? days.size() : 0, date.toString());
            date.setTime(date.getTime() + 24 * 3600 * 1000 * count / Math.abs(count));
        } while (days.size() < Math.abs(count));
        return days;
    }

    public static List<String> latestDays(int count) {
        return latestDays(System.currentTimeMillis(), count);
    }

    /**
     * 生成一个最近count月的日期列表，正序排列
     *
     * @param timePoint 时间点
     * @param count     最近几月
     * @return 列表
     */
    public static List<YearMonth> latestMonths(YearMonth timePoint, int count) {
        YearMonth yearMonth = timePoint;
        List<YearMonth> months = new ArrayList<>();
        while (months.size() < Math.abs(count)) {
            months.add(yearMonth);
            yearMonth = yearMonth.plusMonths(count / Math.abs(count));
        }
        Collections.sort(months);
        return months;
    }

    public static List<YearMonth> latestMonths(int count) {
        return latestMonths(YearMonth.now(), count);
    }

}
