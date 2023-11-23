package io.github.kuugasky.kuugatool.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * RedissonWatchdogDemo
 *
 * @author kuuga
 * @since 2023/9/24-09-24 11:10
 */
public class RedissonWatchdogDemo {
    public static void main(String[] args) {
        // 创建Redisson配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        // 创建Redisson客户端
        RedissonClient redisson = Redisson.create(config);

        // 创建一个分布式锁
        RLock lock = redisson.getLock("myLock");

        // 获取锁，并设置锁的超时时间为10秒
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(5, TimeUnit.SECONDS);

            if (isLocked) {
                System.out.println("获取锁成功");

                // 模拟持有锁的操作
                for (int i = 0; i < 3; i++) {
                    System.out.println("锁状态:" + lock.isLocked());
                    TimeUnit.SECONDS.sleep(1);
                }

                // 如果在持有锁期间锁被释放，Watchdog会自动续期
                // 所以不需要手动续期
            } else {
                System.out.println("获取锁失败");
            }
        } catch (InterruptedException e) {
            // 这行代码用于中断当前线程。它的作用是将当前线程的中断状态设置为 true
            Thread.currentThread().interrupt();
        } finally {
            if (isLocked) {
                lock.unlock();
                System.out.println("释放锁");
            }
        }

        // 关闭Redisson客户端
        redisson.shutdown();
    }

}