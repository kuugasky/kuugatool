package io.github.kuugasky.kuugatool.core.string;

import com.google.common.annotations.Beta;
import com.google.common.base.Splitter;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

import java.util.List;
import java.util.Map;

/**
 * 文本拆分工具类
 *
 * @author kuuga
 * @since 2021/7/13
 */
public final class SplitterUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SplitterUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 井号默认分割器，忽略空字符串，过滤结果中的空白
     */
    public static final Splitter COMMA_JOINER = on(KuugaConstants.COMMAENG).trimResults().omitEmptyStrings();

    /**
     * 井号默认分割器，忽略空字符串，过滤结果中的空白
     */
    public static final Splitter POUND_SIGN_JOINER = on(KuugaConstants.WELL).trimResults().omitEmptyStrings();

    /**
     * 自定义分隔符初始化Splitter
     *
     * @param separator 分隔符
     * @return Splitter
     */
    public static Splitter on(String separator) {
        return Splitter.on(separator);
    }

    /**
     * 根据分隔字符切分成list集合
     *
     * @param separator 分隔字符
     * @param str       原文本
     * @return 分隔好的字符串集合
     */
    public static List<String> splitToList(char separator, String str) {
        return splitToList(String.valueOf(separator), str);
    }

    /**
     * 根据分隔符切分成list集合
     *
     * @param separator 分隔符
     * @param str       原文本
     * @return 分隔好的字符串集合
     */
    public static List<String> splitToList(String separator, String str) {
        return Splitter.on(separator).splitToList(str);
    }

    /**
     * 自定义分隔符和键值分隔符拆分str转换成map
     *
     * @param separator         分隔符(,)
     * @param keyValueSeparator 键值对分隔符(=)
     * @param str               字符串(1=2,3=4)
     * @return map
     */
    @Beta
    @SuppressWarnings("all")
    public static Map<String, String> withKeyValueSeparatorToMap(String separator, String keyValueSeparator, String str) {
        return Splitter.on(separator).withKeyValueSeparator(keyValueSeparator).split(str);
    }

    @Beta
    @SuppressWarnings("all")
    public static Map<String, String> withKeyValueSeparatorToMap(char separator, char keyValueSeparator, String str) {
        return Splitter.on(separator).withKeyValueSeparator(keyValueSeparator).split(str);
    }

}
