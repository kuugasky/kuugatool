package io.github.kuugasky.kuugatool.core.spi;

/**
 * SpiKuuga
 *
 * @author kuuga
 * @since 2023/6/19-06-19 10:55
 */
public class SpiKuuga extends ISpi {
    @Override
    protected void test() {
        System.out.println("Kuuga spi");
    }
}
