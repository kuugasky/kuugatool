package io.github.kuugasky.kuugatool.core.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatchUse
 * <p>
 * CountDownLatch,俗称闭锁,作用是类似加强版的 Join,是让一组线程等待其他的线程完成工作以后才执行。<br>
 * 通过一个计数器实现，设置计数器的初始值。每当countDownLatch.countDown，计数器的值就-1，当计数器的值为0时，闭锁上等待的线程就可以恢复工作。
 * <p>
 * 场景：需要等待多个线程执行任务后再汇总结果的场景中，可以使用CountDownLatch。
 *
 * @author kuuga
 * @since 2021/7/14
 */
public class CountDownLatchUse {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    @SuppressWarnings("unused")
    private CountDownLatchUse() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private final CountDownLatch countDownLatch;

    private CountDownLatchUse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    /**
     * 初始化CountDownLatch闭锁对象
     *
     * @param count 计数值
     * @return 闭锁
     */
    public static CountDownLatchUse build(int count) {
        return new CountDownLatchUse(new CountDownLatch(count));
    }

    /**
     * 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     *
     * @throws InterruptedException 中断异常
     */
    public void await() throws InterruptedException {
        this.countDownLatch.await();
    }

    /**
     * 和await()类似，只不过等待一定的时间后count值还没变为0的话主线程就会继续执行
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 如果当前计数为零，则该方法立即返回值{@code true}。
     * @throws InterruptedException 中断异常
     */
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return this.countDownLatch.await(timeout, unit);
    }

    /**
     * 将count值减1
     */
    public void countDown() {
        this.countDownLatch.countDown();
    }

    /**
     * 返回当前计数
     */
    public long getCount() {
        return this.countDownLatch.getCount();
    }

}
