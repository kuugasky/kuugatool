package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

class DateDurationUtilTest {

    @Test
    void between() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 0, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 0, 0, 0, 1);
        System.out.println(DateDurationUtil.between(startTime, endTime));
        LocalDateTime startTime1 = LocalDateTime.of(2022, 7, 11, 0, 0, 0, 2);
        LocalDateTime endTime1 = LocalDateTime.of(2022, 7, 11, 0, 0, 0, 1);
        System.out.println(DateDurationUtil.between(startTime1, endTime1));
    }

    @Test
    void toDays() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 10, 23, 59, 58, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 23, 59, 58, 0);
        System.out.println(DateDurationUtil.toDays(startTime, endTime));
    }

    @Test
    void toDaysPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 10, 23, 59, 58, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 23, 59, 58, 0);
        System.out.println(DateDurationUtil.toDaysPart(startTime, endTime));
    }

    @Test
    void toHours() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 10, 12, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        System.out.println(DateDurationUtil.toHours(startTime, endTime));
    }

    @Test
    void toHoursPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 10, 12, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        System.out.println(DateDurationUtil.toHoursPart(startTime, endTime));
    }

    @Test
    void toMinutes() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toMinutes(startTime, endTime));
    }

    @Test
    void toMinutesPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toMinutesPart(startTime, endTime));
    }

    @Test
    void toSeconds() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toSeconds(startTime, endTime));
    }

    @Test
    void toSecondsPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toSecondsPart(startTime, endTime));
    }

    @Test
    void toMillis() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toMillis(startTime, endTime));
    }

    @Test
    void toMillisPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toMillisPart(startTime, endTime));
    }

    @Test
    void toNanos() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toNanos(startTime, endTime));
    }

    @Test
    void toNanosPart() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.toNanosPart(startTime, endTime));
    }

    @Test
    void isNegative() {
        LocalTime startTime = LocalTime.of(12, 10, 20);
        LocalTime endTime = LocalTime.of(12, 10, 30);
        System.out.println(DateDurationUtil.isNegative(startTime, endTime));
    }

    @Test
    void testIsNegative() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 11, 13, 0, 0, 100);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 11, 13, 1, 0, 0);
        System.out.println(DateDurationUtil.isNegative(startTime, endTime));
    }

}