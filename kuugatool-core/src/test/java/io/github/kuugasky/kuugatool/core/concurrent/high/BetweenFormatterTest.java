package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.date.interval.BetweenFormatter;
import org.junit.jupiter.api.Test;

public class BetweenFormatterTest {

    @Test
    public void format() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        String format = betweenFormatter.format();
        System.out.println(format);
        BetweenFormatter betweenFormatter1 = new BetweenFormatter(10000000001L, BetweenFormatter.Level.MILLISECOND, 5);
        System.out.println(betweenFormatter1.format(BetweenFormatter.Pattern.CHINESE));
        System.out.println(betweenFormatter1.format(BetweenFormatter.Pattern.ENGLISH_UPPER));
        System.out.println(betweenFormatter1.format(BetweenFormatter.Pattern.ENGLISH_LOWER));
    }

    @Test
    public void getBetweenMs() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        long format = betweenFormatter.getBetweenMs();
        System.out.println(format);
    }

    @Test
    public void setBetweenMs() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        betweenFormatter.setBetweenMs(200000);
        System.out.println(betweenFormatter.format());
    }

    @Test
    public void getLevel() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        BetweenFormatter.Level format = betweenFormatter.getLevel();
        System.out.println(format);
    }

    @Test
    public void setLevel() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        betweenFormatter.setLevel(BetweenFormatter.Level.SECOND);
        System.out.println(betweenFormatter.format());
    }

    @Test
    public void testToString() {
        BetweenFormatter betweenFormatter = new BetweenFormatter(100000, BetweenFormatter.Level.MILLISECOND);
        System.out.println(betweenFormatter);
    }

}