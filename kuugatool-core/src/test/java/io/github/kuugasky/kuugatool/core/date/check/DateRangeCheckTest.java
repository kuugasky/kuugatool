package io.github.kuugasky.kuugatool.core.date.check;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

class DateRangeCheckTest {

    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2022, 8, 28);
        LocalDate endDate = LocalDate.of(2022, 9, 3);

        DateRangeCheck dateRangeCheck = DateRangeCheck.build(startDate, endDate);
        dateRangeCheck.maxEndDays(2);
        dateRangeCheck.rangeDays(7);
        dateRangeCheck.print(true);
        dateRangeCheck.check();
    }

    @Test
    void build() {
        LocalDate date1 = LocalDate.of(2021, 7, 20);
        LocalDate date2 = LocalDate.of(2021, 7, 26);

        Date date3 = DateUtil.toDate(date1);
        Date date4 = DateUtil.toDate(date2);
        DateRangeCheck dateRangeCheck = DateRangeCheck.build(date3, date4).maxEndDays(-1).rangeDays(7);
        System.out.println(dateRangeCheck);
    }

    @Test
    void maxEndDays() {
        LocalDate date1 = LocalDate.of(2021, 7, 20);
        LocalDate date2 = LocalDate.of(2021, 7, 26);

        DateRangeCheck dateRangeCheck = DateRangeCheck.build(date1, date2).maxEndDays(-1).rangeDays(7);
        dateRangeCheck = dateRangeCheck.maxEndDays(0);
        System.out.println(dateRangeCheck);
    }

    @Test
    void rangeDays() {
        LocalDate date1 = LocalDate.of(2021, 7, 20);
        LocalDate date2 = LocalDate.of(2021, 7, 26);

        DateRangeCheck dateRangeCheck = DateRangeCheck.build(date1, date2).maxEndDays(-1).rangeDays(7);
        dateRangeCheck = dateRangeCheck.rangeDays(0);
        System.out.println(dateRangeCheck);
    }

    @Test
    void check() {
        LocalDate date1 = LocalDate.of(2021, 7, 20);
        LocalDate date2 = LocalDate.of(2021, 7, 26);

        DateRangeCheck dateRangeCheck = DateRangeCheck.build(date1, date2).maxEndDays(-1).rangeDays(7);
        dateRangeCheck.check();
    }

    @Test
    void print() {
        LocalDate date1 = LocalDate.of(2021, 7, 20);
        LocalDate date2 = LocalDate.of(2021, 7, 26);

        DateRangeCheck dateRangeCheck = DateRangeCheck.build(date1, date2).maxEndDays(-1).rangeDays(7);
        dateRangeCheck.print();
    }
}