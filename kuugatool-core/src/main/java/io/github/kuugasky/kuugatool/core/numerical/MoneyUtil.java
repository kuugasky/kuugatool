package io.github.kuugasky.kuugatool.core.numerical;

import java.math.BigDecimal;

/**
 * MoneyUtil
 *
 * @author kuuga
 * @since 2024/4/1
 */
public class MoneyUtil {

    /**
     * 数字金额大写转换 先写个完整的然后将如零拾替换成零
     *
     * @param money 数字
     * @return 中文大写数字
     */
    public static String digitUppercase(BigDecimal money) {
        return digitUppercase(money, true);
    }

    /**
     * 数字金额大写转换 先写个完整的然后将如零拾替换成零
     *
     * @param money     数字
     * @param upperCase 大写中文
     * @return 中文大写数字
     */
    public static String digitUppercase(BigDecimal money, boolean upperCase) {
        String[] fraction = {"角", "分"};
        String[] digit;
        String[][] unit;
        if (upperCase) {
            digit = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
            unit = new String[][]{{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};
        } else {
            digit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            unit = new String[][]{{"元", "万", "亿"}, {"", "十", "百", "千"}};
        }

        double moneyDouble = money.doubleValue();
        String head = moneyDouble < 0 ? "负" : "";
        moneyDouble = Math.abs(moneyDouble);

        if (String.valueOf(money).split("\\.")[1].length() > 2) {
            throw new RuntimeException("数字金额转大写，最小单位仅支持到分");
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fraction.length; i++) {
            s.append((digit[(int) (Math.floor(moneyDouble * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", ""));
        }
        if (s.isEmpty()) {
            s = new StringBuilder("整");
        }
        int integerPart = (int) Math.floor(moneyDouble);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            StringBuilder p = new StringBuilder();
            for (int j = 0; j < unit[1].length && moneyDouble > 0; j++) {
                p.insert(0, digit[integerPart % 10] + unit[1][j]);
                integerPart = integerPart / 10;
            }
            s.insert(0, p.toString().replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]);
        }
        return head + s.toString().replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
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
     *
     * @param value   金额
     * @param pattern 格式
     * @return str
     */
    public static String formatForString(BigDecimal value, String pattern) {
        return BigDecimalUtil.formatForString(value, pattern);
    }

    /**
     * 货币格式化
     *
     * @param bigDecimal bigDecimal
     * @return "¥1,234,567.87"
     */
    public static String formatCurrency(BigDecimal bigDecimal) {
        return BigDecimalUtil.formatCurrency(bigDecimal);
    }

    /**
     * 金额格式化
     *
     * @param bigDecimal bigDecimal
     * @return 1, 234, 567, 87
     */
    public static String formatMoney(BigDecimal bigDecimal) {
        return formatMoney(bigDecimal, 0);
    }

    /**
     * 金额格式化
     *
     * @param bigDecimal 金额
     * @param accuracy   精度，小数点后几位
     * @return 1, 234, 567.89
     */
    public static String formatMoney(BigDecimal bigDecimal, int accuracy) {
        if (accuracy < 0) {
            throw new RuntimeException("accuracy must be greater than or equal to 0");
        }
        if (accuracy == 0) {
            return BigDecimalUtil.formatForString(bigDecimal, ",###");
        } else {
            return BigDecimalUtil.formatForString(bigDecimal, ",###.".concat("#".repeat(accuracy)));
        }
    }

    /**
     * 百分比格式化
     * <p>
     * 默认丢失百分比后小数点后位数
     *
     * @param bigDecimal bigDecimal
     * @return 12%
     */
    public static String formatPercent(BigDecimal bigDecimal) {
        return BigDecimalUtil.formatPercent(bigDecimal);
    }

    /**
     * 百分比格式化
     * <p>
     * bigDecimal:0.1234567<br>
     * maximumFractionDigits:3<br>
     * -> 12.3457%
     *
     * @param bigDecimal bigDecimal
     * @param accuracy   精度，小数点后几位
     * @return 12.3457%
     */
    public static String formatPercent(BigDecimal bigDecimal, int accuracy) {
        if (accuracy < 0) {
            throw new RuntimeException("accuracy must be greater than or equal to 0");
        }
        return BigDecimalUtil.formatPercent(bigDecimal, accuracy);
    }

}
