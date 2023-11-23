package io.github.kuugasky.kuugatool.core.number;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 数值解析工具类
 *
 * @author kuuga
 * @date 2021-01-21 上午9:46
 */
@Slf4j
public class NumberParseUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private NumberParseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);

    /**
     * 转换为整型(Integer)
     *
     * @param obj        待转换对象
     * @param defaultVal 默认值(无法转换时返回该值)
     * @return Integer
     */
    public static Integer parseInt(Object obj, Integer defaultVal) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            logger.error("parse integer failed : {}, defaultVal : {}", obj, defaultVal);
            return defaultVal;
        }
    }

    /**
     * 转换为整型(Integer)
     *
     * @param obj 待转换对象
     * @return Integer
     */
    public static Integer parseInt(Object obj) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            logger.error("parse integer failed : {}", obj);
            throw new NumberFormatException("parse integer failed : " + obj);
        }
    }

    /**
     * 转换为长整型(Long)
     *
     * @param obj        待转换对象
     * @param defaultVal 默认值(无法转换时返回该值)
     * @return Long
     */
    public static Long parseLong(Object obj, Long defaultVal) {
        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            logger.error("parse long failed : {}, defaultVal : {}", obj, defaultVal);
            return defaultVal;
        }
    }

    /**
     * 转换为长整型(Long)
     *
     * @param obj 待转换对象
     * @return Long
     */
    public static Long parseLong(Object obj) {
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            throw new NumberFormatException("parse long failed : " + obj);
        }
    }

    /**
     * 转换为浮点数(Float)
     *
     * @param obj        待转换对象
     * @param defaultVal 默认值(无法转换时返回该值)
     * @return Float
     */
    public static Float parseFloat(Object obj, Float defaultVal) {
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            logger.error("parse float failed : {}, defaultVal : {}", obj, defaultVal);
            return defaultVal;
        }
    }

    /**
     * 转换为浮点数(Float)
     *
     * @param obj 待转换对象
     * @return Float
     */
    public static Float parseFloat(Object obj) {
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            throw new NumberFormatException("parse float failed : " + obj);
        }
    }

    /**
     * 转换为双精度浮点数(Double)
     *
     * @param obj        待转换对象
     * @param defaultVal 默认值(无法转换时返回该值)
     * @return Double
     */
    public static Double parseDouble(Object obj, Double defaultVal) {
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            logger.error("parse double failed : {}, defaultVal : {}", obj, defaultVal);
            return defaultVal;
        }
    }

    /**
     * 转换为双精度浮点数(Double)
     *
     * @param obj 待转换对象
     * @return Double
     */
    public static Double parseDouble(Object obj) {
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            throw new NumberFormatException("parse double failed : " + obj);
        }
    }

    /**
     * 转换为双精度浮点数(Double)
     *
     * @param obj        待转换对象
     * @param defaultVal 默认值(无法转换时返回该值)
     * @return Double
     */
    public static BigDecimal parseBigDecimal(Object obj, BigDecimal defaultVal) {
        try {
            return BigDecimal.valueOf(Long.parseLong(obj.toString()));
        } catch (Exception e) {
            logger.error("parse double failed : {}, defaultVal : {}", obj, defaultVal);
            return defaultVal;
        }
    }

    /**
     * 转换为双精度浮点数(Double)
     *
     * @param obj 待转换对象
     * @return Double
     */
    public static BigDecimal parseBigDecimal(Object obj) {
        try {
            return BigDecimal.valueOf(Long.parseLong(obj.toString()));
        } catch (Exception e) {
            throw new NumberFormatException("parse bigDecimal failed : " + obj);
        }
    }

}
