package io.github.kuugasky.kuugatool.core.date;

public class SecondFormatUtilTest {

    public static void main(String[] args) {
        System.out.println(SecondFormatUtil.getSecondFormat(10000L + 86400L, false, true));

        System.out.println(SecondFormatUtil.getSecondFormat(12345, false, true));
        System.out.println(SecondFormatUtil.getSecondFormat(145, true, true));
        System.out.println(SecondFormatUtil.fillSquareBrackets(SecondFormatUtil.getSecondFormat(145)));

        System.out.println(SecondFormatUtil.getSecondFormat(145, true));
        System.out.println(SecondFormatUtil.getSecondFormat(0));
        System.out.println(SecondFormatUtil.getSecondFormat(0, true));

        System.out.println(60 * 60 * 24);
        System.out.println(SecondFormatUtil.getSecondFormat(86400L + 1L));
    }

}