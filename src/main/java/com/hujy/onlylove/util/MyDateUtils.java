package com.hujy.onlylove.util;

import com.baomidou.mybatisplus.extension.api.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-08 10:17
 */
public class MyDateUtils {
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String NORMAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 获取今日日期 yyyy-MM-dd
     */
    public static String getToday() {
        return LocalDate.now().toString();
    }

    /**
     * 获取本月第一天日期 2019-11-01
     */
    public static String getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    /**
     * 获取本月最后一天日期 2019-11-30
     */
    public static String getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    public static String dateToDateStr(Date date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return dateTimeFormatter.format(localDateTime);
    }

    public static Date dateStrToDate(String dateStr, String format) {
        DateTimeFormatter dateDtf = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateStr, dateDtf);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
        return Date.from(instant);
    }

    public static Date dateTimeStrToDate(String dateStr, String format) {
        DateTimeFormatter dateDtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dateDtf);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date getPastDate(Date date, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }
    /**
     * 当前年周数:yyyy-w
     *
     * @param date
     * @return int
     * @author hujy
     * @date 2020-09-08 10:53
     */
    public static String getYearWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //设置一周的第一天是周一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        int year = cal.get(Calendar.YEAR);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        cal.add(Calendar.DAY_OF_MONTH, -7);  //上一周

        //判断是否同一年，并且本周周数小于上周周数，则在上周周数的基础上加一
        if (year == cal.get(Calendar.YEAR) && week < cal.get(Calendar.WEEK_OF_YEAR)) {
            week = cal.get(Calendar.WEEK_OF_YEAR) + 1;
        }


        return year + "-" + week;
    }

    public static String getYesterday(String dateStr) {
        Date date = dateStrToDate(dateStr, NORMAL_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return dateToDateStr(cal.getTime(), NORMAL_DATE_FORMAT);
    }

    public static int getWeekDayNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static void main(String[] args) {
        Date date = dateStrToDate("2020-12-20", NORMAL_DATE_FORMAT);

        int weekDayNo = getWeekDayNum(date);
        System.out.println(weekDayNo);

        String dateStr = dateToDateStr(date, "yyyy-M-d");
        System.out.println(dateStr);
    }
}
