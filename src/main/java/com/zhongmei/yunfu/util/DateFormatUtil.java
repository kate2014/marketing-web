package com.zhongmei.yunfu.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static final String FORMAT_FULL_time = "HH:mm:ss";

    public static final String FORMAT_FULL_DATE = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_MYSQL_TIMESTAMP ="yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DATE_STRING = "yyyyMMddHHmmss";
    /**
     * 格式化为指定支付穿
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDate(Date date) {
        return format(date, FORMAT_DATE);
    }

    /**
     * 解析为日期
     *
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static Date parseDate(String date, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * 解析为时间戳
     *
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static Long parseLong(String date, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date).getTime();
    }

    //获取当天起始时间
    public static Date getStartTime() {
        Calendar todayStart = getCalendarOfDay();
        return todayStart.getTime();
    }

    public static Calendar getCalendarOfDay() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart;
    }
}
