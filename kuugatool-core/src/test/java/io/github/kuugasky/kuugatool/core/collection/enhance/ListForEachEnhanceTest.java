package io.github.kuugasky.kuugatool.core.collection.enhance;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ListForEachEnhanceTest {

    @Test
    public void test() {
        List<KuugaModel> list = ListUtil.newArrayList();
        for (int i = 0; i < 10000000; i++) {
            list.add(new KuugaModel("kuuga_" + i, i));
        }

        List<KuugaModel> result = ListUtil.newArrayList();
        List<KuugaModel> result1 = ListUtil.newArrayList();

        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start("enhance");
        ListForEachEnhance.<KuugaModel>init()
                .data(list)
                .parallel(true)
                .add(item -> item.getSex() % 2 == 0, result::add)
                .execute();
        // System.out.println(result);
        System.out.println("enhance cost time:" + timeInterval.intervalPretty("enhance"));

        timeInterval.start("normal");
        list.stream().filter(item -> item.getSex() % 2 == 0).forEach(result1::add);
        System.out.println("normal cost time:" + timeInterval.intervalPretty("normal"));
    }

    @Test
    public void create() {
        String str = "10000万次";
        List<Long> list = initList(1_000_000);
        long filterCountStart = System.currentTimeMillis();
        long[] filterCount = filterCount(list);
        long filterCountEnd = System.currentTimeMillis();
        System.out.println("filter count" + str + "数据方式花费时间--" + (filterCountEnd - filterCountStart) + "毫秒" + "--数据结果--" + Arrays.toString(filterCount));

        long utilCountStart = System.currentTimeMillis();
        long[] utilCount = utilCount(list, true);
        long utilCountEnd = System.currentTimeMillis();
        System.out.println("无序enhance count" + str + "数据方式花费时间--" + (utilCountEnd - utilCountStart) + "毫秒" + "--数据结果--" + Arrays.toString(utilCount));

        long utilCountStart2 = System.currentTimeMillis();
        long[] utilCount2 = utilCount(list, false);
        long utilCountEnd2 = System.currentTimeMillis();
        System.out.println("有序enhance count" + str + "数据方式花费时间--" + (utilCountEnd2 - utilCountStart2) + "毫秒" + "--数据结果--" + Arrays.toString(utilCount2));
    }

    private List<Long> initList(int init) {
        List<Long> list = ListUtil.newArrayList(init);
        for (long i = 1; i <= init; i++) {
            list.add(i);
        }
        return list;
    }

    private long[] filterCount(List<Long> list) {
        long[] sumArr = new long[10];
        sumArr[0] = list.stream()
                .filter(i -> i % 10 == 0)
                .count();
        sumArr[1] = list.stream()
                .filter(i -> i % 10 == 1)
                .count();
        sumArr[2] = list.stream()
                .filter(i -> i % 10 == 2)
                .count();
        sumArr[3] = list.stream()
                .filter(i -> i % 10 == 3)
                .count();
        sumArr[4] = list.stream()
                .filter(i -> i % 10 == 4)
                .count();
        sumArr[5] = list.stream()
                .filter(i -> i % 10 == 5)
                .count();
        sumArr[6] = list.stream()
                .filter(i -> i % 10 == 6)
                .count();
        sumArr[7] = list.stream()
                .filter(i -> i % 10 == 7)
                .count();
        sumArr[8] = list.stream()
                .filter(i -> i % 10 == 8)
                .count();
        sumArr[9] = list.stream()
                .filter(i -> i % 10 == 9)
                .count();
        return sumArr;
    }

    private long[] utilCount(List<Long> list, boolean disorder) {
        long[] sumArr = new long[10];
        ListForEachEnhance.<Long>init()
                .parallel(disorder)
                .data(list)
                .add(item -> item % 10 == 0, item -> ++sumArr[0])
                .add(item -> item % 10 == 1, item -> ++sumArr[1])
                .add(item -> item % 10 == 2, item -> ++sumArr[2])
                .add(item -> item % 10 == 3, item -> ++sumArr[3])
                .add(item -> item % 10 == 4, item -> ++sumArr[4])
                .add(item -> item % 10 == 5, item -> ++sumArr[5])
                .add(item -> item % 10 == 6, item -> ++sumArr[6])
                .add(item -> item % 10 == 7, item -> ++sumArr[7])
                .add(item -> item % 10 == 8, item -> ++sumArr[8])
                .add(item -> item % 10 == 9, item -> ++sumArr[9])
                .execute();

        return sumArr;
    }

}