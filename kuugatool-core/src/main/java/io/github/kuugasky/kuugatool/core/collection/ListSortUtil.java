package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.collection.sort.ListSort;
import io.github.kuugasky.kuugatool.core.comparator.PinyinComparator;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/**
 * ListSortUtil
 *
 * @author kuuga
 * @since 2022/7/1
 */
public final class ListSortUtil {

    // 反序 ================================================================================================================================================

    /**
     * 反序给定List，会在原List基础上直接修改
     *
     * @param <T>  元素类型
     * @param list 被反转的List
     * @return 反转后的List
     * @since 4.0.6
     */
    public static <T> List<T> reverse(final List<T> list) {
        Collections.reverse(list);
        // Lists.reverse(list);
        return list;
    }

    /**
     * 整个list反向排序,并返回新的集合
     *
     * @param <T>  泛型
     * @param list 源数据集合
     * @return 反向排序后的数组 list
     */
    public static <T> List<T> reverseNew(final List<T> list) {
        // 反转list Lists.reverse(list)是直接反转原list
        // return Lists.reverse(list);
        return reverseNew(list, false);
    }

    /**
     * 整个list反向排序,并返回新的集合
     *
     * @param <T>         泛型
     * @param list        源数据集合
     * @param filterEmpty 是否过滤空元素
     * @return 反向排序后的数组 list
     */
    public static <T> List<T> reverseNew(final List<T> list, final boolean filterEmpty) {
        // return Lists.reverse(ListUtil.optimize(list, filterEmpty));
        if (ListUtil.isEmpty(list)) {
            return ListUtil.newArrayList();
        }
        Stack<T> stack = new Stack<>();
        stack.addAll(list);
        List<T> result = ListUtil.newArrayList(list.size());
        while (stack.size() > 0) {
            T pop = stack.pop();
            boolean needSkip = filterEmpty && (pop == null || StringUtil.isEmpty(pop) || "null".equalsIgnoreCase(pop.toString()));
            if (!needSkip) {
                result.add(pop);
            }
        }
        return result;
    }

    // 单维度和双维度排序 ==========================================================================================================================================================

    /**
     * 针对List排序，排序会修改原List
     *
     * @param <T>  元素类型
     * @param list 被排序的List
     * @param c    {@link Comparator}
     * @return 原list
     * @link `ListUtil.sorted(list, Comparator.comparing(Demo::getLevelIdInt).reversed().thenComparingLong(Demo::getPositionIdLong).reversed())`
     * @see Collections#sort(List, Comparator)
     */
    public static <T> List<T> sorted(final List<T> list, Comparator<? super T> c) {
        list.sort(c);
        return list;
    }

    /**
     * 根据汉字的拼音顺序排序
     *
     * @param list List
     * @return 排序后的List
     * @since 4.0.8
     */
    public static List<String> sortedByPinyin(final List<String> list) {
        return sorted(list, new PinyinComparator());
    }

    /**
     * 对集合进行单维度升序
     *
     * @param list         list
     * @param keyExtractor 排序纬度
     * @return 升序list
     */
    public static <T, U extends Comparable<? super U>> List<T> sortedByComparing(final List<T> list, Function<T, U> keyExtractor) {
        return list.stream().
                sorted(Comparator.comparing(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行双维度升序
     *
     * @param list             list
     * @param keyExtractor     排序纬度一
     * @param thenKeyExtractor 排序纬度二
     * @return 升序list
     */
    public static <T, U extends Comparable<? super U>> List<T> sortedByComparing(final List<T> list, Function<T, U> keyExtractor, Function<T, U> thenKeyExtractor) {
        return list.stream()
                .sorted(Comparator.comparing(keyExtractor).thenComparing(thenKeyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行单维度升序
     *
     * @param list         list
     * @param keyExtractor 排序纬度
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToIntFunction<T> keyExtractor) {
        return list.stream().
                sorted(Comparator.comparingInt(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行双维度升序
     *
     * @param list             list
     * @param keyExtractor     排序纬度一
     * @param thenKeyExtractor 排序纬度二
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToIntFunction<T> keyExtractor, ToIntFunction<T> thenKeyExtractor) {
        return list.stream()
                .sorted(Comparator.comparingInt(keyExtractor).thenComparingInt(thenKeyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行单维度升序
     *
     * @param list         list
     * @param keyExtractor 排序纬度
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToLongFunction<T> keyExtractor) {
        return list.stream().
                sorted(Comparator.comparingLong(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行双维度升序
     *
     * @param list             list
     * @param keyExtractor     排序纬度一
     * @param thenKeyExtractor 排序纬度二
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToLongFunction<T> keyExtractor, ToLongFunction<T> thenKeyExtractor) {
        return list.stream()
                .sorted(Comparator.comparingLong(keyExtractor).thenComparingLong(thenKeyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行单维度升序
     *
     * @param list         list
     * @param keyExtractor 排序纬度
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToDoubleFunction<T> keyExtractor) {
        return list.stream().
                sorted(Comparator.comparingDouble(keyExtractor))
                .collect(Collectors.toList());
    }

    /**
     * 对集合进行双维度升序
     *
     * @param list             list
     * @param keyExtractor     排序纬度一
     * @param thenKeyExtractor 排序纬度二
     * @return 升序list
     */
    public static <T> List<T> sortedByComparing(final List<T> list, ToDoubleFunction<T> keyExtractor, ToDoubleFunction<T> thenKeyExtractor) {
        return list.stream()
                .sorted(Comparator.comparingDouble(keyExtractor).thenComparingDouble(thenKeyExtractor))
                .collect(Collectors.toList());
    }

    // 多维度排序 ==========================================================================================================================================================

    public static <T> ListSort<T> sortedCustom(final List<T> list) {
        return new ListSort<>(list);
    }

}
