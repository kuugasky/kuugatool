package io.github.kuugasky.kuugatool.core.concurrent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

public class ExchangerUseTest {

    @Test
    void test() {
        ExchangerUse<CustomBook> exchanger = ExchangerUse.build();
        // Starting two threads
        Thread threadOfExchangerOne = new Thread(new ExchangerOne(exchanger));
        threadOfExchangerOne.setName("ExchangerOne");
        threadOfExchangerOne.start();

        Thread threadOfExchangerTwo = new Thread(new ExchangerTwo(exchanger));
        threadOfExchangerTwo.setName("ExchangerTwo");
        threadOfExchangerTwo.start();
    }

    @Data
    static class CustomBook {
        private String name;
    }

    @Slf4j
    static class ExchangerOne implements Runnable {

        private final ExchangerUse<CustomBook> ex;

        private ExchangerOne(ExchangerUse<CustomBook> ex) {
            this.ex = ex;
        }

        @Override
        public void run() {
            CustomBook customBook = new CustomBook();
            customBook.setName("book one");

            try {
                CustomBook exchangeCustomBook = ex.exchange(customBook);
                log.info(Thread.currentThread().getName() + "-->" + exchangeCustomBook.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Slf4j
    static class ExchangerTwo implements Runnable {

        private final ExchangerUse<CustomBook> ex;

        private ExchangerTwo(ExchangerUse<CustomBook> ex) {
            this.ex = ex;
        }

        @Override
        public void run() {
            CustomBook customBook = new CustomBook();
            customBook.setName("book two");

            try {
                CustomBook exchangeCustomBook = ex.exchange(customBook);
                log.info(Thread.currentThread().getName() + "-->" + exchangeCustomBook.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}