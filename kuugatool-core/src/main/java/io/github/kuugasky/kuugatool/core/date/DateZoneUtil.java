package io.github.kuugasky.kuugatool.core.date;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * DateZoneUtil
 * <p>
 * 时区 ID 工具类
 *
 * @author kuuga
 * @since 2022/7/11 16:00
 */
public final class DateZoneUtil {

    // ZoneId ===============================================================================================

    /**
     * 获取系统默认时区
     * java.time.ZoneId 是java8的新时区类
     * <p>
     * 它对应以前的 TimeZone
     * 它的子类有 ZoneOffset , ZoneRegion
     *
     * @return ZoneId
     */
    public static ZoneId systemDefault() {
        return ZoneId.systemDefault();
    }

    /**
     * 获取中国时区
     * UTC : 世界协调时
     * GMT : 格林威治平时
     * 不是格林威治本地时 , 格林尼治本地的时间是GMT+1
     * UTC与GMT目前相差小于0.9秒(理论上) 受天体运动影响
     * GMT是时区,UTC不是时区,是一个基准,只是他们表示的时间近乎相同
     * <p>
     * UTC更精准(使用了原子钟)
     * ZoneId.of("Etc/GMT-8")
     * ZoneId.of("GMT+8")
     * ZoneId.of("GMT+08")
     * ZoneId.of("GMT+08:00")
     * <p>
     * ZoneId.of("UTC+8")
     * ZoneId.of("UTC+08")
     * ZoneId.of("UTC+08:00")
     * <p>
     * ZoneId.of("UT+8")
     * ZoneId.of("UT+08")
     * ZoneId.of("UT+08:00")
     * <p>
     * ZoneId.of("+8")
     * ZoneId.of("+08")
     * ZoneId.of("+08:00")
     * <p>
     * ZoneId.of("Asia/Shanghai")
     * ZoneId.of("Asia/Chongqing")
     * ZoneId.of("Asia/Chungking")
     * ZoneId.of("Asia/Hong_Kong")
     * ZoneId.of("Hongkong")
     *
     * @return 中国时区
     */
    public static ZoneId chinaZoneId() {
        return ZoneId.of("GMT+8");
    }

    /**
     * 查看所支持的时区代码
     *
     * @return 时区代码
     */
    public static Set<String> getAvailableZoneIds() {
        return ZoneId.getAvailableZoneIds();
    }

    // ZoneDateTime ===============================================================================================

    /**
     * 从默认时区的系统时钟中获取当前日期-时间。
     *
     * @return ZonedDateTime
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now();
    }

    /**
     * 从指定时区的系统时钟中获取当前日期-时间。
     * {@link ZoneId#getAvailableZoneIds}
     *
     * @param zone zone
     * @return ZonedDateTime
     */
    public static ZonedDateTime now(ZoneId zone) {
        return ZonedDateTime.now(zone);
    }

    /**
     * 从本地日期时间获取{@code ZonedDateTime}的实例。
     *
     * @param localDateTime localDateTime
     * @param zone          zoneId
     * @return ZonedDateTime
     */
    public static ZonedDateTime of(LocalDateTime localDateTime, ZoneId zone) {
        return ZonedDateTime.of(localDateTime, zone);
    }

    /**
     * 从指定的时钟获取当前日期-时间。
     * {@link ZoneId#getAvailableZoneIds}
     *
     * @param clock clock
     * @return ZonedDateTime
     */
    public static ZonedDateTime now(Clock clock) {
        return ZonedDateTime.now(clock);
    }

}
