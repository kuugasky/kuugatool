package io.github.kuugasky.kuugatool.core.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import io.github.kuugasky.kuugatool.core.date.DateUtil;

/**
 * GuavaRateLimiterTest1
 * 方法被无序调用
 *
 * @author kuuga
 * @since 2022/7/29 17:27
 */
public class GuavaRateLimiterTest1 {

    /**
     * 1s最多获取1个令牌
     * 相当于rateLimiter.acquire()，1s只会执行1次
     */
    static RateLimiter rateLimiter = GuavaRateLimiter.create(1);

    public static void main(String[] args) {

        // RateLimiter常用的方法有：
        // 1.acquire,返回一个令牌，会有等待的过程，返回值是等待的时长，单位为秒;可以一次调用获取多个令牌;
        // 2.tryAcquire，立即获取令牌，可以尝试获取多个;返回结果表示是否成功获取到令牌;

        // RateLimiter的默认构造器返回的是SmoothBursty，是一种每秒按照固定速率生成令牌
        // 为了测试出效果，这里使用多个线程重复模拟请求同一个方法
        for (int i = 0; i < 10; i++) {
            print(i);
        }

        // 第[0]次获取,当前时间:1649777733792,waitTime:0.0
        // 第[1]次获取,当前时间:1649777734292,waitTime:0.498631
        // 第[2]次获取,当前时间:1649777734791,waitTime:0.498059
        // 第[3]次获取,当前时间:1649777735292,waitTime:0.499828
        // 第[4]次获取,当前时间:1649777735791,waitTime:0.499297
        // 第[5]次获取,当前时间:1649777736291,waitTime:0.499883
        // 第[6]次获取,当前时间:1649777736791,waitTime:0.499408
        // 第[7]次获取,当前时间:1649777737292,waitTime:0.499867
        // 第[8]次获取,当前时间:1649777737791,waitTime:0.499272
        // 第[9]次获取,当前时间:1649777738291,waitTime:0.499843

    }

    public static void print(int ct) {
        // acquire的返回值是拿到令牌所等待的时长，单位为秒
        double waitTime = rateLimiter.acquire();
        System.out.println("第[" + ct + "]次获取,当前时间:" + System.currentTimeMillis() + "[" + DateUtil.todayTime() + "]" + ",waitTime:" + waitTime);
    }

}
