package io.github.kuugasky.kuugatool.core.random;

import io.github.kuugasky.kuugatool.core.string.IdUtil;

/**
 * 随机验证码密码生成工具类
 *
 * @author kuuga
 */
public final class RandomCodeUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private RandomCodeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final int DEFAULT_LENGTH = 6;

    /**
     * 随机指定长度密码，可选纯数字密码和混合密码
     * replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
     * 简易版，复杂版请使用`SystemSequenceUtil.randomEnglishNumber(3, CharType.CHAR_NUMBER)`
     *
     * @param length   长度
     * @param isNumber 是否纯数字
     * @return 定制密码
     */
    private static String getCode(int length, boolean isNumber) {
        StringBuilder uuid = new StringBuilder(IdUtil.simpleUUID());
        if (uuid.length() > length) {
            uuid = new StringBuilder(uuid.substring(0, length));
        } else if (uuid.length() < length) {
            for (int i = 0; i < length - uuid.length(); i++) {
                uuid.append(Math.round(Math.random() * 9));
            }
        }
        if (isNumber) {
            return uuid.toString()
                    .replaceAll("a", "1")
                    .replaceAll("b", "2")
                    .replaceAll("c", "3")
                    .replaceAll("d", "4")
                    .replaceAll("e", "5")
                    .replaceAll("f", "6");
        } else {
            return uuid.toString();
        }
    }

    /**
     * 随机6位数密码
     *
     * @return 6位长度纯数字密码
     */
    public static String getCode() {
        return getCode(DEFAULT_LENGTH, true);
    }

    /**
     * 自定义长度纯数字密码
     *
     * @param length 自定义密码长度
     * @return 自定义长度纯数字密码
     */
    public static String getCode(int length) {
        if (length <= 0) {
            return null;
        }
        return getCode(length, true);
    }

    /**
     * 随机6位数非纯数字密码
     *
     * @return 随机6位数非纯数字密码
     */
    public static String getMixedCode() {
        return getCode(DEFAULT_LENGTH, false);
    }

    /**
     * 随机6位数密码
     *
     * @param length 长度
     * @return 6位长度含字母密码
     */
    public static String getMixedCode(int length) {
        if (length <= 0) {
            return null;
        }
        return getCode(length, false);
    }

}
