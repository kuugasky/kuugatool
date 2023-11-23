package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * Guava Immutable
 * 将集合并更为不可用集合
 *
 * @author kuuga
 * @since 2021/7/13
 */
public final class ImmutableUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ImmutableUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回一个不可变的集合，其中包含每个元素，减去重复，按照每个元素在源集合中首先出现的顺序。
     *
     * @param set set集合
     * @return 不可变set集合
     */
    public static <E> ImmutableSet<E> copyOf(Set<E> set) {
        return ImmutableSet.copyOf(set);
    }

    /**
     * 返回一个不可变的集合，其中包含每个元素，减去重复，按照每个元素在源集合中首先出现的顺序。
     *
     * @param e 可变元素
     * @return 不可变set集合
     */
    @SafeVarargs
    public static <E> ImmutableSet<E> ofSet(E... e) {
        Iterator<E> iterator = Arrays.stream(e).iterator();
        return ImmutableSet.copyOf(iterator);
    }

    /**
     * 返回一个不可变的集合，其中包含每个元素，减去重复，按照每个元素在源集合中首先出现的顺序。
     *
     * @param list list集合
     * @return 不可变list集合
     */
    public static <E> ImmutableList<E> copyOf(List<E> list) {
        return ImmutableList.copyOf(list);
    }

    /**
     * 返回一个不可变的集合，其中包含每个元素，减去重复，按照每个元素在源集合中首先出现的顺序。
     *
     * @param e 可变元素
     * @return 不可变list集合
     */
    @SafeVarargs
    public static <E> ImmutableList<E> ofList(E... e) {
        Iterator<E> iterator = Arrays.stream(e).iterator();
        return ImmutableList.copyOf(iterator);
    }

    /**
     * 返回包含与映射相同条目的不可变映射。返回的地图以与原始地图的条目集相同的顺序迭代条目。如果映射以某种方式包含具有重复键的条目
     * （例如，如果它是SortedMap，其比较器与等值不一致），则此方法的结果未定义。
     *
     * @param map map
     * @return 不可变map
     */
    public static <K, V> ImmutableMap<K, V> copyOf(Map<K, V> map) {
        return ImmutableMap.copyOf(map);
    }

}
