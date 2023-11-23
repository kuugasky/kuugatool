package io.github.kuugasky.crossprint;

import java.util.concurrent.Semaphore;

/**
 * SequentialPrinterWithSemaphore
 * <p>
 * 交叉打印奇偶（Semaphore信号量）
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:25
 */
public class SequentialPrinterWithSemaphore {

    // 奇数信号量
    private static final Semaphore ODD_SEMAPHORE = new Semaphore(1);
    // 偶数信号量
    private static final Semaphore EVEN_SEMAPHORE = new Semaphore(0);
    private static int number = 1;
    private static final int MAX_NUMBER = 10;

    public static void main(String[] args) {
        Thread oddThread = new Thread(new OddPrinter());
        Thread evenThread = new Thread(new EvenPrinter());

        oddThread.start();
        evenThread.start();
    }

    static class OddPrinter implements Runnable {
        @Override
        public void run() {
            while (number < MAX_NUMBER) {
                try {
                    // 奇数信号量获取锁
                    ODD_SEMAPHORE.acquire();
                    if (number % 2 == 0) {
                        // 当前是偶数，奇数信号量锁释放
                        ODD_SEMAPHORE.release();
                    } else {
                        // 打印奇数
                        System.out.println(Thread.currentThread().getName() + "(奇数):" + number);
                        // 自增变偶数
                        number++;
                        // 偶数信号量锁释放
                        EVEN_SEMAPHORE.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class EvenPrinter implements Runnable {
        @Override
        public void run() {
            while (number < MAX_NUMBER) {
                try {
                    // 奇数偶号量获取锁
                    EVEN_SEMAPHORE.acquire();
                    if (number % 2 == 1) {
                        // 当前是奇数，偶数信号量锁释放
                        EVEN_SEMAPHORE.release();
                    } else {
                        // 打印偶数
                        System.out.println(Thread.currentThread().getName() + "(偶数):" + number);
                        // 自增变奇数
                        number++;
                        // 奇数信号量锁释放
                        ODD_SEMAPHORE.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}