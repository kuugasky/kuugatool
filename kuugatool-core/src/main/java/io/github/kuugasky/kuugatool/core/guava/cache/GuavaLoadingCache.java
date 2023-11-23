package io.github.kuugasky.kuugatool.core.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.util.concurrent.RateLimiter;
import com.sun.istack.NotNull;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.guava.ratelimiter.GuavaRateLimiter;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * GuavaLoadingCache
 * <p>
 * guava cache
 *
 * @author kuuga
 * @since 2022/8/1 17:22
 */
public class GuavaLoadingCache {

    public static void main(String[] args) throws ExecutionException {
        RemovalListener<Integer, Long> integerLongRemovalListener =
                notification
                        // -> System.out.println(notification.getValue());
                        -> System.out.println("缓存已失效");

        LoadingCache<Integer, Long> cacheMap = CacheBuilder.newBuilder()
                // 初始容量
                .initialCapacity(10)
                // 并发级别
                .concurrencyLevel(10)
                // 缓存多久过期
                .expireAfterAccess(Duration.ofSeconds(1))
                // .weakValues()
                // .recordStats()
                .removalListener(integerLongRemovalListener)
                .build(new CacheLoader<>() {
                    @Override
                    public Long load(@NotNull Integer key) {
                        return System.currentTimeMillis();
                    }
                });
        cacheMap.put(1, 999L);

        RateLimiter rateLimiter = GuavaRateLimiter.create(1);
        for (int i = 0; i < 10; i++) {
            System.out.println("(" + rateLimiter.acquire() + ")" + DateUtil.todayTime() + " 缓存值:" + cacheMap.get(1));
        }

    }

}
