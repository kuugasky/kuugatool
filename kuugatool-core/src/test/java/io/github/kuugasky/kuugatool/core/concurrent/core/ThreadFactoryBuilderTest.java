package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryBuilderTest {

    @Test
    public void create() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        System.out.println(StringUtil.formatString(threadFactoryBuilder));
    }

    @Test
    public void setThreadFactory() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        threadFactoryBuilder.setThreadFactory(runnable -> new Thread("Kuuga thread"));
        ThreadFactory build = threadFactoryBuilder.build();
        System.out.println(StringUtil.formatString(build));
    }

    @Test
    public void setNamePrefix() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        threadFactoryBuilder.setThreadFactory(runnable -> new Thread("Kuuga thread"));
        threadFactoryBuilder.setNamePrefix("kuuga");
        ThreadFactory build = threadFactoryBuilder.build();
        System.out.println(StringUtil.formatString(build));
    }

    @Test
    public void setDaemon() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        threadFactoryBuilder.setThreadFactory(runnable -> new Thread("Kuuga thread"));
        threadFactoryBuilder.setNamePrefix("kuuga");
        // 设置是否守护线程
        threadFactoryBuilder.setDaemon(true);
        ThreadFactory build = threadFactoryBuilder.build();
        System.out.println(StringUtil.formatString(build));
    }

    @Test
    public void setPriority() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        threadFactoryBuilder.setThreadFactory(runnable -> new Thread("Kuuga thread"));
        threadFactoryBuilder.setNamePrefix("kuuga");
        // 设置是否守护线程
        threadFactoryBuilder.setDaemon(true);
        // 设置线程优先级
        ThreadFactory build = threadFactoryBuilder.setPriority(1).build();
        System.out.println(StringUtil.formatString(build));
    }

    @Test
    public void setUncaughtExceptionHandler() {
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        // threadFactoryBuilder.setThreadFactory(new NamedThreadFactory("kuuga0410-", true));
        threadFactoryBuilder.setNamePrefix("KUUGA-");
        // 设置是否守护线程
        threadFactoryBuilder.setDaemon(true);
        // 设置未捕获异常的处理方式 & 设置线程优先级
        ThreadFactory build = threadFactoryBuilder.setUncaughtExceptionHandler((thread, throwable)
                        -> System.out.println(StringUtil.format("线程[{}]任务异常：{}", thread.getName(), throwable.getMessage())))
                .setPriority(1).build();

        Thread thread = build.newThread(() -> {
            throw new RuntimeException("exception");
            // System.out.println("Kuuga task running.");
        });
        thread.start();
        ThreadUtil.sleep(3000);
    }

    @Test
    public void build() {
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        ThreadFactory build = threadFactoryBuilder.build();
        Thread thread = build.newThread(() -> System.out.println("Kuuga。。。"));
        thread.start();
    }
}