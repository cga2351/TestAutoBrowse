package com.example.test_auto_browse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2017/9/1
 * description :
 */

public class DateUtil {
    public static final String DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_HYPHEN = "yyyy-MM-dd HH:mm:ss";//2017-7-10 11:41:22
    public static final String DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE = "yyyy_MM_dd_HH_mm_ss";//2017_7_10_11_41_22

    public static String getFormatDate(String pattern, long timeStamp) {
        String formatData = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date(timeStamp);
        formatData = simpleDateFormat.format(date);
        return formatData;
    }

    public static long parseToGMT0000(String pattern, String formatDate) {
        long timeStamp = 0;
        TimeZone timeZone = TimeZone.getTimeZone("GMT00:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(timeZone);
        try {
            timeStamp = simpleDateFormat.parse(formatDate).getTime();
        } catch (ParseException e) {
            Logger.debug("-mqmsdebug", "parseToGMT0000(), formatDate error");
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static long parseToLocal(String pattern, String formatDate) {
        long timeStamp = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(formatDate);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            Logger.debug("-mqmsdebug", "parseToLocal(), formatDate error");
            e.printStackTrace();
        }
        return timeStamp;
    }
}
