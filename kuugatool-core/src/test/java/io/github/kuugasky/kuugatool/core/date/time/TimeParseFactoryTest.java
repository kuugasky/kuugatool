package io.github.kuugasky.kuugatool.core.date.time;

import io.github.kuugasky.kuugatool.core.date.time.core.TimeParse;
import io.github.kuugasky.kuugatool.core.date.time.enums.LimitTime;
import org.junit.jupiter.api.Test;

class TimeParseFactoryTest {

    @Test
    void testCreateEnglish() {
        String requestLimit = "0y0M0d0h0m10s";
        TimeParse timeParse = TimeParseFactory.create(requestLimit);
        long parse = timeParse.parse(requestLimit);
        System.out.println(parse);
    }

    @Test
    void testCreateChina() {
        String requestLimit = "0年1月0日0时0分0秒";
        TimeParse timeParse = TimeParseFactory.create(requestLimit);
        long parse = timeParse.parse(requestLimit);
        System.out.println(parse);
    }

    @Test
    void testCreateToday() {
        String requestLimit = LimitTime.TODAY.name();
        TimeParse timeParse = TimeParseFactory.create(requestLimit);
        long parse = timeParse.parse(requestLimit);
        System.out.println(parse);
    }

    @Test
    void testCreateTodayNow() {
        String requestLimit = LimitTime.TODAY_NOW.name();
        TimeParse timeParse = TimeParseFactory.create(requestLimit);
        long parse = timeParse.parse(requestLimit);
        System.out.println(parse);
    }

}