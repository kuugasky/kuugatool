package io.github.kuugasky.kuugatool.core.concurrent.forkjoin;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ForkJoinPoolTest {

    @Test
    void getResult() {
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            sourceList.add(KuugaModel.builder().name(i + ".Kuuga").sex(i).build());
        }

        ForkJoinCallBack<KuugaModel, KuugaDTO> forkJoinCallBack = value -> MapperUtil.copy(value, KuugaDTO.class);
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        ForkJoinPool forkJoinPool = ForkJoinPool.submit(sourceList, forkJoinCallBack);
        List<Object> result = forkJoinPool.getResult();
        System.out.println(result.size());
        System.out.println("cost time:" + timeInterval.intervalPretty());
    }

    @Test
    void testSubmit() {
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            sourceList.add(KuugaModel.builder().name(i + ".Kuuga").sex(i).build());
        }

        ForkJoinCallBack<KuugaModel, KuugaDTO> forkJoinCallBack = value -> MapperUtil.copy(value, KuugaDTO.class);
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        ForkJoinPool forkJoinPool = ForkJoinPool.submit(sourceList, TaskSplitType.DATA_SIZE, 1000, forkJoinCallBack);
        List<Object> result = forkJoinPool.getResult();
        System.out.println(result.size());
        System.out.println("cost time:" + timeInterval.intervalPretty());
    }

    @Test
    void submitAndGet() {
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            sourceList.add(KuugaModel.builder().name(i + ".Kuuga").sex(i).build());
        }

        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        List<KuugaDTO> lists = ForkJoinPool.submitAndGet(sourceList, value -> {
            KuugaDTO copy = MapperUtil.copy(value, KuugaDTO.class);
            assert copy != null;
            copy.setName("dto-" + copy.getName());
            return copy;
        });
        System.out.println("cost time:" + timeInterval.intervalPretty());

        lists.forEach(System.out::println);
        System.out.println(lists.size());
    }

    @Test
    void testSubmitAndGet() {
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            sourceList.add(KuugaModel.builder().name(i + ".Kuuga").sex(i).build());
        }

        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        List<KuugaDTO> lists = ForkJoinPool.submitAndGet(sourceList, TaskSplitType.TASK_AMOUNT, 100, value -> {
            KuugaDTO copy = MapperUtil.copy(value, KuugaDTO.class);
            assert copy != null;
            copy.setName("dto-" + copy.getName());
            return copy;
        });
        System.out.println("cost time:" + timeInterval.intervalPretty());

        lists.forEach(System.out::println);
        System.out.println(lists.size());
    }

    @Test
    void test() {
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            sourceList.add(KuugaModel.builder().name(i + ".Kuuga").sex(i).build());
        }

        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        List<KuugaDTO> lists = ForkJoinPool.submitAndGet(sourceList, TaskSplitType.TASK_AMOUNT, 100, value -> {
            KuugaDTO copy = MapperUtil.copy(value, KuugaDTO.class);
            assert copy != null;
            copy.setName("dto-" + copy.getName());
            return copy;
        });
        System.out.println("cost time:" + timeInterval.intervalPretty());

        lists.forEach(System.out::println);
        System.out.println(lists.size());
    }

}