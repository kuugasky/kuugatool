package io.github.kuugasky.kuugatool.core.date;


import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

/**
 * 秒格式化
 *
 * @author kuuga
 */
public final class SecondsFormatUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SecondsFormatUtil() {
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

    /**
     * 填充方括号
     *
     * @param timeFormatStr 时间格式Str
     * @return 带方括号带时间格式Str
     */
    public static String fillSquareBrackets(String timeFormatStr) {
        return String.format("[%s]", timeFormatStr);
    }

    /**
     * 格式化秒
     *
     * @param seconds 180s
     * @return 03:00
     */
    public static String format(int seconds) {
        return format(Long.parseLong(String.valueOf(seconds)));
    }

    /**
     * 格式化秒
     *
     * @param seconds 180s
     * @return 03:00
     */
    public static String format(long seconds) {
        return format(seconds, false);
    }

    /**
     * 格式化秒
     *
     * @param seconds    18000s
     * @param fullFormat 是否要完整格式
     * @return 非完整格式：03:00 完整格式：00:03:00
     */
    public static String format(long seconds, boolean fullFormat) {
        return format(seconds, fullFormat, false);
    }

    /**
     * 格式化秒
     *
     * @param seconds            秒
     * @param fullFormat         是否使用完整格式，不满1小时补充0小时
     * @param fillSquareBrackets 是否使用中括号
     * @return 格式化后的时间，详细格式见单测
     */
    public static String format(long seconds, boolean fullFormat, boolean fillSquareBrackets) {
        String timeFormatStr;
        long dayCount = 0;

        // 秒数大于1天
        if (seconds > ONE_DAY_SECOND) {
            dayCount = (seconds / ONE_DAY_SECOND);
            seconds = seconds - (dayCount * ONE_DAY_SECOND);
        }
        // 秒数小于等于0
        if (seconds <= 0) {
            timeFormatStr = fullFormat ? ZERO_SECOND_FULL_FORMAT : ZERO_SECOND;
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        // 秒数小于1分钟
        if (isLessThanOneMinute(seconds)) {
            timeFormatStr = fullFormat ?
                    String.format(FORMAT_MINUTE_SECOND_FULL_FORMAT, "00", "00", zeroPadding(seconds)) :
                    String.format(FORMAT_MINUTE_SECOND, "00", zeroPadding(seconds));
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        // 秒数大于1分钟且小于1小时
        if (isBetweenOneMinuteToOneHour(seconds)) {
            long minute = seconds / ONE_MINUTE_SECOND;
            long second = seconds % ONE_MINUTE_SECOND;
            timeFormatStr = fullFormat ?
                    String.format(FORMAT_MINUTE_SECOND_FULL_FORMAT, "00", zeroPadding(minute), zeroPadding(second)) :
                    String.format(FORMAT_MINUTE_SECOND, zeroPadding(minute), zeroPadding(second));
            return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
        }
        // 秒数大于1小时
        if (isMoreThanOneHour(seconds)) {
            long hour = seconds / ONE_HOUR_SECOND;
            long leftSecond = seconds % ONE_HOUR_SECOND;
            if (isBetweenOneMinuteToOneHour(leftSecond)) {
                long minute = leftSecond / ONE_MINUTE_SECOND;
                long second = leftSecond % ONE_MINUTE_SECOND;
                timeFormatStr = String.format(FORMAT_HOUR_MINUTE_SECOND, zeroPadding(hour), zeroPadding(minute), zeroPadding(second));
                return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
            }
            // 秒数小于1分钟
            if (isLessThanOneMinute(leftSecond)) {
                timeFormatStr = String.format(FORMAT_HOUR_MINUTE_SECOND, zeroPadding(hour), "00", zeroPadding(seconds % ONE_HOUR_SECOND));
                return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
            }
        }
        timeFormatStr = ZERO_SECOND;
        return assemblyResults(dayCount, timeFormatStr, fillSquareBrackets);
    }

    /**
     * 组装结果
     *
     * @param dayCount           超过天数
     * @param timeFormatStr      时间格式
     * @param fillSquareBrackets 是否填充方括号
     * @return 时间格式化组装结果
     */
    private static String assemblyResults(long dayCount, String timeFormatStr, boolean fillSquareBrackets) {
        if (dayCount > 0) {
            timeFormatStr = String.format("%sd %s", dayCount, timeFormatStr);
        }
        if (fillSquareBrackets) {
            return fillSquareBrackets(timeFormatStr);
        }
        return timeFormatStr;
    }

    /**
     * 秒零补
     *
     * @param seconds 秒
     * @return second小于0时，前置补0
     */
    private static String zeroPadding(long seconds) {
        if (seconds < KuugaConstants.TEN) {
            return String.format("0%s", seconds);
        }
        return String.valueOf(seconds);
    }

    /**
     * 是否大于1小时
     *
     * @param seconds 秒
     * @return true：是
     */
    private static boolean isMoreThanOneHour(long seconds) {
        return seconds >= ONE_HOUR_SECOND;
    }

    /**
     * 是否大于等于1分钟且小于1小时
     *
     * @param seconds 秒
     * @return true：是
     */
    private static boolean isBetweenOneMinuteToOneHour(long seconds) {
        return ONE_HOUR_SECOND > seconds && seconds >= ONE_MINUTE_SECOND;
    }

    /**
     * seconds是否小于1分钟
     *
     * @param seconds 秒
     * @return true：是
     */
    private static boolean isLessThanOneMinute(long seconds) {
        return seconds < ONE_MINUTE_SECOND;
    }

}