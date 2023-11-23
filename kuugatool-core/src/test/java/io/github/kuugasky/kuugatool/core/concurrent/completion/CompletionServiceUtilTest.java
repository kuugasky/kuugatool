package io.github.kuugasky.kuugatool.core.concurrent.completion;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.CompletionServiceUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletionServiceUtilTest {

    @Test
    public void takeGet() throws ExecutionException, InterruptedException {
        List<Object> objects = ListUtil.newArrayList();
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        CompletionServiceUtil.build(2)
                .submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    objects.add("1");
                })
                .submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    objects.add("2");
                })
                .takeGet();
        // 同步完成 已最长耗时任务为终点
        System.out.println(objects);
        System.out.println(timeInterval.intervalPretty());
    }

    @Test
    public void takeGetReturn() throws ExecutionException, InterruptedException {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        List<Object> objects = CompletionServiceUtil.build(3, true)
                .submit(() -> 1)
                .submit(() -> 2)
                .submit(() -> 3)
                .takeGetReturn();
        System.out.println(objects);
        System.out.println(timeInterval.intervalPretty());
    }

}