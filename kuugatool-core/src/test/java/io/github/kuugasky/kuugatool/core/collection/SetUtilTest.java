package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMultiset;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class SetUtilTest {

    @Test
    void newConcurrentHashSet() {
        Set<Object> objects = SetUtil.newConcurrentHashSet();
        System.out.println(objects);
    }

    @Test
    void newConcurrentHashSet1() {
        Set<Object> objects = SetUtil.newConcurrentHashSet(ListUtil.newArrayList());
        System.out.println(objects);
    }

    @Test
    void newLinkedHashSet() {
        Set<Object> objects = SetUtil.newLinkedHashSet();
        System.out.println(objects);
    }

    @Test
    void newLinkedHashSet1() {
        Set<Object> objects = SetUtil.newLinkedHashSet(ListUtil.newArrayList());
        System.out.println(objects);
    }

    static class Demo implements Comparable<String> {

        @Override
        public int compareTo(@Nullable String o) {
            return 0;
        }
    }

    static class DemoItem extends Demo {

    }

    @Test
    void newTreeSet() {
        TreeSet<Demo> objects = SetUtil.newTreeSet();
        objects.add(new Demo());
        System.out.println(objects);
    }

    @Test
    void newTreeSet1() {
        TreeSet<DemoItem> objects = SetUtil.newTreeSet(ListUtil.newArrayList());
        System.out.println(objects);
    }

    @Test
    public void NEW() {
        System.out.println(SetUtil.newHashSet());
    }

    @Test
    public void testNEW() {
        System.out.println(SetUtil.newHashSet("kuuga"));
    }

    @Test
    public void isEmpty() {
        System.out.println(SetUtil.isEmpty(ListUtil.newArrayList()));
    }

    @Test
    public void hasItem() {
        System.out.println(SetUtil.hasItem(ListUtil.newArrayList()));
    }

    @Test
    public void optimize() {
        SetUtil.optimize(SetUtil.newHashSet()).forEach(System.out::println);
    }

    @Test
    public void emptySet() {
        System.out.println(SetUtil.emptySet());
    }

    @Test
    public void unmodifiableSet() {
        System.out.println(SetUtil.unmodifiableSet(SetUtil.newHashSet()));
    }

    @Test
    public void findFirst() {
        Set<Integer> set = SetUtil.newHashSet(1, 2, 3);
        Integer first = SetUtil.findFirst(set);
        System.out.println(first);
    }

    @Test
    public void findLast() {
        Set<Integer> set = SetUtil.newHashSet(1, 2, 3);
        Integer first = SetUtil.findLast(set);
        System.out.println(first);
    }

    @Test
    public void newMultiset() {
        Multiset<String> multiset = SetUtil.newMultiset();
        multiset.add("apple");
        multiset.add("apple");
        multiset.add("orange");
        multiset.add("xxx");
        System.out.println(multiset.size()); // 输出 4
        System.out.println(multiset.count("apple")); // 输出 2
        // 元素去重
        Set<String> set = multiset.elementSet();
        System.out.println(set); // 输出 ["orange","apple"]
        // 输出未去重前的所有元素
        for (String s : multiset) {
            System.out.println(s);
        }
        // 还能手动设置某个元素出现的次数
        multiset.setCount("apple", 5);
        System.out.println("--------------------");
        System.out.println(multiset);
        System.out.println("--------------------");
        multiset.setCount("orange", 2);
        for (String s : multiset) {
            System.out.println(s);
        }
    }

    @Test
    void newMultiset1() {
        Multiset<String> multiset = SetUtil.newMultiset(ListUtil.newArrayList());
        multiset.setCount("feng", 3);
        multiset.setCount("allen", 4);
        multiset.setCount("kuuga", 5);
        System.out.println("不去重元素个数：" + multiset.size());
        System.out.println("去重后元素个数：" + multiset.elementSet().size());

        System.out.println("feng size:" + multiset.count("feng"));
        System.out.println("allen size:" + multiset.count("allen"));
        System.out.println("Kuuga size:" + multiset.count("kuuga"));

        Map<String, List<String>> collect = multiset.stream().collect(Collectors.groupingBy(String::new));
        collect.forEach((k, v) -> System.out.printf("%s --> %s%n", k, v.size()));
    }

    @Test
    void union() {
        Set<Integer> s1 = SetUtil.newHashSet(1, 2, 3);
        Set<Integer> s2 = SetUtil.newHashSet(1, 3, 5);
        Sets.SetView<Integer> union = SetUtil.union(s1, s2);
        union.forEach(s -> System.out.println(StringUtil.formatString(s)));
    }

    @Test
    void intersection() {
        Set<Integer> int1 = SetUtil.newHashSet(1, 2, 3);
        Set<Integer> int2 = SetUtil.newHashSet(3, 4, 5);
        Set<Integer> intersection = SetUtil.intersection(int1, int2);
        System.out.println(StringJoinerUtil.join(intersection));
    }

    @Test
    void difference() {
        Set<String> set1 = SetUtil.newHashSet("a", "b", "d");
        Set<String> set2 = SetUtil.newHashSet("d", "e", "f");
        // difference返回：从set1中剔除两个set公共的元素
        System.out.println(SetUtil.difference(set1, set2));
        // symmetricDifference返回：剔除两个set公共的元素，再取两个集合的并集
        System.out.println(SetUtil.symmetricDifference(set1, set2));
        System.out.println(SetUtil.union(set1, set2));
    }

    /**
     * 返回大小为{@code size}的{@code set}的所有子集的集合。例如，{@code
     * <p>
     * combinations(ImmutableSet.of(1, 2, 3), 2)}返回集合{@code {{1, 2}, {1, 3}, {2, 3}}}。
     */
    @Test
    void combinations() {
        Set<Integer> int1 = SetUtil.newHashSet(1, 1, 3, 4, 5, 6);
        Set<Set<Integer>> combinations = SetUtil.combinations(int1, 3);
        for (Set<Integer> combination : combinations) {
            System.out.println(StringJoinerUtil.join(combination));
        }
        System.out.println(combinations.size());
    }

    @Test
    void cartesianProduct() {
        Set<String> set1 = Sets.newHashSet("a", "b", "c");
        Set<String> set2 = Sets.newHashSet("d", "e", "f");
        Set<String> set3 = Sets.newHashSet("1", "2", "3", "4");
        // 获取多个list的笛卡尔集
        Set<List<String>> list = SetUtil.cartesianProduct(set1);
        System.out.println(list);

        for (List<String> strings : list) {
            for (String s : strings) {
                System.out.println(s);
            }
        }

        Set<List<String>> listx = SetUtil.cartesianProduct(set1, set2, set3);
        System.out.println(listx);
        System.out.println(listx.size());
        set2 = Sets.newHashSet("d", "d", "f");
        Set<List<String>> listx2 = SetUtil.cartesianProduct(set1, set2, set3);
        System.out.println(listx2.size());

        List<Set<String>> list3 = Lists.newArrayList(set1, set2, set3);
        // 也可以把多个Set集合，放到一个list中，再计算笛卡尔集
        Set<List<String>> sets1 = SetUtil.cartesianProduct(list3);
        System.out.println(sets1);
    }

    @Test
    void powerSetTest() {
        Set<String> set1 = Sets.newHashSet("a", "b", "c");
        // 获取set可分隔成的所有子集
        Set<Set<String>> allSet = SetUtil.powerSet(set1);
        for (Set<String> set : allSet) {
            System.out.println(set);
        }
    }

    @Test
    void filterTest() {
        Set<String> set1 = Sets.newHashSet("a", "b", "c");
        // 建议可以直接使用java8的过滤，比较方便
        Set<String> set2 = SetUtil.filter(set1, str -> str.equalsIgnoreCase("b"));
        System.out.println(set2);
    }

    @Test
    void synchronizedSet() {
        Set<Integer> objects = SetUtil.newHashSet(1, 2);
        objects.add(3);
        System.out.println(StringJoinerUtil.join(objects));
        Set<Integer> integers = SetUtil.synchronizedSet(objects);
        objects.add(4);
        System.out.println(StringJoinerUtil.join(objects));
        integers.add(5);
        System.out.println(StringJoinerUtil.join(integers));
    }

    @Test
    void toMap() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel kuugaModel4 = KuugaModel.builder().name("kuuga3").sex(2).build();
        Set<KuugaModel> set = SetUtil.newHashSet(kuugaModel1, kuugaModel2, kuugaModel3, kuugaModel4);
        Map<KuugaModel, String> kuugaModelStringMap = SetUtil.asMap(set, KuugaModel::getName);
        System.out.println(MapUtil.toString(kuugaModelStringMap, true));
    }

    @Test
    void newSortedMultiset() {
        SortedMultiset<Comparable<?>> comparables = SetUtil.newSortedMultiset();
        comparables.setCount("kuuga", 2);
        comparables.setCount("allen", 1);
        System.out.println(Objects.requireNonNull(comparables.lastEntry()).getElement());
        System.out.println(Objects.requireNonNull(comparables.lastEntry()).getElement());
        System.out.println(comparables);
    }

}