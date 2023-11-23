package io.github.kuugasky.design.creational.factoryabstract.product;

/**
 * TeslaSportCar
 * <p>
 * 具体产品-特斯拉跑车
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:38
 */
public class TeslaSportCar implements TeslaCar {
    public void charge() {
        System.out.println("给我特斯拉跑车冲满电");
    }
}
