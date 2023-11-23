package io.github.kuugasky.kuugatool.core.number;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * 数值类型比对工具类
 *
 * @author kuuga
 * @since 2021-03-15
 */
public class NumberCompareUtilTest {

    @Test
    public void compareOfBigDecimal() {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(2);
        BigDecimal c = new BigDecimal(3);
        BigDecimal d = new BigDecimal(1);
        System.out.println(NumberCompareUtil.compareOfBigDecimal(a, b));
        System.out.println(NumberCompareUtil.compareOfBigDecimal(c, b));
        System.out.println(NumberCompareUtil.compareOfBigDecimal(a, d));
    }

    @Test
    public void compareOfDouble() {
        Double a = 1D;
        Double b = 2D;
        Double c = 3D;
        Double d = 1D;
        System.out.println(NumberCompareUtil.compareOfDouble(a, b));
        System.out.println(NumberCompareUtil.compareOfDouble(c, b));
        System.out.println(NumberCompareUtil.compareOfDouble(a, d));
    }

    @Test
    public void compareOfFloat() {
        Float a = 1F;
        Float b = 2F;
        Float c = 3F;
        Float d = 1F;
        System.out.println(NumberCompareUtil.compareOfFloat(a, b));
        System.out.println(NumberCompareUtil.compareOfFloat(c, b));
        System.out.println(NumberCompareUtil.compareOfFloat(a, d));
    }

    @Test
    public void compareOfInt() {
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 1;
        System.out.println(NumberCompareUtil.compareOfInt(a, b));
        System.out.println(NumberCompareUtil.compareOfInt(c, b));
        System.out.println(NumberCompareUtil.compareOfInt(a, d));
    }

    @Test
    public void compareOfLong() {
        long a = 1;
        long b = 2;
        long c = 3;
        long d = 1;
        System.out.println(NumberCompareUtil.compareOfLong(a, b));
        System.out.println(NumberCompareUtil.compareOfLong(c, b));
        System.out.println(NumberCompareUtil.compareOfLong(a, d));
        Long e = 1L;
        Long f = 2L;
        Long g = 3L;
        Long l = 1L;
        Long x = 9L;
        Long y = 10L;
        System.out.println(NumberCompareUtil.compareOfLong(e, f));
        System.out.println(NumberCompareUtil.compareOfLong(g, f));
        System.out.println(NumberCompareUtil.compareOfLong(e, l));
        System.out.println(NumberCompareUtil.compareOfLong(x, y));
    }
}