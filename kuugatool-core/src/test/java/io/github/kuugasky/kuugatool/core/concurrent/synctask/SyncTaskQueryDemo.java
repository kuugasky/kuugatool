package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.core.string.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.kuugasky.kuugatool.core.concurrent.synctask.BaoBiaoEnum.*;

/**
 * SyncTaskTest
 *
 * @author pengqinglong
 * @since 2022/4/20
 */
public class SyncTaskQueryDemo {

    public static void main(String[] args) throws Exception {

        // 单个任务测试 异常处理
        // List<ErrorTask<HouseCount>> errorTasks = queryList();
        // for (ErrorTask<HouseCount> houseCountErrorTask : ListUtil.optimize(errorTasks)) {
        //     System.out.println("事件 -：" + houseCountErrorTask.getEvent().getEvent());
        //     System.out.println("对象 -：" + houseCountErrorTask.getSource().toString());
        //     System.out.println("异常 -：" + houseCountErrorTask.getException().fillInStackTrace());
        // }

        // 并发测试 100个线程并发300个任务
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 50; i++) {

            new Thread(() -> {
                count.incrementAndGet();
                try {
                    // list集合中 每个数据 一条sql的查询
                    queryList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count.decrementAndGet();
            }).start();

            new Thread(() -> {
                count.incrementAndGet();
                try {
                    // list集合中 每个数据 一条sql的查询
                    querySingle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                count.decrementAndGet();
            }).start();
        }


        for (int i = 0; i < 40; i++) {
            System.out.println(count.get());
            print();
            TimeUnit.SECONDS.sleep(1);
        }
        SyncTask.shutdown();


    }

    @SuppressWarnings("unused")
    private static void print() {
        SyncTask.ConcurrentTaskInfo concurrentTaskInfo = SyncTask.getTaskInfo();
        System.out.println("已完成" + concurrentTaskInfo.getCompleted());
        System.out.println("当前" + concurrentTaskInfo.getCurrent());
        System.out.println("峰值" + concurrentTaskInfo.getMaxPeak());
    }

    private static void queryList() throws Exception {

        List<HouseCount> houseCounts = selectOrgList();

        SyncTask.data(houseCounts)
                // 异常处理机制
                .addErrorHandle(SyncTaskQueryDemo::handle)
                // 总量
                .addTask(SUM, SyncTaskQueryDemo::selectHouseSumList, SyncTaskQueryDemo::comparable, SyncTaskQueryDemo::handleTask)
                // 增量
                .addTask(ADD, SyncTaskQueryDemo::selectHouseAddList, SyncTaskQueryDemo::comparable)
                // 7日新增
                .addTask(DAY_7_COUNT, SyncTaskQueryDemo::selectHouseDay7List, SyncTaskQueryDemo::comparable)
                // 执行
                .exec(SyncTaskQueryDemo::consumer);

        // System.out.println(houseCounts);
    }

    private static <T> void handle(Exception e, TaskEvent event, T t) {
        System.out.printf("----------事件：%s--------对象：%s---------异常:%s%n", event, t.toString(), e.fillInStackTrace().toString());
    }

    private static <T> void handleTask(Exception e, TaskEvent event, T t) {
        System.out.printf("==========事件：%s===========对象：%s=============异常:%s%n", event, t.toString(), e.fillInStackTrace().toString());
    }

    @SuppressWarnings("unused")
    private static void querySingle() throws Exception {
        List<HouseCount> houseCounts = selectOrgList();

        SyncTask.data(houseCounts)
                .addErrorHandle(SyncTaskQueryDemo::handle)
                // 总量
                .addTask(SUM, SyncTaskQueryDemo::selectHouseSumSingle, SyncTaskQueryDemo::handleTask)
                // 新增
                .addTask(ADD, SyncTaskQueryDemo::selectHouseAddSingle)
                // 7日新增
                .addTask(DAY_7_COUNT, SyncTaskQueryDemo::selectHouseDay7CountSingle)
                // 执行
                .exec(SyncTaskQueryDemo::consumer);


        // System.out.println(houseCounts);
    }


    private static boolean comparable(HouseCount source, Result result) {
        return Objects.equals(source.getOrgId(), result.getPrimaryKey());
    }

    public static List<HouseCount> selectOrgList() {
        List<HouseCount> list = ListUtil.newArrayList();
        // 一页20条
        for (int i = 0; i < 20; i++) {
            list.add(new HouseCount(IdUtil.randomUUID()));
        }
        return list;
    }

    public static ReportResult selectHouseSumSingle(HouseCount houseCount) {

        // 查询耗时
        sleep(200);
        throw new RuntimeException("异常");
    }

    public static ReportResult selectHouseAddSingle(HouseCount houseCount) {

        // 查询耗时 毫秒
        sleep(100);
        throw new RuntimeException("异常");
    }

    public static ReportResult selectHouseDay7CountSingle(HouseCount houseCount) {

        // 查询耗时  毫秒
        sleep(300);
        return new ReportResult(houseCount.getOrgId(), RandomUtil.randomInt(1, 1000));
    }

    public static List<ReportResult> selectHouseSumList(List<HouseCount> list) {
        // 查询耗时  毫秒
        sleep(5000);
        throw new RuntimeException("异常");
    }

    public static List<ReportResult> selectHouseAddList(List<HouseCount> list) {
        // 查询耗时  毫秒
        sleep(1000);

        throw new RuntimeException("异常");
    }

    public static List<ReportResult> selectHouseDay7List(List<HouseCount> list) {
        // 查询耗时  毫秒
        sleep(3000);

        throw new RuntimeException("异常");
    }

    private static void sleep(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (Exception ignored) {

        }
    }

    /**
     * 消费处理：组装结果集
     *
     * @param event  事件
     * @param source 自定义结果收集对象
     * @param result 任务结果
     */
    public static void consumer(TaskEvent event, HouseCount source, Result result) {
        BaoBiaoEnum baoBiaoEnum = BaoBiaoEnum.valueOf(event.getEvent());
        switch (baoBiaoEnum) {
            case SUM -> source.setSum((Integer) result.getResult());
            case ADD -> source.setAddCount((Integer) result.getResult());
            case DAY_7_COUNT -> source.setDay7Count((Integer) result.getResult());
        }
    }
}

@Getter
@AllArgsConstructor
enum BaoBiaoEnum implements TaskEvent {

    /**
     *
     */
    SUM("总量"),
    ADD("新增量"),
    DAY_7_COUNT("7日新增量"),
    ;


    private final String desc;

    @Override
    public String getEvent() {
        return this.name();
    }
}

@Data
class HouseCount {

    private String orgId;

    private int sum;

    private int addCount;

    private int day7Count;

    public HouseCount(String orgId) {
        this.orgId = orgId;
    }
}

@Data
@AllArgsConstructor
class ReportResult implements Result {

    private String orgId;

    private int sum;

    @Override
    public String getPrimaryKey() {
        return orgId;
    }

    @Override
    public Object getResult() {
        return sum;
    }
}