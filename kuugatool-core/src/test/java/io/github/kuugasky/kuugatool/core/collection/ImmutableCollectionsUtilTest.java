package io.github.kuugasky.kuugatool.core.collection;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

class ImmutableCollectionsUtilTest {

    @Test
    void ofList() {
        List<Object> objects = ImmutableCollectionsUtil.ofList();
        objects.add(1);
    }

    @Test
    void testOfList() {
        List<Object> objects = ImmutableCollectionsUtil.ofList(1);
        objects.add(2);
    }

    @Test
    void copyOfList() {
        List<Object> objects = ImmutableCollectionsUtil.copyOfList(ListUtil.newArrayList(1));
        objects.add(2);
    }

    @Test
    void ofSet() {
        Set<Object> objects = ImmutableCollectionsUtil.ofSet();
        objects.add(1);
    }

    @Test
    void testOfSet() {
        Set<Object> objects = ImmutableCollectionsUtil.ofSet(1);
        objects.add(2);
    }

    @Test
    void copyOfSet() {
        Set<Object> objects = ImmutableCollectionsUtil.copyOfSet(SetUtil.newHashSet(1));
        objects.add(2);
    }

    @Test
    void ofMap() {
        Map<Object, Object> objects = ImmutableCollectionsUtil.ofMap();
        objects.put(1, 2);
    }

    @Test
    void testOfMap() {
        Map<Object, Object> objects = ImmutableCollectionsUtil.ofMap(1, 2);
        objects.put(1, 2);
    }

    @Test
    void testOfMap1() {
        Map<Object, Object> objects = ImmutableCollectionsUtil.ofMap(1, 2, 3, 4);
        objects.put(1, 2);
    }

    @Test
    void testOfMap2() {
        System.out.println(ImmutableCollectionsUtil.ofMap());
    }

    @Test
    void testOfMap3() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2));
    }

    @Test
    void testOfMap4() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4));
    }

    @Test
    void testOfMap5() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6));
    }

    @Test
    void testOfMap6() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8));
    }

    @Test
    void testOfMap7() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    void testOfMap8() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    }

    @Test
    void testOfMap9() {
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16));
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18));
        System.out.println(ImmutableCollectionsUtil.ofMap(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    }

    @Test
    void copyOfMap() {
        Map<Object, Object> objects = ImmutableCollectionsUtil.copyOfMap(MapUtil.newHashMap(1, 2));
        objects.put(1, 2);
    }
}