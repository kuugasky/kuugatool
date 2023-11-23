package io.github.kuugasky.design.creational.singleton.demo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * SingletonCAS
 * <p>
 * 用CAS的好处在于不需要使用传统的锁机制来保证线程安全。
 * <p>
 * 但是我们的实现方式中，用了一个for循环一直在进行重试，所以，这种方式有一个比较大的缺点在于，如果忙等待一直执行不成功(一直在死循环中)，会对CPU造成较大的执行开销。
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:33
 */
public class SingletonCAS {

    private static final AtomicReference<SingletonCAS> INSTANCE = new AtomicReference<>();

    private SingletonCAS() {
    }

    public static SingletonCAS getInstance() {
        for (; ; ) {
            SingletonCAS singleton = INSTANCE.get();
            if (null != singleton) {
                return singleton;
            }

            singleton = new SingletonCAS();
            if (INSTANCE.compareAndSet(null, singleton)) {
                return singleton;
            }
        }
    }

}
