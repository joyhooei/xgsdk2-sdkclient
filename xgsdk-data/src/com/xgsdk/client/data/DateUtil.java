
package com.xgsdk.client.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yinlong
 */
public class DateUtil {

    /** 毫秒 */
    public final static long MS = 1L;
    /** 每秒钟的毫秒数 */
    public final static long SECOND_MS = MS * 1000L;
    /** 每分钟的毫秒数 */
    public final static long MINUTE_MS = SECOND_MS * 60L;
    /** 每小时的毫秒数 */
    public final static long HOUR_MS = MINUTE_MS * 60L;
    /** 每天的毫秒数 */
    public final static long DAY_MS = HOUR_MS * 24L;

    /** 标准日期时间格式，精确到毫秒 */
    public final static String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    /** HTTP头中日期时间格式 */
    public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    /** 西瓜数据服务器上报 2015-04-07 15:52:30.250+08 */
    public final static String XG_DATA_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSX";

    private final static ThreadLocal<SimpleDateFormat> XG_DATA_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(XG_DATA_PATTERN);
        }
    };

    public static String formatDataTime(long ts) {
        return formatDataTime(new Date(ts));
    }

    public static String formatDataTime(Date date) {
        return XG_DATA_FORMAT.get().format(date);
    }

    public static String nowDataTime() {
        return formatDataTime(new Date());
    }

    public static long nowTs() {
        return System.currentTimeMillis();
    }

}
