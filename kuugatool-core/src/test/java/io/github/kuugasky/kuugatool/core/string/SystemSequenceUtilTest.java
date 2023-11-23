package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.date.DateFormat;
import org.junit.jupiter.api.Test;

public class SystemSequenceUtilTest {

    @Test
    public void randomSequenceNumber() {
        // 生成流水编号,代表标志
        String Kuuga = SystemSequenceUtil.randomSequenceNumber("kuuga");
        System.out.println(Kuuga);
    }

    @Test
    public void testRandomSequenceNumber() {
        // 生成流水编号,代表标志，指定随机数位数
        System.out.println(SystemSequenceUtil.randomSequenceNumber("kuuga", 5));
    }

    @Test
    public void testRandomSequenceNumber1() {
        // 生成流水编号,代表标志，指定时间格式和随机数位数
        System.out.println(SystemSequenceUtil.randomSequenceNumber("kuuga", DateFormat.yyyyMMdd, 5));
    }

    @Test
    public void randomEnglishNumber() {
        // 大小写英文字母混搭
        System.out.println(SystemSequenceUtil.randomEnglishNumber(10, SystemSequenceUtil.CharType.ORDINARY));
        // 小写英文字母
        System.out.println(SystemSequenceUtil.randomEnglishNumber(10, SystemSequenceUtil.CharType.LOWER_CASE));
        // 大写英文字母
        System.out.println(SystemSequenceUtil.randomEnglishNumber(10, SystemSequenceUtil.CharType.UPPER_CASE));
        // 字符和数字混搭
        System.out.println(SystemSequenceUtil.randomEnglishNumber(10, SystemSequenceUtil.CharType.CHAR_NUMBER));
    }

}