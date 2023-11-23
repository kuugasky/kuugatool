package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * {@link Iterable} 和 {@link Iterator} 相关工具类
 *
 * @author Looly
 * @since 3.1.0
 */
public final class IterUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private IterUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    /**
     * Iterator是否为空
     *
     * @param iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        return null == iterator || !iterator.hasNext();
    }

    /**
     * Iterable是否不为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean hasItem(Iterable<?> iterable) {
        return null != iterable && hasItem(iterable.iterator());
    }

    /**
     * Iterator是否不为空
     *
     * @param iterator Iterator对象
     * @return 是否为空
     */
    public static boolean hasItem(Iterator<?> iterator) {
        return null != iterator && iterator.hasNext();
    }

    /**
     * 是否包含{@code null}元素
     *
     * @param iter 被检查的{@link Iterable}对象，如果为{@code null} 返回true
     * @return 是否包含{@code null}元素
     */
    public static boolean hasNull(Iterable<?> iter) {
        return hasNull(null == iter ? null : iter.iterator());
    }

    /**
     * 是否包含{@code null}元素
     *
     * @param iter 被检查的{@link Iterator}对象，如果为{@code null} 返回true
     * @return 是否包含{@code null}元素
     */
    public static boolean hasNull(Iterator<?> iter) {
        if (null == iter) {
            return true;
        }
        while (iter.hasNext()) {
            if (null == iter.next()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否全部元素为null
     *
     * @param iter iter 被检查的{@link Iterable}对象，如果为{@code null} 返回true
     * @return 是否全部元素为null
     * @since 3.3.0
     */
    public static boolean isAllNull(Iterable<?> iter) {
        return isAllNull(null == iter ? null : iter.iterator());
    }

    /**
     * 是否全部元素为null
     *
     * @param iter iter 被检查的{@link Iterator}对象，如果为{@code null} 返回true
     * @return 是否全部元素为null
     * @since 3.3.0
     */
    public static boolean isAllNull(Iterator<?> iter) {
        if (null == iter) {
            return true;
        }

        while (iter.hasNext()) {
            if (null != iter.next()) {
                return false;
            }
        }
        return true;
    }

    /**
     * list转iterator
     *
     * @param list {@link List}
     * @param <E>  E
     * @return {@link Iterator}
     */
    public static <E> Iterator<E> toIterator(List<E> list) {
        return list.listIterator();
    }

    /**
     * set转iterator
     *
     * @param set {@link Set}
     * @param <E> E
     * @return {@link Iterator}
     */
    public static <E> Iterator<E> toIterator(Set<E> set) {
        return set.iterator();
    }

    /**
     * 根据集合返回一个元素计数的 {@link Map}<br>
     * 所谓元素计数就是假如这个集合中某个元素出现了n次，那将这个元素做为key，n做为value<br>
     * 例如：[a,b,c,c,c] 得到：<br>
     * a: 1<br>
     * b: 1<br>
     * c: 3<br>
     *
     * @param <T>  集合元素类型
     * @param iter {@link Iterator}，如果为null返回一个空的Map
     * @return {@link Map}
     */
    public static <T> Map<T, Integer> countMap(Iterator<T> iter) {
        final HashMap<T, Integer> countMap = new HashMap<>(1 << 4);
        if (null != iter) {
            Integer count;
            T t;
            while (iter.hasNext()) {
                t = iter.next();
                count = countMap.get(t);
                if (null == count) {
                    countMap.put(t, 1);
                } else {
                    countMap.put(t, count + 1);
                }
            }
        }
        return countMap;
    }

    public static <T> Map<T, Integer> countMapThenAsc(Iterator<T> iter) {
        Map<T, Integer> countMap = countMap(iter);

        return MapUtil.sortValue(countMap, Comparator.comparingInt(Entry::getValue));
    }

    public static <T> Map<T, Integer> countMapThenDesc(Iterator<T> iter) {
        Map<T, Integer> countMap = countMap(iter);
        return MapUtil.sortValue(countMap, (o1, o2) -> o2.getValue() - o1.getValue());
    }

    /**
     * 将Entry集合转换为HashMap
     *
     * @param <K>       键类型
     * @param <V>       值类型
     * @param entryIter entry集合
     * @return Map
     */
    public static <K, V> HashMap<K, V> toMap(Iterable<Entry<K, V>> entryIter) {
        final HashMap<K, V> map = new HashMap<>(1 << 4);
        if (hasItem(entryIter)) {
            for (Entry<K, V> entry : entryIter) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    /**
     * 将列表转成值为List的HashMap
     *
     * @param iterable  值列表
     * @param keyMapper Map的键映射
     * @param <K>       键类型
     * @param <V>       值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <K, V> Map<K, List<V>> toListMap(Iterable<V> iterable, Function<V, K> keyMapper) {
        return toListMap(iterable, keyMapper, v -> v);
    }

    /**
     * 将列表转成值为List的HashMap
     *
     * @param iterable    值列表
     * @param keyMapper   Map的键映射
     * @param valueMapper Map中List的值映射
     * @param <T>         列表值类型
     * @param <K>         键类型
     * @param <V>         值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <T, K, V> Map<K, List<V>> toListMap(Iterable<T> iterable, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return toListMap(MapUtil.newHashMap(), iterable, keyMapper, valueMapper);
    }

    /**
     * 将列表转成值为List的HashMap
     *
     * @param resultMap   结果Map，可自定义结果Map类型
     * @param iterable    值列表
     * @param keyMapper   Map的键映射
     * @param valueMapper Map中List的值映射
     * @param <T>         列表值类型
     * @param <K>         键类型
     * @param <V>         值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <T, K, V> Map<K, List<V>> toListMap(Map<K, List<V>> resultMap, Iterable<T> iterable, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (null == resultMap) {
            resultMap = MapUtil.newHashMap();
        }
        if (ObjectUtil.isNull(iterable)) {
            return resultMap;
        }

        for (T value : iterable) {
            resultMap.computeIfAbsent(keyMapper.apply(value), k -> new ArrayList<>()).add(valueMapper.apply(value));
        }

        return resultMap;
    }

    /**
     * 将列表转成HashMap
     *
     * @param iterable  值列表
     * @param keyMapper Map的键映射
     * @param <K>       键类型
     * @param <V>       值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <K, V> Map<K, V> toMap(Iterable<V> iterable, Function<V, K> keyMapper) {
        return toMap(iterable, keyMapper, v -> v);
    }

    /**
     * 将列表转成HashMap
     *
     * @param iterable    值列表
     * @param keyMapper   Map的键映射
     * @param valueMapper Map的值映射
     * @param <T>         列表值类型
     * @param <K>         键类型
     * @param <V>         值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <T, K, V> Map<K, V> toMap(Iterable<T> iterable, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return toMap(MapUtil.newHashMap(), iterable, keyMapper, valueMapper);
    }

    /**
     * 将列表转成Map
     *
     * @param resultMap   结果Map，通过传入map对象决定结果的Map类型
     * @param iterable    值列表
     * @param keyMapper   Map的键映射
     * @param valueMapper Map的值映射
     * @param <T>         列表值类型
     * @param <K>         键类型
     * @param <V>         值类型
     * @return HashMap
     * @since 5.3.6
     */
    public static <T, K, V> Map<K, V> toMap(Map<K, V> resultMap, Iterable<T> iterable, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (null == resultMap) {
            resultMap = MapUtil.newHashMap();
        }
        if (ObjectUtil.isNull(iterable)) {
            return resultMap;
        }

        for (T value : iterable) {
            resultMap.put(keyMapper.apply(value), valueMapper.apply(value));
        }

        return resultMap;
    }

    /**
     * Iterator转List<br>
     * 不判断，直接生成新的List
     *
     * @param <E>  元素类型
     * @param iter {@link Iterator}
     * @return List
     * @since 4.0.6
     */
    public static <E> List<E> toList(Iterable<E> iter) {
        if (null == iter) {
            return null;
        }
        return toList(iter.iterator());
    }

    /**
     * Iterator转List<br>
     * 不判断，直接生成新的List
     *
     * @param <E>  元素类型
     * @param iter {@link Iterator}
     * @return List
     * @since 4.0.6
     */
    public static <E> List<E> toList(Iterator<E> iter) {
        final List<E> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    /**
     * {@link Iterator} 转为 {@link Iterable}
     *
     * @param <E>  元素类型
     * @param iter {@link Iterator}
     * @return {@link Iterable}
     */
    public static <E> Iterable<E> asIterable(final Iterator<E> iter) {
        return () -> iter;
    }

    /**
     * 获取集合的第一个元素
     *
     * @param <T>      集合元素类型
     * @param iterable {@link Iterable}
     * @return 第一个元素
     */
    public static <T> T getFirst(Iterable<T> iterable) {
        if (null == iterable) {
            return null;
        }
        return getFirst(iterable.iterator());
    }

    /**
     * 获取集合的第一个元素
     *
     * @param <T>      集合元素类型
     * @param iterator {@link Iterator}
     * @return 第一个元素
     */
    public static <T> T getFirst(Iterator<T> iterator) {
        if (null != iterator && iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * 获取集合的最后一个元素
     *
     * @param <T>      集合元素类型
     * @param iterable {@link Iterable}
     * @return 最后一个元素
     */
    public static <T> T getLast(Iterable<T> iterable) {
        if (null == iterable) {
            return null;
        }
        return getLast(iterable.iterator());
    }

    /**
     * 获取集合的最后一个元素
     *
     * @param <T>      集合元素类型
     * @param iterator {@link Iterator}
     * @return 最后一个元素
     */
    public static <T> T getLast(Iterator<T> iterator) {
        T t;
        while (null != iterator && iterator.hasNext()) {
            t = iterator.next();
            if (!iterator.hasNext()) {
                return t;
            }
        }
        return null;
    }

    /**
     * 返回一个空Iterator
     *
     * @param <T> 元素类型
     * @return 空Iterator
     * @see Collections#emptyIterator()
     * @since 5.3.1
     */
    public static <T> Iterator<T> empty() {
        return Collections.emptyIterator();
    }

    /**
     * 返回 Iterable 对象的元素数量
     *
     * @param iterable Iterable对象
     * @return Iterable对象的元素数量
     * @since 5.5.0
     */
    public static int size(final Iterable<?> iterable) {
        if (null == iterable) {
            return 0;
        }

        if (iterable instanceof Collection<?>) {
            return ((Collection<?>) iterable).size();
        } else {
            return size(iterable.iterator());
        }
    }

    /**
     * 返回 Iterator 对象的元素数量
     *
     * @param iterator Iterator对象
     * @return Iterator对象的元素数量
     * @since 5.5.0
     */
    public static int size(final Iterator<?> iterator) {
        int size = 0;
        if (iterator != null) {
            while (iterator.hasNext()) {
                iterator.next();
                size++;
            }
        }
        return size;
    }

}
