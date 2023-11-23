package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ImmutableUtilTest {

    @Test
    public void copyOf() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3);
        ImmutableList<Integer> integers = ImmutableUtil.copyOf(list);
        // integers.add(0, 0);
        ImmutableList<Integer> reverse = integers.reverse();
        System.out.println(integers.size());

        System.out.println(integers);
        System.out.println(reverse);

        System.out.println(integers.spliterator().estimateSize());
        Spliterator<Integer> integerSpliterator = integers.spliterator().trySplit();
        Comparator<? super Integer> comparator = integerSpliterator.getComparator();
        int compare = comparator.compare(1, 2);
        System.out.println(integerSpliterator);
        System.out.println(compare);
    }

    @Test
    public void testCopyOf() {
        Set<Object> set = SetUtil.newHashSet();
        set.add(1);
        System.out.println(ImmutableUtil.copyOf(set));
    }

    @Test
    public void testCopyOf1() {
        Map<Object, Object> map = MapUtil.newHashMap();
        map.put(1, 2);
        System.out.println(ImmutableUtil.copyOf(map));
    }

}