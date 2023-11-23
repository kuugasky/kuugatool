package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * StringJoinerUtil
 * <p>
 * 文本拼接工具类
 *
 * @author kuuga
 * @since 2022-05-29
 */
public final class StringJoinerUtil {

    // list merge ===============================================================================================================================================

    /**
     * 两个集合元素合并转文本
     *
     * @param list      list
     * @param otherList otherList
     * @param <T>       T
     * @return String
     */
    public static <T> String merge(List<T> list, List<T> otherList) {
        return merge(null, list, otherList, false);
    }

    /**
     * 两个集合元素合并转文本
     *
     * @param delimiter 分隔符
     * @param list      list
     * @param otherList otherList
     * @param <T>       T
     * @return String
     */
    public static <T> String merge(CharSequence delimiter, List<T> list, List<T> otherList) {
        return merge(delimiter, list, otherList, false);
    }

    /**
     * 两个集合元素合并转文本
     *
     * @param delimiter         分隔符
     * @param list              list
     * @param otherList         otherList
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String merge(CharSequence delimiter, List<T> list, List<T> otherList, boolean removeEmptyOrNull) {
        delimiter = StringUtil.isEmpty(delimiter) ? KuugaConstants.COMMAENG : delimiter;
        StringJoiner stringJoiner = new StringJoiner(delimiter);
        stringJoiner.setEmptyValue(StringUtil.EMPTY);

        StringJoiner otherStringJoiner = new StringJoiner(delimiter);
        otherStringJoiner.setEmptyValue(StringUtil.EMPTY);

        if (removeEmptyOrNull) {
            ListUtil.optimize(list).stream().filter(StringUtil::hasText).forEach(element -> stringJoiner.add(StringUtil.toString(element)));
            ListUtil.optimize(otherList).stream().filter(StringUtil::hasText).forEach(element -> otherStringJoiner.add(StringUtil.toString(element)));
        } else {
            ListUtil.optimize(list).forEach(element -> stringJoiner.add(StringUtil.toString(element)));
            ListUtil.optimize(otherList).forEach(element -> otherStringJoiner.add(StringUtil.toString(element)));
        }

        StringJoiner merge = stringJoiner.merge(otherStringJoiner);

        return merge.toString();
    }

    // list join ===============================================================================================================================================

    // public static <T> String join(List<T> list) {
    //     return join(null, list, false);
    // }
    //
    // public static <T> String join(List<T> list, boolean removeEmpty) {
    //     return join(null, list, removeEmpty);
    // }
    //
    // public static <T> String join(CharSequence delimiter, List<T> list) {
    //     return join(delimiter, list, false);
    // }
    //
    // public static <T> String join(CharSequence delimiter, List<T> list, boolean removeEmpty) {
    //     delimiter = StringUtil.isEmpty(delimiter) ? kuugaConstants.COMMAENG : delimiter;
    //     StringJoiner stringJoiner = new StringJoiner(delimiter);
    //     // 如果list为空则返回emptyValue
    //     stringJoiner.setEmptyValue(StringUtil.EMPTY);
    //     if (removeEmpty) {
    //         ListUtil.optimize(list).stream().filter(StringUtil::hasText).forEach(element -> stringJoiner.add(StringUtil.toString(element)));
    //     } else {
    //         ListUtil.optimize(list).forEach(element -> stringJoiner.add(StringUtil.toString(element)));
    //     }
    //     return stringJoiner.toString();
    // }

    // collections join ===============================================================================================================================================

    /**
     * 文本拼接
     * <p>
     * - 默认分隔符,
     *
     * @param collection 集合
     * @param <T>        T
     * @return String
     */
    public static <T> String join(Collection<T> collection) {
        return join(null, collection, false);
    }

    /**
     * 文本拼接
     * <p>
     * - 默认分隔符,
     *
     * @param collection        集合
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String join(Collection<T> collection, boolean removeEmptyOrNull) {
        return join(null, collection, removeEmptyOrNull);
    }

    /**
     * 文本拼接
     *
     * @param delimiter  分隔符
     * @param collection 集合
     * @param <T>        T
     * @return String
     */
    public static <T> String join(CharSequence delimiter, Collection<T> collection) {
        return join(delimiter, collection, false);
    }

    /**
     * 文本拼接
     *
     * @param delimiter         分隔符
     * @param collection        集合
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String join(CharSequence delimiter, Collection<T> collection, boolean removeEmptyOrNull) {
        return join(delimiter, StringUtil.EMPTY, StringUtil.EMPTY, collection, removeEmptyOrNull);
    }

    /**
     * 文本拼接，不带分隔符
     *
     * @param collection 集合
     * @param <T>        T
     * @return String
     */
    public static <T> String joinOnly(Collection<T> collection) {
        return join(StringUtil.EMPTY, collection, false);
    }

    /**
     * 文本拼接，不带分隔符
     *
     * @param collection        集合
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String joinOnly(Collection<T> collection, boolean removeEmptyOrNull) {
        return join(StringUtil.EMPTY, collection, removeEmptyOrNull);
    }

    // array join ===============================================================================================================================================

    /**
     * 文本拼接，默认分隔符,
     *
     * @param arrays 数组
     * @param <T>    T
     * @return String
     */
    public static <T> String join(T[] arrays) {
        return join(null, arrays, false);
    }

    /**
     * 文本拼接，默认分隔符,
     *
     * @param arrays            数组
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String join(T[] arrays, boolean removeEmptyOrNull) {
        return join(null, arrays, removeEmptyOrNull);
    }

    /**
     * 文本拼接
     *
     * @param delimiter 分隔符
     * @param arrays    数组
     * @param <T>       T
     * @return String
     */
    public static <T> String join(CharSequence delimiter, T[] arrays) {
        return join(delimiter, arrays, false);
    }

