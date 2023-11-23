package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
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
public class SyncMultTaskTest {

    @Data
    @Builder
    static class kuugaSource {
        private int gardenId;
        private long houseCount;
        private long rentHouseCount;
        private long sellHouseCount;
    }

    enum SumType implements TaskEvent {
        HOUSE,
        RENT_HOUSE,
        SELL_HOUSE,
        ;

        @Override
        public String getEvent() {
            return this.name();
        }
    }

    @Data
    @Builder
    static class ResultVo implements Result {

        private int gardenId;
        private long houseCount;
        private long rentHouseCount;
        private long sellHouseCount;

        @Override
        public String getPrimaryKey() {
            return gardenId + "";
        }

        @Override
        public Object getResult() {
            return this;
        }
    }

    Map<SumType, Long> timeMap = new ConcurrentHashMap<>();

    private static <T> void handleTask(Exception e, TaskEvent event, T t) {
        System.out.printf("==========事件：%s===========对象：%s=============异常:%s%n", event, t.toString(), e.fillInStackTrace().toString());
    }

    @Test
    void test() throws Exception {
        timeMap.put(SumType.HOUSE, 0L);
        timeMap.put(SumType.RENT_HOUSE, 0L);
        timeMap.put(SumType.SELL_HOUSE, 0L);

        List<kuugaSource> sources = ListUtil.newArrayList();
        sources.add(kuugaSource.builder().gardenId(1).build());
        sources.add(kuugaSource.builder().gardenId(2).build());
        sources.add(kuugaSource.builder().gardenId(3).build());
        List<ErrorTask<kuugaSource>> errorTasks = SyncTask.data(sources)
                .addTask(SumType.HOUSE, this::countHouseMultiple, SyncMultTaskTest::comparable, SyncMultTaskTest::handleTask)
                .addTask(SumType.RENT_HOUSE, this::countRentHouseMultiple, SyncMultTaskTest::comparable)
                .addTask(SumType.SELL_HOUSE, this::countSellHouseMultiple, SyncMultTaskTest::comparable)
                .exec(this::packResult);

        errorTasks.forEach(System.out::println);

        for (SumType eventEnum : timeMap.keySet()) {
            System.out.printf("[%s]节点 共耗时:[%s]毫秒%n", eventEnum.name(), timeMap.get(eventEnum).toString());
        }
        System.out.println(StringUtil.repeatNormal());

        sources.forEach(System.out::println);

        System.out.println(StringUtil.repeatNormal());

        SyncTask.ConcurrentTaskInfo concurrentTaskInfo = SyncTask.getTaskInfo();
        System.out.println("已完成" + concurrentTaskInfo.getCompleted());
        System.out.println("当前" + concurrentTaskInfo.getCurrent());
        System.out.println("峰值" + concurrentTaskInfo.getMaxPeak());
    }

    private static boolean comparable(kuugaSource source, Result result) {
        return ObjectUtil.equals(String.valueOf(source.getGardenId()), result.getPrimaryKey());
    }

    private void packResult(TaskEvent taskEvent, kuugaSource kuugaSource, Result result) {
        ResultVo resultVo = (ResultVo) result;
        String event = taskEvent.getEvent();
        SumType sumType = SumType.valueOf(event);
        switch (sumType) {
            case HOUSE, RENT_HOUSE, SELL_HOUSE -> {
                kuugaSource.setHouseCount(resultVo.houseCount);
                kuugaSource.setRentHouseCount(resultVo.rentHouseCount);
                kuugaSource.setSellHouseCount(resultVo.sellHouseCount);
            }
        }
    }

    @SneakyThrows
    public List<ResultVo> countHouseMultiple(List<kuugaSource> source) {
        throw new RuntimeException("house异常");
        // TimeInterval timeInterval = new TimeInterval();
        // String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        // timeInterval.start(snowflakeIdStr);
        //
        // // 根据source的gardenIds去查询得到一个集合
        // List<ResultVo> vos = ListUtil.newArrayList();
        // // vos.add(ResultVo.builder().gardenId(1).houseCount(1).rentHouseCount(2).sellHouseCount(3).build());
        //
        // TimeUnit.MILLISECONDS.sleep(500);
        //
        // timeMap.put(SumType.HOUSE, timeMap.get(SumType.HOUSE) + timeInterval.interval(snowflakeIdStr));
        // return vos;
    }

    @SneakyThrows
    public List<ResultVo> countRentHouseMultiple(List<kuugaSource> source) {
        TimeInterval timeInterval = new TimeInterval();
        String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        timeInterval.start(snowflakeIdStr);

        // 根据source的gardenIds去查询得到一个集合
        List<ResultVo> vos = ListUtil.newArrayList();
        vos.add(ResultVo.builder().gardenId(2).houseCount(1).rentHouseCount(2).sellHouseCount(3).build());

        TimeUnit.MILLISECONDS.sleep(500);

        timeMap.put(SumType.RENT_HOUSE, timeMap.get(SumType.RENT_HOUSE) + timeInterval.interval(snowflakeIdStr));
        return vos;
    }

    @SneakyThrows
    public List<ResultVo> countSellHouseMultiple(List<kuugaSource> source) {
        TimeInterval timeInterval = new TimeInterval();
        String snowflakeIdStr = SnowflakeIdUtil.getSnowflakeIdStr();
        timeInterval.start(snowflakeIdStr);

        // 根据source的gardenIds去查询得到一个集合
        List<ResultVo> vos = ListUtil.newArrayList();
        vos.add(ResultVo.builder().gardenId(3).houseCount(1).rentHouseCount(2).sellHouseCount(3).build());

        TimeUnit.MILLISECONDS.sleep(500);

        timeMap.put(SumType.SELL_HOUSE, timeMap.get(SumType.SELL_HOUSE) + timeInterval.interval(snowflakeIdStr));
        return vos;
    }

}
