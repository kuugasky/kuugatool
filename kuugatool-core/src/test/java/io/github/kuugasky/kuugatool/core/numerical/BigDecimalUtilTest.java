package io.github.kuugasky.kuugatool.core.numerical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

class BigDecimalUtilTest {

    @Test
    void test() {
        // new BigDecimal(0.1)得到的是0.1的近似值
        Assertions.assertEquals(new BigDecimal(0.1).toString(), "0.1000000000000000055511151231257827021181583404541015625");
        // new BigDecimal("0.1")得到的是0.1的精确值，标度值1和标度1
        Assertions.assertEquals(new BigDecimal("0.1").toString(), "0.1");
        // BigDecimal.valueOf(0.1)得到的是0.1的精确值，标度值1和标度1
        Assertions.assertEquals(BigDecimal.valueOf(0.1).toString(), "0.1");
    }

    @Test
    void tst() {
        // 标度0
        BigDecimal bigDecimal = new BigDecimal(1);
        // 标度0
        BigDecimal bigDecimal1 = new BigDecimal(1);
        System.out.printf("%s -> %s%n", bigDecimal.scale(), bigDecimal1.scale());
        System.out.println(bigDecimal.equals(bigDecimal1));
        // 标度0
        BigDecimal bigDecimal2 = new BigDecimal(1);
        // 标度0
        BigDecimal bigDecimal3 = new BigDecimal(1.0);
        System.out.printf("%s -> %s%n", bigDecimal2.scale(), bigDecimal3.scale());
        System.out.println(bigDecimal2.equals(bigDecimal3));
        // 标度0
        BigDecimal bigDecimal4 = new BigDecimal("1");
        // 标度1
        BigDecimal bigDecimal5 = new BigDecimal("1.0");
        System.out.printf("%s -> %s%n", bigDecimal4.scale(), bigDecimal5.scale());
        System.out.println(bigDecimal4.equals(bigDecimal5));
    }

