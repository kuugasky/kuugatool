package io.github.kuugasky.kuugatool.core.date.interval;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.date.DateUnit;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class GroupTimeIntervalTest {

    @Test
    void clear() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.clear();
    }

    @Test
    void start() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalPretty("1"));
    }

    @Test
    void intervalRestart() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalPretty("1"));
        groupTimeInterval.intervalRestart("1");
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalPretty("1"));
    }

    @Test
    void interval() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(true);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.interval("1"));
    }

    @Test
    void testInterval() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.interval("1", DateUnit.MS));
    }

    @Test
    void intervalMs() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalMs("1"));
    }

    @Test
    void intervalSecond() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalSecond("1"));
    }

    @Test
    void intervalMinute() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalMinute("1"));
    }

    @Test
    void intervalHour() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalHour("1"));
    }

    @Test
    void intervalDay() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalDay("1"));
    }

    @Test
    void intervalWeek() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalWeek("1"));
    }

    @Test
    void intervalPretty() {
        GroupTimeInterval groupTimeInterval = new GroupTimeInterval(false);
        groupTimeInterval.start("1");

        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        System.out.println(groupTimeInterval.intervalPretty("1"));
    }

}