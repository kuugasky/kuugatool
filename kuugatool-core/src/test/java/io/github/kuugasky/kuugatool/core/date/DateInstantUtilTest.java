package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

class DateInstantUtilTest {

    @Test
    void isBefore() {
        long currentTimeMillis = System.currentTimeMillis();
        Instant startInstant = Instant.ofEpochMilli(currentTimeMillis);
        currentTimeMillis += 1;
        Instant endInstant = Instant.ofEpochMilli(currentTimeMillis);
        boolean before = DateInstantUtil.isBefore(startInstant, endInstant);
        System.out.println(before);
    }

    @Test
    void isAfter() {
        long currentTimeMillis = System.currentTimeMillis();
        Instant endInstant = Instant.ofEpochMilli(currentTimeMillis);
        currentTimeMillis = currentTimeMillis - 1;
        Instant startInstant = Instant.ofEpochMilli(currentTimeMillis);
        boolean after = DateInstantUtil.isAfter(endInstant, startInstant);
        System.out.println(after);
    }

    @Test
    void now() {
        Instant now = DateInstantUtil.now();
        System.out.println(now);
    }

    @Test
    void testNow() {
        ZoneId zoneId = ZoneId.systemDefault();
        // Clock system = DateClockUtil.system(DateZoneUtil.chinaZoneId());
        Clock system = Clock.system(zoneId);
        Instant now = Instant.now(system);
        System.out.println(now);
    }

    @Test
    void toDate() {
        Date date = DateInstantUtil.toDate(Instant.ofEpochMilli(System.currentTimeMillis()));
        System.out.println(DateUtil.formatDateTime(date));
    }

    @Test
    void toInstant() {
        Date date = DateInstantUtil.toDate(Instant.ofEpochMilli(System.currentTimeMillis()));
        System.out.println(DateUtil.formatDateTime(date));
        Instant instant = DateInstantUtil.toInstant(date);

        Instant instant1 = date.toInstant();
        // 丢了8小时，疑似转instant用了UTC
        System.out.println(instant);
        System.out.println(instant1);

        ZoneId zoneId = ZoneId.of("GMT+8");
        Instant instant2 = date.toInstant();
        // instant重新设置时区，8小时回来了
        ZonedDateTime zonedDateTime = instant2.atZone(zoneId);
        System.out.println(zonedDateTime);
    }

    @Test
    void ofEpochSecond() {
        Date date = DateInstantUtil.toDate(DateInstantUtil.ofEpochSecond(1000000));
        System.out.println(DateUtil.formatDateTime(date));
    }

    @Test
    void testOfEpochSecond() {
        Date date = DateInstantUtil.toDate(DateInstantUtil.ofEpochSecond(1000000, 1000));
        System.out.println(DateUtil.formatDateTime(date));
    }

    @Test
    void ofEpochMilli() {
        Instant instant = DateInstantUtil.ofEpochMilli(System.currentTimeMillis());
        System.out.println(instant);
    }

    @Test
    void toEpochMilli() {
        Instant instant = DateInstantUtil.ofEpochMilli(System.currentTimeMillis());
        System.out.println(DateInstantUtil.toEpochMilli(instant));
    }

    @Test
    void atZone() {
        ZonedDateTime zonedDateTime = DateInstantUtil.atZone(DateUtil.getSecond(DateUtil.now()));
        System.out.println(zonedDateTime);
    }

    @Test
    void testAtZone() {
        System.out.println(DateUtil.getSecond(DateUtil.now()));
        ZonedDateTime zonedDateTime = DateInstantUtil.atZone(DateUtil.getSecond(DateUtil.now()), DateZoneUtil.chinaZoneId());
        System.out.println(zonedDateTime);
    }
}