package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateChainUtilTest {

    @Test
    public void rangeBefore() {
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.rangeBefore(DateUtil.of(2024, 1, 17));
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

    @Test
    public void rangeAfter() {
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.rangeAfter(DateUtil.of(2022, 4, 19));
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

    @Test
    public void dateChainRange() {
        Date startDate = DateUtil.of(2022, 4, 19);
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.dateChainRange(startDate, DateChainRangeUtil.DateChainType.BEFORE);
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

    @Test
    public void testRangeBefore() {
        Date fromDate = DateUtil.of(2022, 8, 29);
        Date toDate = DateUtil.of(2022, 9, 4);
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.rangeBefore(fromDate, toDate);
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

    @Test
    public void testRangeAfter() {
        Date fromDate = DateUtil.of(2022, 8, 29);
        Date toDate = DateUtil.of(2022, 9, 4);
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.rangeAfter(fromDate, toDate);
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

    @Test
    public void testDateChainRange() {
        Date fromDate = DateUtil.of(2024, 1, 2);
        Date toDate = DateUtil.of(2024, 1, 8);
        DateChainRangeUtil.DateChainRange date = DateChainRangeUtil.dateChainRange(fromDate, toDate, DateChainRangeUtil.DateChainType.AFTER);
        System.out.println(DateUtil.format(date.getStartDate()));
        System.out.println(DateUtil.format(date.getEndDate()));
    }

}