package io.github.kuugasky.kuugatool.cache.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * RedissonTool
 *
 * @author kuuga
 * @since 2023/9/24-09-24 09:41
 */
public class RedissonTool {

    // @Configuration
    // public class RedissonConfig {
    //     @Bean(destroyMethod="shutdown")
    //     public RedissonClient redisson() throws IOException {
    //         Config config = new Config();
    //         config.useSingleServer().setAddress("redis://127.0.0.1:6379");
    //         RedissonClient redisson = Redisson.create(config);
    //         return redisson;
    //     }
    // }

    private final RedissonClient redissonClient;

    RedissonTool() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }

    /**
     * 可重入锁-加锁
     *
     * @return 加锁状态
     */
    public boolean testLock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            // 设置锁的超时时间为30秒
            lock.lock(30, TimeUnit.SECONDS);
            // 执行需要加锁的代码
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取公平锁
     */
    public boolean getFairLock(String key) {
        RLock fairLock = redissonClient.getFairLock(key);
        try {
            return fairLock.tryLock(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        } finally {
            fairLock.unlock();
        }
    }

    /**
     * 联锁
     */
    public boolean joinLock(RLock... rLocks) {
        RedissonMultiLock lock = new RedissonMultiLock(rLocks);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功。
        try {
            lock.lock();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读写锁
     */
    public boolean getReadWriteLock() {
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("anyRWLock");
        // 最常见的使用方法
        rwLock.readLock().lock();
        // 或
        rwLock.writeLock().lock();
        return true;
    }

    /**
     * redLock
     */
    public void redLockTest() throws InterruptedException {
        // 配置第一个单节点redis
        Config config1 = new Config();
        config1.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 配置第二个单节点redis
        Config config2 = new Config();
        config2.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 配置第三个单节点redis
        Config config3 = new Config();
        config3.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 创建三个节点的客户端
        RedissonClient redissonClient1 = Redisson.create(config1);
        RedissonClient redissonClient2 = Redisson.create(config2);
        RedissonClient redissonClient3 = Redisson.create(config3);

        RLock lock1 = redissonClient1.getLock("lockKey");
        RLock lock2 = redissonClient2.getLock("lockKey");
        RLock lock3 = redissonClient3.getLock("lockKey");

        // 构建红锁（有点想联锁，但是不太一样，联锁是必须都成功，红锁是大部份成功则算成功）
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        boolean lockResult = redLock.tryLock(1, 1, TimeUnit.MILLISECONDS);

        // 业务逻辑

        if (lockResult) {
            try {
                System.out.println("redLock lock success");
                // 业务逻辑
            } finally {
                redLock.unlock();
                System.out.println("redLock unlock success");
            }
        } else {
            // 获取锁失败的处理逻辑
            System.out.println("redLock lock fail");
        }

        redissonClient1.shutdown();
        redissonClient2.shutdown();
        redissonClient3.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        RedissonTool redissonTool = new RedissonTool();
        boolean b = redissonTool.testLock("myLock");
        System.out.println(b);

        System.out.println(redissonTool.getFairLock("anyLock"));

        redissonTool.redLockTest();
    }

}
