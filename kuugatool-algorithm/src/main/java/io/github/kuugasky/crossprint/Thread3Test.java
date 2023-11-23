package io.github.kuugasky.crossprint;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread3Test
 * <p>
 * 使用了 wait() 和 notify() 方法来实现线程之间的通信和协调。
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:06
 */
public class Thread3Test {

    private static final Object LOCK = new Object();
    private static int number = 1;

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(10);


        Thread oddPrinter = new Thread(() -> {
            synchronized (LOCK) {
                while ((number <= i.get())) {
                    if (number % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + "(奇数):" + number);
                        number++;
                        LOCK.notify();
                    } else {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Thread evenPrinter = new Thread(() -> {
            synchronized (LOCK) {
                while ((number <= i.get())) {
                    if (number % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + "(偶数):" + number);
                        number++;
                        LOCK.notify();
                    } else {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        // 打印奇数
        oddPrinter.start();
        // 打印偶数
        evenPrinter.start();
    }

}
