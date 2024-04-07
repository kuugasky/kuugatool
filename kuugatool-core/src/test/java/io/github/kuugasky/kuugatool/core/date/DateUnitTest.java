package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class DateUnitTest {

    @Test
    void test() {
        System.out.println("一周毫秒:" + DateUnit.WEEKS.getMillis());
        System.out.println("一天毫秒:" + DateUnit.DAYS.getMillis());
        System.out.println("一小时毫秒:" + DateUnit.HOURS.getMillis());
        System.out.println("一分钟毫秒:" + DateUnit.MINUTES.getMillis());
        System.out.println("一秒毫秒:" + DateUnit.SECONDS.getMillis());
        System.out.println("一毫秒:" + DateUnit.MS.getMillis());

        System.out.println(StringUtil.repeatNormal());

        System.out.println(ChronoUnit.WEEKS.getDuration().toMillis());
        System.out.println(ChronoUnit.DAYS.getDuration().toMillis());
        System.out.println(ChronoUnit.HOURS.getDuration().toMillis());
        System.out.println(ChronoUnit.MINUTES.getDuration().toMillis());
        System.out.println(ChronoUnit.SECONDS.getDuration().toMillis());
        System.out.println(ChronoUnit.MILLIS.getDuration().toMillis());
    }

    @Test
    public void toChronoUnit() {
        // 小时转ChronoUnit小时
        ChronoUnit chronoUnit = DateUnit.toChronoUnit(DateUnit.HOURS);
        assert chronoUnit != null;
        System.out.println(chronoUnit);

        ChronoUnit chronoUnit1 = DateUnit.HOURS.toChronoUnit();
        System.out.println("1小时多少秒:" + chronoUnit1.getDuration().toSeconds());
        System.out.println("1小时多少毫秒:" + chronoUnit1.getDuration().toMillis());
    }

    @Test
    public void of() {
        DateUnit of = DateUnit.of(ChronoUnit.DAYS);
        assert of != null;
        System.out.println(of);
    }

    @Test
    public void values() {
        DateUnit[] values = DateUnit.values();
        System.out.println(Arrays.toString(values));
    }

    @Test
    public void valueOf() {
        DateUnit days = DateUnit.valueOf("MS");
        System.out.println(days);
    }

}