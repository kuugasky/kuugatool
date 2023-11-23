package io.github.kuugasky.kuugatool.core.date;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * DateClockUtil
 * <p>
 * {@link Clock}使用时区提供对当前时刻、日期和时间的访问的时钟。
 * <p>
 * zoneId需要设置为{@link DateZoneUtil#chinaZoneId()}，否则差8小时
 *
 * @author kuuga
 * @since 2022/7/11 17:24
 */
public final class DateClockUtil {

    // Clock ===============================================================================================

    /**
     * 获取一个时钟，该时钟使用最佳可用系统时钟返回当前时刻，并使用默认时区转换为日期和时间。
     *
     * @return Clock
     */
    public static Clock systemDefaultZone() {
        return Clock.systemDefaultZone();
    }

    /**
     * 使用UTC时区返回当前即时时间。
     *
     * @return Clock
     */
    @SuppressWarnings("all")
    public static Clock systemUTC() {
        return Clock.systemUTC();
    }

    /**
     * 返回从 1970-01-01T00:00Z (UTC) 到开始测量的时钟的当前毫秒时刻。
     * millis 的值等价于 System.currentTimeMillis()。
     *
     * @return millis
     */
    public static long millis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 获取时钟的当前时刻。
     *
     * @return Instant(时间线上的瞬时点)
     */
    public static Instant instant() {
        Clock clock = Clock.systemDefaultZone();
        return clock.instant();
    }

    /**
     * 获得的时钟将早于/晚于/等于基准时钟。
     *
     * @param clock    时钟
     * @param duration 持续时间
     * @return Clock
     */
    public static Clock offset(Clock clock, Duration duration) {
        return Clock.offset(clock, duration);
    }

    /**
     * 获取一个时钟，该时钟使用指定时区返回当前时刻。
     * ZoneId.of("GMT+8")
     * {@link DateZoneUtil#chinaZoneId()}
     *
     * @return Clock
     */
    public static Clock system(ZoneId zone) {
        return Clock.system(zone);
    }

    /**
     * 获取一个时钟，该时钟返回从指定时钟截断到最近出现的指定持续时间的瞬间。
     *
     * @return Clock
     */
    public static Clock tick(Clock baseClock, Duration duration) {
        return Clock.tick(baseClock, duration);
    }

    /**
     * 使用最佳可用系统时钟获取一个时钟，该时钟以整毫秒为单位返回当前即时时间。
     *
     * @return Clock
     */
    public static Clock tickMillis(ZoneId zone) {
        return Clock.tickMillis(zone);
    }

    /**
     * 获取一个时钟，该时钟使用最佳可用系统时钟返回以整秒为单位的当前时刻。
     *
     * @return Clock
     */
    public static Clock tickSeconds(ZoneId zone) {
        return Clock.tickSeconds(zone);
    }

    /**
     * 获取一个时钟，该时钟使用最佳可用系统时钟返回以整分钟为单位的当前时刻。
     *
     * @return Clock
     */
    public static Clock tickMinutes(ZoneId zone) {
        return Clock.tickMinutes(zone);
    }

    /**
     * 获取一个时钟，它总是返回相同的瞬间。
     * 这个时钟只返回指定的瞬间。
     * 因此，它不是传统意义上的时钟。
     * 这个的主要用例是在测试中，固定的时钟确保测试不依赖于当前的时钟。
     *
     * @return Clock
     */
    public static Clock fixed(Instant fixedInstant, ZoneId zone) {
        return Clock.fixed(fixedInstant, zone);
    }

    /**
     * 返回具有不同时区的时钟副本。
     *
     * @param clock clock
     * @param zone  zone
     * @return Clock
     */
    public static Clock withZone(Clock clock, ZoneId zone) {
        return clock.withZone(zone);
    }

    /**
     * 从时钟clock中提取时区对象。
     *
     * @param clock clock
     * @return ZoneId
     */
    public static ZoneId getZone(Clock clock) {
        return clock.getZone();
    }

    /**
     * 检查这个时钟是否等于另一个时钟。
     *
     * @param clock  clock
     * @param object object
     * @return boolean
     */
    public static boolean equals(Clock clock, Object object) {
        return clock.equals(object);
    }

}
