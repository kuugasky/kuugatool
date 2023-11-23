package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

class StringJoinerUtilTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void merge() {
        List<Integer> objects = ListUtil.newArrayList(1, 2);
        List<Integer> otherObjects = ListUtil.newArrayList(2, null, 3);
        System.out.println(StringJoinerUtil.merge(objects, otherObjects));
        System.out.println(StringJoinerUtil.merge("#", objects, otherObjects));
        System.out.println(StringJoinerUtil.merge("#", objects, otherObjects, true));
    }

    @Test
    void join() {
        List<Integer> objects = ListUtil.newArrayList(1, 2, null);
        System.out.println(StringJoinerUtil.join(objects));
        System.out.println(StringJoinerUtil.join(objects, true));
        System.out.println(StringJoinerUtil.join(",", objects));
        System.out.println(StringJoinerUtil.join(",", objects, true));
    }

    @Test
    void testJoin() {
        Integer[] objects = new Integer[]{1, 2, null};
        System.out.println(StringJoinerUtil.join(objects));
        System.out.println(StringJoinerUtil.join(objects, true));
        System.out.println(StringJoinerUtil.join(",", objects));
        System.out.println(StringJoinerUtil.join(",", objects, true));
    }

    @Test
    void testJoin12() {
        String[] objects = new String[]{"1", "2", StringUtil.EMPTY, null};
        System.out.println(StringJoinerUtil.join(objects));
        System.out.println(StringJoinerUtil.join(objects, true));
        System.out.println(StringJoinerUtil.join(",", objects));
        System.out.println(StringJoinerUtil.join(",", objects, true));
    }

    @Test
    void joinCollection() {
        Collection<Integer> objects = ListUtil.newArrayList(1, 2, null);
        System.out.println(StringJoinerUtil.join(objects));
        System.out.println(StringJoinerUtil.join(objects, true));
        System.out.println(StringJoinerUtil.join(",", objects));
        System.out.println(StringJoinerUtil.join(",", objects, true));
    }

    @Test
    void joinCollection2() {
        Collection<Integer> objects = SetUtil.newHashSet(1, 2, null);
        System.out.println(StringJoinerUtil.join(objects));
        System.out.println(StringJoinerUtil.join(objects, true));
        System.out.println(StringJoinerUtil.join(",", objects));
        System.out.println(StringJoinerUtil.join(",", objects, true));
    }

    @Test
    void testJoin1() {
        List<Integer> objects = ListUtil.newArrayList(1, 2, null);
        System.out.println(StringJoinerUtil.join("[", "]", objects));
        System.out.println(StringJoinerUtil.join("[", "]", objects, true));
        System.out.println(StringJoinerUtil.join(",", "[", "]", objects));
        System.out.println(StringJoinerUtil.join(",", "[", "]", objects, true));
        System.out.println(StringJoinerUtil.join(",", "[", "]", objects, true));
    }

    @Test
    void testJoin2() {
        Integer[] objects = new Integer[]{1, 2, null};
        System.out.println(StringJoinerUtil.join("[", "]", objects));
        System.out.println(StringJoinerUtil.join("[", "]", objects, true));
        System.out.println(StringJoinerUtil.join(",", "[", "]", objects));
        System.out.println(StringJoinerUtil.join(",", "[", "]", objects, true));
    }

    @Test
    void joinOnly() {
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3)));
    }

    @Test
    void testJoinOnly() {
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3, null), true));
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3, null), false));
    }

    @Test
    void testJoinOnly1() {
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3).toArray()));
    }

    @Test
    void testJoinOnly2() {
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3).toArray(), true));
        System.out.println(StringJoinerUtil.joinOnly(ListUtil.newArrayList(1, 2, 3).toArray(), false));
        System.out.println(StringJoinerUtil.joinOnly(new Integer[]{1, 2, 3, null}, false));
    }
}