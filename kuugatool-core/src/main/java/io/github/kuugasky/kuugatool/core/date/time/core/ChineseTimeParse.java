package io.github.kuugasky.kuugatool.core.date.time.core;

import io.github.kuugasky.kuugatool.core.date.time.entity.TimeParseEntity;

/**
 * 中文时间解析类
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
public class ChineseTimeParse extends TimeParse {

    @Override
    protected TimeParseEntity initParseUnit() {
        TimeParseEntity entity = new TimeParseEntity();
        entity.setYearUnit("年");
        entity.setMonthUnit("月");
        entity.setDayUnit("日");
        entity.setHourUnit("时");
        entity.setMinuteUnit("分");
        entity.setSecondUnit("秒");
        return entity;
    }

}