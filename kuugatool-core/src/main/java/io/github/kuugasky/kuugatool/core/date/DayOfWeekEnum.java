package io.github.kuugasky.kuugatool.core.date;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

/**
 * 周几枚举
 *
 * @author kuuga
 * @since 2021-11-01
 */
@Getter
@AllArgsConstructor
public enum DayOfWeekEnum {

    /**
     * The singleton instance for the day-of-week of Monday.
     * This has the numeric value of {@code 1}.
     */
    MONDAY(DayOfWeek.MONDAY, "星期一", "周一", 1, 2),
    /**
     * The singleton instance for the day-of-week of Tuesday.
     * This has the numeric value of {@code 2}.
     */
    TUESDAY(DayOfWeek.TUESDAY, "星期二", "周二", 2, 3),
    /**
     * The singleton instance for the day-of-week of Wednesday.
     * This has the numeric value of {@code 3}.
     */
    WEDNESDAY(DayOfWeek.WEDNESDAY, "星期三", "周三", 3, 4),
    /**
     * The singleton instance for the day-of-week of Thursday.
     * This has the numeric value of {@code 4}.
     */
    THURSDAY(DayOfWeek.THURSDAY, "星期四", "周四", 4, 5),
    /**
     * The singleton instance for the day-of-week of Friday.
     * This has the numeric value of {@code 5}.
     */
    FRIDAY(DayOfWeek.FRIDAY, "星期五", "周五", 5, 6),
    /**
     * The singleton instance for the day-of-week of Saturday.
     * This has the numeric value of {@code 6}.
     */
    SATURDAY(DayOfWeek.SATURDAY, "星期六", "周六", 6, 7),
    /**
     * The singleton instance for the day-of-week of Sunday.
     * This has the numeric value of {@code 7}.
     */
    SUNDAY(DayOfWeek.SUNDAY, "星期日", "周日", 7, 1),
    ;

    /**
     * 星期几枚举
     */
    private final DayOfWeek dayOfWeek;

    /**
     * 星期几中文
     */
    private final String xq;

    /**
     * 周几中文
     */
    private final String week;

    /**
     * 中国周几下标（周一开始计数）
     */
    private final int indexOfChina;

    /**
     * 国外周几下标（周日开始计数）
     */
    private final int indexOfForeign;

}
