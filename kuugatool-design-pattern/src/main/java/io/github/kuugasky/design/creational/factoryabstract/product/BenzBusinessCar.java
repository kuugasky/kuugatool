package io.github.kuugasky.design.creational.factoryabstract.product;

/**
 * BenzBusinessCar
 * <p>
 * 具体产品-奔驰商务车
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:37
 */
public class BenzBusinessCar implements BenzCar {
    public void gasUp() {
        System.out.println("给我的奔驰商务车加一般的汽油");
    }
}
