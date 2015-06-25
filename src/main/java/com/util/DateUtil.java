package com.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kelong on 9/2/14.
 */
public class DateUtil {

    private final static Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 时间转换为字符串
     *
     * @param format ：格式方式
     * @param date   ：时间
     * @return
     */
    public static String fomartDateToStr(String format, Date date) {
        String dateString = null;
        try {
            if (null != date) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                dateString = formatter.format(date);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return dateString;
    }

    /**
     * time str covert to date
     *
     * @param format
     * @param dateString
     * @return
     */
    public static Date fomartStrToDate(String format, String dateString) {
        Date date = null;
        try {
            if (!StringUtils.isEmpty(dateString)) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                date = formatter.parse(dateString);
            }
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return date;
    }

    /**
     * @param days
     * @return
     */
    public static String getBeforeDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -days);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
    }

    /**
     * @param hours
     * @return
     */
    public static String getBeforeHours(int hours) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, -hours);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
    }

    /**
     * 从昨天开始推算前hours
     *
     * @param hours
     * @return
     */
    public static String getBeforeHoursFromYeserday(int hours) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.HOUR, -hours);
        return fomartDateToStr("yyyy-MM-dd HH:mm:ss", c.getTime());
    }

    /**
     * 从周1开始推算前几天(上周时间)
     *
     * @param days
     * @return
     */
    public static String getBeforeDaysFromLastWeek(int days) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.DATE, -days);
        return fomartDateToStr("yyyy-MM-dd HH:mm:ss", c.getTime());
    }

    /**
     * 从上月开始向前推算月份
     *
     * @param months
     * @return
     */
    public static String getBeforeMonthsFromLastMonth(int months) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MONTH, -months);
        return fomartDateToStr("yyyy-MM-dd HH:mm:ss", c.getTime());
    }


    /**
     * 往前推前几天[mongodb]
     *
     * @return
     */
    public static Date getLastDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.add(Calendar.DATE, -days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 往前推几天
     *
     * @param days
     * @return
     */
    public static String getDateByDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return DateUtil.fomartDateToStr("yyyyMMdd", c.getTime());
    }


    public static long getBewteenTimesByDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, +1);
        c.set(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date date1 = c.getTime();
        Date date = new Date();
        return (date1.getTime() - date.getTime()) / 1000;
    }

    /**
     * 时间间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getBewteenTimes(Date date1, Date date2) {
        long times = date1.getTime() - date2.getTime();
        return times / 1000;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getBeforeMonthsFromLastMonth(3));//2015-01-19 00:00:00,2015-01-19 03:00:00
        System.out.println(DateUtil.getBeforeMonthsFromLastMonth(2));
        System.out.println(getBewteenTimesByDay());
        System.out.println(getLastDays(0));
        System.out.println(getDateByDays(0));
    }

}
