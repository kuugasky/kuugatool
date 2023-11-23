package io.github.kuugasky.crossprint.abc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * PrintingWithReentrant
 * <p>
 * lock-condition
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:41
 */
public class PrintingWithReentrant {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread1 = new Thread(new PrintNumbers(lock, condition));
        Thread thread2 = new Thread(new PrintLetters(lock, condition));

        thread1.start();
        thread2.start();
    }
}

class PrintNumbers implements Runnable {
    /**
     * 对象锁
     */
    private final Lock lock;
    /**
     * 条件锁
     */
    private final Condition condition;

    public PrintNumbers(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            lock.lock();
            try {
                System.out.print(i);
                condition.signal(); // 唤醒打印字母的线程
                if (i < 3) {
                    condition.await(); // 等待打印字母的线程执行
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

class PrintLetters implements Runnable {
    private final Lock lock;
    private final Condition condition;

    public PrintLetters(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (char letter = 'A'; letter <= 'C'; letter++) {
            lock.lock();
            try {
                System.out.print(letter);
                condition.signal(); // 唤醒打印数字的线程
                if (letter < 'C') {
                    condition.await(); // 等待打印数字的线程执行
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

}
