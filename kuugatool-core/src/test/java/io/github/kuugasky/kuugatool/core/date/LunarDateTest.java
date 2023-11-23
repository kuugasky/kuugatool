package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

public class LunarDateTest {

    @Test
    public void getMaxYear() {
        // 获取支持的最大年（包括）
        System.out.println(LunarDate.getMaxYear());
    }

    @Test
    public void yearDays() {
        // 传回农历 y年的总天数
        System.out.println(LunarDate.yearDays(2021));
        System.out.println(LunarDate.yearDays(2022));
    }

    @Test
    public void leapDays() {
        // 传回农历 y年闰月的天数
        System.out.println(LunarDate.leapDays(1990));
    }

    @Test
    public void monthDays() {
        // 传回农历 y年m月的总天数
        System.out.println(LunarDate.monthDays(2021, 7));
    }

    @Test
    public void leapMonth() {
        // 传回农历 y年闰哪个月 1-12 , 没闰传回 0
        System.out.println(LunarDate.leapMonth(1990));
    }

}