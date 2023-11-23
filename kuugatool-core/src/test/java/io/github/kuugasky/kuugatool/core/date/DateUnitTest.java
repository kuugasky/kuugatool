package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class DateUnitTest {

    @Test
    void test() {
        System.out.println(DateUnit.WEEK.getMillis());
        System.out.println(DateUnit.DAY.getMillis());
        System.out.println(DateUnit.HOUR.getMillis());
        System.out.println(DateUnit.MINUTE.getMillis());
        System.out.println(DateUnit.SECOND.getMillis());
        System.out.println(DateUnit.MS.getMillis());

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
        ChronoUnit chronoUnit = DateUnit.toChronoUnit(DateUnit.HOUR);
        assert chronoUnit != null;
        System.out.println(chronoUnit);

        ChronoUnit chronoUnit1 = DateUnit.HOUR.toChronoUnit();
        System.out.println(chronoUnit1.getDuration().toSeconds());
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