    @Test
    void smallerThan() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.smallerThan(first, second));
    }

    @Test
    void biggerThan() {
        BigDecimal first = new BigDecimal("1.2");
        BigDecimal second = new BigDecimal("1.1");
        System.out.println(BigDecimalUtil.biggerThan(first, second));
    }

    @Test
    void isEqual() {
        BigDecimal first = new BigDecimal("0.1");
        BigDecimal second = new BigDecimal("0.100");
        System.out.println(BigDecimalUtil.isEqual(first, second));
    }

    @Test
    void formatForString() {
        // 圆周率
        BigDecimal pi = new BigDecimal("3.1415927");
        // 取一位整数
        System.out.println(BigDecimalUtil.formatForString(pi, "0"));// 3
        // 取一位整数和两位小数
        System.out.println(BigDecimalUtil.formatForString(pi, "0.00"));// 3.14
        // 取两位整数和三位小数，整数不足部分以0填补。
        System.out.println(BigDecimalUtil.formatForString(pi, "00.000"));// 03.142
        // 取所有整数部分
        System.out.println(BigDecimalUtil.formatForString(pi, "#"));// 3
        // 以百分比方式计数，并取两位小数
        System.out.println(BigDecimalUtil.formatForString(pi, "#.##%"));// 314.16%
        BigDecimal c = new BigDecimal("299792458");// 光速
        // 显示为科学计数法，并取五位小数
        System.out.println(BigDecimalUtil.formatForString(c, "#.#####E0"));// 2.99792E8
        // 显示为两位整数的科学计数法，并取四位小数
        System.out.println(BigDecimalUtil.formatForString(c, "00.####E0"));// 29.9792E7
        // 每三位以逗号进行分隔。
        System.out.println(BigDecimalUtil.formatForString(c, ",###"));// 299,792,458
        // 将格式嵌入文本
        System.out.println(BigDecimalUtil.formatForString(c, "光速大小为每秒,###米。"));// 299,792,458
    }

    @Test
    void formatCurrency() {
        String formatCurrency = BigDecimalUtil.formatCurrency(BigDecimalUtil.of(1.11f));
        System.out.println(formatCurrency);
    }

    @Test
    void formatPercent() {
        String formatCurrency = BigDecimalUtil.formatPercent(BigDecimalUtil.of(0.0311));
        System.out.println(formatCurrency);
        String formatCurrency2 = BigDecimalUtil.formatPercent(BigDecimalUtil.of(0.0311), 2);
        System.out.println(formatCurrency2);
    }

    @Test
    void add() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.add(first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testAdd() {
        System.out.println(BigDecimalUtil.add("1.1", "1.2"));
    }

    @Test
    void testAdd1() {
        System.out.println(BigDecimalUtil.add(1.1f, 1.2f));
        System.out.println(BigDecimalUtil.add(1, 1));
        System.out.println(BigDecimalUtil.add(3, 1));
    }

    @Test
    void subtract() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.subtract(first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testSubtract() {
        System.out.println(BigDecimalUtil.subtract("1.1", "1.2"));
    }

    @Test
    void multiply() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.multiply(first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testMultiply() {
        System.out.println(BigDecimalUtil.multiply("1.1", "1.2"));
    }

    @Test
    void divide() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.divide(first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testDivide() {
        System.out.println(BigDecimalUtil.divide("1.1", "1.2"));
    }

    @Test
    void testDivide1() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.divide(1, first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testDivide2() {
        BigDecimal first = new BigDecimal("1.1");
        BigDecimal second = new BigDecimal("1.2");
        System.out.println(BigDecimalUtil.divide(2, RoundingMode.DOWN, first.doubleValue(), second.doubleValue()));
    }

    @Test
    void testDivide3() {
        System.out.println(BigDecimalUtil.divide(1, "1.1", "1.2"));
    }

    @Test
    void testDivide4() {
        System.out.println(BigDecimalUtil.divide(2, RoundingMode.DOWN, "1.1", "1.2"));
    }

    @Test
    void round() {
        BigDecimal first = new BigDecimal("1.1");
        System.out.println(BigDecimalUtil.round(first.doubleValue(), 1));
    }

    @Test
    void testRound() {
        BigDecimal first = new BigDecimal("1.1");
        System.out.println(BigDecimalUtil.round(first.doubleValue(), 1, RoundingMode.UP));
    }

    @Test
    void testRound1() {
        System.out.println(BigDecimalUtil.round("1.1", 1));
    }

    @Test
    void testRound2() {
        // 四舍五入
        System.out.println("HALF_UP");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.HALF_UP));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.HALF_UP));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.HALF_UP));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.HALF_UP));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.HALF_UP));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.HALF_UP));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.HALF_UP));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.HALF_UP));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.HALF_UP));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.HALF_UP));
        // 五舍六入
        System.out.println("HALF_DOWN");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.HALF_DOWN));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.HALF_DOWN));

        System.out.println("HALF_EVEN");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.055 --> " + BigDecimalUtil.round("1.055", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.HALF_EVEN));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.HALF_EVEN));

        System.out.println("UP");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.UP));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.UP));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.UP));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.UP));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.UP));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.UP));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.UP));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.UP));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.UP));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.UP));

        System.out.println("DOWN");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.DOWN));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.DOWN));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.DOWN));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.DOWN));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.DOWN));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.DOWN));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.DOWN));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.DOWN));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.DOWN));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.DOWN));

        System.out.println("CEILING");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.CEILING));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.CEILING));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.CEILING));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.CEILING));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.CEILING));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.CEILING));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.CEILING));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.CEILING));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.CEILING));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.CEILING));

        System.out.println("FLOOR");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.FLOOR));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.FLOOR));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.FLOOR));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.FLOOR));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.FLOOR));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.FLOOR));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.FLOOR));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.FLOOR));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.FLOOR));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.FLOOR));

        System.out.println("UNNECESSARY");
        System.out.println("1.00 --> " + BigDecimalUtil.round("1.00", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.01 --> " + BigDecimalUtil.round("1.01", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.02 --> " + BigDecimalUtil.round("1.02", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.03 --> " + BigDecimalUtil.round("1.03", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.04 --> " + BigDecimalUtil.round("1.04", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.05 --> " + BigDecimalUtil.round("1.05", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.06 --> " + BigDecimalUtil.round("1.06", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.07 --> " + BigDecimalUtil.round("1.07", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.08 --> " + BigDecimalUtil.round("1.08", 1, RoundingMode.UNNECESSARY));
        System.out.println("1.09 --> " + BigDecimalUtil.round("1.09", 1, RoundingMode.UNNECESSARY));
    }

}