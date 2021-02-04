package com.maintenance.common.utils;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM= "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 格式化时间
     *  - 使用默认时区，服务部署在国内所以默认时区为:GMT+8
     * @param date
     * @param formatStr 例：yyyy-MM-dd HH:mm:ss:SSS
     * @return
     */
    public static String formatDate(Date date, String formatStr) {
    	SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    	return sdf.format(date);
    }

    /**
     * 格式化时间
     *  - 转换为指定时区的区时
     *  - 计算的区时=已知区时-（已知区时的时区-要计算区时的时区），所以不同时区的区时中yyyy-MM-dd和mm:ss:SSS部分是一样的，只有HH部分不一样
     * @param date
     * @param formatStr 例：yyyy-MM-dd HH:mm:ss:SSS
     * @param timeZone 时区
     * @return
     */
    public static String formatDate(Date date, String formatStr, TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
        //或者
        //long time = date.getTime()-TimeZone.getDefault().getRawOffset()+timeZone.getRawOffset();
        //return sdf.format(new Date(time));
    }

    /**
     * 字符串转Date
     * @param dateStr 满足formatStr格式的时间字符串
     * @param formatStr 例：yyyy-MM-dd HH:mm:ss:SSS
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转Date
     * @param dateStr 满足formatStr格式的时间字符串
     * @param formatStr 例：yyyy-MM-dd HH:mm:ss:SSS
     * @param timeZone 时区
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr, TimeZone timeZone){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            sdf.setTimeZone(timeZone);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 时间转换：Date->字符串，相对于formatDate函数更通俗的函数名
     * @param date
     * @return
     */
    public static String dateToString(Date date){
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }
    public static String dateToString(Date date, String formatStr){
        return formatDate(date, formatStr);
    }
    public static String dateToString(Date date, TimeZone timeZone){
        return formatDate(date, YYYY_MM_DD_HH_MM_SS, timeZone);
    }
    public static String dateToString(Date date, String formatStr, TimeZone timeZone){
        return formatDate(date, formatStr, timeZone);
    }

    /**
     * 时间转换：字符串->Date，相对于parseDate函数更通俗的函数名
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr){
        return parseDate(dateStr, YYYY_MM_DD_HH_MM_SS);
    }
    public static Date stringToDate(String dateStr, String formatStr){
        return parseDate(dateStr, formatStr);
    }
    public static Date stringToDate(String dateStr, TimeZone timeZone){
        return parseDate(dateStr, YYYY_MM_DD_HH_MM_SS, timeZone);
    }
    public static Date stringToDate(String dateStr, String formatStr, TimeZone timeZone){
        return parseDate(dateStr, formatStr, timeZone);
    }

    /**
     * 时间转换：字符串->字符串
     * @param dateStr
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String stringToString(String dateStr, String fromFormat, String toFormat){
        return dateToString(stringToDate(dateStr, fromFormat), toFormat);
    }

    /**
     * 时间转换：字符串->毫秒数
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static long stringToMillis(String dateStr, String formatStr) {
        if (!Strings.isNullOrEmpty(dateStr)) {
            return -1;
        }
        if (!Strings.isNullOrEmpty(formatStr)) {
            formatStr = YYYY_MM_DD_HH_MM_SS;
        }
        Date date = stringToDate(dateStr, formatStr);
        return date == null ? -1 : date.getTime();
    }

    /**
     * 获取当前时间对应的标准字符串格式
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(YYYY_MM_DD_HH_MM_SS);
    }
    public static String getCurrentDateTime(String dateFormat) {
        return formatDate(new Date(), dateFormat);
    }

    /**
     * 时间偏移
     * @param date
     * @param cnt 正数向后移，负数向前移
     * @return
     */
    public static Date addSeconds(Date date, int cnt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, cnt);
        return calendar.getTime();
    }
    public static Date addHours(Date date, int cnt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, cnt);
        return calendar.getTime();
    }
    public static Date addDays(Date date, int cnt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, cnt);
        return calendar.getTime();
    }
    public static Date addMonths(Date date, int cnt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, cnt);
        return calendar.getTime();
    }
    public static Date addYears(Date date, int cnt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, cnt);
        return calendar.getTime();
    }

    /**
     * 判断一个时间是否在一个时间段内 </br>
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
       boolean beginFlag=true;
       boolean endFlag =true;
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        if(beginTime!=null){
            Calendar begin = Calendar.getInstance();
            begin.setTime(beginTime);
            beginFlag =date.after(begin);
        }
        if(endTime!=null){
            Calendar end = Calendar.getInstance();
            end.setTime(endTime);
            endFlag=date.before(end);
        }
        return beginFlag && endFlag;
    }

    /**
     * 获取2个时间间隔的秒数
     * @param dateStr1 yyyy-MM-dd HH:mm:ss
     * @param dateStr2 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getDiffSeconds(String dateStr1, String dateStr2){
        Date date1 = stringToDate(dateStr1);
        Date date2 = stringToDate(dateStr2);
        return getDiffSeconds(date1, date2);
    }
    public static int getDiffSeconds(Date date1, Date date2){
        try {
            long t1 = date1.getTime();
            long t2 = date2.getTime();
            return (int) Math.abs(Math.ceil(((t2 - t1) / 1000) / (double) 60));
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * 获取2个时间间隔的毫秒数
     * @param dateStr1 yyyy-MM-dd HH:mm:ss
     * @param dateStr2 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getDiffMilliSeconds(String dateStr1, String dateStr2){
        Date date1 = stringToDate(dateStr1);
        Date date2 = stringToDate(dateStr2);
        return getDiffMilliSeconds(date1, date2);
    }
    public static long getDiffMilliSeconds(Date date1, Date date2){
        try {
            long t1 = date1.getTime();
            long t2 = date2.getTime();
            return Math.abs(t2 - t1);
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * 获取2个时间间隔的月数
     * @param dateStr1 yyyy-MM-dd
     * @param dateStr2 yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static int getDiffMonths(String dateStr1, String dateStr2) {
        Date date1 = stringToDate(dateStr1, YYYY_MM_DD);
        Date date2 = stringToDate(dateStr2, YYYY_MM_DD);
        return getDiffMonths(date1, date2);
    }
    public static int getDiffMonths(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
          yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
          monthInterval--;
        }
        monthInterval %= 12;
        return Math.abs(yearInterval * 12 + monthInterval);
   }


    /**
     * 获取明天的日期,跳过周六周日
     * @param dateStr
     * @return
     */
    public static String getTomorrow(String dateStr) {
        Date date = stringToDate(dateStr);
        if(date == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 6:
                cal.add(Calendar.DATE, 3);
                break;
            case 7:
                cal.add(Calendar.DATE, 2);
                break;
            default:
                cal.add(Calendar.DATE, 1);
                break;
        }
        return dateToString(cal.getTime());
    }

    /**
     * 查询timeStr代表的时间到当天0点的秒数
     * @param timeStr 时间，格式：HH:mm
     * @return
     */
    public static long getSecByTime(String timeStr){
        if(!Strings.isNullOrEmpty(timeStr)){
            return 0;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        long date = 0;
        try {
            date =(sdf.parse(timeStr).getTime()-sdf.parse("00:00").getTime())/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
