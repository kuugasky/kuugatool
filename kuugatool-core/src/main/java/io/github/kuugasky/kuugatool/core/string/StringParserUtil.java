package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * StringParserUtil
 *
 * @author kuuga
 * @since 2022-06-01
 */
public final class StringParserUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private StringParserUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // 解析字符串为字符串集合 ==================================================================================================================================

    public static List<String> parse(String text) {
        return parse(KuugaConstants.COMMAENG, text);
    }

    public static List<String> parse(CharSequence delimiter, String text) {
        return parse(delimiter, text, false);
    }

    public static List<String> parse(CharSequence delimiter, String text, boolean removeEmpty) {
        if (StringUtil.isEmpty(text)) {
            return ListUtil.emptyList();
        }

        List<String> list = ListUtil.newArrayList(text.split(StringUtil.str(delimiter)));
        if (removeEmpty) {
            return ListUtil.blankFilter(list);
        }
        return list;
    }

    // 解析字符串为枚举集合 ==================================================================================================================================

    public static <T extends Enum<T>> List<T> parse(String text, Class<T> enumType) {
        return parse(KuugaConstants.COMMAENG, null, null, text, enumType, false);
    }

    public static <T extends Enum<T>> List<T> parse(String text, Class<T> enumType, boolean removeEmpty) {
        return parse(KuugaConstants.COMMAENG, null, null, text, enumType, removeEmpty);
    }

    public static <T extends Enum<T>> List<T> parse(CharSequence delimiter, String text, Class<T> enumType) {
        return parse(delimiter, null, null, text, enumType, false);
    }

    public static <T extends Enum<T>> List<T> parse(CharSequence delimiter, String text, Class<T> enumType, boolean removeEmpty) {
        return parse(delimiter, null, null, text, enumType, removeEmpty);
    }

    public static <T extends Enum<T>> List<T> parse(CharSequence delimiter, CharSequence prefix, CharSequence suffix, String text, Class<T> enumType) {
        return parse(delimiter, prefix, suffix, text, enumType, false);
    }

    public static <T extends Enum<T>> List<T> parse(CharSequence delimiter, CharSequence prefix, CharSequence suffix, String text, Class<T> enumType, boolean removeEmpty) {
        if (StringUtil.isEmpty(text)) {
            return ListUtil.emptyList();
        }

        text = ObjectUtil.nonNull(prefix) ? StringUtil.removeStart(text, StringUtil.str(prefix)) : text;
        text = ObjectUtil.nonNull(suffix) ? StringUtil.removeEnd(text, StringUtil.str(suffix)) : text;

        List<String> list = ListUtil.newArrayList(text.split(StringUtil.str(delimiter)));
        if (removeEmpty) {
            list = ListUtil.blankFilter(list);
        }
        return ListUtil.optimize(list).stream()
                .map(item -> Enum.valueOf((enumType), item))
                .collect(Collectors.toList());
    }

}
