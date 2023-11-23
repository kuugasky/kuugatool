package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Set工具类
 *
 * @author kuuga
 */
public final class SetUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SetUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 创建HashSet实例
     *
     * @return ArrayList实例
     */
    public static <T> Set<T> newHashSet() {
        return new HashSet<>();
    }

    @SafeVarargs
    public static <T> Set<T> newHashSet(T... items) {
        Set<T> set = new HashSet<>(items.length);
        Collections.addAll(set, items);
        return set;
    }

    /**
     * 创建ConcurrentHashSet
     *
     * @param <E> E
     * @return ConcurrentHashSet
     */
    public static <E> Set<E> newConcurrentHashSet() {
        return Sets.newConcurrentHashSet();
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> elements) {
        return Sets.newConcurrentHashSet(elements);
    }

    /**
     * 创建LinkedHashSet
     *
     * @param <E> E
     * @return LinkedHashSet
     */
    public static <E extends @Nullable Object> LinkedHashSet<E> newLinkedHashSet() {
        return Sets.newLinkedHashSet();
    }

    public static <E extends @Nullable Object> LinkedHashSet<E> newLinkedHashSet(
            Iterable<? extends E> elements) {
        return Sets.newLinkedHashSet(elements);
    }

    /**
     * 创建TreeSet
     *
     * @param <E> E
     * @return TreeSet
     */
    public static <E extends Comparable<?>> TreeSet<E> newTreeSet() {
        return Sets.newTreeSet();
    }

    public static <E extends Comparable<?>> TreeSet<E> newTreeSet(Iterable<? extends E> elements) {
        return Sets.newTreeSet(elements);
    }

    /**
     * 判断Set是否为空(null||empty)
     *
     * @param collection collection
     * @return boolean
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Set是否包含项(size>0)
     *
     * @param collection collection
     * @return boolean
     */
    public static <T> boolean hasItem(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <T> Set<T> optimize(Set<T> set) {
        if (Objects.isNull(set)) {
            return emptySet();
        }
        if (hasItem(set)) {
            return set;
        }
        return emptySet();
    }

    // emptySet ================================================================================================================================================

    public static <T> Set<T> emptySet() {
        return Collections.emptySet();
    }

    // Collections 工具 ================================================================================================================================================

    /**
     * 返回一个只读的set【封装集群】
     *
     * @param objectSet set
     * @return 只读集合
     */
    public static <T> Set<T> unmodifiableSet(Set<T> objectSet) {
        return Collections.unmodifiableSet(objectSet);
    }

    /**
     * 返回指定列表支持的同步（线程安全）Set<br>
     * 防止并发导致集合遍历的时候结构被改变而抛出fail-fast<br>
     * 处理方案：<br>
     * 1.在涉及到会影响到modCount值改变的地方，加上同步锁(synchronized)<br>
     * 2.使用 Collections.synchronizedSet()
     *
     * @param objectSet set
     * @return 线程安全列表
     */
    public static <T> Set<T> synchronizedSet(Set<T> objectSet) {
        return Collections.synchronizedSet(objectSet);
    }

    /**
     * 获取list中存放的第一个元素
     *
     * @param set set
     * @param <T> <T>
     * @return o
     */
    public static <T> T findFirst(Set<T> set) {
        return optimize(set).stream().findFirst().orElse(null);
    }

    /**
     * 获取list中存放的最后一个元素
     *
     * @param set set
     * @param <T> <T>
     * @return o
     */
    public static <T> T findLast(Set<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        return ListUtil.newArrayList(set).get(set.size() - 1);
    }

    // 非常规set ========================================================================

    /**
     * Multiset 一种用来计数的Set<br>
     * 允许存在多个同属性元素<br>
     * 获取元素"c"的计数:multiset.count("c"),类似multiset.stream().collect(Collectors.groupingBy(String::new));<br>
     * 返回去重后的元素set集合:multiset.elementSet();<br>
     * multiset所有元素的个数:multiset.size();<br>
     * multiset去重后的元素个数:multiset.elementSet().size();<br>
     * 元素迭代:Iterator<String> it = multiset.iterator();<br>
     * 通过设置元素的计数，来批量的添加元素，当然能加也能减:multiset.setCount("c",5);<br>
     * 将元素的计数设为0，就相当于移除所有的"c"元素:multiset.setCount("c",0);<br>
     * 移除一个元素:multiset.remove("c");<br>
     * 移除两个元素:multiset.remove("c", 2);
     */
    public static <E> Multiset<E> newMultiset() {
        return HashMultiset.create();
    }

    public static <E> Multiset<E> newMultiset(Collection<? extends E> elementsToAdd) {
        HashMultiset<E> hashMultiset = HashMultiset.create();
        hashMultiset.addAll(elementsToAdd);
        return hashMultiset;
    }

    /**
     * SortedMultiset支持高效的获取指定范围的子集<br>
     * SortedMultiset默认是排好序的，是按元素来进行排序的而不是元素的个数<br>
     * <p>
     * 设置元素个数:sortedMultiset.setCount("c",5);<br>
     * 获取第一个元素:sortedMultiset.firstEntry().getElement();<br>
     * 获了最后一个元素:sortedMultiset.lastEntry().getElement();<br>
     * 获取子集:SortedMultiset<String> subMultiset = sortedMultiset.subMultiset("a", BoundType.OPEN,"b",BoundType.CLOSED);
     *
     * @param <E> E
     * @return SortedMultiset
     */
    public static <E extends Comparable<?>> SortedMultiset<E> newSortedMultiset() {
        return TreeMultiset.create();
    }

    // 并集、交集、差集 ========================================================================

    /**
     * Set并集
     * ps:因为是set所以相当于去重合集
     *
     * @param set1 set1
     * @param set2 set2
     * @return 并集Set
     */
    public static <E extends @Nullable Object> Sets.SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
        return Sets.union(set1, set2);
    }

    /**
     * Set交集
     *
     * @param setA a
     * @param setB b
     * @return 交集Set
     */
    public static <E> Set<E> intersection(Set<E> setA, Set<E> setB) {
        return Sets.intersection(setA, setB);
    }

    /**
     * Set差集
     *
     * @param set1 set1
     * @param set2 set2
     * @return 差集Set
     */
    public static <E extends @Nullable Object> Sets.SetView<E> difference(
            final Set<E> set1, final Set<?> set2) {
        return Sets.difference(set1, set2);
    }

    /**
     * 剔除两个set公共的元素，再取两个集合的并集
     *
     * @param set1 set1
     * @param set2 set2
     * @param <E>  E
     * @return Sets.SetView
     */
    public static <E extends @Nullable Object> Sets.SetView<E> symmetricDifference(
            final Set<? extends E> set1, final Set<? extends E> set2) {
        return Sets.symmetricDifference(set1, set2);
    }

    // 笛卡尔积 =============================================================================================================

    /**
     * 通过按顺序从每个给定列表中选择一个元素，返回每个可能的列表<br>
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">Cartesian product(笛卡尔积)</a>
     *
     * @param sets sets
     * @param <B>  B
     * @return list(每组组合元素量 等于 sets.size)
     */
    @SafeVarargs
    public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... sets) {
        return Sets.cartesianProduct(sets);
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> sets) {
        return Sets.cartesianProduct(sets);
    }

    // 排列组合 =============================================================================================================

    /**
     * 按指定大小进行排列组合
     *
     * @param set  set
     * @param size size
     * @param <E>  E
     * @return set
     */
    @SuppressWarnings("all")
    public static <E> Set<Set<E>> combinations(Set<E> set, final int size) {
        return Sets.combinations(set, size);
    }

    /**
     * 所有的排列组合
     *
     * @param set set
     * @param <E> E
     * @return set
     */
    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        Set<Set<E>> collect = Sets.powerSet(set).stream()
                .filter(Objects::nonNull)
                .filter(SetUtil::hasItem)
                .collect(Collectors.toSet());
        return collect.stream().sorted(Comparator.comparingInt(Set::size))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @SuppressWarnings("all")
    public static <E extends @Nullable Object> Set<E> filter(
            Set<E> unfiltered, Predicate<? super E> predicate) {
        return unfiltered.stream().filter(predicate).collect(Collectors.toSet());
        // return Sets.filter(unfiltered, predicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return Maps.asMap(set, function);
    }

}