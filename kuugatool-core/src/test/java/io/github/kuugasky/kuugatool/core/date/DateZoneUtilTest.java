package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

class DateZoneUtilTest {

    @Test
    void systemDefault() {
        ZoneId zoneId = DateZoneUtil.systemDefault();
        Assertions.assertEquals(zoneId.toString(), "Asia/Shanghai");
    }

    @Test
    void chinaZoneId() {
        ZoneId zoneId = DateZoneUtil.chinaZoneId();
        Assertions.assertEquals(zoneId.toString(), "GMT+08:00");
        System.out.println("Etc/GMT-8 ---> " + LocalDateTime.now(ZoneId.of("Etc/GMT-8")));
        System.out.println("GMT+8 ---> " + LocalDateTime.now(ZoneId.of("GMT+8")));
        System.out.println("GMT+08 ---> " + LocalDateTime.now(ZoneId.of("GMT+08")));
        System.out.println("GMT+08:00 ---> " + LocalDateTime.now(ZoneId.of("GMT+08:00")));
        System.out.println("UTC+8 ---> " + LocalDateTime.now(ZoneId.of("UTC+8")));
        System.out.println("UTC+08 ---> " + LocalDateTime.now(ZoneId.of("UTC+08")));
        System.out.println("UTC+08:00 ---> " + LocalDateTime.now(ZoneId.of("UTC+08:00")));
        System.out.println("UT+8 ---> " + LocalDateTime.now(ZoneId.of("UT+8")));
        System.out.println("UT+08 ---> " + LocalDateTime.now(ZoneId.of("UT+08")));
        System.out.println("UT+08:00 ---> " + LocalDateTime.now(ZoneId.of("UT+08:00")));
        System.out.println("+8 ---> " + LocalDateTime.now(ZoneId.of("+8")));
        System.out.println("+08 ---> " + LocalDateTime.now(ZoneId.of("+08")));
        System.out.println("+08:00 ---> " + LocalDateTime.now(ZoneId.of("+08:00")));
        System.out.println("Asia/Shanghai ---> " + LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
        System.out.println("Asia/Chongqing ---> " + LocalDateTime.now(ZoneId.of("Asia/Chongqing")));
        System.out.println("Asia/Chungking ---> " + LocalDateTime.now(ZoneId.of("Asia/Chungking")));
        System.out.println("Asia/Hong_Kong ---> " + LocalDateTime.now(ZoneId.of("Asia/Hong_Kong")));
        System.out.println("Hongkong ---> " + LocalDateTime.now(ZoneId.of("Hongkong")));
    }

    @Test
    void getAvailableZoneIds() {
        Set<String> availableZoneIds = DateZoneUtil.getAvailableZoneIds();
        System.out.println(availableZoneIds);
    }

    @Test
    void now() {
        ZonedDateTime now = DateZoneUtil.now();
        System.out.println(now);
    }

    @Test
    void testNow() {
        ZonedDateTime now = DateZoneUtil.now(ZoneId.of("GMT+9")); // 东九区
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);
        System.out.println(now);
        Assertions.assertEquals(now.getHour(), localDateTime.getHour());
    }

    @Test
    void of() {
        ZonedDateTime of = DateZoneUtil.of(LocalDateTime.now(), ZoneId.systemDefault());
        System.out.println(of);
    }

    @Test
    void testNow1() {
        ZoneId zoneId = ZoneId.of("GMT+9");// 东九区
        ZonedDateTime now = DateZoneUtil.now(Clock.system(zoneId)); // 东九区
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);
        System.out.println(now);
        Assertions.assertEquals(now.getHour(), localDateTime.getHour());
    }

}