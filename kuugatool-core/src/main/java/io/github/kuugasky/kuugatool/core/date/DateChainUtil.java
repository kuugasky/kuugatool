package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日期环比工具
 *
 * @author kuuga
 * @since 2022-04-19 19:09:35
 */
public final class DateChainUtil {

    enum QoQType {
        /**
         *
         */
        BEFORE,
        AFTER,
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class DateQoQ {
        private Date fromDate;
        private Date toDate;
    }

    /**
     * 获取环比日期对象
     *
     * @param fromDate 环比对标日期
     * @return 返回fromDate前一天的环比日期
     */
    public static DateQoQ qoqBefore(Date fromDate) {
        return qoq(fromDate, QoQType.BEFORE);
    }

    /**
     * 获取环比日期对象
     *
     * @param fromDate 环比对标日期
     * @return 返回fromDate后一天的环比日期
     */
    public static DateQoQ qoqAfter(Date fromDate) {
        return qoq(fromDate, QoQType.AFTER);
    }

    public static DateQoQ qoq(Date fromDate, QoQType type) {
        if (ObjectUtil.containsNull(fromDate, type)) {
            throw new RuntimeException("value can not be null");
        }
        Date moreOrLessDays = DateUtil.moreOrLessDays(fromDate, ObjectUtil.equals(type, QoQType.BEFORE) ? -1 : 1);
        return new DateQoQ(DateUtil.zeroOfDay(moreOrLessDays), DateUtil.lastMomentOfDay(moreOrLessDays));
    }

    /**
     * 获取环比日期对象
     * <p>
     * 1.计算fromDate和toDate相隔天数:
     * <java>
     * long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
     * </java>
     * 2.环比开始日期:fromDate往前推dayDifferenceZeroTime天
     * 3.环比结束日期:fromDate往前推1天
     *
     * @param fromDate 环比对标开始日期
     * @param toDate   环比对标结束日期
     * @return 返回对标日期的环比日期
     */
    public static DateQoQ qoqBefore(Date fromDate, Date toDate) {
        return qoq(fromDate, toDate, QoQType.BEFORE);
    }

    /**
     * 获取环比日期对象
     * <p>
     * 1.计算fromDate和toDate相隔天数:
     * <java>
     * long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
     * </java>
     * 2.环比开始日期:toDate往后推1天
     * 3.环比结束日期:toDate往前推dayDifferenceZeroTime天
     *
     * @param fromDate 环比对标开始日期
     * @param toDate   环比对标结束日期
     * @return 返回对标日期的环比日期
     */
    public static DateQoQ qoqAfter(Date fromDate, Date toDate) {
        return qoq(fromDate, toDate, QoQType.AFTER);
    }

    public static DateQoQ qoq(Date fromDate, Date toDate, QoQType type) {
        if (ObjectUtil.containsNull(fromDate, toDate, type)) {
            throw new RuntimeException("value can not be null");
        }
        if (DateUtil.after(fromDate, toDate)) {
            throw new RuntimeException("fromDate can not greater than toDate");
        }
        long dayDifferenceZeroTime = DateUtil.dayDifferenceZeroTime(fromDate, toDate);
        if (ObjectUtil.equals(type, QoQType.BEFORE)) {
            Date qoqToDate = DateUtil.moreOrLessDays(fromDate, -1);
            Date qoqFromDate = DateUtil.moreOrLessDays(qoqToDate, -NumberUtil.parseInt(String.valueOf(dayDifferenceZeroTime)));
            return new DateQoQ(DateUtil.zeroOfDay(qoqFromDate), DateUtil.lastMomentOfDay(qoqToDate));
        } else if (ObjectUtil.equals(type, QoQType.AFTER)) {
            Date qoqFromDate = DateUtil.moreOrLessDays(toDate, 1);
            Date qoqToDate = DateUtil.moreOrLessDays(qoqFromDate, NumberUtil.parseInt(String.valueOf(dayDifferenceZeroTime)));
            return new DateQoQ(DateUtil.zeroOfDay(qoqFromDate), DateUtil.lastMomentOfDay(qoqToDate));
        } else {
            return new DateQoQ();
        }
    }

}
