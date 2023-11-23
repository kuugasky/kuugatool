package io.github.kuugasky.kuugatool.core.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * RateLimiterUtil
 * <p>
 * 原理：Guava RateLimiter基于令牌桶算法，我们只需要告诉RateLimiter系统限制的QPS是多少，
 * <p>
 * 那么RateLimiter将以这个速度往桶里面放入令牌，然后请求的时候，通过tryAcquire()方法向RateLimiter获取许可（令牌）。
 * <p>
 * RateLimiter经常用于限制对一些物理资源或者逻辑资源的访问速率。
 * <p>
 * 与 Semaphore 相比，Semaphore 限制了并发访问的数量而不是使用速率。（注意尽管并发性和速率是紧密相关的，比如参考Little定律）
 * <p>
 * 扩展：<JDK>Semaphore</JDK>只能控制并发总量，而RateLimiter不光可以控制并发总量，还能控制并发的速率。
 * <p>
 * 注意：<br>
 * acquire会阻塞<br>
 * tryAcquire不阻塞<br>
 *
 * @author kuuga
 * @since 2022/7/29 16:32
 */
public final class GuavaRateLimiter {

    // public static ConcurrentHashMap<String, RateLimiter> resourceRateLimiter = new ConcurrentHashMap<>();

    /**
     * 创建一个具有指定稳定吞吐量的{@code RateLimiter}，给定为“许可证-秒”（通常称为<i>QPS</i>，每秒查询）。
     *
     * @param permitsPerSecond 允许每秒QPS
     * @return 速率限制器
     */
    public static RateLimiter create(double permitsPerSecond) {
        return RateLimiter.create(permitsPerSecond);
    }

    /**
     * Creates a {@code RateLimiter} with the specified stable throughput, given as "permits per
     * second" (commonly referred to as <i>QPS</i>, queries per second), and a <i>warmup period</i>,
     * during which the {@code RateLimiter} smoothly ramps up its rate, until it reaches its maximum
     * rate at the end of the period (as long as there are enough requests to saturate it). Similarly,
     * if the {@code RateLimiter} is left <i>unused</i> for a duration of {@code warmupPeriod}, it
     * will gradually return to its "cold" state, i.e. it will go through the same warming up process
     * as when it was first created.
     *
     * <p>The returned {@code RateLimiter} is intended for cases where the resource that actually
     * fulfills the requests (e.g., a remote server) needs "warmup" time, rather than being
     * immediately accessed at the stable (maximum) rate.
     *
     * <p>The returned {@code RateLimiter} starts in a "cold" state (i.e. the warmup period will
     * follow), and if it is left unused for long enough, it will return to that state.
     *
     * @param permitsPerSecond the rate of the returned {@code RateLimiter}, measured in how many
     *                         permits become available per second
     * @param warmupPeriod     the duration of the period where the {@code RateLimiter} ramps up its rate,
     *                         before reaching its stable (maximum) rate
     * @throws IllegalArgumentException if {@code permitsPerSecond} is negative or zero or {@code
     *                                  warmupPeriod} is negative
     * @since 28.0
     */
    public static RateLimiter create(double permitsPerSecond, Duration warmupPeriod) {
        return RateLimiter.create(permitsPerSecond, warmupPeriod);
    }


    /**
     * Creates a {@code RateLimiter} with the specified stable throughput, given as "permits per
     * second" (commonly referred to as <i>QPS</i>, queries per second), and a <i>warmup period</i>,
     * during which the {@code RateLimiter} smoothly ramps up its rate, until it reaches its maximum
     * rate at the end of the period (as long as there are enough requests to saturate it). Similarly,
     * if the {@code RateLimiter} is left <i>unused</i> for a duration of {@code warmupPeriod}, it
     * will gradually return to its "cold" state, i.e. it will go through the same warming up process
     * as when it was first created.
     *
     * <p>The returned {@code RateLimiter} is intended for cases where the resource that actually
     * fulfills the requests (e.g., a remote server) needs "warmup" time, rather than being
     * immediately accessed at the stable (maximum) rate.
     *
     * <p>The returned {@code RateLimiter} starts in a "cold" state (i.e. the warmup period will
     * follow), and if it is left unused for long enough, it will return to that state.
     *
     * @param permitsPerSecond the rate of the returned {@code RateLimiter}, measured in how many
     *                         permits become available per second
     * @param warmupPeriod     the duration of the period where the {@code RateLimiter} ramps up its rate,
     *                         before reaching its stable (maximum) rate
     * @param unit             the time unit of the warmupPeriod argument
     * @throws IllegalArgumentException if {@code permitsPerSecond} is negative or zero or {@code
     *                                  warmupPeriod} is negative
     */
    public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
        return RateLimiter.create(permitsPerSecond, warmupPeriod, unit);
    }

}
/*
- double：acquire()
-- 从RateLimiter获取一个许可，该方法会被阻塞直到获取到请求

- double：acquire(int permits)
-- 从RateLimiter获取指定许可数，该方法会被阻塞直到获取到请求

- static RateLimiter：create(double permitsPerSecond)
-- 根据指定的稳定吞吐率创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少查询）

- static RateLimiter：create(double permitsPerSecond, long warmupPeriod, TimeUnit unit)
-- 根据指定的稳定吞吐率和预热期来创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少个请求量），在这段预热时间内，RateLimiter每秒分配的许可数会平稳地增长直到预热期结束时达到其最大速率。（只要存在足够请求数来使其饱和）

- double：getRate()
-- 返回RateLimiter 配置中的稳定速率，该速率单位是每秒多少许可数

- void：setRate(double permitsPerSecond)
-- 更新RateLimiter的稳定速率，参数permitsPerSecond 由构造RateLimiter的工厂方法提供。

- String：toString()
-- 返回对象的字符表现形式

- boolean：tryAcquire()
-- 从RateLimiter 获取许可，如果该许可可以在无延迟下的情况下立即获取得到的话

- boolean：tryAcquire(int permits)
-- 从RateLimiter 获取许可数，如果该许可数可以在无延迟下的情况下立即获取得到的话

- boolean：tryAcquire(int permits, long timeout, TimeUnit unit)
-- 从RateLimiter 获取指定许可数如果该许可数可以在不超过timeout的时间内获取得到的话，或者如果无法在timeout 过期之前获取得到许可数的话，那么立即返回false （无需等待）

- boolean：tryAcquire(long timeout, TimeUnit unit)
-- 从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话，或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待）
 */

/*
// RateLimiter 构造方法，每秒限流permitsPerSecond
public static RateLimiter create(double permitsPerSecond)
// 每秒限流 permitsPerSecond，warmupPeriod 则是数据初始预热时间，从第一次acquire 或 tryAcquire 执行开时计算
public static RateLimiter create(double permitsPerSecond, Duration warmupPeriod)
// 获取一个令牌，阻塞，返回阻塞时间
public double acquire()
// 获取 permits 个令牌，阻塞，返回阻塞时间
public double acquire(int permits)
// 获取一个令牌，超时返回
public boolean tryAcquire(Duration timeout)
// 获取 permits 个令牌，超时返回
public boolean tryAcquire(int permits, Duration timeout)
 */