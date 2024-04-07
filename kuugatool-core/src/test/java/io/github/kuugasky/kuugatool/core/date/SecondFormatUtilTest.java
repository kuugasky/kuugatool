package io.github.kuugasky.kuugatool.core.date;

public class SecondFormatUtilTest {

    public static void main(String[] args) {
        // 超过1天[1d 02:46:40]
        System.out.println(SecondsFormatUtil.format(10000L + 86400L, false, true));
        // 1天内[03:25:45]
        System.out.println(SecondsFormatUtil.format(12345, false, true));
        // 不满1小时，补齐0小时格式[00:02:25]
        System.out.println(SecondsFormatUtil.format(145, true, true));
        // 不满1小时，不补齐0小时格式[02:25]
        System.out.println(SecondsFormatUtil.fillSquareBrackets(SecondsFormatUtil.format(145)));
        // 不满1小时，补齐0小时格式00:02:25，不带中括号
        System.out.println(SecondsFormatUtil.format(145, true));
        // 0秒，默认补齐0分钟00:00
        System.out.println(SecondsFormatUtil.format(0));
        // 0秒，补齐0小时0分钟00:00:00
        System.out.println(SecondsFormatUtil.format(0, true));
        // 1天秒数
        System.out.println(60 * 60 * 24);
        // 1天加1秒1d 00:01
        System.out.println(SecondsFormatUtil.format(86400L + 1L));
        // 1天加1秒，补齐0小时1d 00:00:01
        System.out.println(SecondsFormatUtil.format(86400L + 1L, true));
        // 1天加1秒，补齐0小时，带中括号[1d 00:00:01]
        System.out.println(SecondsFormatUtil.format(86400L + 1L, true, true));
    }

}