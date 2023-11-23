package io.github.kuugasky.crossprint.abc;

/**
 * PrintingWithYeild
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:44
 */
public class PrintingWithYeild {

    private static volatile boolean printNumber = true;

    public static void main(String[] args) {
        Thread numberThread = new Thread(new NumberPrinter());
        Thread letterThread = new Thread(new LetterPrinter());

        numberThread.start();
        letterThread.start();
    }

    static class NumberPrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                while (!printNumber) {
                    Thread.yield();
                }
                System.out.print(i);
                printNumber = false;
                Thread.yield();
            }
        }
    }

    static class LetterPrinter implements Runnable {
        @Override
        public void run() {
            for (char c = 'A'; c <= 'C'; c++) {
                while (printNumber) {
                    Thread.yield();
                }
                System.out.print(c);
                printNumber = true;
                Thread.yield();
            }
        }
    }

}
