package io.github.kuugasky.kuugatool.core.date.interval;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TimeIntervalTest {

    @Test
    public void start() {
        // 开始计时并返回当前时间：毫秒
        long start = new TimeInterval().start();
        System.out.println(start);
        // 开始计时并返回当前时间：纳秒
        long start1 = new TimeInterval(true).start();
        System.out.println(start1);
    }

    @Test
    public void start1() {
        // 开始计时并返回当前时间：毫秒
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start("1000");
        ThreadUtil.sleep(1000);
        timeInterval.start("2000");
        ThreadUtil.sleep(1000);
        System.out.println("start1000耗时：" + timeInterval.intervalPretty("1000"));
        System.out.println("start2000耗时：" + timeInterval.intervalPretty("2000"));
    }

    @Test
    public void intervalRestart() {
        TimeInterval timeInterval = new TimeInterval();
        long start = timeInterval.start();
        System.out.println(start);
        ThreadUtil.sleep(1000);
        // 重新计时并返回从开始到当前的持续时间
        long l = timeInterval.intervalRestart();
        System.out.println("从start开始到l的时长，毫秒：" + l);
    }

    @Test
    public void restart() {
        TimeInterval timeInterval = new TimeInterval();
        System.out.println(timeInterval.start());
        // 重新开始计算时间（重置开始时间）
        timeInterval = timeInterval.restart();
        ThreadUtil.sleep(1000);
        System.out.println(timeInterval.start());
    }

    @Test
    public void interval() {
        TimeInterval timeInterval = new TimeInterval();
        System.out.println("start:" + timeInterval.start());
        ThreadUtil.sleep(1000);
        // 从开始到当前的间隔时间（毫秒数）
        System.out.println("从开始到当前的间隔时间:" + timeInterval.interval());
    }

    @Test
    public void intervalPretty() {
        TimeInterval timeInterval = new TimeInterval();
        ThreadUtil.sleep(3000);
        String s = timeInterval.intervalPretty();
        System.out.println("从开始到当前的间隔时间:" + s);
    }

    @Test
    public void intervalMs() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔时间
        ThreadUtil.sleep(100);
        long l = timeInterval.intervalMs();
        System.out.println(l);

    }

    @Test
    public void intervalSecond() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔秒数，取绝对值
        ThreadUtil.sleep(1000);
        long l = timeInterval.intervalSecond();
        System.out.println(l);
    }

    @Test
    public void intervalMinute() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔分钟数，取绝对值
        ThreadUtil.sleep(2, TimeUnit.MINUTES);
        long l = timeInterval.intervalMinute();
        System.out.println(l);
    }

    @Test
    public void intervalHour() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔小时数，取绝对值
        ThreadUtil.sleep(2, TimeUnit.HOURS);
        long l = timeInterval.intervalHour();
        System.out.println(l);
    }

    @Test
    public void intervalDay() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔天数，取绝对值
        ThreadUtil.sleep(2, TimeUnit.DAYS);
        long l = timeInterval.intervalDay();
        System.out.println(l);
    }

    @Test
    public void intervalWeek() {
        TimeInterval timeInterval = new TimeInterval();
        // 从开始到当前的间隔天数，取绝对值
        ThreadUtil.sleep(2, TimeUnit.DAYS);
        long l = timeInterval.intervalWeek();
        System.out.println(l);
    }

}