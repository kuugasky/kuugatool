package io.github.kuugasky.kuugatool.core.date.time.core;

import io.github.kuugasky.kuugatool.core.date.time.entity.TimeParseEntity;

/**
 * 英文时间解析类
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
public class EnglishTimeParse extends TimeParse {

    @Override
    protected TimeParseEntity initParseUnit() {
        TimeParseEntity entity = new TimeParseEntity();
        entity.setYearUnit("y");
        entity.setMonthUnit("M");
        entity.setDayUnit("d");
        entity.setHourUnit("h");
        entity.setMinuteUnit("m");
        entity.setSecondUnit("s");
        return entity;
    }

}