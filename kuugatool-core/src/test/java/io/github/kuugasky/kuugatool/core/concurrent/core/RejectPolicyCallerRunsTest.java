package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * RejectPolicyCallerRuns
 *
 * @author kuuga
 * @since 2022/11/28-11-28 15:16
 */
public class RejectPolicyCallerRunsTest {

    /**
     * 代码中定义了核心线程数为2的线程池，一共有7个任务要执行，其中2个任务创建线程执行去了，3个任务放入了任务队列（workQueue）。
     * <p>
     * 当提交到第6个任务的时候，会触发拒绝策略，在这里我们配置了CallerRunsPolicy策略，主线程直接执行第六个任务去了，不再向下执行main方法中的es.execute(t7)这段代码。
     * <p>
     * 也就是说，在本程序中最多会有3个任务在执行（2个线程池线程+1个主线程），3个在等待。由此限制了线程池的等待任务数与执行线程数。
     * <p>
     * 所以JDK文档才会说：“这提供了一个简单的反馈控制机制，将降低新任务提交的速度”。
     * <p>
     * 数据不丢失，只是执行会受实际运行线程量进行阻塞。
     * <p>
     * 指定core和maxCode后，采用CallerRunsPolicy时，使用阻塞队列或者同步队列无甚差异。
     * <p>
     * CallerRunsPolicy会将超过线程队列和线程数部分的任务交由主线程去执行，同时卡住后续任务，等主线程和线程池线程空闲后再继续执行后续任务。
     * <p>
     * 注意：是无序的！！！
     */
    @Test
    void RejectPolicyCallerRuns() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setMaxPoolSize(2);
        executorBuilder.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
        // executorBuilder.setWorkQueue(new ArrayBlockingQueue<>(3));
        executorBuilder.useArrayBlockingQueue(3);
        // executorBuilder.useSynchronousQueue();
        executorBuilder.setThreadFactory(ThreadFactoryBuilder.create().build());
        executorBuilder.setHandler(RejectPolicy.CALLER_RUNS.getValue());
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();

        List<MyTask> list = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(new MyTask("id:" + (i + 1)));
        }

        list.forEach(threadPoolExecutor::execute);

        DaemonThread.await();
    }

    @Test
    void test() {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        MyTask t1 = new MyTask("id:1");
        MyTask t2 = new MyTask("id:2");
        MyTask t3 = new MyTask("id:3");
        MyTask t4 = new MyTask("id:4");
        MyTask t5 = new MyTask("id:5");
        MyTask t6 = new MyTask("id:6");
        MyTask t7 = new MyTask("id:7");

        es.execute(t1);
        es.execute(t2);
        es.execute(t3);
        es.execute(t4);
        es.execute(t5);
        es.execute(t6);
        es.execute(t7);
    }

    static class MyTask implements Runnable {
        private final String id;

        public MyTask(String id) {
            this.id = id;
        }

        public void run() {
            int timeout = RandomUtil.randomInt(100, 1000);
            ThreadUtil.sleep(timeout, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + "--->" + id);
        }
    }

}
