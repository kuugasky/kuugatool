package io.github.kuugasky.kuugatool.core.date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 日期格式化枚举
 *
 * @author kuuga
 */
public enum DateFormat {

    /**
     * 常用格式
     */
    yyyyMMdd("yyyyMMdd"),
    yyyy_MM_dd("yyyy-MM-dd"),
    yyyy_MM_dd2("yyyy/MM/dd"),
    yyyy_MM_dd3("yyyy年MM月dd日"),
    yyMMdd("yyMMdd"),

    yyyyMM("yyyyMM"),
    yyyyMM2("yyyy-MM"),
    // 支持format，不支持parse
    yyyyMM3("yyyy/MM"),

    // 支持format，不支持parse
    MMdd("MMdd"),
    // 支持format，不支持parse
    MMdd2("MM-dd"),
    // 支持format，不支持parse
    MMdd3("MM/dd"),

    HH_mm_ss("HH:mm:ss"),

    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    yyyy_MM_dd_HH_mm_ss2("yyyy/MM/dd HH:mm:ss"),
    yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss.SSS");

    static final List<DateFormat> VALID_PARSE_FORMATS = new ArrayList<>(7);
    /**
     * 特殊格式
     */
    static final List<DateFormat> SPECIAL_FORMATS = Arrays.asList(DateFormat.yyyyMM, DateFormat.yyyyMM2, DateFormat.yyyyMM3, DateFormat.yyMMdd);
    /**
     * 日期格式
     */
    static final List<DateFormat> DATE_FORMATS = Arrays.asList(DateFormat.yyyy_MM_dd, DateFormat.yyyyMMdd, DateFormat.yyyy_MM_dd2, DateFormat.yyyy_MM_dd3);
    /**
     * 时间格式
     */
    static final List<DateFormat> TIME_FORMATS = Collections.singletonList(DateFormat.HH_mm_ss);
    /**
     * 日期时间格式
     */
    static final List<DateFormat> DATE_TIME_FORMATS = Arrays.asList(
            DateFormat.yyyy_MM_dd_HH_mm, DateFormat.yyyy_MM_dd_HH_mm_ss2,
            DateFormat.yyyyMMddHHmmss, DateFormat.yyyy_MM_dd_HH_mm_ss, DateFormat.yyyy_MM_dd_HH_mm_ss_SSS);

    static {
        VALID_PARSE_FORMATS.add(DateFormat.yyyyMMdd);
        VALID_PARSE_FORMATS.add(DateFormat.yyyyMM);
        VALID_PARSE_FORMATS.add(DateFormat.yyyyMM2);
        VALID_PARSE_FORMATS.add(DateFormat.yyyyMM3);
        VALID_PARSE_FORMATS.add(DateFormat.yyMMdd);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd2);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd3);
        VALID_PARSE_FORMATS.add(DateFormat.HH_mm_ss);
        VALID_PARSE_FORMATS.add(DateFormat.yyyyMMddHHmmss);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd_HH_mm_ss);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd_HH_mm_ss2);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd_HH_mm);
        VALID_PARSE_FORMATS.add(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS);
    }

    DateFormat(String format) {
        this.format = format;
    }

    private final String format;

    public String format() {
        return format;
    }

}
