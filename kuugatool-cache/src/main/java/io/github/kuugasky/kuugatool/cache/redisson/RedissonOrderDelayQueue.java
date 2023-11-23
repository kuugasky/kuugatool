package io.github.kuugasky.kuugatool.cache.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * RedissonOrderDelayQueue
 *
 * @author kuuga
 * @since 2023/9/24-09-24 11:17
 */
public class RedissonOrderDelayQueue {

    private final RedissonClient redissonClient;

    RedissonOrderDelayQueue() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }


    public void addTaskToDelayQueue(String orderId) {
        // 获取阻塞队列
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("orderQueue");
        // 获取延迟队列
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "添加任务到延时队列里面");
        delayedQueue.offer(orderId, 3, TimeUnit.SECONDS);
        delayedQueue.offer(orderId, 6, TimeUnit.SECONDS);
        delayedQueue.offer(orderId, 9, TimeUnit.SECONDS);
    }


    public void getOrderFromDelayQueue(String orderId) throws InterruptedException {
        // 获取阻塞队列
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("orderQueue");
        // 获取延迟队列
        // RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        // 从阻塞队列获取元素
        // String take1 = blockingDeque.take();
        // System.out.println("阻塞队列元素：" + take1);
        String take = null;
        do {
            System.out.println("阻塞队列元素：" + blockingDeque.take());
        } while (true);

        // 从延迟队列获取元素
        // String poll = delayedQueue.poll();
        // System.out.println("延迟队列元素：" + poll);
    }

    public static void main(String[] args) throws InterruptedException {
        RedissonOrderDelayQueue redissonOrderDelayQueue = new RedissonOrderDelayQueue();
        redissonOrderDelayQueue.addTaskToDelayQueue("kuuga");
        redissonOrderDelayQueue.getOrderFromDelayQueue("kuuga");
    }

}
