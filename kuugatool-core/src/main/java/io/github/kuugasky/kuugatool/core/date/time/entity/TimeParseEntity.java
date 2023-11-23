package io.github.kuugasky.kuugatool.core.date.time.entity;

import lombok.Data;

/**
 * 时间解析实体
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
@Data
public class TimeParseEntity {

    private boolean today;

    private String yearUnit;

    private String monthUnit;

    private String dayUnit;

    private String hourUnit;

    private String minuteUnit;

    private String secondUnit;

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private int second;

}