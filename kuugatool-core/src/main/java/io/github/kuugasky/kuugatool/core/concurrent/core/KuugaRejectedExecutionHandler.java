package io.github.kuugasky.kuugatool.core.concurrent.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程任务溢出后自定义拒绝策略
 *
 * @author pengqinglong
 * @since 2022/3/9
 */
@Slf4j
public class KuugaRejectedExecutionHandler implements RejectedExecutionHandler {

    /**
     * 存放因阻塞溢出的任务队列
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 线程执行器的任务队列
     */
    private BlockingQueue<Runnable> executorQueue;

    @SuppressWarnings("all")
    public KuugaRejectedExecutionHandler() {
        // 创建链式阻塞双端队列
        taskQueue = new LinkedBlockingDeque<>();
        Thread thread = new Thread(this::handle);
        thread.setName("KUUGA-THREAD-POOL-REJECTED-POLICY-DAEMON");
        // 这里如果设置为守护线程，当用户线程都关闭时，JMV不会等守护线程执行完再关闭，会丢失队列任务
        // thread.setDaemon(true);
        // 开启线程执行器任务队列填补任务线程
        thread.start();
    }

    @SuppressWarnings("all")
    public void handle() {
        // 死循环 take阻塞线程 put阻塞线程
        while (true) {
            try {
                // 从【存放因阻塞溢出的任务队列】中提取任务放入【线程执行器的任务队列】
                // .take()阻塞方法，一直等到taskQueue有内容才会返回
                Runnable take = taskQueue.take();
                // .put()阻塞方法，一直等到能插入工作队列才会返回
                executorQueue.put(take);
            } catch (InterruptedException e) {
                log.error("线程池溢出任务处理异常:{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 拒绝执行处理方法
     *
     * @param r        Runnable
     * @param executor ThreadPoolExecutor
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 初始化executorQueue
        if (executorQueue == null) {
            synchronized (taskQueue) {
                // 不用双重检查 因为就算重新赋值也是一样的
                executorQueue = executor.getQueue();
            }
        }
        // 将溢出的任务放入taskQueue中
        boolean offer = taskQueue.offer(r);
        if (!offer) {
            log.error("KuugaRejectedExecutionHandler.rejectedExecution:将溢出的任务放入taskQueue中失败,status:{}", false);
        }
    }

}
