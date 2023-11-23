package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ExecutorBuilderTest {

    @Test
    void setCorePoolSize() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setMaxPoolSize() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setMaxPoolSize(3);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setKeepAliveTime() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(10);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testSetKeepAliveTime() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder = executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setWorkQueue() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void useArrayBlockingQueue() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder = executorBuilder.useArrayBlockingQueue(10);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void useSynchronousQueue() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder.useArrayBlockingQueue(10);
        executorBuilder.useSynchronousQueue();
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testUseSynchronousQueue() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder.useArrayBlockingQueue(10);
        executorBuilder.useSynchronousQueue(true);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setThreadFactory() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder.useArrayBlockingQueue(10);
        executorBuilder.useSynchronousQueue();
        executorBuilder.setThreadFactory(ThreadFactoryBuilder.create().build());
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setHandler() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder.useArrayBlockingQueue(10);
        executorBuilder.useSynchronousQueue();
        executorBuilder.setThreadFactory(ThreadFactoryBuilder.create().build());
        executorBuilder.setHandler(new KuugaRejectedExecutionHandler());
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void setAllowCoreThreadTimeOut() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        executorBuilder.setCorePoolSize(2);
        executorBuilder.setKeepAliveTime(1, TimeUnit.MINUTES);
        executorBuilder.setWorkQueue(new SynchronousQueue<>());
        executorBuilder.useArrayBlockingQueue(10);
        executorBuilder.useSynchronousQueue();
        executorBuilder.setThreadFactory(ThreadFactoryBuilder.create().build());
        executorBuilder = executorBuilder.setAllowCoreThreadTimeOut(true);
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        threadPoolExecutor.submit(() -> System.out.println("kuuga"));
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void create() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        System.out.println(executorBuilder);
    }

    @Test
    void build() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        ThreadPoolExecutor threadPoolExecutor = executorBuilder.build();
        System.out.println(threadPoolExecutor);
    }

    @Test
    void buildFinalizable() {
        ExecutorBuilder executorBuilder = ExecutorBuilder.create();
        ExecutorService executorService = executorBuilder.buildFinalizable();
        System.out.println(executorService);
    }

}