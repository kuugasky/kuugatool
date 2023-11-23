package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.collection.bean.FairCutData;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.stream.Student;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtilTest {

    private KuugaModel kuugaModel;

    @BeforeEach
    void setUp() {
        kuugaModel = KuugaModel.builder().name("kuuga").sex(1).build();
    }

    @Test
    void newLinked() {
        List<KuugaModel> list = ListUtil.newLinkedList();
        System.out.println(list.size());
    }

    @Test
    void newVector() {
        List<KuugaModel> list = ListUtil.newVector();
        System.out.println(list.size());
    }


    @Test
    void newCopyOnWriteArrayList() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        List<KuugaModel> list = ListUtil.newCopyOnWriteArrayList(objects);
        System.out.println(list.size());
    }

    @Test
    void newArrayList() {
        List<KuugaModel> list = ListUtil.newArrayList(10);
        System.out.println(list.size());
    }

    @Test
    void newList1() {
        List<KuugaModel> list = ListUtil.newArrayList();
        System.out.println(list.size());
    }

    @Test
    void testNEW() {
        List<KuugaModel> list = ListUtil.newArrayList(new KuugaModel(), new KuugaModel());
        System.out.println(list.size());
    }

    @Test
    void isEmpty() {
        System.out.println(ListUtil.isEmpty(ListUtil.newArrayList()));
    }

    @Test
    void hasItem() {
        List<Object> list = ListUtil.newArrayList();
        System.out.println(ListUtil.hasItem(list));
    }

    @Test
    void size() {
        System.out.println(ListUtil.size(ListUtil.newArrayList()));
    }

    @Test
    void toArray() {
        KuugaModel[] kuugaModels = ListUtil.toArray(ListUtil.newArrayList(kuugaModel), new KuugaModel[]{});
        System.out.println(kuugaModels.length);
    }

    @Test
    void pluck() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList(kuugaModel1, kuugaModel2);
        List<String> nameList = ListUtil.pluck(list, "name");
        System.out.println(nameList);
    }

    @Test
    void remove() {
        List<String> list = ListUtil.newArrayList("kuuga", "kuugatool");
        Collection<String> Kuuga = ListUtil.remove(list, "kuuga");
        System.out.println(Kuuga);
    }

    @Test
    void removeOfIterator() {
        List<String> list = ListUtil.newArrayList("kuuga", "kuugatool");
        Collection<String> Kuuga = ListUtil.removeByIterator(list, "kuuga");
        System.out.println(Kuuga);
    }

    @Test
    void uniqueSum() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        List<Integer> list2 = ListUtil.newArrayList(2, 4, 6, 8, 10);
        List<Integer> list3 = ListUtil.newArrayList(11);
        Collection<Integer> objects = ListUtil.uniqueUnion(list, list2);
        System.out.println(objects);
        Collection<Integer> objects1 = ListUtil.uniqueUnion(list, list2, list3);
        System.out.println(objects1);

        Collection<Integer> subtract = CollectionUtils.subtract(list, list2);
        System.out.println(subtract);

        list.removeAll(list2);
        System.out.println(list);
    }

    @Test
    void union() {
        List<Long> list = ListUtil.newArrayList(1L, 2L, 3L, 4L, 5L);
        List<Long> list2 = ListUtil.newArrayList(2L, 4L, 6L, 8L, 10L);
        List<Long> list3 = ListUtil.newArrayList(11L);
        Collection<Long> objects = ListUtil.union(list, list2);
        System.out.println(objects);
        List<Long> union = ListUtil.union(list, list2, list3);
        System.out.println(union);
    }

    @Test
    void intersection() {
        List<Float> list = ListUtil.newArrayList(1F, 2F, 3F, 4F, 5F);
        List<Float> list2 = ListUtil.newArrayList(2F, 4F, 6F, 8F, 10F);
        Collection<Float> objects = ListUtil.intersection(list, list2);
        System.out.println(objects);
    }

    @Test
    void subtract1() {
        List<Double> list = ListUtil.newArrayList(1D, 2D, 3D, 4D, 5D);
        List<Double> list2 = ListUtil.newArrayList(2D, 4D, 6D);
        Collection<Double> subtract = ListUtil.subtract(list, list2);
        System.out.println(subtract);
    }

    @Test
    void summary() {
        List<Double> list = ListUtil.newArrayList(1D, 2D, 3D, 4D, 5D);
        List<Double> list2 = ListUtil.newArrayList(2D, 4D, 6D, 8D, 10D);
        List<List<Double>> result = ListUtil.newArrayList();
        result.add(list);
        result.add(list2);
        Collection<?> objects = ListUtil.summary(result);
        System.out.println(objects);
    }

    @Test
    void unmodifiableCollection() {
        Collection<Object> objects = ListUtil.unmodifiableCollection(ListUtil.newArrayList());
        System.out.println(objects.size());
    }

    @Test
    void unmodifiableList() {
        List<Object> objects = ListUtil.unmodifiableList(ListUtil.newArrayList());
        System.out.println(objects.size());
    }

    @Test
    void synchronizedList() {
        List<Object> objects = ListUtil.synchronizedList(ListUtil.newArrayList());
        System.out.println(objects.size());
    }

    @Test
    void splitAvg() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel kuugaModel4 = KuugaModel.builder().name("kuuga4").sex(1).build();
        KuugaModel kuugaModel5 = KuugaModel.builder().name("kuuga5").sex(1).build();
        KuugaModel kuugaModel6 = KuugaModel.builder().name("kuuga6").sex(1).build();
        KuugaModel kuugaModel7 = KuugaModel.builder().name("kuuga7").sex(1).build();
        KuugaModel kuugaModel8 = KuugaModel.builder().name("kuuga8").sex(1).build();
        KuugaModel kuugaModel9 = KuugaModel.builder().name("kuuga9").sex(1).build();
        KuugaModel kuugaModel10 = KuugaModel.builder().name("kuuga10").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(kuugaModel3);
        list.add(kuugaModel4);
        list.add(kuugaModel5);
        list.add(kuugaModel6);
        list.add(kuugaModel7);
        list.add(kuugaModel8);
        list.add(kuugaModel9);
        list.add(kuugaModel10);
        List<List<KuugaModel>> objects = ListUtil.splitAvg(list, 3);
        objects.forEach(object -> System.out.println(object.size()));
    }

    @Test
    void splitAvgRatio() {
        List<Integer> list = ListUtil.newArrayList();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        List<List<Integer>> objects = ListUtil.splitAvgRatio(list, 5);
        objects.forEach(object -> System.out.println(object.size()));
    }

    @Test
    void splitList() {
        List<Integer> list = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        List<List<Integer>> objects = ListUtil.splitList(list, 3);
        System.out.println(StringJoinerUtil.join(objects));

        List<List<List<Integer>>> partition = Lists.partition(objects, 3);
        System.out.println(StringJoinerUtil.join(partition));
    }

    @Test
    void cartesianProduct() {
        List<String> list1 = Lists.newArrayList("a", "b", "c");
        List<String> list2 = Lists.newArrayList("d", "e", "f");
        List<String> list3 = Lists.newArrayList("1", "2", "3", "4");
        // 获取多个list的笛卡尔集
        List<List<String>> list = ListUtil.cartesianProduct(list1);
        System.out.println(list);

        for (List<String> strings : list) {
            for (String s : strings) {
                System.out.println(s);
            }
        }

        List<List<String>> listx = ListUtil.cartesianProduct(list1, list2, list3);
        System.out.println(listx);
    }

    @Test
    void emptyList() {
        System.out.println(ListUtil.emptyList());
    }

    @Test
    void optimize() {
        ListUtil.optimize(ListUtil.newArrayList()).forEach(System.out::println);
    }

    @Test
    void testOptimize() {
        ListUtil.optimize(ListUtil.newArrayList(), true).forEach(System.out::println);
    }

    @Test
    void testOptimize1() {
        ListUtil.optimize(new Object[]{}).forEach(System.out::println);
    }

    @Test
    void findFirst() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(kuugaModel3);
        KuugaModel first = ListUtil.findFirst(list);
        System.out.println(first);
    }

    @Test
    void findLast() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(kuugaModel3);
        KuugaModel first = ListUtil.findLast(list);
        System.out.println(first);
        KuugaModel firstOrDefaultValue = ListUtil.findLast(list, null);
        System.out.println(firstOrDefaultValue);
    }

    @Test
    void indexOf() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(kuugaModel3);
        int index = ListUtil.indexOf(list, kuugaModel2);
        System.out.println(index);
    }

    @Test
    void createRangeInteger() {
        System.out.println(ListUtil.createRangeInteger(1, 10));
    }

    @Test
    void createRangeLong() {
        System.out.println(ListUtil.createRangeLong(1, 10));
    }

    @Test
    void createRangeString() {
        System.out.println(ListUtil.createRangeString(1, 10));
    }

    @Test
    void createRandomDoubleValues() {
        System.out.println(ListUtil.getRandomDoubleValue(1));
    }

    @Test
    void createRandomIntegerValues() {
        System.out.println(ListUtil.getRandomIntegerValues(1, 10, 5));
    }

    @Test
    void nullFilter() {
        List<Object> list = ListUtil.newArrayList("1", null, true);
        System.out.println(ListUtil.nullFilter(list));
    }

    @Test
    void blankFilter() {
        List<Object> list = ListUtil.newArrayList("1", StringUtil.EMPTY, null, true);
        System.out.println(ListUtil.blankFilter(list));
    }

    @Test
    void get() {
        List<String> list = ListUtil.newArrayList("a", "b", "c");
        System.out.println(ListUtil.get(list, 2));
    }

    @Test
    void testEquals() {
        List<String> listA = ListUtil.newArrayList("1", "2", "10");
        List<String> listB = ListUtil.newArrayList("2", "10", "1");

        // kuugaDTO kuuga1 = kuugaDTO.builder().name("kuuga1").sex(1).build();
        // kuugaDTO kuuga2 = kuugaDTO.builder().name("kuuga3").sex(2).build();
        //
        // kuugaDTO kuuga4 = kuugaDTO.builder().name("kuuga3").sex(2).build();
        // kuugaDTO kuuga3 = kuugaDTO.builder().name("kuuga1").sex(1).build();
        // List<kuugaDTO> listC = ListUtil.newArrayList();
        // listC.add(kuuga1);
        // listC.add(kuuga2);
        // List<kuugaDTO> listD = ListUtil.newArrayList();
        // listD.add(kuuga4);
        // listD.add(kuuga3);

        System.out.println(ListUtil.equals(listA, listB));
        // System.out.println(equals(listC, listD));
    }

    @Test
    void testEqualsNo() {
        List<String> listA = ListUtil.newArrayList("1", "2", "10");
        List<String> listB = ListUtil.newArrayList("2", "10", "2");
        System.out.println(ListUtil.equalsNo(listA, listB));
    }

    @Test
    void startEndIndexOfAverageSplit() {
        TreeMap<Integer, Integer> integerIntegerTreeMap = ListUtil.startEndIndexOfAverageSplit(10, 3);
        System.out.println(StringUtil.formatString(integerIntegerTreeMap));
    }

    @Test
    void distinct() {
        List<String> listA = ListUtil.newArrayList("1", "2", "3", "1", "3");
        List<String> distinctA = ListUtil.distinct(listA);
        System.out.println(Collections.unmodifiableList(distinctA));

        List<Integer> listB = ListUtil.newArrayList(1, 2, 3, 1, 3);
        List<Integer> distinctB = ListUtil.distinct(listB);
        System.out.println(Collections.unmodifiableList(distinctB));
    }

    @Test
    void toMap() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        objects.add(new KuugaModel("1", 1));
        objects.add(new KuugaModel("2", 2));
        objects.add(new KuugaModel("1", 3));
        Map<String, List<KuugaModel>> stringListMap = ListUtil.toMap(objects, KuugaModel::getName);
        System.out.println(MapUtil.toString(stringListMap, true));

        System.out.println(StringUtil.repeatNormal());

        // (k1,k2)->k2 避免键重复 k1-取第一个数据；k2-取最后一条数据
        // key和value,都可以根据传入的值返回不同的Map
        Map<String, Integer> deviceMap = objects.stream()
                .collect(Collectors.toMap(KuugaModel::getName, KuugaModel::getSex, (k1, k2) -> k1));
        System.out.println(MapUtil.toString(deviceMap, true));

        System.out.println(StringUtil.repeatNormal());

        Map<String, Object> map = objects.stream()
                .collect(Collectors.toMap(i -> i.getName() + i.getSex(), j -> j, (k1, k2) -> k1));
        System.out.println(MapUtil.toString(map, true));
    }

    @Test
    void testToMap() {
        Student s1 = new Student("aa", 10, 1);
        Student s2 = new Student("bb", 20, 2);
        Student s3 = new Student("cc", 20, 1);
        Student s4 = new Student("cc", 20, 2);
        List<Student> list = Arrays.asList(s1, s2, s3, s4);
        Map<Integer, Map<Integer, List<Student>>> integerMapMap = ListUtil.toMap(list, Student::getType, Student::getAge);
        System.out.println(StringUtil.formatString(integerMapMap));

        System.out.println(StringUtil.repeatNormal());
        Predicate<Student> studentPredicate = student -> student.getAge() == 10;
        Map<Boolean, List<Student>> integerMapMap2 = ListUtil.partitioningBy(list, studentPredicate);
        System.out.println(StringUtil.formatString(integerMapMap2));
        System.out.println(StringUtil.repeatNormal());
        Function<? super Student, Boolean> booleanFunction = student -> student.getAge() == 10;
        Map<Boolean, List<Student>> integerMapMap3 = ListUtil.toMap(list, booleanFunction);
        System.out.println(StringUtil.formatString(integerMapMap3));
    }

    private static Map<Integer, Map<Integer, List<Student>>> getCollect(List<Student> list) {
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getType,
                                Collectors.groupingBy(Student::getAge)
                        )
                );
    }

    @Test
    void toMap1() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        objects.add(new KuugaModel("1", 1));
        objects.add(new KuugaModel("1", 2));
        objects.add(new KuugaModel("1", 10));
        objects.add(new KuugaModel("2", 2));
        objects.add(new KuugaModel("2", 4));
        objects.add(new KuugaModel("2", 10));
        // key为对象属性，value也为对象属性
        Map<String, Integer> stringIntegerMap = ListUtil.toMap(objects, KuugaModel::getName, KuugaModel::getSex, (k1, k2) -> k2);
        System.out.println(stringIntegerMap);
        // key为对象属性，value为对象本身
        Map<String, Object> stringIntegerMap2 = ListUtil.toMap(objects, KuugaModel::getName, j -> j, (k1, k2) -> k2);
        System.out.println(stringIntegerMap2);
    }

    @Test
    void flatAndAnyMatch() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        objects.add(new KuugaModel("1", 1));
        objects.add(new KuugaModel("2", 2));
        boolean andAnyMatch = ListUtil.flatAndAnyMatch(objects, KuugaModel::getName, ListUtil.newArrayList("3")::contains);
        System.out.println(andAnyMatch);
    }

    @Test
    void flatAndAllMatch() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        objects.add(new KuugaModel("1", 1));
        objects.add(new KuugaModel("2", 2));
        boolean andAnyMatch = ListUtil.flatAndAllMatch(objects, KuugaModel::getName, ListUtil.newArrayList("1", "2", "3")::contains);
        System.out.println(andAnyMatch);
    }

    @Test
    void flatAndNoneMatch() {
        List<KuugaModel> objects = ListUtil.newArrayList();
        objects.add(new KuugaModel("1", 1));
        objects.add(new KuugaModel("2", 2));
        boolean andAnyMatch = ListUtil.flatAndNoneMatch(objects, KuugaModel::getName, ListUtil.newArrayList("3")::contains);
        System.out.println(andAnyMatch);
    }

    @Test
    void list() {
        List<Object> list = ListUtil.list(true);
        System.out.println(list.getClass().toString());
    }

    @Test
    void list1() {
        List<Object> list = ListUtil.list(true, "1", "2");
        System.out.println(list.getClass().toString());
        System.out.println(StringUtil.formatString(list));
    }

    @Test
    void subtract() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        List<Integer> list2 = ListUtil.newArrayList(2, 4, 6, 8, 10);
        Collection<Integer> objects = ListUtil.uniqueUnion(list, list2);
        System.out.println(objects);

        Collection<Integer> subtract = CollectionUtils.subtract(list, list2);
        System.out.println(subtract);

        Collection<Integer> subtract1 = ListUtil.subtract(list, list2);
        System.out.println(subtract1);
    }

    @Test
    void getRandomElements() {
        List<Integer> objects = ListUtil.newArrayList();
        objects.add(null);
        objects.add(1);
        objects.add(null);
        objects.add(1);
        objects.add(2);
        List<Integer> randomElements = ListUtil.getRandomElements(objects, 2);
        System.out.println(StringUtil.formatString(randomElements));
    }

    @Test
    void flat() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga3").sex(2).build();
        List<KuugaDTO> kuugaDTOS = ListUtil.newArrayList(kuuga1, kuuga2);
        List<String> flat = ListUtil.flat(kuugaDTOS, KuugaDTO::getName);
        System.out.println(flat);

        KuugaDTO[] objects = new KuugaDTO[]{kuuga1, kuuga2};
        List<String> flat1 = ListUtil.flat(objects, KuugaDTO::getName);
        System.out.println(flat1);
    }

    @Test
    void flatMap() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga3").sex(2).build();
        List<KuugaDTO> kuugaDTOS = ListUtil.newArrayList(kuuga1, kuuga2);
        Stream<String> objectStream = ListUtil.flatMap(kuugaDTOS, kuugaDTO -> Stream.of(kuugaDTO.getName()));
        System.out.println(objectStream.collect(Collectors.toList()));

        List<Integer> collect = kuugaDTOS.stream().flatMap(kuugaDTO -> Stream.of(kuugaDTO.getSex())).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    void flatToSet() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga1").sex(2).build();
        List<KuugaDTO> kuugaDTOS = ListUtil.newArrayList(kuuga1, kuuga2);
        Set<String> set = ListUtil.flatToSet(kuugaDTOS, KuugaDTO::getName);
        System.out.println(set);

        KuugaDTO[] objects = new KuugaDTO[]{kuuga1, kuuga2};
        Set<String> set1 = ListUtil.flatToSet(objects, KuugaDTO::getName);
        System.out.println(set1);
    }

    @Test
    void charactersOf() {
        ImmutableList<Character> characters = ListUtil.charactersOf("Kuuga is me.");
        characters.forEach(c -> System.out.println(c.toString()));
    }

    @Test
    void toMapSingle() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        KuugaDTO kuuga3 = KuugaDTO.builder().name("kuuga3").sex(3).build();
        List<KuugaDTO> kuugaDTOS = ListUtil.newArrayList(kuuga1, kuuga2, kuuga3);
        Map<Object, KuugaDTO> objectkuugaDTOMap = ListUtil.toMapSingle(kuugaDTOS, KuugaDTO::getName);
        System.out.println(MapUtil.toString(objectkuugaDTOMap, true));
        Map<Object, List<KuugaDTO>> objectkuugaDTOListMap = ListUtil.toMap(kuugaDTOS, KuugaDTO::getName);
        System.out.println(MapUtil.toString(objectkuugaDTOListMap, true));
        Map<String, Integer> stringIntegerMap = ListUtil.toMap(kuugaDTOS, KuugaDTO::getName, KuugaDTO::getSex, (k1, k2) -> k1);
        System.out.println(MapUtil.toString(stringIntegerMap, true));
    }

    @Test
    void toSet() {
        List<Integer> objects = ListUtil.newArrayList(1, 1, 2, 3, 4);
        Set<Integer> x = ListUtil.toSet(objects);
        System.out.println(StringJoinerUtil.join(x));
    }

    @Test
    void testkuuga() {
        AtomicInteger sumCount = new AtomicInteger(0);
        List<FairCutData> fairCutDataList = ListUtil.newArrayList();
        for (int i = 1; i <= 10; i++) {
            FairCutData vo = new FairCutData();
            vo.setId(String.valueOf(i));
            vo.setCount(i);
            fairCutDataList.add(vo);
        }
        List<List<String>> lists = ListUtil.splitData(fairCutDataList, sumCount, 20, 3);
        lists.forEach(list -> {
            System.out.println(StringUtil.repeatNormal());
            System.out.println(list.toString());
        });
    }

    @Test
    void limit() {
        // List<Integer> rangeInteger = ListUtil.getRandomIntegerValues(1, 10, 5);
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        List<Integer> limit = ListUtil.limit(rangeInteger, 20);
        limit.forEach(System.out::println);
    }

    @Test
    void skip() {
        // List<Integer> rangeInteger = ListUtil.getRandomIntegerValues(1, 10, 5);
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        List<Integer> limit = ListUtil.skip(rangeInteger, 5);
        limit.forEach(System.out::println);
    }

    @Test
    void count() {
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        long count = ListUtil.count(rangeInteger, integer -> integer % 2 == 0);
        System.out.println(count);
    }

    @Test
    void anyMatch() {
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        // 是否有任意一个值大于5
        boolean b = ListUtil.anyMatch(rangeInteger, integer -> integer > 5);
        System.out.println(b);
    }

    @Test
    void allMatch() {
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        // 是否所有值都大于0
        boolean b = ListUtil.allMatch(rangeInteger, integer -> integer > 0);
        System.out.println(b);
    }

    @Test
    void noneMatch() {
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 10);
        // 是否所有值都大于0
        boolean b = ListUtil.noneMatch(rangeInteger, integer -> integer < 2);
        System.out.println(b);
    }

    @Test
    void findAny() {
        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 100);
        // 是否所有值都大于0
        for (int i = 0; i < 10; i++) {
            System.out.println(ListUtil.findAny(rangeInteger));
        }
        System.out.println(StringUtil.repeatNormal());
        ThreadUtil.concurrencyTest(20, () -> System.out.println(ListUtil.findAny(rangeInteger)));
    }

    @Test
    void peeked() {
        List<String> result = ListUtil.newArrayList();

        List<Integer> rangeInteger = ListUtil.createRangeInteger(1, 5);
        ListUtil.peeked(rangeInteger, integer -> {
            // 提取元素
            result.add("Kuuga->" + integer);
        });
        System.out.println(result);
        System.out.println(StringUtil.repeatNormal());
        // 查看元素
        ListUtil.peeked(rangeInteger, System.out::println);
    }

    @Test
    void peek() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(KuugaModel.builder().name("kuuga1").sex(1).build());
        list.add(KuugaModel.builder().name("kuuga2").sex(2).build());
        // 直接操作元素
        ListUtil.peeked(list, x -> x.setSex(3));

        System.out.println(list);
        System.out.println(StringUtil.repeatNormal());
    }

    @Test
    void sumByReduce() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        System.out.println("reduce：求和：" + list.stream().reduce(0, Integer::sum));
    }

    @Data
    @AllArgsConstructor
    public static class Person {
        private String name;
        private String nameFirst;
        private String job;
        private String sex;
        private int salary;
        private int i2;
    }


    List<Person> javaProgrammers = new ArrayList<>() {
        {
            add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 2000, 18));
            add(new Person("Tamsen", "Brittany", "Java programmer", "female", 2371, 55));
            add(new Person("Floyd", "Donny", "Java programmer", "male", 3322, 25));
            add(new Person("Sindy", "Jonie", "Java programmer", "female", 35020, 15));
            add(new Person("Vere", "Hervey", "Java programmer", "male", 2272, 25));
            add(new Person("Maude", "Jaimie", "Java programmer", "female", 2057, 87));
            add(new Person("Shawn", "Randall", "Java programmer", "male", 3120, 99));
            add(new Person("Jayden", "Corrina", "Java programmer", "female", 345, 25));
            add(new Person("Palmer", "Dene", "Java programmer", "male", 3375, 14));
            add(new Person("Addison", "Pam", "Java programmer", "female", 3426, 20));
        }
    };

    @Test
    void reduce() {
        OptionalInt reduce = javaProgrammers.stream()
                .mapToInt(Person::getSalary)// 返回数值流，减少拆箱封箱操作，避免占用内存  IntStream
                .reduce(Integer::sum);
        // .reduce((x, y) -> x + y);
        if (reduce.isPresent()) {
            System.out.printf("方式一   reduce(BinaryOperator<T> accumulator)   求薪资测试结果：" + reduce.getAsInt());
        }
        /*解析：
             1. reduce(BinaryOperator<T> accumulator)    reduce方法接受一个函数，这个函数有两个参数
             2. 第一个参数是上次函数执行的返回值（也称为中间结果），第二个参数是stream中的元素，这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数
         *注意：
             1.第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素
             2.方法返回值类型是Optional
         */
    }

    @Test
    void reduce1() {
        int reduce = javaProgrammers.stream().mapToInt(Person::getSalary).reduce(10000, Integer::sum);
        System.out.printf("方式二  reduce(T identity, BinaryOperator<T> accumulator)   求薪资测试结果：" + reduce);

        /*注意：
         *      1.与方式一相比设置了累加器的初始值，参数一（x）则不再是Stream中的第一个数据而是设置的初始值（10000）其他相同
         */
    }

    @Test
    void reduce3() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        // 最小值
        System.out.println(list.stream().reduce(Integer::min).orElse(null));
        // 最大值
        System.out.println(list.stream().reduce(Integer::max).orElse(null));
    }

    @Test
    void reduce2() {
        // 第三个参数---参数的数据类型必须为返回数据类型，改参数主要用于合并多个线程的result值
        // （Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result）
        ArrayList<Integer> accResult_ = Stream.of(1, 2, 3, 4)
                // 第一个参数，初始值为ArrayList
                .reduce(new ArrayList<>(),
                        // 第二个参数，实现了BiFunction函数式接口中apply方法，并且打印BiFunction
                        (acc, item) -> {
                            acc.add(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("BiFunction");
                            return acc;
                        }, (acc, item) -> {
                            System.out.println("BinaryOperator");
                            acc.addAll(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("--------");
                            return acc;
                        });
        System.out.println("accResult_: " + accResult_);

        System.out.println("------------------lambda优化代码-----------------");

        ArrayList<Integer> newList = new ArrayList<>();

        ArrayList<Integer> accResult_s = Stream.of(1, 2, 3, 4)
                .reduce(newList,
                        (acc, item) -> {
                            acc.add(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("BiFunction");
                            return acc;
                        }, (acc, item) -> null);
        System.out.println("accResult_s: " + accResult_s);
    }

    @Test
    void test() {
        List<SysBizConfigValueVo> paramConfigs = ListUtil.newArrayList();
        paramConfigs.add(SysBizConfigValueVo.builder().variableName("1").paramValue("11").build());
        paramConfigs.add(SysBizConfigValueVo.builder().variableName("2").paramValue(null).build());

        Map<String, Map<String, List<SysBizConfigValueVo>>> stringMapMap = ListUtil.toMap(paramConfigs, SysBizConfigValueVo::getVariableName, SysBizConfigValueVo::getParamValue);
        System.out.println(StringUtil.formatString(stringMapMap));

        // Map<String, String> paramMap = ListUtil.optimize(paramConfigs).stream().collect(Collectors.toMap(SysBizConfigValueVo::getVariableName, SysBizConfigValueVo::getParamValue));
        // System.out.println(StringUtil.formatString(paramMap));
    }

    @Builder
    @Getter
    static class SysBizConfigValueVo implements Serializable {

        private String variableName;

        private String variableExpression;

        private String applyId;

        /**
         * 关于参数值类型的说明：
         * TEXT("输入框") 单个字符串值，无需解析 例如 "深圳市南山区"
         * RADIO("单选框") 单选框为井号 # 隔开的字符串 例如： "北京#广州#上海#深圳"
         * CHECKBOX("复选框")#单选框为井号 # 隔开的字符串 例如： "北京#广州#上海#深圳"
         * RANGE("范围值")# 范围值中间以井号 # 隔开 例如 "2021-03-01#2021-04-21"
         * SWITCH("开关");  字符串类型  开：YES 关：NO
         */
        private String paramValue;

        private BizConfigParamTypeEnum paramType;

    }

    enum BizConfigParamTypeEnum {
        TEXT("输入框"),
        RADIO("单选框"),
        CHECKBOX("复选框"),
        RANGE("范围值"),
        SWITCH("开关");

        private final String desc;

        public String getDesc() {
            return this.desc;
        }

        private BizConfigParamTypeEnum(String desc) {
            this.desc = desc;
        }
    }

}