package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactoryTest {

    public static final String PREFIX = "kuuga";

    @Test
    public void newThread() {
        // 创建NameThreadFactory
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(true);
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        // 将NameThreadFactory注入到线程工厂构建器
        ThreadFactory build = threadFactoryBuilder.setThreadFactory(namedThreadFactory).build();

        System.out.println(StringUtil.repeatNormal());

        build.newThread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("test1");
        }).start();
    }

    @Test
    public void newThread1() {
        // 创建NameThreadFactory
        NamedThreadFactory company = new NamedThreadFactory(PREFIX, new ThreadGroup("company"), true);
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        // 将NameThreadFactory注入到线程工厂构建器
        ThreadFactory build1 = threadFactoryBuilder.setThreadFactory(company).build();

        System.out.println(StringUtil.repeatNormal());
        build1.newThread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getThreadGroup().getName() + "test2");
        }).start();
    }

    @Test
    public void newThread2() {
        // 创建NameThreadFactory
        ThreadGroup threadGroup = new ThreadGroup("company");
        // 未捕获的异常处理程序
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                (thread, throwable) ->
                        System.out.println(StringUtil.format("线程[{}]任务异常：{}", thread.getName(), throwable.getMessage()));

        NamedThreadFactory company1 = new NamedThreadFactory(PREFIX, threadGroup, true, uncaughtExceptionHandler);
        // 创建线程工厂构建器
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create();
        // 将NameThreadFactory注入到线程工厂构建器
        ThreadFactory build2 = threadFactoryBuilder.setThreadFactory(company1).build();

        System.out.println(StringUtil.repeatNormal());
        build2.newThread(() -> {
            System.out.println(Thread.currentThread().getName());
            int i = 1 / 0;
            System.out.println(i);
            System.out.println(Thread.currentThread().getThreadGroup().getName() + "test2");
        }).start();
    }

}