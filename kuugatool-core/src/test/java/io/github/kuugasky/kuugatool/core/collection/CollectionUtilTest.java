package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionUtilTest {

    @Test
    public void forEach() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        CollectionUtil.forEach(list, (index, e) -> System.out.println(index + "--->" + e));
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        CollectionUtil.forEach(set, (index, e) -> System.out.println(index + "--->" + e));
    }

    @Test
    public void isEmpty() {
        List<Object> lists = ListUtil.newArrayList();
        System.out.println(CollectionUtil.isEmpty(lists));
        Set<Object> sets = SetUtil.newHashSet();
        System.out.println(CollectionUtil.isEmpty(sets));
    }

    @Test
    public void hasItem() {
        List<Object> lists = ListUtil.newArrayList("test");
        System.out.println(CollectionUtil.hasItem(lists));
        Set<Object> sets = SetUtil.newHashSet("test");
        System.out.println(CollectionUtil.hasItem(sets));
    }

    @Test
    public void clear() {
        List<Object> lists = ListUtil.newArrayList("test");
        CollectionUtil.clear(lists);
    }

    @Test
    public void reverse() {
        List<Object> lists = ListUtil.newArrayList(1, 2, 3, 4, 5);
        List<Object> reverse = CollectionUtil.reverse(lists);
        System.out.println(lists);
        System.out.println(reverse);
    }

    @Test
    public void reverseNew() {
        List<Object> lists = ListUtil.newArrayList(1, 2, 3, 4, 5);
        List<Object> result = CollectionUtil.reverseNew(lists);
        System.out.println(StringJoinerUtil.join(lists));
        System.out.println(StringJoinerUtil.join(result));
    }

    @Test
    public void keySet() {
        List<Map<String, ?>> lists = new ArrayList<>();
        lists.add(MapUtil.newHashMap("1", "kuuga1"));
        lists.add(MapUtil.newHashMap("2", "kuuga2"));
        lists.add(MapUtil.newHashMap("1", "kuuga3"));
        Set<String> keys = CollectionUtil.keySet(lists);
        System.out.println(StringJoinerUtil.join(keys));
    }

    @Test
    public void values() {
        List<Map<?, String>> lists = new ArrayList<>();
        lists.add(MapUtil.newHashMap("1", "kuuga1"));
        lists.add(MapUtil.newHashMap("2", "kuuga2"));
        lists.add(MapUtil.newHashMap("3", "kuuga3"));
        List<String> values = CollectionUtil.values(lists);
        System.out.println(StringJoinerUtil.join(values));

    }

    @Test
    public void max() {
        System.out.println(CollectionUtil.max(ListUtil.newArrayList("1kuuga1", "kuuga2", "kuuga3")));
        System.out.println(CollectionUtil.max(ListUtil.newArrayList("1", "2", "3")));
        System.out.println(CollectionUtil.max(ListUtil.newArrayList(1, 2, 3)));
    }

    @Test
    public void min() {
        System.out.println(CollectionUtil.min(ListUtil.newArrayList("1kuuga1", "kuuga0", "kuuga3")));
        System.out.println(CollectionUtil.min(ListUtil.newArrayList("1", "2", "3")));
        System.out.println(CollectionUtil.min(ListUtil.newArrayList(1, 2, 0, 3)));
    }

    @Test
    public void unmodifiable() {
        Collection<String> Kuuga = CollectionUtil.unmodifiable(ListUtil.newArrayList("kuuga"));
        Kuuga.add("test");
        System.out.println(Kuuga.size());
    }

    @Test
    public void empty() {
        System.out.println(CollectionUtil.toString(ListUtil.newArrayList(1, 2, 3), ","));

        Collection<Object> empty = CollectionUtil.empty(List.class);
        System.out.println(empty.toString());
        System.out.println(CollectionUtil.empty(Set.class).toString());
        System.out.println(CollectionUtil.empty(SortedSet.class).toString());
        System.out.println(CollectionUtil.empty(NavigableSet.class).toString());
        System.out.println(CollectionUtil.empty(Iterable.class).toString());
    }

    @Test
    void testContains() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3, 4, 5);
        System.out.println(CollectionUtil.containsAll(integers, 1, 3, 5));
    }

    @Test
    void contains() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3, 4, 5);
        Set<Integer> integers1 = SetUtil.newHashSet(4, 2);
        System.out.println(CollectionUtil.contains(integers, integers1));
        List<Integer> integers2 = ListUtil.newArrayList(1, 6, 7, 8);
        System.out.println(CollectionUtil.contains(integers, integers2));
    }

    @Test
    void containsAll() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3, 4, 5);
        Set<Integer> integers1 = SetUtil.newHashSet(1, 10);
        System.out.println(CollectionUtil.containsAll(integers, integers1));

        System.out.println(CollectionUtil.containsAll(integers, 10, 3, 5));
    }

}