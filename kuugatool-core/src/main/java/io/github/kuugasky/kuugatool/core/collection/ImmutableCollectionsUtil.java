package io.github.kuugasky.kuugatool.core.collection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jdk11 Immutable
 * <p>
 * 将集合并变更为不可变集合的方式
 *
 * @author kuuga
 * @since 2021/7/13
 */
public final class ImmutableCollectionsUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ImmutableCollectionsUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回包含零元素的不可修改列表。有关详细信息，请参阅不可修改的列表。
     *
     * @return 一个空列表
     */
    public static <E> List<E> ofList() {
        return List.of();
    }

    /**
     * 返回包含任意数量元素的不可修改列表。有关详细信息，请参阅不可修改的列表。
     *
     * @param elements 可变元素
     * @return 一个不可变列表
     */
    @SafeVarargs
    public static <E> List<E> ofList(E... elements) {
        return List.of(elements);
    }

    /**
     * 按迭代顺序返回包含给定集合元素的不可修改列表。给定的集合不得为空，也不得包含任何空元素。如果给定的集合随后被修改，则返回的列表将不会反映此类修改。
     * <p>
     * 内部实现：ImmutableCollections.listCopy(coll)
     *
     * @param coll 集合
     * @return 不可变list集合
     */
    static <E> List<E> copyOfList(Collection<? extends E> coll) {
        return List.copyOf(coll);
    }

    /**
     * 返回一个包含零元素的不可修改的集合。有关详细信息，请参阅不可修改的设置。
     *
     * @return 一个空的set
     */
    public static <E> Set<E> ofSet() {
        return Set.of();
    }

    /**
     * 返回包含任意数量元素的不可修改set。有关详细信息，请参阅不可修改的set。
     *
     * @param elements 可变元素
     * @return 一个不可变set
     */
    @SafeVarargs
    public static <E> Set<E> ofSet(E... elements) {
        return Set.of(elements);
    }

    /**
     * 按迭代顺序返回包含给定集合元素的不可修改列表。给定的集合不得为空，也不得包含任何空元素。如果给定的集合随后被修改，则返回的列表将不会反映此类修改。
     *
     * @param coll 集合
     * @return 不可变set集合
     */
    static <E> Set<E> copyOfSet(Collection<? extends E> coll) {
        return Set.copyOf(coll);
    }

    public static <K, V> Map<K, V> ofMap() {
        return Map.of();
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1) {
        return Map.of(k1, v1);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2) {
        return Map.of(k1, v1, k2, v2);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        return Map.of(k1, v1, k2, v2, k3, v3);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                         K k6, V v6) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                         K k6, V v6, K k7, V v7) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                         K k6, V v6, K k7, V v7, K k8, V v8) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                         K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9);
    }

    public static <K, V> Map<K, V> ofMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                         K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return Map.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10);
    }

    static <K, V> Map<K, V> copyOfMap(Map<? extends K, ? extends V> map) {
        return Map.copyOf(map);
    }

}
