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

    /**
     * 在给定的时间格式字符串周围添加方括号。
     * <li>入参：02:25</li>
     * <li>出参：[02:25]</li>
     *
     * @param timeFormatStr 时间格式字符串
     * @return 添加方括号后的时间格式字符串
     */
    public static String fillSquareBrackets(String timeFormatStr) {
        return String.format("[%s]", timeFormatStr);
    }

    /**
     * 格式化秒
     *
     * @param second 180s
     * @return 03:00
     */
    public static String getSecondFormat(int second) {
        return getSecondFormat(Long.parseLong(second + StringUtil.EMPTY));
    }

    /**
     * 格式化秒
     *
     * @param second 180s
     * @return 03:00
     */
    public static String getSecondFormat(long second) {
        return getSecondFormat(second, false);
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

    /**
     * 根据传入的时间获取秒的格式化字符串
     *
     * @param time               时间值秒
     * @param fullFormat         是否显示完整格式
     * @param fillSquareBrackets 是否添加方括号
     * @return 格式化后的字符串结果
     */
    public static String getSecondFormat(long time, boolean fullFormat, boolean fillSquareBrackets) {
        String timeFormatStr;
        long dayCount = 0;
        if (time > ONE_DAY_SECOND) {
            // 抛出运行时异常，表示时间超过一天的最大秒数
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

    /**
     * 拼接结果
     *
     * @param dayCount           天数
     * @param timeFormatStr      时间格式字符串
     * @param fillSquareBrackets 是否填充方括号
     * @return 拼接后的结果字符串
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
     * 对给定的长整型时间值进行补零处理，使其总位数等于10位。<br>
     * 如果时间值小于10，则在时间值前补零；<br>
     * 如果时间值大于等于10，则在时间值后添加一个空字符串。<br>
     *
     * @param time 需要进行补零处理的时间值
     * @return 补零处理后的字符串
     */
    private static String zeroPadding(long time) {
        if (time < KuugaConstants.TEN) {
            return "0" + time;
        }
        return time + StringUtil.EMPTY;
    }

    private static boolean isHour(long time) {
        // 判断给定的时间是否大于等于一小时
        return time >= ONE_HOUR_SECOND;
    }

    private static boolean isMinute(long time) {
        // 判断给定的时间是否在一小时和一分钟之间
        return ONE_HOUR_SECOND > time && time >= ONE_MINUTE_SECOND;
    }

    private static boolean isSecond(long time) {
        // 判断给定的时间是否小于一分钟
        return time < ONE_MINUTE_SECOND;
    }

}