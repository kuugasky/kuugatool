package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateChainUtilTest {

    @Test
    public void qoqBefore() {
        DateChainUtil.DateQoQ date = DateChainUtil.qoqBefore(DateUtil.of(2022, 4, 19));
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

    @Test
    public void qoqAfter() {
        DateChainUtil.DateQoQ date = DateChainUtil.qoqAfter(DateUtil.of(2022, 4, 19));
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

    @Test
    public void qoq() {
        Date startDate = DateUtil.of(2022, 4, 19);
        DateChainUtil.DateQoQ date = DateChainUtil.qoq(startDate, DateChainUtil.QoQType.BEFORE);
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

    @Test
    public void testQoqBefore() {
        Date fromDate = DateUtil.of(2022, 8, 29);
        Date toDate = DateUtil.of(2022, 9, 4);
        DateChainUtil.DateQoQ date = DateChainUtil.qoqBefore(fromDate, toDate);
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

    @Test
    public void testQoqAfter() {
        Date fromDate = DateUtil.of(2022, 8, 29);
        Date toDate = DateUtil.of(2022, 9, 4);
        DateChainUtil.DateQoQ date = DateChainUtil.qoqAfter(fromDate, toDate);
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

    @Test
    public void testQoq() {
        Date fromDate = DateUtil.of(2022, 4, 11);
        Date toDate = DateUtil.of(2022, 4, 17);
        DateChainUtil.DateQoQ date = DateChainUtil.qoq(fromDate, toDate, DateChainUtil.QoQType.AFTER);
        System.out.println(DateUtil.format(date.getFromDate()));
        System.out.println(DateUtil.format(date.getToDate()));
    }

}