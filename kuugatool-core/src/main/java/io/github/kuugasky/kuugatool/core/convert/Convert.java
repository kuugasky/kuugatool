package io.github.kuugasky.kuugatool.core.convert;

import java.util.Set;

/**
 * @author kuuga
 * @since 2021-01-19 下午4:00
 */
public final class Convert {

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    @SuppressWarnings("all")
    public static String toSBC(String input) {
        return toSBC(input, null);
    }

    /**
     * 半角转全角
     *
     * @param input         String
     * @param notConvertSet 不替换的字符集合
     * @return 全角字符串.
     */
    @SuppressWarnings("all")
    public static String toSBC(String input, Set<Character> notConvertSet) {
        final char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (null != notConvertSet && notConvertSet.contains(c[i])) {
                // 跳过不替换的字符
                continue;
            }

            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

}
