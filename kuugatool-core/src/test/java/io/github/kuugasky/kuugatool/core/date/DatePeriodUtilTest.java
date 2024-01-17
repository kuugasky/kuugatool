package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

class DatePeriodUtilTest {

    @Test
    void getYears() {
        Date startDate = DateUtil.of(2021, 6, 9, 12, 0, 0);
        Date endDate = DateUtil.of(2022, 7, 10, 0, 0, 0);
        int years = DatePeriodUtil.getYears(DateUtil.toLocalDate(startDate), DateUtil.toLocalDate(endDate));
        System.out.println(years);
    }

    @Test
    void getMonths() {
        Date startDate = DateUtil.of(2021, 6, 9, 12, 0, 0);
        Date endDate = DateUtil.of(2022, 7, 10, 0, 0, 0);
        int months = DatePeriodUtil.getMonths(DateUtil.toLocalDate(startDate), DateUtil.toLocalDate(endDate));
        System.out.println(months);
    }

    @Test
    void getDays() {
        Date startDate = DateUtil.of(2021, 6, 9, 12, 0, 0);
        Date endDate = DateUtil.of(2022, 7, 10, 0, 0, 0);
        int days = DatePeriodUtil.getDays(DateUtil.toLocalDate(startDate), DateUtil.toLocalDate(endDate));
        System.out.println(days);
    }

    @Test
    void between() {
        Date startDate = DateUtil.of(2022, 7, 9, 12, 0, 0);
        Date endDate = DateUtil.of(2022, 8, 10, 0, 0, 0);

        Period period = DatePeriodUtil.between(DateUtil.toLocalDate(startDate), DateUtil.toLocalDate(endDate));

        Console.log(StringUtil.formatString(period));

        long l = DateUtil.monthDifference(startDate, endDate);
        System.out.println("相差月份" + l);

        long dayCount = DateUtil.dayDifference(startDate, endDate);
        System.out.println("相差天份" + dayCount);
    }

    @Test
    void period() {
        Period p = Period.of(2021, 12, 3);
        System.out.println("年月日：" + p);
        p = Period.ofYears(1);
        System.out.println("年：" + p);
        p = Period.ofWeeks(2);
        System.out.println("周的天数" + p);

        Period period = Period.of(2021, -1, 8);
        System.out.println("校验年月日任何一位值是否有负数：" + period.isNegative());// true
    }

    @Test
    void before() {
        LocalDate startDate = LocalDate.of(2022, 7, 8);
        LocalDate endDate = LocalDate.of(2022, 7, 7);
        boolean isNegative = DatePeriodUtil.isNegative(startDate, endDate);
        System.out.println(isNegative);
    }

    @Test
    void isNegative() {
        boolean isNegative = DatePeriodUtil.isNegative(2022, -19, 2);
        System.out.println(isNegative);
    }

    @Test
    void isNegative1() {
        Period period = Period.of(2021, 12, 3);
        System.out.println(DatePeriodUtil.isNegative(period));
    }

}