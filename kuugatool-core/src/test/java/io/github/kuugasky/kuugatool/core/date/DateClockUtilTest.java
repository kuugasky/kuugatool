package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

class DateClockUtilTest {

    @Test
    void systemDefaultZone() {
        // 获取一个时钟，该时钟使用最佳可用系统时钟返回当前时刻，并使用默认时区转换为日期和时间。
        Clock clock = DateClockUtil.systemDefaultZone();
        Assertions.assertEquals(clock.toString(), "SystemClock[Asia/Shanghai]");
    }

    @Test
    void systemUTC() {
        // 使用UTC时区返回当前即时时间
        Clock clock = DateClockUtil.systemUTC();
        Assertions.assertEquals(clock.toString(), "SystemClock[Z]");
    }

    @Test
    void millis() {
        // 返回从 1970-01-01T00:00Z (UTC) 到开始测量的时钟的当前毫秒时刻。 millis 的值等价于 System
        long clock = DateClockUtil.millis();
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
        Assertions.assertEquals(clock, currentTimeMillis);
    }

    @Test
    void instant() {
        // 获取时钟的当前时刻
        Instant instant = DateClockUtil.instant();
        System.out.println(instant);
    }

    @Test
    void offset() {
        // Clock baseClock = Clock.systemDefaultZone();
        ZoneId zoneId = ZoneId.of("GMT+9");// 东九区˚
        Clock baseClock = Clock.system(zoneId);
        System.out.println(StringUtil.repeatNormal());

        System.out.println(ZoneId.systemDefault());

        System.out.println(StringUtil.repeatNormal());

        System.out.println(baseClock.instant());

        System.out.println(StringUtil.repeatNormal());
        // Obtained clock will be later than baseClock
        Clock clock = DateClockUtil.offset(baseClock, Duration.ofHours(1));
        System.out.println(clock.instant());

        // Obtained clock will be earlier than baseClock
        clock = DateClockUtil.offset(baseClock, Duration.ofHours(-1));
        System.out.println(clock.instant());

        // Obtained clock will be same as baseClock
        clock = DateClockUtil.offset(baseClock, Duration.ZERO);
        System.out.println(clock.instant());
    }

    @Test
    void system() {
        Clock system = DateClockUtil.system(ZoneId.systemDefault());
        Assertions.assertEquals(system.toString(), "SystemClock[Asia/Shanghai]");
    }

    @Test
    void tick() {
        Clock baseClock = Clock.systemDefaultZone();
        Clock clock = DateClockUtil.tick(baseClock, Duration.ofSeconds(3));

        for (int i = 0; i < 5; i++) {
            System.out.println(clock.instant());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void tickMillis() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        Clock clock = DateClockUtil.tickMillis(zoneId);
        for (int i = 0; i < 5; i++) {
            System.out.println(clock.instant());
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void tickSeconds() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        Clock clock = DateClockUtil.tickSeconds(zoneId);
        for (int i = 0; i < 5; i++) {
            System.out.println(clock.instant());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void tickMinutes() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        Clock clock = DateClockUtil.tickMinutes(zoneId);
        for (int i = 0; i < 3; i++) {
            System.out.println(clock.instant());
            try {
                Thread.sleep(90000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void fixed() throws InterruptedException {
        Instant instant = Instant.parse("2021-06-08T15:16:17.00Z");
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        Clock clock = DateClockUtil.fixed(instant, zoneId);
        Thread.sleep(1000);
        System.out.println(clock.instant());
    }

    @Test
    void withZone() {
        ZoneId zone1 = ZoneId.of("Asia/Aden");
        Clock clock1 = DateClockUtil.system(zone1);
        System.out.println(clock1.instant());

        ZoneId zone2 = ZoneId.of("America/Cuiaba");
        Clock clock2 = DateClockUtil.withZone(clock1, zone2);
        System.out.println(clock2.instant());
    }

    @Test
    void getZone() {
        Clock clock = Clock.systemDefaultZone();
        ZoneId zone = DateClockUtil.getZone(clock);
        System.out.println(zone.getId());
    }

    @Test
    void testEquals() {
        Clock clock = Clock.systemDefaultZone();
        System.out.println(DateClockUtil.equals(clock, Clock.systemDefaultZone()));
        System.out.println(DateClockUtil.equals(clock, Clock.systemUTC()));
    }

}