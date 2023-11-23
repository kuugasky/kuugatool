package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.*;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.enums.ByteType;
import io.github.kuugasky.kuugatool.core.enums.EnumUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapUtilTest {

    @Test
    public void put() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2);
        System.out.println(MapUtil.put(integerIntegerMap, 3, 4));
    }

    @Test
    public void testPut() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2);
        System.out.println(MapUtil.put(integerIntegerMap, true, null, 4));
    }

    @Test
    public void testnewHashMap() {
        System.out.println(MapUtil.newHashMap(true, null, 1, null, 2, 3, 4));
    }

    @Test
    public void testNewMap1() {
        System.out.println(MapUtil.newHashMap(true, StringUtil.EMPTY, 1, 2, 3));
    }

    @Test
    public void testNewMap2() {
        System.out.println(MapUtil.newHashMap(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void testNewMap3() {
        System.out.println(MapUtil.newHashMap(10));
    }

    @Test
    public void NEW() {
        System.out.println(MapUtil.newHashMap(1, 2, 3, 4));
    }

    @Test
    public void testNewMap4() {
        System.out.println(MapUtil.newHashMap());
    }

    @Test
    public void isEmpty() {
        System.out.println(MapUtil.isEmpty(MapUtil.newHashMap()));
    }

    @Test
    public void hasItem() {
        System.out.println(MapUtil.hasItem(MapUtil.newHashMap()));
    }

    @Test
    public void size() {
        System.out.println(MapUtil.size(MapUtil.newHashMap()));
    }

    @Test
    public void emptyMap() {
        System.out.println(MapUtil.emptyMap());
    }

    @Test
    public void cleanKeyByNullAndEmpty() {
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();
        integerIntegerMap.put(null, 1);
        integerIntegerMap.put(2, null);
        integerIntegerMap.put(3, 4);
        MapUtil.removeIfKeyEmpty(integerIntegerMap);
        System.out.println(integerIntegerMap);
    }

    @Test
    public void cleanValueByNullAndEmpty() {
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();
        integerIntegerMap.put(null, 1);
        integerIntegerMap.put(2, null);
        integerIntegerMap.put(3, 4);
        MapUtil.removeIfValueEmpty(integerIntegerMap);
        System.out.println(integerIntegerMap);
    }

    @Test
    public void unmodifiableMap() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.unmodifiableMap(integerIntegerMap));
    }

    @Test
    public void getCapacity() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.getCapacity(integerIntegerMap));
    }

    @Test
    public void getSize() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.getSize(integerIntegerMap));
    }

    @Test
    public void getLoadFactor() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.getLoadFactor(integerIntegerMap));
    }

    @Test
    public void getThreshold() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.getThreshold(integerIntegerMap));
    }

    @Test
    public void mapToBean() throws InvocationTargetException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        Map<String, Object> stringObjectMap = ObjectUtil.toMap(Kuuga);
        System.out.println(MapUtil.toJavaBean(KuugaModel.class, stringObjectMap));
    }

    @Test
    public void print() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(122, 2, 3, 4, 5, 6);
        MapUtil.print(integerIntegerMap, true);
    }

    @Test
    public void testToString() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        System.out.println(MapUtil.toString(integerIntegerMap, true));
    }

    @Test
    public void optimize() {
        Map<Integer, Integer> integerIntegerMap = MapUtil.newHashMap(1, 2, 3, 4, 5, 6);
        MapUtil.optimize(integerIntegerMap).forEach((k, v) -> System.out.println(k + "---" + v));
    }

    @Test
    public void join() {
        Map<Integer, Integer> integerIntegerMap = new HashMap<>();
        integerIntegerMap.put(null, 1);
        integerIntegerMap.put(2, null);
        integerIntegerMap.put(3, 4);
        MapUtil.removeIfKeyEmpty(integerIntegerMap);
        MapUtil.removeIfValueEmpty(integerIntegerMap);
        System.out.println(MapUtil.join(integerIntegerMap, "&"));
    }

    @Test
    void join2() {
        Map<String, String> map = new HashMap<>(12) {{
            put("https://i.pximg.net/img-original/img/2017/08/31/11/02/23/64709947_p0.jpg", "1.jpg");
            put("https://i.pximg.net/img-original/img//05/22/12/23/47/57003517_p0.jpg", "2.jpg");
            put("https://i.pximg.net/img-original/img/2018/04/27/00/42/19/68420926_p0.jpg", "3.jpg");
            put("https://i.pximg.net/img-original/img/2017/01/02/10/51/11/60718057_p0.jpg", "4.jpg");
            put("https://i.pximg.net/img-/10/05/23/04/24/62288832_p0.jpg", "5.jpg");
            put("https://i.pximg.net/img-original/img/2018/04/30/00/00/02/68476297_p0.jpg", "6.jpg");
            put("https://i.pximg.net/img-/05/03/23/09/00/56688320_p0.jpg", "7.jpg");
            put("https://i.pximg.net/img-original//08/17/09/54/42/64463656_p0.jpg", "8.jpg");
            put("https://i.pximg.net/img-original/img//19/04/67553106_p0.jpg", "9.jpg");
            put("https://i.pximg.net/img-original/img/2016/12/25/13/39/24/60546670_p0.jpg", "10.jpg");
        }};

        // print(map, true);
        System.out.println(MapUtil.join(map, "&"));
    }

    @Test
    public void newMultimap() {
        Multimap<String, Integer> multimap = MapUtil.newMultimap();
        multimap.put(" Kuuga", 1);
        multimap.put("kuuga", 2);
        multimap.put("kuuga", 2);
        multimap.put("kuuga", 3);
        Map<String, Collection<Integer>> stringCollectionMap = multimap.asMap();
        System.out.println(stringCollectionMap);
    }

    @Test
    public void newHashBiMap() {
        HashBiMap<String, Integer> hashBiMap = MapUtil.newHashBiMap();
        hashBiMap.put("kuuga", 1);
        // forcePut会覆盖同值的key
        hashBiMap.forcePut("kuuga1", 1);
        Set<String> keySet = hashBiMap.keySet();
        System.out.println(keySet);
        System.out.println(StringUtil.repeatAndJoin("===", 10, StringUtil.EMPTY));
        System.out.println(hashBiMap.containsKey("kuuga1"));
        System.out.println(StringUtil.repeatAndJoin("===", 10, StringUtil.EMPTY));
        System.out.println(hashBiMap.containsValue(1));
        System.out.println(StringUtil.repeatAndJoin("===", 10, StringUtil.EMPTY));
        BiMap<Integer, String> inverse = hashBiMap.inverse();
        System.out.println(inverse);
        Map<Integer, String> kuuga0 = MapUtil.newHashMap(2, "kuuga0");
        inverse.putAll(kuuga0);
        System.out.println(inverse);
    }

    @Test
    public void newHashBiMap1() {
        HashBiMap<String, Integer> hashBiMap = MapUtil.newHashBiMap(2);
        hashBiMap.put("kuuga", 1);
        // forcePut会覆盖同值的key
        hashBiMap.put("kuuga1", 1);
        System.out.println(StringUtil.formatString(hashBiMap));
    }

    @Test
    public void newTable() {
        // 一批用户，同时按年龄和性别分组
        Table<Integer, String, String> table = MapUtil.newTable();
        table.put(18, "男", "yideng");
        table.put(18, "女", "Lily");
        System.out.println(table.get(18, "男")); // 输出 yideng
        System.out.println(table.get(18, "女")); // 输出 yideng
        // 这其实是一个二维的Map，可以查看行数据
        Map<String, String> row = table.row(18);
        System.out.println(row); // 输出 {"男":"yideng","女":"Lily"}
        // 查看列数据
        Map<Integer, String> column = table.column("男");
        System.out.println(column); // 输出 {18:"yideng"}
    }

    @Test
    void toList() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        Map<String, KuugaModel> map = MapUtil.newHashMap("1", kuuga1, "2", kuuga2);
        List<KuugaDTO> kuugaDTOS = MapUtil.toList(map, item -> {
            KuugaDTO kuugaDTO = new KuugaDTO();
            kuugaDTO.setName(item.getValue().getName() + "#Kuuga");
            kuugaDTO.setSex(3);
            return kuugaDTO;
        });
        System.out.println(StringUtil.formatString(kuugaDTOS));
    }

    @Test
    void newTreeMap() {
        TreeMap<Comparable<?>, @Nullable Object> comparableObjectTreeMap = MapUtil.newTreeMap();
        comparableObjectTreeMap.put(1, 2);
        comparableObjectTreeMap.put(2, null);
        comparableObjectTreeMap.put(3, 3);
        System.out.println(MapUtil.toString(comparableObjectTreeMap, true));
    }

    @Test
    void newConcurrentMap() {
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = MapUtil.newConcurrentMap();
        objectObjectConcurrentMap.put(1, 2);
        objectObjectConcurrentMap.put(2, null);
        objectObjectConcurrentMap.put(3, 3);
        System.out.println(MapUtil.toString(objectObjectConcurrentMap, true));
    }

    @Test
    void newConcurrentHashMap() {
        ConcurrentHashMap<Object, Object> objectObjectConcurrentMap = MapUtil.newConcurrentHashMap();
        objectObjectConcurrentMap.put(1, 2);
        objectObjectConcurrentMap.put(2, null);
        objectObjectConcurrentMap.put(3, 3);
        System.out.println(MapUtil.toString(objectObjectConcurrentMap, true));
    }

    @Test
    void newLinkedHashMap() {
        LinkedHashMap<@Nullable Object, @Nullable Object> objectObjectLinkedHashMap = MapUtil.newLinkedHashMap();
        objectObjectLinkedHashMap.put(1, 2);
        objectObjectLinkedHashMap.put(2, null);
        objectObjectLinkedHashMap.put(3, 3);
        System.out.println(MapUtil.toString(objectObjectLinkedHashMap, true));
    }

    @Test
    void difference() {
        Map<Integer, Integer> leftMap = MapUtil.newHashMap(1, 2, 3, 4);
        Map<Integer, Integer> rightMap = MapUtil.newHashMap(2, 3, 4, 5);
        Map<Integer, Integer> rightMap2 = MapUtil.newHashMap(1, 3, 3, 5);
        Map<Integer, Integer> rightMap3 = MapUtil.newHashMap(1, 2, 4, 5);
        Map<Integer, Integer> rightMap4 = MapUtil.newHashMap(0, 2, 5, 4);
        MapDifference<Integer, Integer> difference = MapUtil.difference(leftMap, rightMap);
        System.out.println(StringUtil.formatString(difference));
        MapDifference<Integer, Integer> difference2 = MapUtil.difference(leftMap, rightMap2);
        System.out.println(StringUtil.formatString(difference2));
        MapDifference<Integer, Integer> difference3 = MapUtil.difference(leftMap, rightMap3);
        System.out.println(StringUtil.formatString(difference3));
        MapDifference<Integer, Integer> difference4 = MapUtil.difference(leftMap, rightMap4);
        System.out.println(StringUtil.formatString(difference4));
    }

    @Test
    void inCommon() {
        Map<Integer, Integer> map1 = MapUtil.newHashMap(1, 2, 3, 4);
        Map<Integer, Integer> map2 = MapUtil.newHashMap(2, 3, 4, 5);
        Map<Integer, Integer> map3 = MapUtil.newHashMap(1, null, null, 5, 1, 5);
        Map<Integer, Integer> map4 = MapUtil.newHashMap(1, 2, 4, 5);
        Map<Integer, Integer> map5 = MapUtil.newHashMap(1, null, null, 5, 1, 5);

        System.out.println(MapUtil.inCommon(map1, map2));
        System.out.println(MapUtil.inCommon(map1, map3));
        System.out.println(MapUtil.inCommon(map1, map4));
        System.out.println(MapUtil.inCommon(map1, map5));

        System.out.println(MapUtil.inCommon(map3, map5));
    }

    @Test
    void filterEntries() {
        Map<Integer, Integer> unfiltered = MapUtil.newHashMap(1, 2, 3, 4);
        Map<Integer, Integer> integerIntegerMap = MapUtil.filterEntries(unfiltered, input -> input.getKey() == 3);
        System.out.println(MapUtil.toString(integerIntegerMap, true));

        BiMap<Integer, Integer> biMap = MapUtil.newHashBiMap();
        biMap.put(1, 2);
        biMap.put(3, 4);
        Map<Integer, Integer> biMapResult = MapUtil.filterEntries(biMap, input -> input.getKey() == 3);
        System.out.println(MapUtil.toString(biMapResult, true));
    }

    @Test
    void filterKeys() {
        Map<Integer, Integer> unfiltered = MapUtil.newHashMap(1, 2, 3, 4);
        Map<Integer, Integer> integerIntegerMap = MapUtil.filterKeys(unfiltered, key -> key == 1);
        System.out.println(MapUtil.toString(integerIntegerMap, true));

        BiMap<Integer, Integer> biMap = MapUtil.newHashBiMap();
        biMap.put(1, 2);
        biMap.put(3, 4);
        Map<Integer, Integer> biMapResult = MapUtil.filterKeys(biMap, key -> key == 3);
        System.out.println(MapUtil.toString(biMapResult, true));
    }

    @Test
    void filterValues() {
        Map<Integer, Integer> unfiltered = MapUtil.newHashMap(1, 2, 3, 4);
        Map<Integer, Integer> integerIntegerMap = MapUtil.filterValues(unfiltered, key -> key == 2);
        System.out.println(MapUtil.toString(integerIntegerMap, true));

        BiMap<Integer, Integer> biMap = MapUtil.newHashBiMap();
        biMap.put(1, 2);
        biMap.put(3, 4);
        Map<Integer, Integer> biMapResult = MapUtil.filterValues(biMap, key -> key == 4);
        System.out.println(MapUtil.toString(biMapResult, true));
    }

    @Test
    void transFormEntriesTest() {
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("a", "1");
        map1.put("b", "2");
        map1.put("c", "3");
        Map<String, String> result = MapUtil.transformEntries(map1, (k, v) -> k + v);
        System.out.println(result);
    }

    @Test
    void transformValuesTest() {
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("a", "1");
        map1.put("b", "2");
        map1.put("c", "3");
        Map<String, String> result = MapUtil.transformValues(map1, value -> value + 10);
        System.out.println(result);
    }

    @Test
    void reverse() {
        Map<Class<?>, Object> objectObjectMap = MapUtil.newHashMap();
        objectObjectMap.put(Integer.class, 1);
        objectObjectMap.put(Float.class, 2);
        objectObjectMap.put(Void.class, 3);
        objectObjectMap.put(Double.class, 4);
        objectObjectMap.put(String.class, 4);
        Map<Object, Class<?>> reverse = MapUtil.reverse(objectObjectMap);
        System.out.println(MapUtil.toString(reverse, true));
    }

    @Test
    void toMap() {
        System.out.println(EnumUtil.toMap(ByteType.class, ListUtil.newArrayList("EB")));
        System.out.println(EnumUtil.toMap(ByteType.class, null));
        System.out.println(EnumUtil.toMap(ByteType.B));
        System.out.println(EnumUtil.toMap(ByteType.B, ByteType.KB, ByteType.EB));
    }

    @Test
    void sort() {
        Map<String, String> map = MapUtil.newHashMap();
        map.put("bbb", "BBB");
        map.put("d", "D");
        map.put("cc", "CC");
        map.put("aaaa", "AAAA");
        TreeMap<String, String> sort = MapUtil.sortKey(map);
        System.out.println(MapUtil.toString(sort));
    }

    @Test
    void sortKey() {
        Map<String, String> map = MapUtil.newHashMap();
        map.put("bbb", "BBB");
        map.put("d", "D");
        map.put("cc", "CC");
        map.put("aaaa", "AAAA");
        // 根据key长度升序，长度短的排前面
        TreeMap<String, String> sort = MapUtil.sortKey(map, Comparator.comparingInt(String::length));
        System.out.println(MapUtil.toString(sort));
    }

    @Test
    void sortKey2() {
        Map<Integer, String> map = MapUtil.newHashMap();
        map.put(100, "BBB");
        map.put(10, "D");
        map.put(1, "CC");
        map.put(1000, "AAAA");
        // 根据key值升序，值小的排前面
        TreeMap<Integer, String> sort = MapUtil.sortKey(map, (Comparator.comparingInt(o -> o)));
        System.out.println(MapUtil.toString(sort));
    }

    @Test
    void sortValue() {
        Map<Integer, String> map = MapUtil.newHashMap();
        map.put(100, "BBB");
        map.put(10, "D");
        map.put(1, "CC");
        map.put(1000, "AAAA");
        // 根据value长度升序，长度短的排前面
        LinkedHashMap<Integer, String> sort = MapUtil.sortValue(map, Comparator.comparingInt(o -> o.getValue().length()));
        System.out.println(MapUtil.toString(sort));
    }

    @Test
    void sortValue2() {
        Map<String, Integer> map = MapUtil.newHashMap();
        map.put("BBB", 100);
        map.put("D", 10);
        map.put("CC", 1);
        map.put("AAAA", 1000);
        // 根据value值升序，值小的排前面
        LinkedHashMap<String, Integer> sort = MapUtil.sortValue(map, Comparator.comparingInt(Map.Entry::getValue));
        System.out.println(MapUtil.toString(sort));
    }

}