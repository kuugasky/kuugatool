package io.github.kuugasky.kuugatool.core.jdk17;

import org.junit.jupiter.api.Test;

/**
 * NullPointer
 *
 * @author kuuga
 * @since 2022/8/8 14:26
 */
public class NullPointer {

    /**
     * JDK14:JEP 358: 友好的空指针异常
     */
    @Test
    void nullPoint() {
        People p = new People();
        p.address.go();
    }

}
