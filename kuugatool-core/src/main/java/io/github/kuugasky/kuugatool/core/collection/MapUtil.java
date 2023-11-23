package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import io.github.kuugasky.kuugatool.core.encoder.UrlCodeUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Map工具类
 * 动态map
 * <p>
 * 容量-capacity : 16  【初始化容量大小，扩容公式：当前容量 * 2，扩容条件，size大小超过临界值】
 * 大小-size : 0   【map当前元素size大小】
 * 负载因子-loadFactor : 0.75f【负载因子，用于计算临界值】
 * 临界值-threshold : 16  【临界值，每次put前，计算阈值：capacity * loadFactor，如果size大于临界值，则进行扩容，16 > 32】
 * 扩容倍数：2倍
 * 底层实现：Map.Ent
 *
 * @author kuuga
 */
public final class MapUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private MapUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Java中HashMap的初始容量设置
     * 根据阿里巴巴Java开发手册上建议HashMap初始化时设置已知的大小，如果不超过16个，那么设置成默认大小16：
     * <p>
     * 集合初始化时， 指定集合初始值大小。
     * 说明： HashMap使用HashMap(int initialCapacity)初始化，
     * 正例：initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子（即loader factor）默认为0.75f， 如果暂时无法确定初始值大小，则默认设置为16。
     * 反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表，严重影响性能。
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 为Map设值
     *
     * @param map         Map实例
     * @param filterEmpty 是否过滤空键或空值
     * @param k           键
     * @param v           值
     * @return 设置后的Map
     */
    public static <K, V> Map<K, V> put(Map<K, V> map, boolean filterEmpty, K k, V v) {
        if (map == null) {
            return emptyMap();
        }
        if (filterEmpty && StringUtil.hasText(k) && StringUtil.hasText(v)) {
            map.put(k, v);
        }
        return map;
    }

    /**
     * 为Map设值(过滤空键或空值)
     *
     * @param map Map实例
     * @param k   键
     * @param v   值
     * @return 设置后的Map
     */
    public static <K, V> Map<K, V> put(Map<K, V> map, K k, V v) {
        return put(map, true, k, v);
    }

    /**
     * 创建HashMap实例
     *
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap() {
        return newHashMap(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 创建HashMap实例
     *
     * @param expectedSize 期望初始化容量
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(int expectedSize) {
        /* 最大限度避免扩容带来的性能消耗，建议默认容量数值设为【(int)((float) expectedSize / 0.75F + 1.0F)】
         * 采用Guava实现
         */
        return Maps.newHashMapWithExpectedSize(expectedSize);
    }

    /**
     * 创建HashMap实例
     *
     * @param filterEmpty 是否过滤空键或空值
     * @param k           键
     * @param v           值
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(boolean filterEmpty, K k, V v) {
        Map<K, V> map = newHashMap();
        put(map, filterEmpty, k, v);
        return map;
    }

    /**
     * 创建HashMap实例
     *
     * @param filterEmpty 是否过滤空键或空值
     * @param k1          键1
     * @param v1          值1
     * @param k2          键2
     * @param v2          值2
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(boolean filterEmpty, K k1, V v1, K k2, V v2) {
        Map<K, V> map = newHashMap();
        put(map, filterEmpty, k1, v1);
        put(map, filterEmpty, k2, v2);
        return map;
    }

    /**
     * 创建HashMap实例
     *
     * @param filterEmpty 是否过滤空键或空值
     * @param k1          键1
     * @param v1          值1
     * @param k2          键2
     * @param v2          值2
     * @param k3          键3
     * @param v3          值3
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(boolean filterEmpty, K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = newHashMap(filterEmpty, k1, v1, k2, v2);
        put(map, filterEmpty, k3, v3);
        return map;
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k 键
     * @param v 值
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(K k, V v) {
        return newHashMap(true, k, v);
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k1 键1
     * @param v1 值1
     * @param k2 键2
     * @param v2 值2
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(K k1, V v1, K k2, V v2) {
        return newHashMap(true, k1, v1, k2, v2);
    }

    /**
     * 创建HashMap实例(过滤空键或空值)
     *
     * @param k1 键1
     * @param v1 值1
     * @param k2 键2
     * @param v2 值2
     * @param k3 键3
     * @param v3 值3
     * @return HashMap实例
     */
    public static <K, V> Map<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        return newHashMap(true, k1, v1, k2, v2, k3, v3);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K> K
     * @param <V> V
     * @return TreeMap
     */
    public static <K extends Comparable<?>, V extends @Nullable Object> TreeMap<K, V> newTreeMap() {
        return Maps.newTreeMap();
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map
     * @param comparator Key比较器
     * @return TreeMap
     * @since 3.2.3
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (!isEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    /**
     * 创建一个空的{@link ConcurrentHashMap}实例
     *
     * @param <K> K 不能为null
     * @param <V> V 不能为null
     * @return ConcurrentMap
     */
    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return Maps.newConcurrentMap();
    }

    /**
     * 创建一个空的{@link ConcurrentHashMap}实例
     *
     * @param <K> K 不能为null
     * @param <V> V 不能为null
     * @return ConcurrentMap
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>(1 << 4);
    }

    /**
     * Creates a <i>mutable</i>, empty, insertion-ordered {@code LinkedHashMap} instance.
     *
     * @param <K> K
     * @param <V> V
     * @return LinkedHashMap
     */
    public static <K extends @Nullable Object, V extends @Nullable Object> LinkedHashMap<K, V> newLinkedHashMap() {
        return Maps.newLinkedHashMap();
    }

    /**
     * 判断Map是否为空(null||empty)
     *
     * @param map map
     * @return boolean
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否包含项(size>0)
     *
     * @param map map
     * @return boolean
     */
    public static <K, V> boolean hasItem(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 获取Map的size
     *
     * @return int 为null时返回0
     */
    public static <K, V> int size(Map<K, V> map) {
        return map == null ? 0 : map.size();
    }

    // emptyMap ================================================================================================================================================

    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    // 清除元素 ================================================================================================================================================

    /**
     * 过滤Map中key为null或空字符串的元素
     *
     * @param map Map实例
     */
    public static <K, V> void removeIfKeyEmpty(Map<K, V> map) {
        if (hasItem(map)) {
            map.keySet().removeIf(StringUtil::isEmpty);
        }
    }

    /**
     * 过滤Map中value为null或空字符串的元素
     *
     * @param map Map实例
     */
    public static <K, V> void removeIfValueEmpty(Map<K, V> map) {
        if (hasItem(map)) {
            map.values().removeIf(value -> value == null || StringUtil.isEmpty(value.toString()));
        }
    }

    // Collections 工具 ================================================================================================================================================

    /**
     * 返回一个只读的map【封装集群】
     *
     * @param objectMap map
     * @return 只读集合
     */
    public static Map<?, ?> unmodifiableMap(Map<?, ?> objectMap) {
        return Collections.unmodifiableMap(objectMap);
    }

    // 反射获取map内部属性 ================================================================================================================================================

    /**
     * 获取map容量大小
     */
    public static <K, V> Object getCapacity(Map<K, V> map) {
        try {
            Class<?> mapType = map.getClass();
            Method capacity = mapType.getDeclaredMethod("capacity");
            capacity.setAccessible(true);
            return capacity.invoke(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 获取map的size
     */
    public static <K, V> Object getSize(Map<K, V> map) {
        try {
            Class<?> mapType = map.getClass();
            Field size = mapType.getDeclaredField("size");
            size.setAccessible(true);
            return size.get(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 获取map的负载因数
     */
    public static <K, V> Object getLoadFactor(Map<K, V> map) {
        try {
            Class<?> mapType = map.getClass();
            Field size = mapType.getDeclaredField("loadFactor");
            size.setAccessible(true);
            return size.get(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 获取map的阈值
     */
    public static <K, V> Object getThreshold(Map<K, V> map) {
        try {
            Class<?> mapType = map.getClass();
            Field size = mapType.getDeclaredField("threshold");
            size.setAccessible(true);
            return size.get(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // map to bean ================================================================================================================================================

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param tClass 要转化的类型
     * @param map    包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static <T> T toJavaBean(Class<T> tClass, Map<String, Object> map) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 获取类属性
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
        // 创建 JavaBean 对象
        T obj = tClass.getDeclaredConstructor().newInstance();

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    // print ================================================================================================================================================

    public static <K, V> void print(Map<K, V> map, boolean forceAlignment) {
        System.out.println(toString(map, forceAlignment));
    }

    public static <K, V> String toString(Map<K, V> map) {
        return toString(map, true);
    }

    public static <K, V> String toString(Map<K, V> map, boolean forceAlignment) {
        if (isEmpty(map)) {
            return StringUtil.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();

        if (forceAlignment) {
            int maxLength = map.keySet().stream().mapToInt(k -> {
                k = getKeyStr(k);
                return k.toString().length();
            }).max().orElse(0);
            map.forEach((k, v) -> {
                k = getKeyStr(k);
                int phaseDifference = maxLength - k.toString().length();
                stringBuilder.append("key: ").append(k).append(StringUtil.repeat(" ", phaseDifference + 3)).append("value: ").append(v).append("\n");
            });
        } else {
            map.forEach((k, v) -> {
                k = getKeyStr(k);
                stringBuilder.append("key: ").append(k.toString()).append("\t").append("value: ").append(v.toString()).append("\n");
            });
        }
        return StringUtil.removeEnd(stringBuilder.toString(), "\n");
    }

    private static <K> K getKeyStr(K k) {
        k = ObjectUtil.cast(ObjectUtil.getOrElse(k, "null"));
        return k;
    }

    // optimize ================================================================================================================================================

    public static <K, V> Map<K, V> optimize(Map<K, V> map) {
        if (Objects.isNull(map)) {
            return emptyMap();
        }
        return hasItem(map) ? map : emptyMap();
    }

    // toString ================================================================================================================================================

    public static <K, V> String join(Map<K, V> map, String separator) {
        return join(map, separator, false);
    }

    public static <K, V> String join(Map<K, V> map, String separator, boolean useUrlDecoder) {
        if (isEmpty(map)) {
            return StringUtil.EMPTY;
        }
        separator = StringUtil.hasText(separator) ? separator : "&";

        StringBuilder stringBuilder = new StringBuilder();
        String finalSeparator = separator;
        map.forEach((key, value) -> {
            String keyStr = String.valueOf(key);
            String valueStr = String.valueOf(value);
            stringBuilder.append(useUrlDecoder ? UrlCodeUtil.encode(keyStr) : keyStr).append("=").append(useUrlDecoder ? UrlCodeUtil.encode(valueStr) : valueStr).append(finalSeparator);
        });
        return StringUtil.removeEnd(stringBuilder.toString(), finalSeparator);
    }

    /**
     * Map转list
     *
     * @param map 源数据
     * @return list
     */
    public static <K, V> List<Map<K, V>> toList(Map<K, V> map) {
        if (isEmpty(map)) {
            return ListUtil.emptyList();
        }
        List<Map<K, V>> list = ListUtil.newArrayList(map.size());
        map.forEach((k, v) -> list.add(newHashMap(k, v)));
        return list;
    }

    public static <R, K, V> List<R> toList(Map<K, V> map, Function<? super Map.Entry<K, V>, R> mapper) {
        return map.entrySet().stream().map(mapper).collect(Collectors.toList());
    }

    // =========================================================

    /**
     * 排序已有Map，Key有序的Map，使用默认Key排序方式（字母顺序）
     *
     * @param <K> key的类型
     * @param <V> value的类型
     * @param map Map
     * @return TreeMap
     * @see #newTreeMap(Map, Comparator)
     * @since 4.0.1
     */
    public static <K, V> TreeMap<K, V> sortKey(Map<K, V> map) {
        // 简单转下treeMap，key sort
        return sortKey(map, null);
    }

    /**
     * 排序已有Map，Key有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map，为null返回null
     * @param comparator Key比较器
     * @return TreeMap，map为null返回null
     * @see #newTreeMap(Map, Comparator)
     * @since 4.0.1
     */
    public static <K, V> TreeMap<K, V> sortKey(Map<K, V> map, Comparator<? super K> comparator) {
        if (null == map) {
            return null;
        }

        TreeMap<K, V> result;
        if (map instanceof TreeMap) {
            // 已经是可排序Map，此时只有比较器一致才返回原map
            result = (TreeMap<K, V>) map;
            if (null == comparator || comparator.equals(result.comparator())) {
                return result;
            }
        } else {
            result = newTreeMap(map, comparator);
        }

        return result;
    }

    /**
     * 排序已有Map，Value有序的Map
     *
     * @param <K>        key的类型
     * @param <V>        value的类型
     * @param map        Map，为null返回null
     * @param comparator Value比较器
     * @return TreeMap，map为null返回null
     * @see #newTreeMap(Map, Comparator)
     * @since 4.0.1
     */
    public static <K, V> LinkedHashMap<K, V> sortValue(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        if (null == map) {
            return null;
        }

        // 利用Map的entrySet方法，转化为list进行排序
        List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());
        // 利用Collections的sort方法对list排序
        entryList.sort(comparator);
        // 遍历排序好的list，一定要放进LinkedHashMap，因为只有LinkedHashMap是根据插入顺序进行存储
        LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> e : entryList
        ) {
            linkedHashMap.put(e.getKey(), e.getValue());
        }
        return linkedHashMap;
    }

    // 非常规MAP ================================================================================

    /**
     * Multimap 一个key可以映射多个value的HashMap
     * <p>
     * Multimap的特点其实就是可以包含有几个重复Key的value，可以put进入多个不同value但是相同的key，但是又不会覆盖前面的内容
     *
     * @return Multimap多重映射map
     */
    public static <K, V> Multimap<K, V> newMultimap() {
        return ArrayListMultimap.create();
    }

    /**
     * BiMap的键必须唯一，值也必须唯一，可以实现value和key互转
     * <p>
     * 如果key重复：<br>
     * - put相同key会覆盖之前的键值<br>
     * - forcePut也会覆盖之前的键值<br>
     * 如果value重复：<br>
     * - put方法会抛异常，除非用forcePut方法<br>
     * 反转key/value[biMap.inverse()][输出 {"value":"key"}]<br>
     * 如果key和value都重复：<br>
     * - put会覆盖之前的键值，forcePut也一样<br>
     *
     * @return HashBiMap
     */
    public static <K, V> HashBiMap<K, V> newHashBiMap() {
        return HashBiMap.create();
    }

    /**
     * BiMap的键必须唯一，值也必须唯一，可以实现value和key互转
     *
     * @param expectedSize 预期大小
     * @return HashBiMap
     */
    public static <K, V> HashBiMap<K, V> newHashBiMap(int expectedSize) {
        return HashBiMap.create(expectedSize);
    }

    /**
     * Table 一种有两个key的HashMap【二维的Map】
     * <br>
     * 新增数据：table.put(R,C,V)
     * <br>
     * 获取数据：V v = table.get(R,C)
     * <br>
     * 遍历数据: Set<R> set = table.rowKeySet(); Set<C> set = table.columnKeySet();
     * <br>
     * 新增数据：table.put(R,C,V)
     * <br>
     * 获取数据：V v = table.get(R,C)
     * <br>
     * 遍历数据: Set<R> set = table.rowKeySet(); Set<C> set = table.columnKeySet();
     * <br>
     * <p>Example:
     * <table class="striped">
     * <caption>Rounding mode UP Examples</caption>
     * <thead>
     * <tr style="vertical-align:top"><th scope="col">Input Number</th>
     *    <th scope="col">Input rounded to one digit<br> with {@code UP} rounding
     * </thead>
     * <tbody>
     * <tr><th scope="row"></th><td>语文</td><td>数学</td></tr>
     * <tr><th scope="row">Kuuga</th><td>10</td><td>20</td></tr>
     * <tr><th scope="row">feng</th><td>30</td><td>40</td></tr>
     * </tbody>
     * </table>
     *
     * @param <R> 行
     * @param <C> 列
     * @param <V> 存储值
     */
    public static <R, C, V> Table<R, C, V> newTable() {
        return HashBasedTable.create();
    }

    // ====================================================================================================================

    /**
     * 计算map的差值
     * 两个Map相同的部分.entriesInCommon()
     * 左边集合剔除相同部分后的剩余.entriesOnlyOnLeft()
     * 右边集合剔除相同部分后的剩余.entriesOnlyOnRight()
     *
     * @param left  leftMap
     * @param right rightMap
     * @param <K>   K
     * @param <V>   V
     * @return MapDifference
     */
    public static <K extends @Nullable Object, V extends @Nullable Object> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
        return Maps.difference(left, right);
    }

    /**
     * 获取两个map相同的部分(k相同,v相同)
     * k相同且v为null的会过滤掉，如：1:null 和 1:null 不会判定为inCommon
     *
     * @param left  leftMap
     * @param right rightMap
     * @param <K>   K
     * @param <V>   V
     * @return map
     */
    public static <K, V> Map<K, V> inCommon(Map<K, V> left, Map<? extends K, ? extends V> right) {
        return Maps.difference(left, right).entriesInCommon();
    }

    /**
     * 根据指定条件过滤出map元素
     *
     * @param unfiltered     map
     * @param entryPredicate entryPredicate(eg:entry -> entry.getKey() == 1)
     * @param <K>            K
     * @param <V>            V
     * @return Map
     */
    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterEntries(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return Maps.filterEntries(unfiltered, entryPredicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterEntries(BiMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return Maps.filterEntries(unfiltered, entryPredicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterKeys(
            Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        return Maps.filterKeys(unfiltered, keyPredicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterKeys(
            BiMap<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        return Maps.filterKeys(unfiltered, keyPredicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterValues(
            Map<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
        return Maps.filterValues(unfiltered, valuePredicate);
    }

    @SuppressWarnings("all")
    public static <K extends @Nullable Object, V extends @Nullable Object> Map<K, V> filterValues(
            BiMap<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
        return Maps.filterValues(unfiltered, valuePredicate);
    }

    /**
     * 转换Entries
     *
     * @param fromMap     fromMap
     * @param transformer transformer
     * @param <K>         K
     * @param <V1>        V1
     * @param <V2>        V2
     * @return Map
     */
    public static <K extends @Nullable Object, V1 extends @Nullable Object, V2 extends @Nullable Object> Map<K, V2> transformEntries(
            Map<K, V1> fromMap, Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return Maps.transformEntries(fromMap, transformer);
    }

    /**
     * 转换Value
     *
     * @param fromMap  fromMap
     * @param function function
     * @param <K>      K
     * @param <V1>     V1
     * @param <V2>     V2
     * @return Map
     */
    public static <
            K extends @Nullable Object, V1 extends @Nullable Object, V2 extends @Nullable Object> Map<K, V2> transformValues(
            Map<K, V1> fromMap, Function<? super V1, V2> function) {
        return Maps.transformValues(fromMap, function::apply);
    }

    // reverse ====================================================================================================================

    /**
     * 骚操作-key value反转
     * 注意：key相同会覆盖value
     *
     * @param map map
     * @param <K> K
     * @param <V> V
     * @return new map
     */
    public static <K, V> Map<V, K> reverse(Map<K, V> map) {
        if (map.isEmpty()) {
            return MapUtil.emptyMap();
        }
        Map<V, K> resultMap = MapUtil.newHashMap(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            resultMap.put(entry.getValue(), entry.getKey());
        }
        return resultMap;
    }

}
