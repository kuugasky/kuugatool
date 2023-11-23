package io.github.kuugasky.kuugatool.core.string;

import com.google.common.base.Joiner;

import java.util.Map;

/**
 * 字符串拼接工具类
 * 入参去空字符串：omitEmptyStrings()
 * 结果去空字符串：trimResults()
 * 使用指定分隔符拼接：withKeyValueSeparator("=")
 * 字符串分隔为list：splitToList(str)
 * 分割：split(str)
 * 拼接：join(str)
 *
 * @author kuuga
 * @since 2021/7/13
 */
public final class JoinerUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private JoinerUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 逗号默认拼接器，默认移除null值
     */
    public static final Joiner COMMA_JOINER = Joiner.on(",").skipNulls();

    /**
     * 井号默认拼接器，默认移除null值
     */
    public static final Joiner POUND_SIGN_JOINER = Joiner.on("#").skipNulls();

    /**
     * 返回一个自动将{@code separator}放在连续元素之间。
     * <p>
     * Returns a joiner which automatically places {@code separator} between consecutive elements.
     * <p>
     * 常用方法：<br>
     * - skipNulls()：跳过null值<br>
     * - useForNull("")：null值转为""
     */
    public static Joiner on(String separator) {
        return Joiner.on(separator);
    }

    /**
     * Returns a joiner which automatically places {@code separator} between consecutive elements.
     */
    public static Joiner on(char separator) {
        return Joiner.on(String.valueOf(separator));
    }

    /**
     * 使用关键值分隔符加入
     *
     * @param separator         分隔符字符
     * @param keyValueSeparator keyValue的分隔符字符
     * @param map               map对象
     * @return 拼接好的字符串
     */
    public static <K, V> String withKeyValueSeparatorJoin(char separator, char keyValueSeparator, Map<K, V> map) {
        return withKeyValueSeparatorJoin(String.valueOf(separator), String.valueOf(keyValueSeparator), map);
    }

    /**
     * 使用关键值分隔符加入
     *
     * @param separator         分隔符
     * @param keyValueSeparator keyValue的分隔符
     * @param map               map对象
     * @return 拼接好的字符串
     */
    public static <K, V> String withKeyValueSeparatorJoin(String separator, String keyValueSeparator, Map<K, V> map) {
        return Joiner.on(separator).withKeyValueSeparator(keyValueSeparator).join(map);
    }

}
