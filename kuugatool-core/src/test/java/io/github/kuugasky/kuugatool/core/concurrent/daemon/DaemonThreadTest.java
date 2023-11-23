package io.github.kuugasky.kuugatool.core.concurrent.daemon;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class DaemonThreadTest {

    @Test
    void testAwait() {
        DaemonThread.await();
    }

    @Test
    void testTestAwait() {
        DaemonThread.await(3, TimeUnit.SECONDS);
    }
}