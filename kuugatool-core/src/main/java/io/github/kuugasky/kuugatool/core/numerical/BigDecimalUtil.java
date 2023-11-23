package io.github.kuugasky.kuugatool.core.numerical;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 大数值型精度计算工具类
 *
 * @author kuuga
 */
public final class BigDecimalUtil {

    @AllArgsConstructor
    enum Type {
        /**
         *
         */
        HALF_UP("向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入", "四舍五入"),
        HALF_DOWN("向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入", "五舍六入"),
        /*
         * 四舍五入模式向{@literal“近邻”}四舍五入，除非两个邻居都是等距的，在这种情况下，向偶数邻居四舍五入。
         * 如果丢弃分数左侧的数字是奇数，则表现为{@code RoundingMode.HALF_UP}；如果是偶数，则表现为{@code RoundingMode.HALF_DOWN}。
         * 请注意，这是四舍五入模式，在一系列计算中反复应用时，在统计学上最大限度地减少累积误差。
         * 它有时被称为{@literal“Banker's rounding”，}主要在美国使用。
         * 这种四舍五入模式类似于Java中用于{@code float}和{@code double}算术的四舍五入策略。
         */
        HALF_EVEN("向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入", "“银行家舍入法”，主要在美国使用。四舍六入，五分向相邻的偶数舍入。"),
        UP("远离零方向舍入的舍入模式", "末位大于1的全部向上加1"),
        DOWN("向零方向舍入的舍入模式", "末位舍弃"),

        CEILING("向正无限大方向舍入的舍入模式", "末位大于1的全部向上加1"),
        FLOOR("向负无限大方向舍入的舍入模式", "末位舍弃"),

        UNNECESSARY("用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入", StringUtil.EMPTY),
        ;
        @Getter
        private final String desc;
        @Getter
        private final String remark;
    }

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private BigDecimalUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 默认保留小数点后位数
     */
    private final static int DEFAULT_DIV_SCALE = 2;

    public static BigDecimal of(Number number) {
        return new BigDecimal(String.valueOf(number));
    }

    /**
     * 相加
     *
     * @param params params
     * @return BigDecimal
     */
    public static BigDecimal add(double... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (double param : params) {
            sum = sum.add(new BigDecimal(Double.toString(param)));
        }
        return sum;

    }

    /**
     * 相加
     *
     * @param params params
     * @return BigDecimal
     */
    public static BigDecimal add(String... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (String param : params) {
            sum = sum.add(new BigDecimal(Double.valueOf(param).toString()));
        }
        return sum;
    }

    /**
     * 减法
     */
    public static BigDecimal subtract(double... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.valueOf(params[0]);
        for (int i = 1; i < params.length; i++) {
            result = result.subtract(new BigDecimal(Double.toString(params[i])));
        }
        return result;

    }

    /**
     * 减法
     */
    public static BigDecimal subtract(String... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal result;
        result = new BigDecimal(StringUtil.removeAllSpace(params[0]));
        for (int i = 1; i < params.length; i++) {
            result = result.subtract(new BigDecimal(String.valueOf(Double.valueOf(params[i]))));
        }
        return result;
    }

    /**
     * 相乘
     */
    public static BigDecimal multiply(double... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = new BigDecimal(BigInteger.ONE);
        for (double param : params) {
            result = result.multiply(new BigDecimal(Double.toString(param)));
        }
        return round(result.doubleValue(), 2);
    }

    public static BigDecimal multiply(String... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = new BigDecimal(BigInteger.ONE);
        for (String param : params) {
            result = result.multiply(new BigDecimal(String.valueOf(Double.valueOf(param))));
        }
        return round(result.doubleValue(), 2);
    }

    /**
     * 相除
     *
     * @return double
     */
    public static BigDecimal divide(double... params) {
        return divide(DEFAULT_DIV_SCALE, params);
    }

    public static BigDecimal divide(int scale, double... params) {
        return divide(scale, RoundingMode.HALF_EVEN, params);
    }

    public static BigDecimal divide(int scale, RoundingMode roundMode, double... params) {
        if (params.length == 0) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = DEFAULT_DIV_SCALE;
        }

