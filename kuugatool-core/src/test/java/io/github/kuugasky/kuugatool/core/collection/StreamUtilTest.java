package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.stream.StreamUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.*;

class StreamUtilTest {

    @Test
    void mapMultiToInt() {
    }

    @Test
    void of() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3);
        Stream<Integer> of = StreamUtil.of(integers);
        System.out.println(StringUtil.formatString(of));
    }

    @Test
    void testOf() {
        Set<Integer> integers = SetUtil.newHashSet(1, 2, 3);
        Stream<Integer> of = StreamUtil.of(integers);
        System.out.println(StringUtil.formatString(of));
    }

    @Test
    void map() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        Stream<String> map = StreamUtil.map(integers, KuugaDTO::getName);
        String join = map.collect(Collectors.joining(","));
        System.out.println(StringUtil.formatString(join));
    }

    @Test
    void mapToInt() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        IntStream map = StreamUtil.mapToInt(integers, KuugaDTO::getSex);
        IntSummaryStatistics intSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(intSummaryStatistics));
    }

    @Test
    void mapToLong() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        LongStream map = StreamUtil.mapToLong(integers, KuugaDTO::getSex);
        LongSummaryStatistics longSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(longSummaryStatistics));
    }

    @Test
    void mapToDouble() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        DoubleStream map = StreamUtil.mapToDouble(integers, KuugaDTO::getSex);
        DoubleSummaryStatistics doubleSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(doubleSummaryStatistics));
    }

    @Test
    void flatMap() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        Stream<String> map = StreamUtil.flatMap(integers, kuugaDTO -> Stream.of(kuugaDTO.getName() + "*"));
        List<String> collect = map.collect(Collectors.toList());
        System.out.println(StringUtil.formatString(collect));
    }

    @Test
    void flatMapToInt() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        IntStream map = StreamUtil.flatMapToInt(integers, kuugaDTO -> IntStream.of(kuugaDTO.getSex()));
        IntSummaryStatistics intSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(intSummaryStatistics));
    }

    @Test
    void flatMapToLong() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        LongStream map = StreamUtil.flatMapToLong(integers, kuugaDTO -> LongStream.of(Long.parseLong(kuugaDTO.getSex() + StringUtil.EMPTY)));
        LongSummaryStatistics longSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(longSummaryStatistics));
    }

    @Test
    void flatMapToDouble() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        DoubleStream map = StreamUtil.flatMapToDouble(integers, kuugaDTO -> DoubleStream.of(Double.parseDouble(kuugaDTO.getSex() + StringUtil.EMPTY)));
        DoubleSummaryStatistics doubleSummaryStatistics = map.summaryStatistics();
        System.out.println(StringUtil.formatString(doubleSummaryStatistics));
    }

    /**
     * stream处理后再次转成stream
     */
    @Test
    void flatMapArraysStream() {
        // 将集合中的字符串中单词提取出来，不考虑特殊字符
        List<String> words = Arrays.asList("hello c++", "hello java", "hello python");
        List<String> result = words.stream()
                // 将单词按照空格切合，返回Stream<String[]>类型的数据
                .map(word -> word.split(" "))
                // 将Stream<String[]>转换为Stream<String>
                .flatMap(Arrays::stream)
                // 去重
                .distinct()
                .collect(Collectors.toList());
        System.out.println(result);
    }

    @Test
    void flatMapCollectionStream() {
        // 初始化测试数据
        List<String> hobby1 = Arrays.asList("java", "c", "音乐");
        List<String> hobby2 = Arrays.asList("c++", "c", "游戏");
        User user1 = new User(1, "张三", hobby1);
        User user2 = new User(2, "李四", hobby2);
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        // 将集合中每个用户的爱好进行计算，取并集
        List<String> result = users.stream()
                .map(user -> user.hobby)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(result);
    }

    static class User {
        int id;
        String name;
        List<String> hobby;

        public User(int id, String name, List<String> hobby) {
            this.id = id;
            this.name = name;
            this.hobby = hobby;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}