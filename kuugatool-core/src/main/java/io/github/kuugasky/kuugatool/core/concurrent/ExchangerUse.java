package io.github.kuugasky.kuugatool.core.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ExchangerUse
 * <p>
 * Exchanger,俗称交换器,用于在线程之间交换数据,但是比较受限,因为只能两个线程之间交换数据
 *
 * @author kuuga
 * @since 2023/5/22-05-22 13:19
 */
public class ExchangerUse<V> {


    private final Exchanger<V> exchanger;

    private ExchangerUse(Exchanger<V> exchanger) {
        this.exchanger = exchanger;
    }

    /**
     * SemaphoreUse
     *
     * @return SemaphoreUse
     */
    public static <V> ExchangerUse<V> build() {
        return new ExchangerUse<V>(new Exchanger<>());
    }

    /**
     * 等待另一个线程到达这个交换点（除非当前线程是{@linkplain Thread#interrupt interrupted}），然后将给定的对象转移到它，接收其对象作为回报。
     *
     * @param x 交换的对象
     * @throws InterruptedException 中断异常
     */
    public V exchange(V x) throws InterruptedException {
        return this.exchanger.exchange(x);
    }

    /**
     * 等待另一个线程到达这个交换点（除非当前线程是{@linkplain Thread#interrupt interrupted}），然后将给定的对象转移到它，接收其对象作为回报。
     *
     * @param x       交换的对象
     * @param timeout 超时时间
     * @param unit    超时时间单位
     * @throws InterruptedException 中断异常
     * @throws TimeoutException     超时异常
     */
    public V exchange(V x, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        return this.exchanger.exchange(x, timeout, unit);
    }

}
