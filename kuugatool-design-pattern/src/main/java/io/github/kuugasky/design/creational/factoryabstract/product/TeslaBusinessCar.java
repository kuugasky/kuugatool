package io.github.kuugasky.design.creational.factoryabstract.product;

/**
 * TeslaBusinessCar
 * <p>
 * 具体产品-特斯拉商务车
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:39
 */
public class TeslaBusinessCar implements TeslaCar {
    public void charge() {
        System.out.println("不用给我特斯拉商务车冲满电");
    }
}