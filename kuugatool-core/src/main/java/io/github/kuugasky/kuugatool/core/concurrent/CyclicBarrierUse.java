package io.github.kuugasky.kuugatool.core.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CyclicBarrierUse
 * <p>
 * CyclicBarrier,俗称栅栏锁,也叫循环屏障,作用是让一组线程到达某个屏障,被阻塞,一直到组内的最后一个线程到达,然后屏障开放,接着,所有的线程继续运行.<br>
 * - {@link CyclicBarrier#CyclicBarrier(int)} 创建一定数量的栅栏
 * - 调用{@link CyclicBarrier#await()} 表示该线程已到达栅栏处，等达到栅栏上限数量，则放开栅栏
 * - 注意：代码要确保必须能顺利执行累计到上限到栅栏数，不然会一直循环等待
 * <p>
 *
 * @author kuuga
 * @since 2023/4/27-04-27 09:22
 */
public class CyclicBarrierUse {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    @SuppressWarnings("unused")
    private CyclicBarrierUse() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private final CyclicBarrier cyclicBarrier;

    private CyclicBarrierUse(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    /**
     * 初始化CountDownLatch闭锁对象
     *
     * @param parties 线程数，各方必须调用{@link #await()}的线程数，才能释放屏障
     * @return 闭锁
     */
    public static CyclicBarrierUse build(int parties) {
        return new CyclicBarrierUse(new CyclicBarrier(parties));
    }

    /**
     * 初始化CountDownLatch闭锁对象
     *
     * @param parties       线程数，各方必须调用{@link #await()}的线程数，才能释放屏障
     * @param barrierAction 屏障行动
     * @return 闭锁
     */
    public static CyclicBarrierUse build(int parties, Runnable barrierAction) {
        return new CyclicBarrierUse(new CyclicBarrier(parties, barrierAction));
    }

    /**
     * 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     *
     * @throws BrokenBarrierException 破屏障异常
     * @throws InterruptedException   中断异常
     */
    public void await() throws BrokenBarrierException, InterruptedException {
        this.cyclicBarrier.await();
    }

    /**
     * 和await()类似，只不过等待一定的时间后count值还没变为0的话主线程就会继续执行
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 如果当前计数为零，则该方法立即返回值{@code true}。
     * @throws BrokenBarrierException 破屏障异常
     * @throws InterruptedException   中断异常
     * @throws TimeoutException       超时异常
     */
    public int await(long timeout, TimeUnit unit) throws BrokenBarrierException, InterruptedException, TimeoutException {
        return this.cyclicBarrier.await(timeout, unit);
    }

}
