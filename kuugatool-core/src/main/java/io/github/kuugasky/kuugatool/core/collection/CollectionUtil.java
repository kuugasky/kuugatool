package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 集合工具类
 *
 * @author kuuga
 * @since 2023-01-01
 */
public final class CollectionUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private CollectionUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     * @see IterUtil#isEmpty(Iterable)
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return IterUtil.isEmpty(iterable);
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean hasItem(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 增强遍历[下标]
     *
     * @param elements Iterable实现类
     * @param action   消费者行为
     * @param <T>      T
     */
    public static <T> void forEach(Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (T element : elements) {
            action.accept(index++, element);
        }
    }

    /**
     * 清除一个或多个集合内的元素，每个集合调用clear()方法
     *
     * @param collections 一个或多个集合
     * @since 5.3.6
     */
    public static void clear(Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (hasItem(collection)) {
                collection.clear();
            }
        }
    }

    /**
     * 反序给定List，会在原List基础上直接修改
     *
     * @param <T>  元素类型
     * @param list 被反转的List
     * @return 反转后的List
     * @since 4.0.6
     */
    public static <T> List<T> reverse(List<T> list) {
        return ListSortUtil.reverse(list);
    }

    /**
     * 反序给定List，会创建一个新的List，原List数据不变
     *
     * @param <T>  元素类型
     * @param list 被反转的List
     * @return 反转后的List
     * @since 4.0.6
     */
    public static <T> List<T> reverseNew(List<T> list) {
        return ListSortUtil.reverseNew(list);
    }

    /**
     * 获取指定Map列表中所有的Key
     *
     * @param <K>           键类型
     * @param mapCollection Map列表
     * @return key集合
     * @since 4.5.12
     */
    public static <K> Set<K> keySet(Collection<Map<K, ?>> mapCollection) {
        if (isEmpty(mapCollection)) {
            return new HashSet<>();
        }
        final HashSet<K> set = new HashSet<>(mapCollection.size() * 16);
        for (Map<K, ?> map : mapCollection) {
            set.addAll(map.keySet());
        }

        return set;
    }

    /**
     * 获取指定Map列表中所有的Value
     *
     * @param <V>           值类型
     * @param mapCollection Map列表
     * @return Value集合
     * @since 4.5.12
     */
    public static <V> List<V> values(Collection<Map<?, V>> mapCollection) {
        final List<V> values = new ArrayList<>();
        for (Map<?, V> map : mapCollection) {
            values.addAll(map.values());
        }

        return values;
    }

    /**
     * 取最大值
     *
     * @param <T>  元素类型
     * @param coll 集合
     * @return 最大值
     * @see Collections#max(Collection)
     * @since 4.6.5
     */
    public static <T extends Comparable<? super T>> T max(Collection<T> coll) {
        return Collections.max(coll);
    }

    /**
     * 取最小值
     *
     * @param <T>  元素类型
     * @param coll 集合
     * @return 最小值
     * @see Collections#min(Collection)
     * @since 4.6.5
     */
    public static <T extends Comparable<? super T>> T min(Collection<T> coll) {
        return Collections.min(coll);
    }


    /**
     * 转为只读集合
     *
     * @param <T> 元素类型
     * @param c   集合
     * @return 只读集合
     * @since 5.2.6
     */
    public static <T> Collection<T> unmodifiable(Collection<? extends T> c) {
        return Collections.unmodifiableCollection(c);
    }

    /**
     * 根据给定的集合类型，返回对应的空集合，支持类型包括：
     * *
     * <pre>
     *     1. NavigableSet
     *     2. SortedSet
     *     3. Set
     *     4. List
     * </pre>
     *
     * @param <E>             元素类型
     * @param <T>             集合类型
     * @param collectionClass 集合类型
     * @return 空集合
     * @since 5.3.1
     */
    @SuppressWarnings("unchecked")
    public static <E, T extends Collection<E>> T empty(Class<?> collectionClass) {
        if (null == collectionClass) {
            return (T) Collections.emptyList();
        }

        if (Set.class.isAssignableFrom(collectionClass)) {
            if (NavigableSet.class == collectionClass) {
                return (T) Collections.emptyNavigableSet();
            } else if (SortedSet.class == collectionClass) {
                return (T) Collections.emptySortedSet();
            } else {
                return (T) Collections.emptySet();
            }
        } else if (List.class.isAssignableFrom(collectionClass)) {
            return (T) Collections.emptyList();
        }

        // 不支持空集合的集合类型
        throw new IllegalArgumentException(StringUtil.format("[{}] is not support to get empty!", collectionClass));
    }

    /**
     * 将集合转为字符串[Iterator实现]
     *
     * @param collection 集合
     * @param separator  分隔符
     * @return 字符串
     */
    public static <T> String toString(Collection<T> collection, String separator) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtil.hasItem(collection)) {
            Iterator<?> it = collection.iterator();
            sb.append(it.next());

            while (it.hasNext()) {
                sb.append(separator).append(it.next());
            }
        }
        return sb.toString();
    }

    /**
     * 检测原集合是否包含了目标集合的某个元素
     *
     * @param sources     原集合
     * @param targetArray 目标数组
     * @param <T>         T
     * @return boolean
     */
    @SafeVarargs
    public static <T> boolean contains(Collection<T> sources, T... targetArray) {
        ObjectUtil.requireNonNull(sources, new NullPointerException("原集合不能为空"));
        ObjectUtil.requireNonNull(targetArray, new NullPointerException("原集合不能为空"));
        Set<T> targetSet = SetUtil.newHashSet(targetArray);
        return targetSet.stream().anyMatch(sources::contains);
    }

    /**
     * 检测原集合是否包含了目标集合的某个元素
     *
     * @param sources 原集合
     * @param targets 目标集合
     * @param <T>     T
     * @return boolean
     */
    public static <T> boolean contains(Collection<T> sources, Collection<T> targets) {
        ObjectUtil.requireNonNull(sources, new NullPointerException("原集合不能为空"));
        ObjectUtil.requireNonNull(targets, new NullPointerException("目标集合不能为空"));
        return targets.stream().anyMatch(sources::contains);
    }

    /**
     * 检测原集合是否完整包含了目标集合的所有元素
     *
     * @param sources 原集合
     * @param targets 目标集合
     * @param <T>     T
     * @return boolean
     */
    public static <T> boolean containsAll(Collection<T> sources, Collection<T> targets) {
        ObjectUtil.requireNonNull(sources, new NullPointerException("原集合不能为空"));
        ObjectUtil.requireNonNull(targets, new NullPointerException("目标集合不能为空"));
        return sources.containsAll(targets);
    }

    /**
     * 检测原集合是否完整包含了目标数组的所有元素
     *
     * @param sources     原集合
     * @param targetArray 目标数组
     * @param <T>         T
     * @return boolean
     */
    @SafeVarargs
    public static <T> boolean containsAll(Collection<T> sources, T... targetArray) {
        ObjectUtil.requireNonNull(sources, new NullPointerException("原集合不能为空"));
        ObjectUtil.requireNonNull(targetArray, new NullPointerException("原集合不能为空"));
        Set<T> targetSet = SetUtil.newHashSet(targetArray);
        return sources.containsAll(targetSet);
    }

    // findFirst or findLast ========================================================================================

    /**
     * 获取collection中存放的第一个元素
     *
     * @param <T>        集合元素泛型
     * @param collection collection
     * @return o 第一个元素
     */
    public static <T> T findFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().findFirst().orElse(null);
    }

    /**
     * 获取collection中存放的第一个元素
     *
     * @param <T>          集合元素泛型
     * @param collection   collection
     * @param defaultValue the default value
     * @return o 第一个元素
     */
    public static <T> T findFirst(Collection<T> collection, T defaultValue) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.stream().findFirst().orElse(defaultValue);
    }

}
