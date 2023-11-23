package io.github.kuugasky.crossprint.abc;

/**
 * PrintingWithWaitNotify
 * <p>
 * 两个线程，一个打印123，一个打印ABC，交替输出1A2B3C
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:30
 */
public class PrintingWithWaitNotify {

    private static final Object LOCK = new Object();

    private static boolean printNumber = true;

    public static void main(String[] args) {
        Thread numberThread = new Thread(new NumberPrinter());
        Thread letterThread = new Thread(new LetterPrinter());
        numberThread.start();
        letterThread.start();
    }

    /**
     * 数字
     */
    static class NumberPrinter implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK) {
                for (int i = 1; i <= 3; i++) {
                    // 这里需要自旋for，因为要等另一个线程输出后才能继续往下执行
                    while (!printNumber) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(i);
                    printNumber = false;
                    LOCK.notify();
                }
            }
        }
    }

    /**
     * 字母
     */
    static class LetterPrinter implements Runnable {

        @Override
        public void run() {
            synchronized (LOCK) {
                for (char i = 'A'; i <= 'C'; i++) {
                    while (printNumber) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    System.out.println(i);
                    printNumber = true;
                    LOCK.notify();
                }
            }
        }
    }

}

