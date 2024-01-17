package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日期链范围工具
 * <p>
 * 用于推导某个日期之前或之后的日期
 * <p>
 * 或者某个时间段对应的之前日期范围，或之后日期范围
 *
 * @author kuuga
 * @since 2022-04-19 19:09:35
 */
public final class DateChainRangeUtil {

    /**
     * 日期链类型
     */
    public enum DateChainType {
        /**
         * 往前推导日期范围
         */
        BEFORE,
        /**
         * 往后推导日期范围
         */
        AFTER,
    }

    /**
     * 日期链范围
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateChainRange {
        private Date startDate;
        private Date endDate;
    }

    /**
     * 获取环比日期对象
     *
     * @param fromDate 环比对标日期
     * @return 返回fromDate前一天的环比日期
     */
    public static DateChainRange rangeBefore(Date fromDate) {
        return dateChainRange(fromDate, DateChainType.BEFORE);
    }

    /**
     * 获取环比日期对象
     *
     * @param fromDate 环比对标日期
     * @return 返回fromDate后一天的环比日期
     */
    public static DateChainRange rangeAfter(Date fromDate) {
        return dateChainRange(fromDate, DateChainType.AFTER);
    }

    /**
     * 根据给定的起始日期和环比类型计算季度与上一季（或下一季）之间的日期差，返回相对应的日期差
     *
     * @param fromDate 起始日期
     * @param type     环比类型，可选值为BEFORE（上一季）或 AFTER（下一季）
     * @return 计算后的日期差
     * @throws RuntimeException 如果起始日期或环比类型为null，则抛出异常
     */
    public static DateChainRange dateChainRange(Date fromDate, DateChainType type) {
        if (ObjectUtil.containsNull(fromDate, type)) {
            throw new RuntimeException("value can not be null");
        }
        Date moreOrLessDays = DateUtil.moreOrLessDays(fromDate, ObjectUtil.equals(type, DateChainType.BEFORE) ? -1 : 1);
        return new DateChainRange(DateUtil.zeroOfDay(moreOrLessDays), DateUtil.lastMomentOfDay(moreOrLessDays));
    }


    /**
     * 获取环比日期对象
     * <p>
     * 1.计算fromDate和toDate相隔天数:
     * <p>
     * long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
     * <p>
     * 2.环比开始日期:fromDate往前推dayDifferenceZeroTime天
     * 3.环比结束日期:fromDate往前推1天
     *
     * @param fromDate 环比对标开始日期
     * @param toDate   环比对标结束日期
     * @return 返回对标日期的环比日期
     */
    public static DateChainRange rangeBefore(Date fromDate, Date toDate) {
        return dateChainRange(fromDate, toDate, DateChainType.BEFORE);
    }

    /**
     * 获取环比日期对象
     * <p>
     * 1.计算fromDate和toDate相隔天数:
     * <p>
     * long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
     * <p>
     * 2.环比开始日期:toDate往后推1天
     * 3.环比结束日期:toDate往前推dayDifferenceZeroTime天
     *
     * @param fromDate 环比对标开始日期
     * @param toDate   环比对标结束日期
     * @return 返回对标日期的环比日期
     */
    public static DateChainRange rangeAfter(Date fromDate, Date toDate) {
        return dateChainRange(fromDate, toDate, DateChainType.AFTER);
    }

    /**
     * 根据给定的起始日期、结束日期和环比类型计算季度与上一季（或下一季）之间的日期差，返回相对应的日期差对象
     *
     * @param fromDate 起始日期
     * @param toDate   结束日期
     * @param type     环比类型，可选值为 DateChainType.BEFORE（上一季）或 DateChainType.AFTER（下一季）
     * @return 计算后的季度环比结果对象
     * @throws RuntimeException 如果起始日期、结束日期或环比类型为null，则抛出异常
     * @throws RuntimeException 如果起始日期大于结束日期，则抛出异常
     */
    public static DateChainRange dateChainRange(Date fromDate, Date toDate, DateChainType type) {
        if (ObjectUtil.containsNull(fromDate, toDate, type)) {
            throw new RuntimeException("value can not be null");
        }
        if (DateUtil.isAfter(fromDate, toDate)) {
            throw new RuntimeException("fromDate can not greater than toDate");
        }
        long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
        if (ObjectUtil.equals(type, DateChainType.BEFORE)) {
            Date rangeEndDate = DateUtil.moreOrLessDays(fromDate, -1);
            Date rangeStartDate = DateUtil.moreOrLessDays(rangeEndDate, -NumberUtil.parseInt(String.valueOf(dayDifferenceZeroTime)));
            return new DateChainRange(DateUtil.zeroOfDay(rangeStartDate), DateUtil.lastMomentOfDay(rangeEndDate));
        } else if (ObjectUtil.equals(type, DateChainType.AFTER)) {
            Date rangeStartDate = DateUtil.moreOrLessDays(toDate, 1);
            Date rangeEndDate = DateUtil.moreOrLessDays(rangeStartDate, NumberUtil.parseInt(String.valueOf(dayDifferenceZeroTime)));
            return new DateChainRange(DateUtil.zeroOfDay(rangeStartDate), DateUtil.lastMomentOfDay(rangeEndDate));
        } else {
            return new DateChainRange();
        }
    }

}
