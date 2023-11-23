package io.github.kuugasky.kuugatool.core.concurrent;

import java.util.concurrent.Semaphore;

/**
 * SemaphoreUse
 * <p>
 * Semaphore,俗称信号量,作用于控制同时访问某个特定资源的线程数量,用在流量控制
 *
 * @author kuuga
 * @since 2023/5/22-05-22 13:07
 */
public class SemaphoreUse {

    private final Semaphore semaphore;

    private SemaphoreUse(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    /**
     * SemaphoreUse
     *
     * @param permits 许可证数量（默认非公平锁）
     * @return SemaphoreUse
     */
    public static SemaphoreUse build(int permits) {
        return new SemaphoreUse(new Semaphore(permits));
    }

    /**
     * SemaphoreUse
     *
     * @param permits 许可证数量
     * @param fair    是否公平锁
     *                fair ? new FairSync(permits) : new NonfairSync(permits);
     * @return SemaphoreUse
     */
    public static SemaphoreUse build(int permits, boolean fair) {
        return new SemaphoreUse(new Semaphore(permits, fair));
    }

    /**
     * 尝试获取许可
     *
     * @throws InterruptedException 中断异常
     */
    public void acquire() throws InterruptedException {
        this.semaphore.acquire();
    }

    /**
     * 释放许可
     */
    public void release() {
        this.semaphore.release();
    }

}
