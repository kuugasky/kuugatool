package io.github.kuugasky.kuugatool.core.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import io.github.kuugasky.kuugatool.core.object.Preconditions;

import java.util.concurrent.CountDownLatch;

/**
 * GuavaRateLimiterTest2
 * 多线程并发抢占资源
 *
 * @author kuuga
 * @since 2022/7/29 18:13
 */
public class GuavaRateLimiterTest2 {

    // 限速器
    static RateLimiter rateLimiter = RateLimiter.create(8);
    // 并发测试控制器
    static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        // RateLimiter的默认构造器返回的是SmoothBursty，是一种每秒按照固定速率生成令牌
        // RateLimiter常用的方法有：
        // 1.acquire,返回一个令牌，会有等待的过程，返回值是等待的时长，单位为秒;可以一次调用获取多个令牌;
        // 2.tryAcquire，立即获取令牌，可以尝试获取多个;返回结果表示是否成功获取到令牌;
        System.out.println("--------------------------");
        System.out.println("let's...");
        System.out.println("--------------------------");

        // 为了测试出效果，这里使用多个线程重复模拟请求同一个方法
        // 设置每秒生成8个令牌，当获取令牌失败时，会抛出异常，代表请求被拒绝
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println("thread:" + Thread.currentThread().getName() + ",ready...");
                    latch.await();

                    // Guava自带的测试工具进行翻车检查
                    Preconditions.checkState(rateLimiter.tryAcquire(), "thread:" + Thread.currentThread().getName() + "令牌不足访问被拒绝...");

                    // 获取令牌成功，执行具体业务
                    System.out.println("thread:" + Thread.currentThread().getName() + ",doing...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }
        // 等待3s后，latch.countDown();释放锁，让9个先线程都去争抢执行
        Thread.sleep(3000L);
        latch.countDown();
        System.out.println("--------------------------");
        System.out.println("go!");
        System.out.println("--------------------------");

//    --------------------------
//       let's...
//    --------------------------
//    thread:t0,ready...
//    thread:t2,ready...
//    thread:t1,ready...
//    thread:t3,ready...
//    thread:t5,ready...
//    thread:t4,ready...
//    thread:t6,ready...
//    thread:t8,ready...
//    thread:t9,ready...
//    thread:t7,ready...
//    --------------------------
//       go!
//    --------------------------
//    thread:t2,doing...
//    thread:t7,doing...
//    thread:t8,doing...
//    thread:t5,doing...
//    thread:t9,doing...
//    thread:t1,doing...
//    thread:t3,doing...
//    thread:t6,doing...
//    thread:t4,doing...
//    Exception in thread "t0" java.lang.IllegalStateException: thread:t0令牌不足访问被拒绝...
//    at com.google.common.base.Preconditions.checkState(Preconditions.java:507)
//    at com.xxx.guava.GuavaRateLimiterTest2.lambda$main$0(GuavaRateLimiterTest2.java:41)
//    at java.lang.Thread.run(Thread.java:748)
    }

}
