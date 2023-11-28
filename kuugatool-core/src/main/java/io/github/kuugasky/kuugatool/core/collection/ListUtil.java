package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import io.github.kuugasky.kuugatool.core.clazz.ClassUtil;
import io.github.kuugasky.kuugatool.core.collection.bean.FairCutData;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * ArrayList工具类
 * ArrayList是个动态数组
 * 初始化大小 : 初始化大小
 * 大小 : 0
 * 加载因子 : 1
 * 扩展倍数 : 1.5倍+1
 * 底层实现 : Object数组
 * 线程不安全
 *
 * @author kuuga
 */
public final class ListUtil {

    // 构造 ================================================================================================================================================

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ListUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // arrayList ================================================================================================================================================

    /**
     * 创建ArrayList实例
     *
     * @param <E> the type parameter
     * @return ArrayList实例 list
     */
    public static <E> List<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建ArrayList实例
     *
     * @param <E>   the type parameter
     * @param items 初始化元素列表
     * @return ArrayList实例 list
     */
    @SafeVarargs
    public static <E> List<E> newArrayList(E... items) {
        List<E> list = new ArrayList<>(items.length);
        Collections.addAll(list, items);
        return list;
    }

    /**
     * 创建ArrayList实例
     *
     * @param <E>             the type parameter
     * @param initialCapacity the initial capacity
     * @return ArrayList实例 list
     */
    public static <E> List<E> newArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = 0;
        }
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 创建ArrayList实例
     *
     * @param <E>        the type parameter
     * @param collection 源集合
     * @return ArrayList实例 list
     */
    public static <E> List<E> newArrayList(Collection<? extends E> collection) {
        return new ArrayList<>(collection);
    }

    // linkedList ================================================================================================================================================

    /**
     * 创建LinkedList实例
     * 如果有大量的增加删除操作并且没有很多的随机访问元素的操作，应该首先LinkedList。（LinkedList更适合从中间插入或者删除（链表的特性））
     *
     * @param <E> 泛型
     * @return LinkedList实例 list
     */
    public static <E> List<E> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * 创建LinkedList实例
     * 如果有大量的增加删除操作并且没有很多的随机访问元素的操作，应该首先LinkedList。（LinkedList更适合从中间插入或者删除（链表的特性））
     *
     * @param <E> 泛型
     * @return LinkedList实例 list
     */
    public static <E> List<E> newLinkedList(Collection<? extends E> collection) {
        return new LinkedList<>(collection);
    }

    // vector ================================================================================================================================================

    /**
     * 创建Vector集合
     *
     * @param <E> 泛型
     * @return Vector实例 vector
     */
    public static <E> Vector<E> newVector() {
        return new Vector<>();
    }

    // copyOnWriteArrayList ================================================================================================================================================

    /**
     * 新建一个CopyOnWriteArrayList
     *
     * @param <E>        集合元素类型
     * @param collection 集合
     * @return {@link CopyOnWriteArrayList}
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Collection<E> collection) {
        return (null == collection) ? (new CopyOnWriteArrayList<>()) : (new CopyOnWriteArrayList<>(collection));
    }

    // list系列快捷创建 ================================================================================================================================================

    /**
     * 新建一个空List
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @return List对象
     * @since 4.1.2
     */
    public static <E> List<E> list(boolean isLinked) {
        return isLinked ? newLinkedList() : newArrayList();
    }

    /**
     * 新建一个List
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param values   数组
     * @return List对象
     * @since 4.1.2
     */
    @SafeVarargs
    public static <E> List<E> list(boolean isLinked, E... values) {
        if (ArrayUtil.isEmpty(values)) {
            return list(isLinked);
        }
        final List<E> arrayList = isLinked ? newLinkedList() : newArrayList(values.length);
        Collections.addAll(arrayList, values);
        return arrayList;
    }

    /**
     * 新建一个List
     *
     * @param <E>        集合元素类型
     * @param isLinked   是否新建LinkedList
     * @param collection 集合
     * @return List对象
     * @since 4.1.2
     */
    public static <E> List<E> list(boolean isLinked, Collection<E> collection) {
        if (null == collection) {
            return list(isLinked);
        }
        return isLinked ? newLinkedList(collection) : newArrayList(collection);
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iterable {@link Iterable}
     * @return List对象
     * @since 4.1.2
     */
    public static <E> List<E> list(boolean isLinked, Iterable<E> iterable) {
        if (null == iterable) {
            return list(isLinked);
        }
        return list(isLinked, iterable.iterator());
    }

    /**
     * 新建一个ArrayList<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <E>      集合元素类型
     * @param isLinked 是否新建LinkedList
     * @param iter     {@link Iterator}
     * @return ArrayList对象
     * @since 4.1.2
     */
    public static <E> List<E> list(boolean isLinked, Iterator<E> iter) {
        final List<E> list = list(isLinked);
        if (null != iter) {
            while (iter.hasNext()) {
                list.add(iter.next());
            }
        }
        return list;
    }

    /**
     * 新建一个List<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <E>         集合元素类型
     * @param isLinked    是否新建LinkedList
     * @param enumeration {@link Enumeration}
     * @return ArrayList对象
     * @since 3.0.8
     */
    public static <E> List<E> list(boolean isLinked, Enumeration<E> enumeration) {
        final List<E> list = list(isLinked);
        if (null != enumeration) {
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        return list;
    }

    // hasItem isEmpty ================================================================================================================================================

    /**
     * 判断List是否为空(null||empty)
     *
     * @param <E>        the type parameter
     * @param collection collection
     * @return boolean
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断List是否包含项(size>0)
     *
     * @param <E>        the type parameter
     * @param collection collection
     * @return boolean
     */
    public static <E> boolean hasItem(Collection<E> collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取集合size
     *
     * @param <E>        the type parameter
     * @param collection collection
     * @return int 为null时返回0
     */
    public static <E> int size(Collection<E> collection) {
        if (hasItem(collection)) {
            return collection.size();
        }
        return 0;
    }

    // toMap ================================================================================================================================================

    /**
     * 将集合转为Map
     * <p>
     * 类似sql group by
     *
     * @param <T>        T
     * @param <K>        K
     * @param list       对象集合
     * @param classifier 分类器
     * @return map map
     */
    public static <T, K> Map<K, List<T>> toMap(final List<T> list, Function<? super T, ? extends K> classifier) {
        if (isEmpty(list)) {
            return MapUtil.emptyMap();
        }
        T first = findFirst(list);
        if (ClassUtil.isBasicType(first.getClass())) {
            return MapUtil.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 将集合转换为Map<br>
     * 注意：如果classifier提取出来的key有重复值，会抛异常:java.lang.IllegalStateException: Duplicate key
     *
     * @param list       list
     * @param classifier classifier
     * @param <T>        T
     * @param <K>        K
     * @return map
     */
    public static <T, K> Map<K, T> toMapSingle(final List<T> list, Function<? super T, ? extends K> classifier) {
        if (isEmpty(list)) {
            return MapUtil.emptyMap();
        }
        T first = findFirst(list);
        if (ClassUtil.isBasicType(first.getClass())) {
            return MapUtil.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(classifier, item -> item));
    }

    /**
     * 将集合转为Map
     * <p>
     * ⚠️Collectors.toMap内部有个逻辑Collectors.uniqKeysMapAccumulator[Objects.requireNonNull(valueMapper.apply(element));]
     * value不能为null，否则会报空指针异常，key可以为null
     *
     * @param <T>           T
     * @param <K>           K
     * @param <U>           U
     * @param list          对象集合
     * @param keyMapper     键映射器
     * @param valueMapper   值映射器
     * @param mergeFunction 合并函数
     *                      -- (k1, k2) -> k1 : Map<K, U>中的U只取分类后的第一个对象
     *                      -- (k1, k2) -> k2 : Map<K, U>中的U只取分类后的最后一个对象
     *                      -- (k1, k2) -> k2 - k1 : Map<K, U>中的U为分类后的最后一个对象减去第一个对象
     *                      -- 分类函数实际用法根据U的类型判定，比如U是String时可以：(k1, k2) -> k2 + "=" + k1
     * @return map map
     */
    public static <T, K, U> Map<K, U> toMap(final List<T> list,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper,
                                            BinaryOperator<U> mergeFunction) {
        if (isEmpty(list)) {
            return MapUtil.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * 多条件分组转Map
     *
     * @param list        集合
     * @param classifier1 第一分组条件
     * @param classifier2 第二分组条件
     * @return map
     */
    public static <K, A, T> Map<K, Map<A, List<T>>> toMap(final List<T> list, Function<T, K> classifier1, Function<T, A> classifier2) {
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                classifier1,
                                Collectors.groupingBy(classifier2)
                        )
                );
    }

    /**
     * 集合分区
     *
     * @param list      集合
     * @param predicate x-x>0 判断条件
     * @return 根据判断条件进行分区
     */
    public static <T> Map<Boolean, List<T>> toMap(final List<T> list, Predicate<T> predicate) {
        return partitioningBy(list, predicate);
    }

    // partitioningBy ======================================================================================================================

    /**
     * 集合分区
     * <p>
     * 返回一个收集器，该收集器根据谓词对输入元素进行分区，并将它们组织成Map<Boolean，List<T>>。
     * 返回的地图始终包含假键和真键的映射。对返回的地图或列表的类型、可变性、可序列化性或线程安全性没有保证。
     *
     * @param list      集合
     * @param predicate x-x>0 判断条件
     * @return 根据判断条件进行分区
     */
    public static <T> Map<Boolean, List<T>> partitioningBy(final List<T> list, Predicate<T> predicate) {
        return list.stream().collect(Collectors.partitioningBy(predicate));
    }

    // toArray ================================================================================================================================================

    /**
     * 将List转为数组
     *
     * @param <E> the type parameter
     * @param c   c
     * @param es  数组实例
     * @return 同类型数组 t [ ]
     */
    public static <E> E[] toArray(final Collection<E> c, E[] es) {
        return c.toArray(es);
    }

    /**
     * 从集合中获取某个属性
     * 集合对象，遍历集合，把对象中的【name】属性通过反射取出来，组装成list返回
     * 注：使用lambda更方便
     *
     * @param <E>        the type parameter
     * @param <R>        the type parameter
     * @param collection map/bean集合
     * @param name       key或属性名
     * @return 特定属性集合 list
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E, R> List<R> pluck(final Collection<E> collection, R name) {
        if (collection == null) {
            return emptyList();
        }

        List<R> results = newArrayList();
        if (StringUtil.isEmpty(name)) {
            return results;
        }
        if (collection.isEmpty()) {
            return results;
        }

        collection.forEach(obj -> {
            if (obj instanceof Map) {
                results.add((R) ((Map) obj).get(name));
            } else {
                results.add((R) ReflectionUtil.getFieldValue(obj, (String) name));
            }
        });

        return results;
    }

    // remove ================================================================================================================================================

    /**
     * 从集合中移除某个属性
     *
     * @param list the list
     * @param name the name
     * @return the collection
     */
    public static <E> Collection<E> remove(final List<E> list, E name) {
        if (hasItem(list)) {
            list.removeIf(name::equals);
        }
        return list;
    }

    /**
     * Remove of iterator list.
     *
     * @param list         the list
     * @param removeObject the remove object
     * @return the list
     */
    public static <E> List<E> removeByIterator(final List<E> list, E removeObject) {
        if (isEmpty(list)) {
            return list;
        }
        Iterator<E> listIterator = list.iterator();
        while (listIterator.hasNext()) {
            E obj = listIterator.next();
            if (obj == null) {
                continue;
            }
            if (ObjectUtil.equals(removeObject, obj)) {
                listIterator.remove();
            }
        }
        return list;
    }

    // 交集、并集处理 ================================================================================================================================================

    /**
     * 并集
     *
     * @param <E>        the type parameter
     * @param listArrays the list arrays
     * @return the collection
     */
    @SafeVarargs
    public static <E extends @Nullable Object> List<E> union(final List<E>... listArrays) {
        if (ArrayUtil.isEmpty(listArrays)) {
            return newArrayList();
        }
        List<List<E>> lists = Arrays.stream(listArrays).filter(Objects::nonNull).collect(Collectors.toList());
        List<E> list = new ArrayList<>();
        optimize(lists).forEach(list::addAll);
        return list;
    }

    /**
     * 并集
     *
     * @param <E>   the type parameter
     * @param list1 list1
     * @param list2 list2
     * @return list
     */
    public static <E extends @Nullable Object> List<E> union(final List<? extends E> list1, final List<? extends E> list2) {
        List<E> list = new ArrayList<>();
        Stream.of(list1, list2).forEach(list::addAll);
        return list;
    }

    /**
     * 去重并集
     *
     * @param <E>        the type parameter
     * @param listArrays the list arrays
     * @return list
     */
    @SafeVarargs
    public static <E extends @Nullable Object> List<E> uniqueUnion(final List<E>... listArrays) {
        if (ArrayUtil.isEmpty(listArrays)) {
            return newArrayList();
        }

        List<E> resultList = listArrays[0];

        for (int i = 1; i < listArrays.length; i++) {
            resultList = uniqueUnion(resultList, listArrays[i]);
        }
        return resultList;
    }

    /**
     * 去重并集
     *
     * @param <E>   the type parameter
     * @param list1 list1
     * @param list2 list2
     * @return list
     */
    private static <E extends @Nullable Object> List<E> uniqueUnion(final List<? extends E> list1, final List<? extends E> list2) {
        List<E> aList = newArrayList();
        List<E> bList = newArrayList();
        if (hasItem(list1)) {
            aList.addAll(list1);
        }
        if (hasItem(list2)) {
            bList.addAll(list2);
        }
        bList.removeAll(aList);
        aList.addAll(bList);
        return aList;
    }

    /**
     * 交集
     *
     * @param <E>        the type parameter
     * @param listArrays the list arrays
     * @return list
     */
    @SafeVarargs
    public static <E extends @Nullable Object> List<E> intersection(final List<E>... listArrays) {
        if (ArrayUtil.isEmpty(listArrays)) {
            return newArrayList();
        }

        List<E> resultList = listArrays[0];

        for (int i = 1; i < listArrays.length; i++) {
            resultList = intersection(resultList, listArrays[i]);
            // resultList = (List<E>) CollectionUtils.retainAll(resultList, listArrays[i]);
        }
        return resultList;
    }

    /**
     * 交集
     *
     * @param <E>   the type parameter
     * @param list1 list1
     * @param list2 list2
     * @return list
     */
    private static <E extends @Nullable Object> List<E> intersection(final List<? extends E> list1, final List<? extends E> list2) {
        List<E> aList = newArrayList();
        List<E> bList = newArrayList();
        if (hasItem(list1)) {
            aList.addAll(list1);
        }
        if (hasItem(list2)) {
            bList.addAll(list2);
        }
        aList.retainAll(bList);
        return aList;
    }

    /**
     * Subtract collection.
     *
     * @param <E>   the type parameter
     * @param list1 the list 1
     * @param list2 the list 2
     * @return the collection
     */
    @SuppressWarnings("all")
    public static <E> Collection<E> subtract(List<E> list1, List<E> list2) {
        return CollectionUtils.subtract(list1, list2);
    }

    // List合并 工具 ===================================================================================================================================================

    /**
     * Summary list.
     *
     * @param <E>   the type parameter
     * @param lists the lists
     * @return the list
     */
    public static <E> List<E> summary(List<List<E>> lists) {
        List<E> result = ListUtil.newArrayList();
        ListUtil.optimize(lists).forEach(listItem -> result.addAll(ListUtil.optimize(listItem)));
        return result;
    }

    // Collections 工具 ================================================================================================================================================

    /**
     * 返回一个无法改变的集合
     *
     * @param <E>        the type parameter
     * @param collection collection
     * @return Collection collection
     */
    public static <E> Collection<E> unmodifiableCollection(Collection<E> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    /**
     * 返回一个只读的list【封装集群】
     *
     * @param <E>        the type parameter
     * @param objectList list
     * @return 只读集合 list
     */
    public static <E> List<E> unmodifiableList(List<E> objectList) {
        return Collections.unmodifiableList(objectList);
    }

    /**
     * 返回指定列表支持的同步（线程安全）列表
     * 防止并发导致集合遍历的时候结构被改变而抛出fail-fast
     * 处理方案：
     * 1.在涉及到会影响到modCount值改变的地方，加上同步锁(synchronized)
     * 2.使用 Collections.synchronizedList()
     *
     * @param <E>        the type parameter
     * @param objectList list
     * @return 线程安全列表 list
     */
    public static <E> List<E> synchronizedList(List<E> objectList) {
        return Collections.synchronizedList(objectList);
    }

    // list split ================================================================================================================================================

    /**
     * Start end index of average split tree map.
     * 平均分裂视图的开始结束索引
     *
     * @param maxNumber     the max number
     * @param sizeOfOneTask the size of one task
     * @return the tree map
     */
    public static TreeMap<Integer, Integer> startEndIndexOfAverageSplit(int maxNumber, int sizeOfOneTask) {
        List<Integer> rangeInteger = createRangeInteger(1, maxNumber);
        List<List<Integer>> lists = splitAvg(rangeInteger, sizeOfOneTask);
        TreeMap<Integer, Integer> startEndIndexMap = new TreeMap<>();
        ListUtil.optimize(lists).forEach(list -> startEndIndexMap.put(findFirst(list), findLast(list)));
        return startEndIndexMap;
    }

    /**
     * 将list按均拆分
     *
     * @param <E>             the type parameter
     * @param sourceList      源list
     * @param pointsDataLimit 每个list包含元素数量
     * @return list
     */
    public static <E> List<List<E>> splitAvg(List<E> sourceList, int pointsDataLimit) {
        // list集合元素太多，可以分成若干个集合，每个集合10个元素-套一层list，Lists.partition返回的是asList
        return ListUtil.newArrayList(Lists.partition(sourceList, pointsDataLimit));
        // if (isEmpty(sourceList)) {
        //     return emptyList();
        // }
        // List<E> tempList = newArrayList(sourceList);
        // //限制条数
        // int sourceSize = tempList.size();
        // List<List<E>> result = newArrayList(sourceSize);
        // //判断是否有必要分批
        // if (pointsDataLimit >= sourceSize) {
        //     result.add(tempList);
        //     return result;
        // }
        //
        // //分批数
        // int part = sourceSize / pointsDataLimit;
        //
        // for (int i = 0; i < part; i++) {
        //     //pointsDataLimit条
        //     List<E> listPage = tempList.subList(0, pointsDataLimit);
        //     result.add(new ArrayList<>(listPage));
        //     //剔除
        //     tempList.subList(0, pointsDataLimit).clear();
        // }
        // if (hasItem(tempList)) {
        //     result.add(new ArrayList<>(tempList));
        //     tempList.clear();
        // }
        // return result;
    }

    /**
     * 数组拆分【4:4:2】
     *
     * @param <E>         T
     * @param sourceList  源数据
     * @param elementSize 需要拆分成多少份子集
     * @return list
     */
    public static <E> List<List<E>> splitAvgRatio(List<E> sourceList, int elementSize) {
        if (isEmpty(sourceList)) {
            return emptyList();
        }
        if (elementSize > sourceList.size()) {
            elementSize = sourceList.size();
        }
        List<List<E>> result = newArrayList(sourceList.size());
        int i;
        if (sourceList.size() % elementSize == 0) {
            i = sourceList.size() / elementSize;
        } else {
            i = sourceList.size() / elementSize + 1;
        }

        int endIndex = i;
        int startIndex = 0;

        for (int i1 = 0; i1 < elementSize; i1++) {
            if (i1 == elementSize - 1) {
                endIndex = sourceList.size();
            }
            List<E> strings = new ArrayList<>(sourceList.subList(startIndex, endIndex));
            result.add(strings);
            startIndex += i;
            endIndex += i;
        }
        return result;
    }

    /**
     * 数组拆分【4:3:3】
     *
     * @param <E>        T
     * @param sourceList 源数据
     * @param size       需要拆分成多少份子集
     * @return list
     */
    public static <E> List<List<E>> splitList(List<E> sourceList, int size) {
        List<List<E>> result = newArrayList();
        if (sourceList.size() < size) {
            result.add(sourceList);
            return result;
        }
        if (sourceList.size() % size != 0) {
            // 余数
            int remainder = sourceList.size() % size;
            // 默认列数大小
            int listSize = sourceList.size() / size;
            int j = 0;
            for (int i = 0; i < size; i++) {
                List<E> list = newArrayList();
                // 当前列数少于余数时，该列大小应该加1
                if (i < remainder) {
                    for (int i1 = 0; i1 < listSize + 1; i1++) {
                        list.add(sourceList.get(j++));
                    }
                } else {
                    for (int i1 = 0; i1 < listSize; i1++) {
                        list.add(sourceList.get(j++));
                    }
                }
                result.add(list);
            }
        } else {
            int listSize = sourceList.size() / size;
            int j = 0;
            for (int i = 0; i < size; i++) {
                List<E> list = newArrayList();
                for (int i1 = 0; i1 < listSize; i1++) {
                    list.add(sourceList.get(j++));
                }
                result.add(list);
            }

        }
        return result;
    }

    // emptyList ================================================================================================================================================

    /**
     * 空集合
     *
     * @param <E> 泛型
     * @return 空集合 list
     */
    public static <E> List<E> emptyList() {
        return Collections.emptyList();
    }

    // optimize ================================================================================================================================================

    /**
     * 优化封装list
     *
     * @param <E>    泛型
     * @param arrays 数组
     * @return list
     */
    public static <E> List<E> optimize(E[] arrays) {
        if (Objects.isNull(arrays)) {
            return emptyList();
        }
        if (ArrayUtil.isEmpty(arrays)) {
            return emptyList();
        }
        return newArrayList(arrays);
    }

    /**
     * 优化封装list
     *
     * @param <E>  泛型
     * @param list 数组
     * @return list
     */
    public static <E> List<E> optimize(List<E> list) {
        if (Objects.isNull(list)) {
            return emptyList();
        }
        if (hasItem(list)) {
            return list;
        }
        return emptyList();
    }

    /**
     * Optimize list.
     *
     * @param <E>        the type parameter
     * @param list       the list
     * @param filterNull the filter null
     * @return the list
     */
    public static <E> List<E> optimize(List<E> list, boolean filterNull) {
        if (Objects.isNull(list)) {
            return emptyList();
        }
        if (!filterNull) {
            return optimize(list);
        }

        return ListUtil.optimize(list).stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    // find ================================================================================================================================================

    /**
     * 获取list中存放的第一个元素
     *
     * @param <E>  集合元素泛型
     * @param list list
     * @return o 第一个元素
     */
    public static <E> E findFirst(List<E> list) {
        return optimize(list).stream().findFirst().orElse(null);
    }

    /**
     * 获取list中存放的第一个元素
     *
     * @param <E>          集合元素泛型
     * @param list         list
     * @param defaultValue the default value
     * @return o 第一个元素
     */
    public static <E> E findFirst(List<E> list, E defaultValue) {
        return optimize(list).stream().findFirst().orElse(defaultValue);
    }

    /**
     * 获取list中存放的最后一个元素
     *
     * @param <E>  <E>
     * @param list list
     * @return o t
     */
    public static <E> E findLast(List<E> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 获取list中存放的最后一个元素
     *
     * @param <E>          <E>
     * @param list         list
     * @param defaultValue the default value
     * @return o t
     */
    public static <E> E findLast(List<E> list, E defaultValue) {
        if (isEmpty(list)) {
            return defaultValue;
        }
        E lastElement = list.get(list.size() - 1);
        return ObjectUtil.nonNull(lastElement) ? lastElement : defaultValue;
    }

    /**
     * 获取list中key元素的下标
     *
     * @param <E>     the type parameter
     * @param list    list对象集合
     * @param element 元素对象
     * @return 下标值 int
     */
    public static <E> int indexOf(List<E> list, E element) {
        return list.indexOf(element);
    }

    // 生成范围数、随机数 ================================================================================================================================================

    /**
     * 生成范围整数集合[m,n]
     * <p>
     * .boxed() 返回一个包含该流元素的{@code Stream}，每个装箱为{@code Integer}。
     *
     * @param startInclusive startInclusive
     * @param endInclusive   endInclusive
     * @return List<Integer> list
     */
    public static List<Integer> createRangeInteger(int startInclusive, int endInclusive) {
        // return Stream.iterate(startInclusive, item -> item + 1).limit(endExclusive - startInclusive + 1).collect(Collectors.toList());
        return IntStream.range(startInclusive, endInclusive + 1).boxed().collect(Collectors.toList());
    }

    /**
     * 生成范围整数Long集合[m,n]
     * <p>
     * .boxed() 返回一个包含该流元素的{@code Stream}，每个装箱为{@code Long}。
     *
     * @param startInclusive startInclusive
     * @param endInclusive   endInclusive
     * @return List<Long> list
     */
    public static List<Long> createRangeLong(int startInclusive, int endInclusive) {
        return LongStream.range(startInclusive, endInclusive + 1).boxed().collect(Collectors.toList());
    }

    /**
     * 生成范围字符串集合[m,n]
     *
     * @param startInclusive startInclusive
     * @param endInclusive   endInclusive
     * @return List<String> list
     */
    public static List<String> createRangeString(int startInclusive, int endInclusive) {
        return IntStream.range(startInclusive, endInclusive + 1).mapToObj(String::valueOf).collect(Collectors.toList());
    }

    /**
     * 获取随机双精度值
     *
     * @param limitCount list.size
     * @return list random double value
     */
    public static List<Double> getRandomDoubleValue(int limitCount) {
        // 这段代码就是先获取一个无限长度的双精度集合的Stream，然后取出前limitCount个打印。千万记住使用limit方法，不然会无限打印下去。
        return Stream.generate(Math::random)
                .limit(limitCount)
                .collect(Collectors.toList());
    }

    /**
     * 获取随机整数值集合，整数值取值范围[m,n]
     *
     * @param startInclusive 开始包含
     * @param endInclusive   结束包含
     * @param limitCount     限制个数
     * @return list random integer values
     */
    public static List<Integer> getRandomIntegerValues(int startInclusive, int endInclusive, int limitCount) {
        // 这段代码就是先获取一个无限长度的正整数集合的Stream，然后取出前limitCount个打印。千万记住使用limit方法，不然会无限打印下去。
        return Stream.generate(() -> startInclusive + (int) (Math.random() * (endInclusive + 1 - startInclusive)))
                .limit(limitCount)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定数量且元素不重复的随机元素集合
     *
     * @param list        源数据集合
     * @param expectCount 期望获取的元素数量
     * @param <E>         T
     * @return list
     */
    public static <E> List<E> getRandomElements(List<E> list, int expectCount) {
        if (ListUtil.isEmpty(list) || expectCount <= 0) {
            return ListUtil.emptyList();
        }

        int listSize = list.size();
        List<E> expectList = ListUtil.newArrayList();

        int[] indexArray = NumberUtil.generateRandomNumber(0, listSize, expectCount);
        for (int index : indexArray) {
            expectList.add(list.get(index));
        }
        return expectList;
    }

    // Filter ================================================================================

    /**
     * 过滤掉list的null元素
     *
     * @param <E>  the type parameter
     * @param list list
     * @return list
     */
    public static <E> List<E> nullFilter(List<E> list) {
        return ListUtil.optimize(list).stream().filter(ObjectUtil::nonNull).collect(Collectors.toList());
    }

    /**
     * 过滤掉list的null和空字符串元素
     *
     * @param <E>  the type parameter
     * @param list list
     * @return list
     */
    public static <E> List<E> blankFilter(List<E> list) {
        return ListUtil.optimize(list).stream().filter(StringUtil::hasText).collect(Collectors.toList());
    }

    // get ================================================================================

    /**
     * Get t.
     *
     * @param <E>   the type parameter
     * @param list  the list
     * @param index the index
     * @return the t
     */
    public static <E> E get(List<E> list, int index) {
        if (index <= 0) {
            return null;
        }
        if (ListUtil.isEmpty(list)) {
            return null;
        }
        if (index > list.size() - 1) {
            return null;
        }
        return list.get(index);
    }

    // distinct ================================================================================

    /**
     * list去重
     *
     * @param <E>  T
     * @param list list
     * @return list
     */
    public static <E> List<E> distinct(final List<E> list) {
        return distinct(list, false);
    }

    /**
     * list去重
     *
     * @param list       list
     * @param filterNull 是否过滤掉null
     * @param <E>        E
     * @return list
     */
    public static <E> List<E> distinct(final List<E> list, boolean filterNull) {
        return ListUtil.optimize(list, filterNull).stream().distinct().collect(Collectors.toList());
    }

    /**
     * list抽取对象某一属性值去重返回list
     *
     * @param list   list
     * @param mapper 某一属性值
     * @param <T>    T
     * @param <R>    R
     * @return list
     */
    public static <T, R> List<R> distinct(List<T> list, Function<? super T, ? extends R> mapper) {
        return optimize(list).stream().map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * list抽取对象某一属性值去重返回list
     *
     * @param list       list
     * @param filterNull 是否过滤掉null
     * @param mapper     某一属性值
     * @param <T>        T
     * @param <R>        R
     * @return list
     */
    public static <T, R> List<R> distinct(List<T> list, Function<? super T, ? extends R> mapper, boolean filterNull) {
        return optimize(list, filterNull).stream().map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * list转set
     *
     * @param list list
     * @param <E>  E
     * @return set
     */
    public static <E> Set<E> toSet(final List<E> list) {
        return toSet(list, false);
    }

    /**
     * list转set
     *
     * @param list       list
     * @param filterNull 是否过滤掉null
     * @param <E>        E
     * @return set
     */
    public static <E> Set<E> toSet(final List<E> list, boolean filterNull) {
        return new HashSet<>(ListUtil.optimize(list, filterNull));
    }

    // filter ================================================================================

    public static <T> List<T> filter(final List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    // flat ================================================================================

    /**
     * 集合对象扁平化转list
     *
     * @param <T>    T
     * @param <R>    R
     * @param list   原集合
     * @param mapper 映射器
     * @return 扁平后的集合 list
     */
    public static <T, R> List<R> flat(List<T> list, Function<? super T, ? extends R> mapper) {
        // return Lists.transform(list, mapper);
        return optimize(list).stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 数组对象扁平化转list
     *
     * @param <T>    T
     * @param <R>    R
     * @param array  原数组
     * @param mapper 映射器
     * @return 扁平后的集合 list
     */
    public static <T, R> List<R> flat(T[] array, Function<? super T, ? extends R> mapper) {
        return optimize(array).stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合对象扁平化转set
     *
     * @param <T>    the type parameter
     * @param <R>    the type parameter
     * @param list   the list
     * @param mapper the mapper
     * @return the set
     */
    public static <T, R> Set<R> flatToSet(List<T> list, Function<? super T, ? extends R> mapper) {
        return optimize(list).stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * 数组对象扁平化转set
     *
     * @param <T>    the type parameter
     * @param <R>    the type parameter
     * @param array  the array
     * @param mapper the mapper
     * @return the set
     */
    public static <T, R> Set<R> flatToSet(T[] array, Function<? super T, ? extends R> mapper) {
        return optimize(array).stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * 集合对象扁平化转set
     *
     * @param <T>    the type parameter
     * @param <R>    the type parameter
     * @param list   the list
     * @param mapper the mapper (对象树形提取后转换为{@link Stream})
     * @return the set
     */
    public static <T, R> Stream<R> flatMap(List<T> list, Function<? super T, ? extends Stream<? extends R>> mapper) {
        return list.stream().flatMap(mapper);
    }

    /**
     * 结合对象扁平化转list，并判断转换后的list中任一元素是否符合predicate条件，是则返回true
     *
     * @param <T>       T
     * @param <R>       R
     * @param list      原集合
     * @param mapper    映射器
     * @param predicate 条件判断
     * @return boolean
     */
    public static <T, R> boolean flatAndAnyMatch(List<T> list, Function<? super T, ? extends R> mapper, Predicate<? super R> predicate) {
        return optimize(list).stream().map(mapper).anyMatch(predicate);
    }

    /**
     * Flat and all match boolean.
     * <p>
     * 结合对象扁平化转list，并判断转换后的list中所有元素是否符合predicate条件，是则返回true
     *
     * @param <T>       the type parameter
     * @param <R>       the type parameter
     * @param list      the list
     * @param mapper    the mapper
     * @param predicate the predicate
     * @return the boolean
     */
    public static <T, R> boolean flatAndAllMatch(List<T> list, Function<? super T, ? extends R> mapper, Predicate<? super R> predicate) {
        return optimize(list).stream().map(mapper).allMatch(predicate);
    }

    /**
     * Flat and none match boolean.
     * <p>
     * 结合对象扁平化转list，并判断转换后的list中所有元素是否都不符合predicate条件，是则返回true
     *
     * @param <T>       the type parameter
     * @param <R>       the type parameter
     * @param list      the list
     * @param mapper    the mapper
     * @param predicate the predicate
     * @return the boolean
     */
    public static <T, R> boolean flatAndNoneMatch(List<T> list, Function<? super T, ? extends R> mapper, Predicate<? super R> predicate) {
        return optimize(list).stream().map(mapper).noneMatch(predicate);
    }

    // equals ================================================================================

    /**
     * 判断list是否相同
     * <p>
     * - size是否形同
     * - 取AB交集判断交集size是否等于A
     *
     * @param <E>   the type parameter
     * @param listA listA
     * @param listB listB
     * @return true/false
     */
    public static <E> boolean equals(List<E> listA, List<E> listB) {
        listA = ObjectUtil.getOrElse(listA, ListUtil.emptyList());
        listB = ObjectUtil.getOrElse(listB, ListUtil.emptyList());

        if (listA.size() != listB.size()) {
            return false;
        }
        if (listA.isEmpty()) {
            return true;
        }
        Collection<E> intersection = intersection(listA, listB);
        return intersection.size() == listA.size();
    }

    /**
     * 判断list是否不相同
     *
     * @param <E>   the type parameter
     * @param listA listA
     * @param listB listB
     * @return true/false
     */
    public static <E> boolean equalsNo(List<E> listA, List<E> listB) {
        return !equals(listA, listB);
    }

    /**
     * 将指定字符串的视图作为不可变的字符值列表返回
     *
     * @param string 字符串
     * @return 字符值列表
     */
    public static ImmutableList<Character> charactersOf(String string) {
        return Lists.charactersOf(string);
    }

    // 笛卡尔积 =============================================================================================================

    /**
     * 通过按顺序从每个给定列表中选择一个元素，返回每个可能的列表<br>
     * <a href="http://en.wikipedia.org/wiki/Cartesian_product">Cartesian product(笛卡尔积)</a>
     *
     * @param lists lists
     * @param <B>   B
     * @return list
     */
    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B>... lists) {
        return Lists.cartesianProduct(lists);
    }

    // 公平切分 =============================================================================================================

    /**
     * 尽量公平的切割数据
     * <p>
     * eg:<br>
     * 入参:<br>
     * [["id":1,"count":1],["id":2,"count":2],["id":3,"count":3]]<br>
     * 假如innerArrayCountSumLimitUpper=3，innerArrayElementCountLimitUpper=2
     * 出参:<br>
     * [[3],[1,2]]
     *
     * @param fairCutDataList                  公平切割数据集合
     *                                         - FairCutData.id:值
     *                                         - FairCutData.count:数量
     * @param sumCount                         总数据量 sum(fairCutDataList.count)
     * @param innerArrayCountSumLimitUpper     内层数组count总和上限（内层数组id对应的countSum上限）
     * @param innerArrayElementCountLimitUpper 内层数组id元素量上限（内层数组元素count上限）
     */
    public static List<List<String>> splitData(List<FairCutData> fairCutDataList,
                                               AtomicInteger sumCount,
                                               int innerArrayCountSumLimitUpper,
                                               int innerArrayElementCountLimitUpper
    ) {
        Deque<FairCutData> gardenIdDeque = Queues.newArrayDeque(fairCutDataList);
        List<List<String>> splitList = ListUtil.newArrayList();
        FairCutData poll;
        // 读取末尾元素
        while ((poll = gardenIdDeque.pollLast()) != null) {
            List<String> gardenIdList = ListUtil.newArrayList(poll.getId());
            if (poll.getCount() > innerArrayCountSumLimitUpper) {
                // 如果末尾元素数量大于外层数组限定值，该元素自成一个数组
                sumCount.addAndGet(poll.getCount());
                splitList.add(gardenIdList);
                continue;
            }

            FairCutData accum;
            // 如果末尾元素数量小于外层数组限定值，则从首位摘出元素，判断两个元素的count量
            while ((accum = gardenIdDeque.peekFirst()) != null) {
                // 双端碰头（插入到下一个外层数组）
                if (accum == poll) {
                    // 结束
                    break;
                }

                // 超过max 跳出（插入到下一个外层数据）
                if (poll.getCount() + accum.getCount() > innerArrayCountSumLimitUpper) {
                    break;
                }

                // 每次最多查询100个楼盘（插入到下一个外层数据）
                if (gardenIdList.size() >= innerArrayElementCountLimitUpper) {
                    break;
                }

                // 没超过max 累加（两个元素的count量小于外层数组限定值，合并成一个内层数组，并移除首位元素）
                poll.setCount(poll.getCount() + accum.getCount());
                gardenIdList.add(accum.getId());
                gardenIdDeque.removeFirst();
            }
            sumCount.addAndGet(poll.getCount());
            splitList.add(gardenIdList);
        }
        return splitList;
    }

    // limit =============================================================================================================

    /**
     * 返回由此流的元素组成的流，其长度被截断为不超过maxSize。
     * <p>
     * - list.size() > maxSize >= 0 : 有序截取
     * - maxSize > list.size() : 截取最大位数list.size()
     * - maxSize < 0 : 异常java.lang.IllegalArgumentException
     *
     * @param list    集合
     * @param maxSize 最大截取位数
     * @param <E>     E
     * @return 截取后新生成的list
     */
    public static <E> List<E> limit(List<E> list, long maxSize) {
        return list.stream().limit(maxSize).collect(Collectors.toList());
    }

    // skip =============================================================================================================

    /**
     * 在丢弃流的前n个元素后，返回由该流的其余元素组成的流。如果此流包含的元素少于n个，则返回空流。
     *
     * @param list 集合
     * @param n    要跳过的前导元素的数量
     * @param <E>  E
     * @return 跳过特定元素后新生成的list
     */
    public static <E> List<E> skip(List<E> list, long n) {
        return list.stream().skip(n).collect(Collectors.toList());
    }

    // count =============================================================================================================

    /**
     * 根据条件过滤后计数
     *
     * @param list      集合
     * @param predicate 过滤条件
     * @param <E>       E
     * @return 截取后新生成的list
     */
    public static <E> long count(List<E> list, Predicate<? super E> predicate) {
        return list.stream().filter(predicate).count();
    }

    // anyMatch/allMatch/noneMatch =============================================================================================================

    /**
     * 任意匹配
     * <p>
     * 任意一个匹配，返回true；全部不匹配，返回false。
     *
     * @param list      集合
     * @param predicate 判断条件
     * @param <E>       E
     * @return boolean
     */
    public static <E> boolean anyMatch(List<E> list, Predicate<? super E> predicate) {
        return list.stream().anyMatch(predicate);
    }

    /**
     * 全部匹配
     * <p>
     * 全部匹配，返回true；任意一个不匹配，返回false。
     *
     * @param list      集合
     * @param predicate 判断条件
     * @param <E>       E
     * @return boolean
     */
    public static <E> boolean allMatch(List<E> list, Predicate<? super E> predicate) {
        return list.stream().allMatch(predicate);
    }

    /**
     * 全部不匹配
     * <p>
     * 全部不匹配，返回true；匹配到任意一个，返回false。
     *
     * @param list      集合
     * @param predicate 判断条件
     * @param <E>       E
     * @return boolean
     */
    public static <E> boolean noneMatch(List<E> list, Predicate<? super E> predicate) {
        return list.stream().noneMatch(predicate);
    }

    // findAny =============================================================================================================

    /**
     * 返回描述流中某些元素的可选参数，如果流为空则返回空的可选参数。
     * <p>
     * - stream():串行流就是一个线程在处理业务
     * - parallelStream():并行流是会有多个线程来并发处理业务
     * <p>
     * 通过返回结果观察，list像是分成了树形数据结构，每次返回结果大量重复在某一个节点上。比如1-100，取值到66概率最高。
     *
     * @param list 集合
     * @param <E>  E
     * @return E
     */
    public static <E> E findAny(List<E> list) {
        // 使用串行流时，无论是单线程或者多线程，多次拿到的都是第一个元素，类似findFirst()

        // return list.stream().findAny().orElse(null);

        // 使用并行流时，无论是单线程或者多线程，多次拿到的都是随机元素（多次调用仍有可能取到的都是同一个元素）

        return list.parallelStream().findAny().orElse(null);
    }

    // peek =============================================================================================================

    /**
     * 返回由此流的元素组成的流，并在从结果流中消费元素时对每个元素执行所提供的操作。
     * 这是一个中间操作。
     * <p>
     * peek官方文档说明：peek方法主要用于调试，在流程某个步骤查看执行元素情况。
     * <p>
     * peek操作可以对集合中的元素进行直接操作。也可用于用其他容器去提取或接收元素，也可进行元素查看。
     *
     * @param list   集合
     * @param action 从结果流中消费元素时要执行的操作
     * @param <E>    E
     */
    public static <E> void peeked(List<E> list, Consumer<? super E> action) {
        // toList()后空循环是为了让流关闭，action才会执行完
        List<E> es = list.stream().peek(action).toList();
        int i = 0;
        while (i < es.size()) {
            i++;
        }
    }

}