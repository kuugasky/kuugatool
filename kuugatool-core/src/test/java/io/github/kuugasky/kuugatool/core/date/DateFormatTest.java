package io.github.kuugasky.kuugatool.core.date;

import java.util.Date;

public class DateFormatTest {

    public static void main(String[] args) {
        System.out.println(DateUtil.parse(DateFormat.yyyyMMdd, "19900514"));
        System.out.println(DateUtil.parse(DateFormat.yyyyMM, "199005"));
        System.out.println(DateUtil.parse(DateFormat.yyyyMM2, "1990-05"));
        System.out.println(DateUtil.parse(DateFormat.yyyyMM3, "1990/05"));
        System.out.println(DateUtil.parse(DateFormat.MMdd, "0513"));
        System.out.println(DateUtil.parse(DateFormat.MMdd2, "05-13"));
        System.out.println(DateUtil.parse(DateFormat.MMdd3, "05/13"));
        System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd, "1990-05-13"));
        System.out.println(DateUtil.parse(DateFormat.HH_mm_ss, "12:00:00"));
        System.out.println(DateUtil.parse(DateFormat.yyyyMMddHHmmss, "19900513120000"));
        System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm, "1990-05-13 12:00"));
        System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "1990-05-13 12:00:00"));
        System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, "1990-05-13 12:00:00.999"));

        System.out.println(DateUtil.format(DateFormat.yyyyMMdd, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyyMM, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyyMM2, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyyMM3, new Date()));
        System.out.println(DateUtil.format(DateFormat.MMdd, new Date()));
        System.out.println(DateUtil.format(DateFormat.MMdd2, new Date()));
        System.out.println(DateUtil.format(DateFormat.MMdd3, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd2, new Date()));
        System.out.println(DateUtil.format(DateFormat.HH_mm_ss, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyyMMddHHmmss, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, new Date()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, new Date()));
    }

}