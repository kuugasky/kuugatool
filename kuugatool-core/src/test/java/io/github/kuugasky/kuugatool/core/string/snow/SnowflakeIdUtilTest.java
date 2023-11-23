package io.github.kuugasky.kuugatool.core.string.snow;

import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.lang.Console;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class SnowflakeIdUtilTest {

    public static void main(String[] args) {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        Set<Long> set = SetUtil.newHashSet();
        for (int i = 0; i < 10000; i++) {
            long snowflakeId = SnowflakeIdUtil.getSnowflakeId();
            System.out.println(snowflakeId);
            set.add(snowflakeId);
        }
        Console.redLog(set.size());
        Console.blueLog("1w " + timeInterval.intervalPretty());
    }

    @Test
    public void test() {
        System.out.println(SnowflakeIdUtil.getSnowflakeIdStr());
    }

}