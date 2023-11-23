package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.collection.sort.SortBy;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class ListSortUtilTest {

    @Test
    void reverse() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuuga1);
        list.add(kuuga2);
        List<KuugaModel> reverse = ListSortUtil.reverse(list);
        System.out.println(StringUtil.formatString(reverse));

        reverse.add(null);
        System.out.println("list个数：" + list.size());
        System.out.println("反转的个数：" + reverse.size());
    }

    @Test
    void sortByReverse() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(kuugaModel3);
        List<KuugaModel> kuugaModels = ListSortUtil.reverseNew(list);
        kuugaModels.forEach(i -> System.out.println(i.getName()));
        kuugaModels.add(null);
        System.out.println("list个数：" + list.size());
        System.out.println("反转new的个数：" + kuugaModels.size());
    }

    @Test
    void sortByReverseAndFilterNull() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(null);
        list.add(kuugaModel3);
        List<KuugaModel> kuugaModels = ListSortUtil.reverseNew(list, true);
        kuugaModels.forEach(i -> System.out.println(i.getName()));
        // kuugaModels.add(null);
        // System.out.println("list个数：" + list.size());
        // System.out.println("反转new的个数："  + kuugaModels.size());
    }

    @Test
    void sortByPinyin() {
        List<String> strings = ListSortUtil.sortedByPinyin(ListUtil.newArrayList("ben", "alibaba", "jd"));
        strings.forEach(System.out::println);
    }

    @Test
    void sorted() {
        List<Demo> list = ListUtil.newArrayList();
        list.add(new Demo("1", "2"));
        list.add(new Demo("2", "3"));
        list.add(new Demo("1", "1"));
        list.add(new Demo("1", "3"));
        ListSortUtil.sorted(list, Comparator.comparingInt(o -> o.getPositionId().length()));

        System.out.println("岗位ID长度排序:" + String.join(",", ListUtil.flat(list, Demo::getPositionId)));
        List<Demo> sortedByComparing = ListSortUtil.sortedByComparing(list, Demo::getLevelId);
        System.out.println("职级ID排序:" + String.join(",", ListUtil.flat(sortedByComparing, Demo::getLevelId)));

        List<Demo> sortedByComparing1 = ListSortUtil.sortedByComparing(list, Demo::getLevelId, Demo::getPositionId);
        List<Object> collect = sortedByComparing1.stream().flatMap((Function<Demo, Stream<?>>) demo -> Stream.of(demo.getLevelId() + "-" + demo.positionId)).collect(toList());
        System.out.println("先排职级ID，再排岗位ID排序:" + StringJoinerUtil.join(",", collect));

        List<Demo> collectx1 = list.stream()
                .sorted(Comparator.comparingInt(Demo::getLevelIdInt).thenComparingInt(Demo::getPositionIdInt)).toList();
        List<Object> collect1 = collectx1.stream().flatMap((Function<Demo, Stream<?>>) demo -> Stream.of(demo.getLevelId() + "-" + demo.positionId)).collect(toList());
        System.out.println("先排职级ID，再排岗位ID排序:" + StringJoinerUtil.join(",", collect1));

        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdInt));
        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdInt, Demo::getPositionIdInt));
        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdLong));
        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdLong, Demo::getPositionIdLong));
        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdDouble));
        System.out.println(ListSortUtil.sortedByComparing(list, Demo::getLevelIdDouble, Demo::getLevelIdDouble));
        ListSortUtil.sorted(list, Comparator.comparing(Demo::getLevelIdInt).reversed().thenComparingLong(Demo::getPositionIdLong).reversed());
    }

    @Test
    void transform() {
        List<Demo> list = ListUtil.newArrayList();
        list.add(new Demo("1", "2"));
        list.add(new Demo("2", "3"));
        list.add(new Demo("1", "1"));
        list.add(new Demo("1", "3"));
        // List<String> transform = Lists.transform(list, Demo::getPositionId);
        // System.out.println(StringJoinerUtil.join(transform));

        List<String> flat = ListUtil.flat(list, Demo::getPositionId);
        System.out.println(StringJoinerUtil.join(flat));

        System.out.println(Lists.transform(list, Demo::getPositionId));
    }

    @Test
    void sort() {
        List<Demo> list = ListUtil.newArrayList();
        list.add(new Demo("1", "2"));
        list.add(new Demo("2", "3"));
        list.add(new Demo("2", "1"));
        list.add(new Demo("1", "3"));
        // ListUtil.sort(list, Comparator.comparingInt(Demo::getPositionId));

        list = list.stream().sorted(Comparator.comparing(Demo::getPositionId).thenComparing(Demo::getLevelId)).collect(toList());

        System.out.println(StringUtil.formatString(list));
    }

    @Test
    void testSortByReverse() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(kuugaModel1);
        list.add(kuugaModel2);
        list.add(null);
        list.add(kuugaModel3);
        List<KuugaModel> kuugaModels = ListSortUtil.reverseNew(list, false);
        kuugaModels.forEach(System.out::println);
    }

    @Test
    void sortedCustom1() {
        List<DemoTest> testList = new ArrayList<>();
        Date d = DateUtil.now();
        for (int i = 1; i <= 3; i++) {
            DemoTest t = new DemoTest(i, DateUtil.moreOrLessDays(d, i));
            testList.add(t);
        }
        for (int i = 1; i <= 3; i++) {
            DemoTest t = new DemoTest(i, DateUtil.moreOrLessMonths(d, i));
            testList.add(t);
        }

        List<DemoTest> sortList = ListSortUtil.sortedCustom(testList)
                .rule(DemoTest::getState)
                .rule(DemoTest::getState, SortBy.DESC)
                .rule(DemoTest::getTime)
                .rule(DemoTest::getTime, SortBy.DESC)
                .rule(DemoTest::getL)
                .rule(DemoTest::getL, SortBy.DESC)
                .rule(DemoTest::getD)
                .rule(DemoTest::getD, SortBy.DESC)
                .sorted();
        System.out.println(StringUtil.formatString(sortList));
    }

    @Test
    void sortedCustom() {
        List<DemoTest> testList = new ArrayList<>();
        Date d = DateUtil.now();
        for (int i = 1; i <= 3; i++) {
            DemoTest t = new DemoTest(i, DateUtil.moreOrLessDays(d, i));
            testList.add(t);
        }
        for (int i = 1; i <= 3; i++) {
            DemoTest t = new DemoTest(i, DateUtil.moreOrLessMonths(d, i));
            testList.add(t);
        }

        testList.forEach(o -> System.out.println(o.toString()));

        List<DemoTest> sort = testList.stream()
                .sorted(
                        Comparator.comparing(DemoTest::getState).reversed()
                                .thenComparing(DemoTest::getTime, Comparator.reverseOrder())).toList();

        System.out.println("List.stream------------------------------------");

        sort.forEach(o -> System.out.println(o.toString()));

        System.out.println("ListSortUtil.sortedCustom------------------------------------");

        List<DemoTest> sortList = ListSortUtil.sortedCustom(testList)
                .rule(DemoTest::getState, SortBy.DESC)
                .rule(DemoTest::getTime, SortBy.DESC)
                .sorted();

        sortList.forEach(o -> System.out.println(o.toString()));
    }

    @Data
    static class DemoTest {
        // 状态
        private int state;
        // 时间
        private Date time;

        private long l;
        private double d;

        public DemoTest(int state, Date time) {
            this.state = state;
            this.time = time;
        }

        @Override
        public String toString() {
            return "test{" +
                    "state=" + state +
                    ", time=" + DateUtil.formatDateTime(time) +
                    '}';
        }
    }

    @Data
    @AllArgsConstructor
    static class Demo {
        private String positionId;
        private String levelId;
        private int positionIdInt;
        private int levelIdInt;
        private long positionIdLong;
        private long levelIdLong;
        private double positionIdDouble;
        private double levelIdDouble;

        public Demo(String positionId, String levelId) {
            this.positionId = positionId;
            this.levelId = levelId;
        }

        public Demo(int positionId, int levelId) {
            this.positionIdInt = positionId;
            this.levelIdInt = levelId;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "positionId='" + positionId + '\'' +
                    ", levelId='" + levelId + '\'' +
                    ", positionIdInt=" + positionIdInt +
                    ", levelIdInt=" + levelIdInt +
                    ", positionIdLong=" + positionIdLong +
                    ", levelIdLong=" + levelIdLong +
                    ", positionIdDouble=" + positionIdDouble +
                    ", levelIdDouble=" + levelIdDouble +
                    '}';
        }
    }

    @Test
    void isAsc() {
        System.out.println(SortBy.isAsc(SortBy.ASC));
    }

}