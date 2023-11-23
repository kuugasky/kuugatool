package io.github.kuugasky.kuugatool.core.number;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.math.BigDecimal;

/**
 * 数值类型比对工具类
 *
 * @author kuuga
 * @date 2021-01-21 上午11:43
 */
public class NumberCompareUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private NumberCompareUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 比对BigDecimal
     *
     * @param val1 val1
     * @param val2 val2
     * @return 0：相等，1：val1 > val2，-1：val1 < val2
     */
    public static int compareOfBigDecimal(BigDecimal val1, BigDecimal val2) {
        if (ObjectUtil.containsNull(val1, val2)) {
            throw new NumberFormatException("number can not null");
        }
        int result = 0;
        if (val1.compareTo(val2) < 0) {
            result = -1;
        }
        if (val1.compareTo(val2) == 0) {
            result = 0;
        }
        if (val1.compareTo(val2) > 0) {
            result = 1;
        }
        return result;
    }

    /**
     * 比对Double
     *
     * @param val1 val1
     * @param val2 val2
     * @return 0：相等，1：val1 > val2，-1：val1 < val2
     */
    public static int compareOfDouble(Double val1, Double val2) {
        if (ObjectUtil.containsNull(val1, val2)) {
            throw new NumberFormatException("number can not null");
        }
        return compareOfBigDecimal(new BigDecimal(val1), new BigDecimal(val2));
    }

    /**
     * 比对Float
     *
     * @param val1 val1
     * @param val2 val2
     * @return 0：相等，1：val1 > val2，-1：val1 < val2
     */
    public static int compareOfFloat(Float val1, Float val2) {
        if (ObjectUtil.containsNull(val1, val2)) {
            throw new NumberFormatException("number can not null");
        }
        return compareOfBigDecimal(new BigDecimal(val1), new BigDecimal(val2));
    }

    /**
     * 比对Integer
     *
     * @param val1 val1
     * @param val2 val2
     * @return 0：相等，1：val1 > val2，-1：val1 < val2
     */
    public static int compareOfInt(Integer val1, Integer val2) {
        if (ObjectUtil.containsNull(val1, val2)) {
            throw new NumberFormatException("number can not null");
        }
        return compareOfBigDecimal(new BigDecimal(val1), new BigDecimal(val2));
    }

    /**
     * 比对Long
     *
     * @param val1 val1
     * @param val2 val2
     * @return 0：相等，1：val1 > val2，-1：val1 < val2
     */
    public static int compareOfLong(Long val1, Long val2) {
        if (ObjectUtil.containsNull(val1, val2)) {
            throw new NumberFormatException("number can not null");
        }
        return compareOfBigDecimal(new BigDecimal(val1), new BigDecimal(val2));
    }

}