    /**
     * 文本拼接
     *
     * @param delimiter         分隔符
     * @param arrays            数组
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String join(CharSequence delimiter, T[] arrays, boolean removeEmptyOrNull) {
        return join(delimiter, StringUtil.EMPTY, StringUtil.EMPTY, arrays, removeEmptyOrNull);
    }

    /**
     * 文本拼接，不带分隔符
     *
     * @param arrays 数组
     * @param <T>    T
     * @return String
     */
    public static <T> String joinOnly(T[] arrays) {
        return join(StringUtil.EMPTY, arrays, false);
    }

    /**
     * 文本拼接，不带分隔符
     *
     * @param arrays            数组
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String joinOnly(T[] arrays, boolean removeEmptyOrNull) {
        return join(StringUtil.EMPTY, arrays, removeEmptyOrNull);
    }

    // list join and prefix suffix ===============================================================================================================================================

    /**
     * 文本拼接，分隔符,
     *
     * @param prefix     前缀
     * @param suffix     后缀
     * @param collection 集合
     * @param <T>        T
     * @return String
     */
    public static <T> String join(CharSequence prefix, CharSequence suffix, Collection<T> collection) {
        return join(null, prefix, suffix, collection, false);
    }

    /**
     * 文本拼接，分隔符,
     *
     * @param prefix            前缀
     * @param suffix            后缀
     * @param collection        集合
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return String
     */
    public static <T> String join(CharSequence prefix, CharSequence suffix, Collection<T> collection, boolean removeEmptyOrNull) {
        return join(null, prefix, suffix, collection, removeEmptyOrNull);
    }

    /**
     * 文本拼接
     *
     * @param delimiter  分隔符
     * @param prefix     前缀
     * @param suffix     后缀
     * @param collection 集合
     * @param <T>        T
     * @return String
     */
    public static <T> String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, Collection<T> collection) {
        return join(delimiter, prefix, suffix, collection, false);
    }

    /**
     * 文本拼接-集合
     *
     * @param delimiter         分隔符
     * @param prefix            前缀
     * @param suffix            后缀
     * @param collection        集合
     * @param removeEmptyOrNull 是否移除空格
     * @param <T>               T
     * @return 拼接好的文本
     */
    public static <T> String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, Collection<T> collection, boolean removeEmptyOrNull) {
        delimiter = null == delimiter ? KuugaConstants.COMMAENG : delimiter;
        StringJoiner stringJoiner = new StringJoiner(delimiter, prefix, suffix);
        // 如果list为空则返回emptyValue
        stringJoiner.setEmptyValue(StringUtil.EMPTY);
        if (removeEmptyOrNull) {
            collection.stream().filter(StringUtil::hasText).forEach(element -> stringJoiner.add(StringUtil.toString(element)));
        } else {
            collection.forEach(element -> stringJoiner.add(StringUtil.toString(element)));
        }
        return stringJoiner.toString();
    }

    // array join and prefix suffix ===============================================================================================================================================

    /**
     * 文本拼接-数组，分隔符,
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @param arrays 数组
     * @param <T>    T
     * @return 拼接好的文本
     */
    public static <T> String join(CharSequence prefix, CharSequence suffix, T[] arrays) {
        return join(null, prefix, suffix, arrays, false);
    }

    /**
     * 文本拼接-数组，分隔符,
     *
     * @param prefix            前缀
     * @param suffix            后缀
     * @param arrays            数组
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return 拼接好的文本
     */
    public static <T> String join(CharSequence prefix, CharSequence suffix, T[] arrays, boolean removeEmptyOrNull) {
        return join(null, prefix, suffix, arrays, removeEmptyOrNull);
    }

    /**
     * 文本拼接-数组
     *
     * @param delimiter 分隔符
     * @param prefix    前缀
     * @param suffix    后缀
     * @param arrays    数组
     * @param <T>       T
     * @return 拼接好的文本
     */
    public static <T> String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, T[] arrays) {
        return join(delimiter, prefix, suffix, arrays, false);
    }

    /**
     * 文本拼接-数组
     *
     * @param delimiter         分隔符
     * @param prefix            前缀
     * @param suffix            后缀
     * @param arrays            数组
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <T>               T
     * @return 拼接好的文本
     */
    public static <T> String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, T[] arrays, boolean removeEmptyOrNull) {
        return join(delimiter, prefix, suffix, ListUtil.newArrayList(arrays), removeEmptyOrNull);
    }

    // collections join fast ===============================================================================================================================================

    /**
     * 文本拼接-集合
     *
     * @param collection 集合
     * @param delimiter  分隔符
     * @param prefix     前缀
     * @param suffix     后缀
     * @param <E>        E
     * @return String
     */
    public <E> String join(Collection<E> collection,
                           CharSequence delimiter,
                           CharSequence prefix,
                           CharSequence suffix) {
        return join(collection, delimiter, prefix, suffix, false);
    }

    /**
     * 文本拼接-集合
     *
     * @param collection        集合
     * @param delimiter         分隔符
     * @param prefix            前缀
     * @param suffix            后缀
     * @param removeEmptyOrNull 是否移除空格或Null
     * @param <E>               E
     * @return String
     */
    public <E> String join(Collection<E> collection,
                           CharSequence delimiter,
                           CharSequence prefix,
                           CharSequence suffix,
                           boolean removeEmptyOrNull) {
        if (removeEmptyOrNull) {
            return collection.stream().filter(StringUtil::hasText)
                    .map(Object::toString)
                    .collect(
                            Collectors.joining(delimiter, prefix, suffix)
                    );
        } else {
            return collection.stream()
                    .map(Object::toString)
                    .collect(
                            Collectors.joining(delimiter, prefix, suffix)
                    );
        }

    }

}
