package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;

import java.util.Date;
import java.util.Random;

/**
 * 系统序列号工具类
 *
 * @author kuuga
 */
public class SystemSequenceUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SystemSequenceUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 生成流水编号,代表标志(sign+yyyyMMddHHmmss+3位随机数)
     *
     * @return String
     */
    public static synchronized String randomSequenceNumber(String sign) {
        return randomSequenceNumber(sign, DateFormat.yyyyMMddHHmmss, 3);
    }

    /**
     * 生成流水编号,代表标志(sign+yyyyMMddHHmmss+自定义位数随机数)
     *
     * @return String
     */
    public static synchronized String randomSequenceNumber(String sign, int randomNumberLength) {
        return randomSequenceNumber(sign, DateFormat.yyyyMMddHHmmss, randomNumberLength);
    }

    /**
     * 生成流水编号,代表标志(sign+自定义日期格式+自定义位数随机数)
     *
     * @return String
     */
    public static synchronized String randomSequenceNumber(String sign, DateFormat dateFormat, int randomNumberLength) {
        if (randomNumberLength <= 0) {
            randomNumberLength = 0;
        }
        String dateTime = DateUtil.format(dateFormat, new Date());
        return sign + dateTime + RandomUtil.randomNumbers(randomNumberLength);
    }

    /**
     * 随机英文数字
     *
     * @param randomNumberLength 随机数长度
     * @param type               字符类型
     * @return String
     */
    public static synchronized String randomEnglishNumber(int randomNumberLength, CharType type) {
        if (randomNumberLength <= 0) {
            randomNumberLength = 0;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < randomNumberLength; i++) {
            // 通过随机函数生成，字母对应的int整型数字，然后转换才char类型的字母。
            int randomInt = (int) (Math.random() * 26 + 97);
            char randomChar = (char) randomInt;
            if (randomInt % 2 == 0) {
                sb.append(String.valueOf(randomChar).toUpperCase());
            } else {
                sb.append(randomChar);
            }
        }

        return switch (type) {
            case UPPER_CASE -> sb.toString().toUpperCase();
            case LOWER_CASE -> sb.toString().toLowerCase();
            case CHAR_NUMBER -> {
                StringBuilder result = new StringBuilder();
                for (char c : sb.toString().toCharArray()) {
                    Random random = new Random();
                    int number = random.nextInt(10);
                    if (number % 2 == 0) {
                        result.append(random.nextInt(10));
                    } else {
                        result.append(c);
                    }
                }
                yield result.toString();
            }
            default -> sb.toString();
        };
    }

    public enum CharType {
        /**
         * 常规
         */
        ORDINARY,
        /**
         * 大写英文字母
         */
        UPPER_CASE,
        /**
         * 小写英文字母
         */
        LOWER_CASE,
        /**
         * 字符和数字混搭
         */
        CHAR_NUMBER
    }

}