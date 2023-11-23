package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.string.snow.SnowflakeIdUtil;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * SyncTaskTest
 *
 * @author kuuga
 * @since 2023/3/10-03-10 17:15
 */
public class SyncSingTaskTest {

    @Data
    @Builder
    static class KuugaSource {
        private int gardenId;
        private long houseCount;
        private long rentHouseCount;
        private long sellHouseCount;
    }

    @Data
    static class ResultVo implements Result {

        private int gardenId;
        private long houseCount;
        private long rentHouseCount;
        private long sellHouseCount;

        @Override
        public String getPrimaryKey() {
            return String.valueOf(gardenId);
        }

        @Override
        public Object getResult() {
            return this;
        }
    }

    Map<SyncMultTaskTest.SumType, Long> timeMap = new ConcurrentHashMap<>();

    @Test
    void test() throws Exception {
        timeMap.put(SyncMultTaskTest.SumType.HOUSE, 0L);
        timeMap.put(SyncMultTaskTest.SumType.RENT_HOUSE, 0L);
        timeMap.put(SyncMultTaskTest.SumType.SELL_HOUSE, 0L);

        List<KuugaSource> sources = ListUtil.newArrayList();
        sources.add(KuugaSource.builder().gardenId(1).build());
        sources.add(KuugaSource.builder().gardenId(2).build());
        sources.add(KuugaSource.builder().gardenId(3).build());

        List<ErrorTask<KuugaSource>> errorTasks = SyncTask.data(sources)
                .addTask(SyncMultTaskTest.SumType.HOUSE, this::countHouse)
                .addTask(SyncMultTaskTest.SumType.RENT_HOUSE, this::countRentHouse)
                .addTask(SyncMultTaskTest.SumType.SELL_HOUSE, this::countSellHouse)
                .exec(this::packResult);

        errorTasks.forEach(System.out::println);

        for (SyncMultTaskTest.SumType eventEnum : timeMap.keySet()) {
            System.out.printf("[%s]节点 共耗时:[%s]毫秒%n", eventEnum.name(), timeMap.get(eventEnum).toString());
        }
        sources.forEach(System.out::println);

        SyncTask.ConcurrentTaskInfo concurrentTaskInfo = SyncTask.getTaskInfo();
        System.out.println("已完成" + concurrentTaskInfo.getCompleted());
        System.out.println("当前" + concurrentTaskInfo.getCurrent());
        System.out.println("峰值" + concurrentTaskInfo.getMaxPeak());
    }

    private void packResult(TaskEvent taskEvent, KuugaSource kuugaSource, Result result) {
        ResultVo resultVo = (ResultVo) result;
        String event = taskEvent.getEvent();
        SyncMultTaskTest.SumType sumType = SyncMultTaskTest.SumType.valueOf(event);
        switch (sumType) {
            case HOUSE -> kuugaSource.setHouseCount(resultVo.houseCount);
            case RENT_HOUSE -> kuugaSource.setRentHouseCount(resultVo.rentHouseCount);
            case SELL_HOUSE -> kuugaSource.setSellHouseCount(resultVo.sellHouseCount);
        }
    }

    @SneakyThrows
    public ResultVo countHouse(KuugaSource source) {
        TimeInterval timeInterval = new TimeInterval();
        String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        timeInterval.start(snowflakeIdStr);

        TimeUnit.MILLISECONDS.sleep(500);
        // throw new RuntimeException("countHouse error");
        System.out.printf("根据gardenId:%s进行db查询%n", source.getGardenId());
        ResultVo resultVo = new ResultVo();
        resultVo.setGardenId(1);
        // resultVo.setHouseCount(100);

        timeMap.put(SyncMultTaskTest.SumType.HOUSE, timeMap.get(SyncMultTaskTest.SumType.HOUSE) + timeInterval.interval(snowflakeIdStr));
        return resultVo;
    }

    @SneakyThrows
    public ResultVo countRentHouse(KuugaSource source) {
        TimeInterval timeInterval = new TimeInterval();
        String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        timeInterval.start(snowflakeIdStr);

        TimeUnit.MILLISECONDS.sleep(600);
        System.out.printf("根据gardenId:%s进行db查询%n", source.getGardenId());
        ResultVo resultVo = new ResultVo();
        resultVo.setGardenId(1);
        resultVo.setRentHouseCount(200);

        timeMap.put(SyncMultTaskTest.SumType.RENT_HOUSE, timeMap.get(SyncMultTaskTest.SumType.RENT_HOUSE) + timeInterval.interval(snowflakeIdStr));
        return resultVo;
    }

    @SneakyThrows
    public ResultVo countSellHouse(KuugaSource source) {
        TimeInterval timeInterval = new TimeInterval();
        String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        timeInterval.start(snowflakeIdStr);

        TimeUnit.MILLISECONDS.sleep(400);
        System.out.printf("根据gardenId:%s进行db查询%n", source.getGardenId());
        ResultVo resultVo = new ResultVo();
        resultVo.setGardenId(1);
        resultVo.setSellHouseCount(300);

        timeMap.put(SyncMultTaskTest.SumType.SELL_HOUSE, timeMap.get(SyncMultTaskTest.SumType.SELL_HOUSE) + timeInterval.interval(snowflakeIdStr));
        return resultVo;
    }

}
