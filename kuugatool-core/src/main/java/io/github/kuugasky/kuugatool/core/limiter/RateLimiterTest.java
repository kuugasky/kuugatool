package io.github.kuugasky.kuugatool.core.limiter;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * RateLimiterTest
 *
 * @author kuuga
 * @since 2021/7/13
 */
public class RateLimiterTest {

    public static void main(String[] args) {
        Stopwatch started = Stopwatch.createStarted();
        RateLimiter limiter = RateLimiter.create(2, 3, TimeUnit.SECONDS);
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        System.out.println("get one permit cost time: " + limiter.acquire(1) + "s");
        long elapsed = started.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(elapsed);
    }

}
