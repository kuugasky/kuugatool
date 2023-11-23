package io.github.kuugasky.kuugatool.core.date.check;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Date;

/**
 * 日期范围检查
 *
 * @author kuuga
 * @since 2021/7/15
 */
@Slf4j
public final class DateRangeCheck {

    /**
     * 开始日期
     */
    private final Date startDate;
    /**
     * 结束日期
     */
    private final Date endDate;
    /**
     * 最大限制结束日期
     */
    private Date maxEndDate = DateUtil.lastMomentOfDay(DateUtil.now());
    /**
     * 允许时间范围
     */
    private Integer rangeDays = null;
    /**
     * 预期允许时间范围
     */
    private Integer expectedRangeDays = null;

    public DateRangeCheck(@NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        this.startDate = DateUtil.zeroOfDay(DateUtil.toDate(startDate));
        this.endDate = DateUtil.lastMomentOfDay(DateUtil.toDate(endDate));
    }

    public DateRangeCheck(@NonNull Date startDate, @NonNull Date endDate) {
        this.startDate = DateUtil.zeroOfDay(startDate);
        this.endDate = DateUtil.lastMomentOfDay(endDate);
    }

    /**
     * 构建时间范威检查器
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return DateRangeCheck
     */
    public static DateRangeCheck build(LocalDate startDate, LocalDate endDate) {
        return new DateRangeCheck(startDate, endDate);
    }

    /**
     * 构建时间范威检查器
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return DateRangeCheck
     */
    public static DateRangeCheck build(Date startDate, Date endDate) {
        return new DateRangeCheck(startDate, endDate);
    }

    /**
     * 设置截止天数，用于计算预期允许的最大截止日期
     *
     * @param endDays 0：截止日期最大是当天
     *                1：明天，以此类推
     *                -1：昨天，以此类推
     * @return DateRangeCheck
     */
    public DateRangeCheck maxEndDays(int endDays) {
        this.maxEndDate = DateUtil.lastMomentOfDay(DateUtil.moreOrLessDays(DateUtil.now(), endDays));
        return this;
    }

    /**
     * 设置允许的范围天数
     * 比如{@code rangeDays}=5，则入参开始时间和入参结束时间间隔必须在5天内
     *
     * @param rangeDays 范围天数
     * @return DateRangeCheck
     */
    public DateRangeCheck rangeDays(int rangeDays) {
        this.expectedRangeDays = rangeDays;
        // 时间天数范围要减1，以一个日期为一天，预期7天，实际是6个完整天00:00:00~23:59:59
        this.rangeDays = rangeDays - 1;
        return this;
    }

    /**
     * 检测判断
     */
    public void check() {
        if (ObjectUtil.containsNull(rangeDays, expectedRangeDays)) {
            throw new RuntimeException("日期范围值不能为空");
        }

        LocalDate startDateOfLocalDate = DateUtil.toLocalDate(startDate);
        LocalDate endDateOfLocalDate = DateUtil.toLocalDate(endDate);
        if (endDateOfLocalDate.isBefore(startDateOfLocalDate)) {
            throw new RuntimeException("结束日期不能大于开始日期");
        }

        // 预期最晚结束时间
        LocalDate expectedMinEndDate = DateUtil.toLocalDate(maxEndDate);
        // 实际结束日期不能大于预期允许的最大结束日期
        if (endDateOfLocalDate.isAfter(expectedMinEndDate)) {
            throw new RuntimeException(String.format("结束日期大于预期值[%s]", expectedMinEndDate));
        }

        // 预期最早开始日期
        LocalDate expectedMinStartDate = DateUtil.toLocalDate(DateUtil.moreOrLessDays(endDate, -(rangeDays)));

        if (startDateOfLocalDate.isBefore(expectedMinStartDate)) {
            throw new RuntimeException("开始日期至结束日期超过天数限制");
        }
    }

    public void print(boolean console) {
        if (console) {
            Console.println("入参开始时间：" + DateUtil.toLocalDate(startDate).toString());
            Console.println("入参结束时间：" + DateUtil.toLocalDate(endDate).toString());

            Console.println("预期最大结束日期：" + DateUtil.toLocalDate(maxEndDate).toString());
            Console.println("预期日期范围天数：" + expectedRangeDays);
            Console.println("实际日期范围天数：" + (DateUtil.dayDifferenceZeroTime(startDate, endDate) + 1));
            return;
        }
        print();
    }

    public void print() {
        log.info("入参开始时间：" + DateUtil.toLocalDate(startDate).toString());
        log.info("入参结束时间：" + DateUtil.toLocalDate(endDate).toString());

        log.info("预期最大结束日期：" + DateUtil.toLocalDate(maxEndDate).toString());
        log.info("预期日期范围天数：" + expectedRangeDays);
        log.info("实际日期范围天数：" + (DateUtil.dayDifferenceZeroTime(startDate, endDate) + 1));
    }

}