        BigDecimal result = BigDecimal.valueOf(params[0]);
        for (int i = 1; i < params.length; i++) {
            if (params[i] == 0) {
                throw new ArithmeticException("除数不能为0.");
            }
            result = result.divide(new BigDecimal(Double.toString(params[i])), scale, roundMode);
        }
        return result;

    }

    public static BigDecimal divide(String... params) {
        return divide(DEFAULT_DIV_SCALE, params);
    }

    public static BigDecimal divide(int scale, String... params) {
        return divide(scale, RoundingMode.HALF_EVEN, params);
    }

    public static BigDecimal divide(int scale, RoundingMode roundMode, String... params) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal result = new BigDecimal(params[0]);
        for (int i = 1; i < params.length; i++) {
            if (Double.valueOf(params[i]).intValue() == 0) {
                throw new ArithmeticException("除数不能为0.");
            }
            result = result.divide(new BigDecimal(Double.valueOf(params[i]).toString()), scale, roundMode);
        }

        return result;

    }

    //-- 精度控制 ------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 精确小数点位数
     *
     * @param value 数值
     * @param scale 小数点后几位
     * @return 精确小数点后位数的大数值
     */
    public static BigDecimal round(double value, int scale) {
        // 四舍五入
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    /**
     * 精确小数点位数
     *
     * @param value     数值
     * @param scale     小数点后几位
     * @param roundMode 舍入模式
     *                  ROUND_UP = 0 : 只要小数点scale位后的数值大于0，前一位直接加1。注意，这种四舍五入模式不会降低计算值的大小。eg：1.121 = 1.13
     *                  ROUND_DOWN = 1 : 直接舍弃小数点scale位后的数值。注意，这种四舍五入模式不会增加计算值的大小。eg：1.125 = 1.12
     *                  ROUND_CEILING = 2 : 如果value是正数，只要小数点scale位后的数值大于0，前一位直接加1；如果是负数，无论小数点scale位后的数值是什么，直接舍弃。注意，这个四舍五入模式不会降低计算值。eg：1.121 = 1.13，-1.129 = -1.12
     *                  ROUND_FLOOR = 3 : 如果value是正数，无论小数点scale位后的数值是什么，直接舍弃；如果是负数，只要小数点scale位后的数值大于0，前一位直接加1。注意，这个舍入模式不会增加计算值。eg：1.129 = 1.12，-1.121 = -1.13
     *                  ROUND_HALF_UP = 4 : 常规四舍五入，无论正数负数一样四舍五入。请注意，这是我们大多数人在小学教的舍入模式。
     *                  ROUND_HALF_DOWN = 5 : 只要小数点scale位后的数值大于5（等于5不入），前一位直接加1。eg：1.125 = 1.12，1.126 = 1.13，-1.125 = -1.12，-1.126 = -1.13
     *                  ROUND_HALF_EVEN = 6 : 常规四舍五入的基础上，如果舍弃值是>=5，且舍弃值左边是奇数，则+1，如果左边是偶数，则舍弃（正数负数一样）。注意，这是一种舍入模式，它可以使累积错误最小化，在计算过程中重复使用。eg：5.5 = 6，4.5 = 4
     *                  ROUND_UNNECESSARY = 7 : 舍入模式断言所请求的操作有一个确切的结果，因此没有必要进行四舍五入。如果在一个操作上指定了这个舍入模式，结果会产生一个不精确的结果，那么就会抛出一个{code算术异常}。
     * @return 精确小数点后位数的大数值
     */
    public static BigDecimal round(double value, int scale, RoundingMode roundMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = new BigDecimal(value);
        return result.setScale(scale, roundMode);
    }

    public static BigDecimal round(String value, int scale) {
        // 四舍五入
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal round(String v, int scale, RoundingMode roundMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = new BigDecimal(v);
        return result.setScale(scale, roundMode);
    }

    //-- 大小比对 ------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 第一个是否小于第二个值
     *
     * @param first  first
     * @param second second
     * @return boolean
     */
    public static boolean smallerThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    /**
     * 第一个是否大于第二个值
     *
     * @param first  first
     * @param second second
     * @return boolean
     */
    public static boolean biggerThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    /**
     * 是否相等
     *
     * @param first  first
     * @param second second
     * @return boolean
     */
    public static boolean isEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    //-- 金额格式化 ------------------------------------------------------------------------------------------------------------------------------------------

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
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    /**
     * 货币格式化
     *
     * @param bigDecimal bigDecimal
     * @return str
     */
    public static String formatCurrency(BigDecimal bigDecimal) {
        // 建立货币格式化引用
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(bigDecimal);
    }

    /**
     * 百分比格式化
     * <p>
     * 默认丢失百分比后小数点后位数
     *
     * @param bigDecimal bigDecimal
     * @return str
     */
    public static String formatPercent(BigDecimal bigDecimal) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        return percent.format(bigDecimal);
    }

    /**
     * 百分比格式化
     *
     * @param bigDecimal            bigDecimal
     * @param maximumFractionDigits 百分比小数点最多位数
     * @return str
     */
    public static String formatPercent(BigDecimal bigDecimal, int maximumFractionDigits) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        // 设置百分比小数点最多位数
        percent.setMaximumFractionDigits(maximumFractionDigits);
        return percent.format(bigDecimal);
    }

}
