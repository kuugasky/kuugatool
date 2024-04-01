package io.github.kuugasky.kuugatool.core.numerical;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyUtilTest {

    @Test
    void digitUppercase() {
        assertEquals("壹佰贰拾叁万肆仟伍佰陆拾柒元捌角柒分", MoneyUtil.formatMoneyUnit(BigDecimalUtil.of(1234567.87)));
        System.out.println(MoneyUtil.formatMoneyUnit(BigDecimalUtil.of(7891.56)));
        System.out.println(MoneyUtil.formatMoneyUnit(BigDecimalUtil.of(7891.56), false));
        System.out.println(MoneyUtil.formatMoneyUnit(BigDecimalUtil.of(-7891.56)));
    }

    /**
     * 金额格式化
     * 【pattern】
     * "0"：取一位整数
     * "#"：取所有整数部分
     * "0.00"：取一位整数和两位小数
     * "00.000"：取两位整数和三位小数，整数不足部分以0填补
     * "#.##%"：以百分比方式计数，并取两位小数
     * "#.#####E0"：显示为科学计数法，并取五位小数
     * "00.####E0"：显示为两位整数的科学计数法，并取四位小数
     * ",###"：每三位以逗号进行分隔
     * "光速大小为每秒,###米。"：将格式嵌入文本
     */
    @Test
    void formatForString() {
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "0"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "#"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "0.00"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "00.000"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "#.##%"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "#.#####E0"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "00.####E0"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(12345671111111.87), ",###"));
        System.out.println(MoneyUtil.formatForString(BigDecimalUtil.of(1234567.87), "###米"));
    }

    @Test
    void formatCurrency() {
        assertEquals(MoneyUtil.formatCurrency(BigDecimalUtil.of(1234567.87)), "¥1,234,567.87");
    }

    @Test
    void formatPercent() {
        assertEquals(MoneyUtil.formatPercent(BigDecimalUtil.of(0.1234567)), "12%");
    }

    @Test
    void formatPercent2() {
        assertEquals(MoneyUtil.formatPercent(BigDecimalUtil.of(0.1234567), 4), "12.3457%");
    }

    @Test
    void formatMoney() {
        assertEquals(MoneyUtil.formatMoney(BigDecimalUtil.of(1234567.87)), "1,234,568");
        assertEquals(MoneyUtil.formatMoney(BigDecimalUtil.of(1234567.87123456789), 2), "1,234,567.87");
        assertEquals(MoneyUtil.formatMoney(BigDecimalUtil.of(1234567.87123456789), 3), "1,234,567.871");
        assertEquals(MoneyUtil.formatMoney(BigDecimalUtil.of(1234567.87123456789), 4), "1,234,567.8712");
        assertEquals(MoneyUtil.formatMoney(BigDecimalUtil.of(1234567.87123456789), 5), "1,234,567.87123");
    }

}