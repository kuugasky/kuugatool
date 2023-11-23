package io.github.kuugasky.kuugatool.core.date;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

/**
 * DatePeriodUtil
 * <p>
 * {@link Period} 在概念上和 {@link Duration} 类似，区别在于 Period 是以年月日来衡量一个时间段。
 * <p>
 * Duration 用于计算两个时间间隔，Period 用于计算两个日期间隔，所以 between() 方法只能接收 LocalDate 类型的参数。
 *
 * @author kuuga
 * @since 2022/7/11 10:01
 */
public final class DatePeriodUtil {

    /**
     * 计算两个日期的周期信息
     * <p>
     * 注意：
     * <p>
     * 1.该方法类似<p>
     * {@link DateUtil#yearDifference}<p>
     * {@link DateUtil#monthDifference}<p>
     * {@link DateUtil#dayDifference}<p>
     * 但不同的是DateUtil的保留了时间信息做对比，而当前方法只保留日期做对比。前后一天未超过24小时，Period同样是相隔1天。
     * <p>
     * 2.该方法计算出来的是完整周期纬度信息，比如超过一个月的，period：xMxd，而{@link DateUtil#dayDifference}的则是两个时间间隔的完整天数，月份也一样。
     *
     * @param startDateInclusive 开始时间
     * @param endDateExclusive   结束时间
     * @return 周期
     */
    public static Period between(final LocalDate startDateInclusive, final LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive);
    }

    /**
     * 获取两个日期相差年份。
     * 注意：比对的仅仅只是year，非完整日期
     *
     * @param startDateInclusive startDateInclusive
     * @param endDateExclusive   endDateExclusive
     * @return int
     */
    public static int getYears(final LocalDate startDateInclusive, final LocalDate endDateExclusive) {
        return between(startDateInclusive, endDateExclusive).getYears();
    }

    /**
     * 获取两个日期相差月份。
     * 注意：比对的仅仅只是month，非完整日期
     *
     * @param startDateInclusive startDateInclusive
     * @param endDateExclusive   endDateExclusive
     * @return int
     */
    public static int getMonths(final LocalDate startDateInclusive, final LocalDate endDateExclusive) {
        return between(startDateInclusive, endDateExclusive).getMonths();
    }

    /**
     * 获取两个日期相差天数。
     * 注意：比对的仅仅只是day，非完整日期
     *
     * @param startDateInclusive startDateInclusive
     * @param endDateExclusive   endDateExclusive
     * @return int
     */
    public static int getDays(final LocalDate startDateInclusive, final LocalDate endDateExclusive) {
        return between(startDateInclusive, endDateExclusive).getDays();
    }

    /**
     * 判断日期是否负数
     *
     * @param period 周期
     * @return 是否负数
     */
    public static boolean isNegative(Period period) {
        return period.isNegative();
    }

    /**
     * 判断日期是否负数
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 是否负数
     */
    public static boolean isNegative(int year, int month, int day) {
        return Period.of(year, month, day).isNegative();
    }

    /**
     * 如果endDate日期早于startDate日期，则返回false，否则为true
     * 同日期返回false，只有当endDate日期早于startDate才返回true
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return boolean
     */
    public static boolean isNegative(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).isNegative();
    }

}
