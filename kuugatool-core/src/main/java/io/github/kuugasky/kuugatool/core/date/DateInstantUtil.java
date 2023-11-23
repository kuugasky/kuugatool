package io.github.kuugasky.kuugatool.core.date;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * DateInstantUtil
 * <p>
 * Instant:高精度时间戳,可以和ZonedDateTime以及long互相转换
 *
 * @author kuuga
 * @since 2022/7/11 17:55
 */
public final class DateInstantUtil {

    /**
     * 检查指定瞬间是否在其他瞬间之前。
     *
     * @param instant      指定瞬间
     * @param otherInstant 其他瞬间
     * @return boolean
     */
    public static boolean isBefore(Instant instant, Instant otherInstant) {
        return instant.isBefore(otherInstant);
    }

    /**
     * 检查指定瞬间是否在其他瞬间之后。
     *
     * @param instant      指定瞬间
     * @param otherInstant 其他瞬间
     * @return boolean
     */
    public static boolean isAfter(Instant instant, Instant otherInstant) {
        return instant.isAfter(otherInstant);
    }

    /**
     * 从系统时钟获取当前时刻。
     * {@link System#currentTimeMillis}
     *
     * @return Instant
     */
    public static Instant now() {
        return Instant.now();
    }

    /**
     * 从指定的时钟获取当前时刻。
     *
     * @param clock clock
     * @return Instant
     */
    public static Instant now(Clock clock) {
        return Instant.now(clock);
    }

    /**
     * 从即时对象获取日期的实例。
     *
     * @param instant instant
     * @return Date
     */
    public static Date toDate(Instant instant) {
        return Date.from(instant);
    }

    /**
     * 将此日期对象转换为即时时刻。
     *
     * @param date date
     * @return Instant
     */
    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    /**
     * 获取1970-01-01T00:00:00Z纪元的秒 + epochSecond的即时实例。
     *
     * @param epochSecond 纪元秒-从1970-01-01T00:00:00Z开始的秒数
     * @return Instant
     */
    public static Instant ofEpochSecond(long epochSecond) {
        return Instant.ofEpochSecond(epochSecond);
    }

    /**
     * 获取一个{@code Instant}的实例，使用1970-01-01T00:00:00Z纪元的秒数和秒的纳秒分数 + epochSecond + nanoAdjustment。
     *
     * @param epochSecond    纪元秒-从1970-01-01T00:00:00Z开始的秒数
     * @param nanoAdjustment 对秒数的纳秒调整，正数或负数
     * @return Instant
     */
    public static Instant ofEpochSecond(long epochSecond, long nanoAdjustment) {
        return Instant.ofEpochSecond(epochSecond, nanoAdjustment);
    }

    /**
     * 获取1970-01-01T00:00:00Z纪元的毫秒 + epochMilli的即时实例。
     *
     * @param epochMilli 从1970-01-01T00:00:00Z开始的毫秒数
     * @return Instant
     */
    public static Instant ofEpochMilli(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli);
    }

    /**
     * 将此瞬间转换为1970-01-01T00:00:00Z纪元的毫秒数。
     * 如果这个瞬间表示时间线上的一个点，它在未来或过去太久，无法用很长的毫秒来表示，那么就会抛出一个异常。
     *
     * @param instant instant
     * @return long
     */
    public static long toEpochMilli(Instant instant) {
        return instant.toEpochMilli();
    }

    /**
     * 将此纪元秒与系统默认时区组合起来创建ZonedDateTime。
     *
     * @param epochSecond 纪元秒
     * @return ZonedDateTime
     */
    public static ZonedDateTime atZone(long epochSecond) {
        return atZone(epochSecond, ZoneId.systemDefault());
    }

    /**
     * 将此纪元秒与指定时区组合起来创建ZonedDateTime。
     *
     * @param epochSecond epochSecond
     * @param zone        zone
     * @return ZonedDateTime
     */
    public static ZonedDateTime atZone(long epochSecond, ZoneId zone) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        return instant.atZone(zone);
    }

}
