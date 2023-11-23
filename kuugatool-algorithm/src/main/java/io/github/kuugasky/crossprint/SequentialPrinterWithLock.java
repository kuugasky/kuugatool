package io.github.kuugasky.crossprint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SequentialPrinterWithLock
 * <p>
 * 交叉打印奇偶（Condition）
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:18
 */
public class SequentialPrinterWithLock {

    // 可重入锁
    private static final Lock LOCK = new ReentrantLock();
    // 偶数条件
    private static final Condition ODD_CONDITION = LOCK.newCondition();
    // 奇数条件
    private static final Condition EVEN_CONDITION = LOCK.newCondition();
    // 下标
    private static int number = 1;
    // 最大值
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
                // 必须是可重入锁，因为两个线程都要重入
                LOCK.lock();
                try {
                    if (number % 2 == 0) {
                        // 暂时挂起，等奇数打印
                        ODD_CONDITION.await();
                    }
                    // number是static，上面挂起后，等偶数打印后，收到信号，再继续输出，这时number就会是奇数
                    System.out.println(Thread.currentThread().getName() + "(奇数):" + number);
                    number++;
                    EVEN_CONDITION.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

    static class EvenPrinter implements Runnable {
        @Override
        public void run() {
            while (number < MAX_NUMBER) {
                LOCK.lock();
                try {
                    if (number % 2 != 0) {
                        EVEN_CONDITION.await();
                    }

                    System.out.println(Thread.currentThread().getName() + "(偶数):" + number);
                    number++;
                    ODD_CONDITION.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

}
