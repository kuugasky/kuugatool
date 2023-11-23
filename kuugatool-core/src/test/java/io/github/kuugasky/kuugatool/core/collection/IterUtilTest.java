package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * {@link IterUtil} 单元测试
 *
 * @author looly
 */
public class IterUtilTest {

    private static Iterator<Integer> iterator;

    @BeforeAll
    static void beforeAll() {
        iterator = ListUtil.newArrayList(1, 2, 3, 4, 5, null).iterator();
    }

    @Test
    void isEmpty() {
        System.out.println(IterUtil.isEmpty(iterator));
    }

    @Test
    void testIsEmpty() {
        List<Object> list = ListUtil.newArrayList(1);
        System.out.println(IterUtil.isEmpty(list));
    }

    @Test
    void hasItem() {
        System.out.println(IterUtil.hasItem(iterator));
    }

    @Test
    void testHasItem() {
        List<Object> list = ListUtil.newArrayList(1);
        System.out.println(IterUtil.hasItem(list));
    }

    @Test
    void hasNull() {
        List<Object> list = ListUtil.newArrayList(1, null);
        System.out.println(IterUtil.hasNull(list));
    }

    @Test
    void testHasNull() {
        System.out.println(IterUtil.hasNull(iterator));
    }

    @Test
    void isAllNull() {
        System.out.println(IterUtil.isAllNull(iterator));
    }

    @Test
    void testIsAllNull() {
        List<Object> list = ListUtil.newArrayList(null, null);
        System.out.println(IterUtil.isAllNull(list));
    }

    @Test
    void countMap() {
        Map<Integer, Integer> x = IterUtil.countMap(iterator);
        System.out.println(MapUtil.toString(x, true));
    }

    @Test
    void countMapThenAsc() {
        iterator = ListUtil.newArrayList(1, 2, 2, 5, 2, 3, 4, 2, 3, 4, 5, null).iterator();
        Map<Integer, Integer> x = IterUtil.countMapThenAsc(iterator);
        System.out.println(MapUtil.toString(x, true));
    }

    @Test
    void countMapThenDesc() {
        iterator = ListUtil.newArrayList(1, 2, 2, 5, 2, 3, 4, 2, 3, 4, 5, null).iterator();
        Map<Integer, Integer> x = IterUtil.countMapThenDesc(iterator);
        System.out.println(MapUtil.toString(x, true));
    }

    @Test
    void toMap() {
        Map<Integer, Integer> x = IterUtil.countMap(iterator);
        Set<Map.Entry<Integer, Integer>> entries = x.entrySet();
        HashMap<Integer, Integer> integerIntegerHashMap = IterUtil.toMap(entries);
        System.out.println(MapUtil.toString(MapUtil.filterKeys(integerIntegerHashMap, Objects::nonNull), true));
    }

    @Test
    void toListMap() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, List<KuugaModel>> stringListMap = IterUtil.toListMap(list, KuugaModel::getName);
        System.out.println(MapUtil.toString(stringListMap, true));
    }

    @Test
    void testToListMap() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, List<Integer>> stringListMap = IterUtil.toListMap(list, KuugaModel::getName, KuugaModel::getSex);
        System.out.println(MapUtil.toString(stringListMap, true));
    }

    @Test
    void testToListMap1() {
        Map<String, List<Integer>> result = MapUtil.newHashMap();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, List<Integer>> stringListMap = IterUtil.toListMap(result, list, KuugaModel::getName, KuugaModel::getSex);
        System.out.println(MapUtil.toString(stringListMap, true));
    }

    @Test
    void testToMap() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, KuugaModel> stringkuugaModelMap = IterUtil.toMap(list, KuugaModel::getName);
        System.out.println(MapUtil.toString(stringkuugaModelMap, true));
    }

    @Test
    void testToMap1() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, Integer> stringkuugaModelMap = IterUtil.toMap(list, KuugaModel::getName, KuugaModel::getSex);
        System.out.println(MapUtil.toString(stringkuugaModelMap, true));
    }

    @Test
    void testToMap2() {
        Map<String, List<Integer>> result = MapUtil.newHashMap();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(new KuugaModel("kuuga1", 1));
        list.add(new KuugaModel("kuuga2", 2));
        Map<String, List<Integer>> stringListMap = IterUtil.toListMap(result, list, KuugaModel::getName, KuugaModel::getSex);
        System.out.println(MapUtil.toString(stringListMap, true));
    }

    @Test
    void toList() {
        System.out.println(IterUtil.toList(iterator));
    }

    @Test
    void testToList() {
        System.out.println(IterUtil.toList(ListUtil.newArrayList(1, 2, 3)));
    }

    @Test
    void asIterable() {
        Iterable<Integer> x = IterUtil.asIterable(iterator);
        System.out.println(x);
    }

    @Test
    void getLast() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer first = IterUtil.getLast(list.listIterator());
        System.out.println(first);
    }

    @Test
    void testGetLast() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer first = IterUtil.getLast(list);
        System.out.println(first);
    }

    @Test
    void getFirst() {
        Integer first = IterUtil.getFirst(iterator);
        System.out.println(first);
    }

    @Test
    void testGetFirst() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3);
        Integer first = IterUtil.getFirst(integers);
        System.out.println(first);
    }

    @Test
    void empty() {
        Iterator<Object> empty = IterUtil.empty();
        System.out.println(empty);
    }

    @Test
    void size() {
        int size = IterUtil.size(iterator);
        System.out.println(size);
    }

    @Test
    void testSize() {
        int size = IterUtil.size(ListUtil.newArrayList(1, 2, 3));
        System.out.println(size);
    }

    @Test
    void toListIterator() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        Iterator<Integer> integerIterator = IterUtil.toIterator(list);
        System.out.println(StringUtil.formatString(integerIterator));
    }

    @Test
    void toSetIterator() {
        Iterator<Integer> integerIterator = IterUtil.toIterator(SetUtil.newHashSet(1, 2, 3, 4, 5));
        System.out.println(StringUtil.formatString(integerIterator));
    }

}
