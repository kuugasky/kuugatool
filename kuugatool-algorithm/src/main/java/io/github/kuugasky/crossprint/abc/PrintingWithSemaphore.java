package io.github.kuugasky.crossprint.abc;

import java.util.concurrent.Semaphore;

/**
 * PrintingWithSemaphore
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:44
 */
public class PrintingWithSemaphore {

    /**
     * 打印号码的信号量
     */
    private static final Semaphore SEMAPHORE_NUMBER = new Semaphore(1);
    /**
     * 打印字母的信号量
     */
    private static final Semaphore SEMAPHORE_LETTER = new Semaphore(0);

    public static void main(String[] args) {
        Thread numberThread = new Thread(new NumberPrinter());
        Thread letterThread = new Thread(new LetterPrinter());

        numberThread.start();
        letterThread.start();
    }

    static class NumberPrinter implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 1; i <= 3; i++) {
                    // 获取数字信号，打印1
                    SEMAPHORE_NUMBER.acquire();
                    System.out.print(i);
                    // 释放字母信号
                    SEMAPHORE_LETTER.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class LetterPrinter implements Runnable {
        @Override
        public void run() {
            try {
                for (char c = 'A'; c <= 'C'; c++) {
                    // 获取字母信号， 打印A
                    SEMAPHORE_LETTER.acquire();
                    System.out.print(c);
                    // 释放数字信号
                    SEMAPHORE_NUMBER.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}