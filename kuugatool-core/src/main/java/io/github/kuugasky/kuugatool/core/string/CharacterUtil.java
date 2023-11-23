package io.github.kuugasky.kuugatool.core.string;

/**
 * CharacterUtil
 *
 * @author kuuga
 * @since 2022/6/3 12:15
 */
public final class CharacterUtil {

    /**
     * 是否空白字符
     *
     * @param codePoint 要测试的字符（Unicode代码点）
     * @return 如果字符是Java空白字符，则为true；否则为false。
     */
    public static boolean isWhitespace(int codePoint) {
        return Character.isWhitespace(codePoint);
    }

    /**
     * 是否空白字符
     *
     * @param ch 字符
     * @return 如果字符是Java空白字符，则为true；否则为false。
     */
    public static boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }

    public static void main(String[] args) {
        System.out.println(isWhitespace(' '));
        System.out.println(isWhitespace(StringUtil.EMPTY.hashCode()));
        System.out.println(isWhitespace(" ".hashCode()));
        System.out.println(isWhitespace('\u2001'));
        System.out.printf("(%s)%n", '\u2001');
    }

}
