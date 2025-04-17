package com.lin.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类
 * @since 2022-09-26 10:44
 */
public class DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式化
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    /**
     * 日期时间格式化
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 00:00:00
     */
    public static final String BEGIN_DATE_TIME = " 00:00:00";

    /**
     * 23:59:59
     */
    public static final String END_DATE_TIME = " 23:59:59";

    /**
     * 获取一天的开始时间 00:00:00
     *
     * @param localDate 时间
     * @return 开始时间 00:00:00
     */
    public static LocalDateTime getDateStartTime(LocalDate localDate) {
        String dateStartTimeStr = getDateStartTimeStr(localDate);
        if (StringUtils.isBlank(dateStartTimeStr)) {
            return null;
        }
        return parseDateTime(dateStartTimeStr);
    }

    /**
     * 获取一天的开始时间 00:00:00
     *
     * @param localDate 时间
     * @return 开始时间 00:00:00
     */
    public static String getDateStartTimeStr(LocalDate localDate) {
        if (localDate == null) {
            return StringUtils.EMPTY;
        }
        return localDate + BEGIN_DATE_TIME;
    }

    /**
     * 获取一天的结束时间 23:59:59
     *
     * @param localDate 时间
     * @return 结束时间 23:59:59
     */
    public static LocalDateTime getDateEndTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return parseDateTime(localDate + END_DATE_TIME);
    }

    /**
     * 获取一天的结束时间 23:59:59
     *
     * @param localDate 时间
     * @return 结束时间 23:59:59
     */
    public static String getDateEndTimeStr(LocalDate localDate) {
        if (localDate == null) {
            return StringUtils.EMPTY;
        }
        return localDate + END_DATE_TIME;
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取时间字符串, 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getDateTime(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return StringUtils.EMPTY;
        }
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前年份
     * @return 当前年份
     */
    public static Integer getCurrentYear() {
        return LocalDate.now().getYear();
    }


    /**
     * LocalDateTimeStr to LocalDateTime <br/>
     * LocalDateTimeStr 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static LocalDateTime parseDateTime(String localDateTimeStr) {
        if (StringUtils.isBlank(localDateTimeStr)) {
            return LocalDateTime.now();
        }
        if (localDateTimeStr.length() == 10) {
            return LocalDate.parse(localDateTimeStr).atStartOfDay();
        }
        return LocalDateTime.parse(localDateTimeStr, DATE_TIME_FORMATTER);
    }


    /**
     * LocalDateStr to LocalDate <br/>
     * LocalDateTimeStr 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static LocalDate parseLocalDate(String localDateStr) {
        if (StringUtils.isBlank(localDateStr)) {
            return LocalDate.now();
        }
        if (localDateStr.length() == 10) {
            return LocalDate.parse(localDateStr);
        }
        return LocalDate.parse(localDateStr, DATE_FORMATTER);
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 当前时间标识 即时分秒 如 010000
     */
    public static String timeFlag() {
        Date now = new Date();
        return DateFormatUtils.format(now, "HHmmss");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 日期格式化
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
