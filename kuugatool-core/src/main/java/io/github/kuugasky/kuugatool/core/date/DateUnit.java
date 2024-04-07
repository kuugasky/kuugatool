package io.github.kuugasky.kuugatool.core.date;

import lombok.Getter;

import java.time.temporal.ChronoUnit;

/**
 * 日期时间单位，每个单位都是以毫秒为基数
 *
 * @author Looly
 */
@Getter
public enum DateUnit {
    /**
     * 一毫秒
     */
    MS(1),
    /**
     * 一秒的毫秒数
     */
    SECONDS(1000),
    /**
     * 一分钟的毫秒数
     */
    MINUTES(SECONDS.getMillis() * 60),
    /**
     * 一小时的毫秒数
     */
    HOURS(MINUTES.getMillis() * 60),
    /**
     * 一天的毫秒数
     */
    DAYS(HOURS.getMillis() * 24),
    /**
     * 一周的毫秒数
     */
    WEEKS(DAYS.getMillis() * 7);

    /**
     * 单位对应的毫秒数
     */
    private final long millis;

    DateUnit(long millis) {
        this.millis = millis;
    }

    /**
     * 单位兼容转换，将DateUnit转换为对应的{@link ChronoUnit}
     *
     * @return {@link ChronoUnit} 一组标准的日期期间单位。
     * @since 5.4.5
     */
    public ChronoUnit toChronoUnit() {
        return DateUnit.toChronoUnit(this);
    }

    /**
     * 单位兼容转换，将{@link ChronoUnit}转换为对应的DateUnit
     *
     * @param unit {@link ChronoUnit}
     * @return DateUnit，null表示不支持此单位
     * @since 5.4.5
     */
    public static DateUnit of(ChronoUnit unit) {
        return switch (unit) {
            case MICROS -> DateUnit.MS;
            case SECONDS -> DateUnit.SECONDS;
            case MINUTES -> DateUnit.MINUTES;
            case HOURS -> DateUnit.HOURS;
            case DAYS -> DateUnit.DAYS;
            case WEEKS -> DateUnit.WEEKS;
            default -> null;
        };
    }

    /**
     * 单位兼容转换，将DateUnit转换为对应的{@link ChronoUnit}
     *
     * @param unit DateUnit
     * @return {@link ChronoUnit}
     * @since 5.4.5
     */
    public static ChronoUnit toChronoUnit(DateUnit unit) {
        return switch (unit) {
            case MS -> ChronoUnit.MICROS;
            case SECONDS -> ChronoUnit.SECONDS;
            case MINUTES -> ChronoUnit.MINUTES;
            case HOURS -> ChronoUnit.HOURS;
            case DAYS -> ChronoUnit.DAYS;
            case WEEKS -> ChronoUnit.WEEKS;
        };
    }

}
