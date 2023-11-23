package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.enums.SignLocation;

/**
 * 号码遮罩工具类
 *
 * @author kuuga
 * @date 2021-01-21 下午2:17
 */
public class StringMaskUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private StringMaskUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 隐藏中间指定星号个数进行号码遮罩
     *
     * @param text   文本
     * @param length 长度
     * @return 隐藏后的文本
     */
    public static String mask(String text, int length) {
        return mask(text, SignLocation.CENTER, length);
    }

    /**
     * 隐藏指定位置和星号个数进行号码遮罩
     *
     * @param text         文本
     * @param signLocation 遮罩符位置
     * @param length       长度
     * @return 隐藏后的文本
     */
    public static String mask(String text, SignLocation signLocation, int length) {
        return mask(text, signLocation, '*', length);
    }

    /**
     * 隐藏指定位置&指定遮罩符&指定长度进行号码遮罩
     *
     * @param text         文本
     * @param signLocation 遮罩符位置
     * @param sign         遮罩符
     * @param length       长度
     * @return 隐藏后的文本
     */
    public static String mask(String text, SignLocation signLocation, char sign, int length) {
        if (StringUtil.isEmpty(text)) {
            return text;
        }
        text = text.trim();
        if (text.length() <= length) {
            return buildUpAsterisk(sign, text.length());
        }

        if (signLocation == SignLocation.LEFT) {
            return buildUpAsterisk(sign, length) + text.substring(length);
        }
        if (signLocation == SignLocation.CENTER) {
            int index = (text.length() - length) / 2;
            return text.substring(0, index) + buildUpAsterisk(sign, length) + text.substring(index + length);
        }
        return text.substring(0, text.length() - length) + buildUpAsterisk(sign, length);
    }

    /**
     * 拼接遮罩符
     *
     * @param sign  遮罩符
     * @param count 追加次数
     * @return String
     */
    private static String buildUpAsterisk(char sign, int count) {
        sign = StringUtil.isEmpty(sign) ? '*' : sign;
        return String.valueOf(sign).repeat(Math.max(0, count));
    }

}
