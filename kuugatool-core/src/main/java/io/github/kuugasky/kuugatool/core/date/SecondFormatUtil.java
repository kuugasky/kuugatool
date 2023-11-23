package io.github.kuugasky.kuugatool.core.date;


import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

/**
 * 秒格式化
 *
 * @author kuuga
 */
public final class SecondFormatUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SecondFormatUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final long ONE_DAY_SECOND = 86400L;
    private static final long ONE_HOUR_SECOND = 3600L;
    private static final long ONE_MINUTE_SECOND = 60L;

    private static final String FORMAT_HOUR_MINUTE_SECOND = "%s:%s:%s";
    private static final String FORMAT_MINUTE_SECOND = "%s:%s";
    private static final String FORMAT_MINUTE_SECOND_FULL_FORMAT = "%s:%s:%s";
    private static final String ZERO_SECOND = "00:00";
    private static final String ZERO_SECOND_FULL_FORMAT = "00:00:00";

    public static String fillSquareBrackets(String timeFormatStr) {
        return String.format("[%s]", timeFormatStr);
    }

    /**
     * 格式化秒
     *
     * @param time 180s
     * @return 03:00
     */
    public static String getSecondFormat(int time) {
        return getSecondFormat(Long.parseLong(time + StringUtil.EMPTY));
    }

    /**
     * 格式化秒
     *
     * @param time 180s
     * @return 03:00
     */
    public static String getSecondFormat(long time) {
        return getSecondFormat(time, false);
    }

    /**
     * 格式化秒
     *
     * @param time       18000s
     * @param fullFormat 是否要完整格式
     * @return 非完整格式：03:00 完整格式：00:03:00
     */
    public static String getSecondFormat(long time, boolean fullFormat) {
        return getSecondFormat(time, fullFormat, false);
    }

    public static String getSecondFormat(long time, boolean fullFormat, boolean fillSquareBrackets) {
        String timeFormatStr;
        long dayCount = 0;
        if (time > ONE_DAY_SECOND) {
            // throw new RuntimeException("超过一天最大秒数");
            dayCount = (time / ONE_DAY_SECOND);
            time = time - (dayCount * ONE_DAY_SECOND);
        }
        if (time < 0) {
            timeFormatStr = fullFormat ? ZERO_SECOND_FULL_FORMAT : ZERO_SECOND;
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        if (isSecond(time)) {
            timeFormatStr = fullFormat ?
                    String.format(FORMAT_MINUTE_SECOND_FULL_FORMAT, "00", "00", zeroPadding(time)) :
                    String.format(FORMAT_MINUTE_SECOND, "00", zeroPadding(time));
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        if (isMinute(time)) {
            long minute = time / ONE_MINUTE_SECOND;
            long second = time % ONE_MINUTE_SECOND;
            timeFormatStr = fullFormat ?
                    String.format(FORMAT_MINUTE_SECOND_FULL_FORMAT, "00", zeroPadding(minute), zeroPadding(second)) :
                    String.format(FORMAT_MINUTE_SECOND, zeroPadding(minute), zeroPadding(second));
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        if (isHour(time)) {
            long hour = time / ONE_HOUR_SECOND;
            long leftSecond = time % ONE_HOUR_SECOND;
            if (isMinute(leftSecond)) {
                long minute = leftSecond / ONE_MINUTE_SECOND;
                long second = leftSecond % ONE_MINUTE_SECOND;
                timeFormatStr = String.format(FORMAT_HOUR_MINUTE_SECOND, zeroPadding(hour), zeroPadding(minute), zeroPadding(second));
                return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
            }
            if (isSecond(leftSecond)) {
                timeFormatStr = String.format(FORMAT_HOUR_MINUTE_SECOND, zeroPadding(hour), "00", zeroPadding(time % ONE_HOUR_SECOND));
                return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
            }
        }
        timeFormatStr = ZERO_SECOND;
        return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
    }

    private static String assemblyResults(long dayCount, String timeFormatStr, boolean fillSquareBrackets) {
        if (dayCount > 0) {
            timeFormatStr = String.format("%sd %s", dayCount, timeFormatStr);
        }
        if (fillSquareBrackets) {
            return fillSquareBrackets(timeFormatStr);
        }
        return timeFormatStr;
    }

    private static String zeroPadding(long time) {
        if (time < KuugaConstants.TEN) {
            return "0" + time;
        }
        return time + StringUtil.EMPTY;
    }

    private static boolean isHour(long time) {
        return time >= ONE_HOUR_SECOND;
    }

    private static boolean isMinute(long time) {
        return ONE_HOUR_SECOND > time && time >= ONE_MINUTE_SECOND;
    }

    private static boolean isSecond(long time) {
        return time < ONE_MINUTE_SECOND;
    }

}