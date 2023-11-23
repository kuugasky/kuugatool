package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串井号Util
 * #A#B#C格式拼接和解析工具
 * 支持拼接和解析枚举项/字符串
 *
 * @author kuuga
 * @since 2021/3/16
 */
public final class StringPoundSignUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private StringPoundSignUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 文本集合拼接井号符
     *
     * @param list list
     * @return String[#1#3#2#]
     */
    public static String joinSign(List<String> list) {
        if (ListUtil.isEmpty(list)) {
            return StringUtil.EMPTY;
        }
        list = ListUtil.blankFilter(list);
        return StringJoinerUtil.join(KuugaConstants.WELL, KuugaConstants.WELL, KuugaConstants.WELL, list);
    }

    /**
     * 枚举集合拼接井号符
     *
     * @param list list
     * @return String[#MB#G#]
     */
    public static <T extends Enum<T>> String joinSignEnum(List<T> list) {
        List<String> collect = list.stream().filter(StringUtil::hasText).map(Enum::name).collect(Collectors.toList());
        return joinSign(collect);
    }

    /**
     * 解析带有井号符的文本为文本集合
     *
     * @param text text
     * @return String[1, 3, 2]
     */
    public static List<String> parseSign(String text) {
        if (StringUtil.isEmpty(text)) {
            return ListUtil.emptyList();
        }
        return ListUtil.blankFilter(ListUtil.newArrayList(text.split(KuugaConstants.WELL)));
    }

    /**
     * 解析带有井号符的文本为枚举集合
     *
     * @param text text
     * @return String[MB, G, KB]
     */
    public static <T extends Enum<T>> List<T> parseSignEnum(String text, Class<T> enumType) {
        if (StringUtil.isEmpty(text)) {
            return ListUtil.emptyList();
        }
        List<String> list = ListUtil.blankFilter(ListUtil.newArrayList(text.split(KuugaConstants.WELL)));
        return ListUtil.optimize(list).stream()
                .map(item -> Enum.valueOf((enumType), item))
                .collect(Collectors.toList());
    }

}
