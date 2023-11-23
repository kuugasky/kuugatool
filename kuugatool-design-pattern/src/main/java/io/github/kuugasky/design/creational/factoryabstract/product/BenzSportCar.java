package io.github.kuugasky.design.creational.factoryabstract.product;

/**
 * BenzSportCar
 * <p>
 * 具体产品-奔驰跑车
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:37
 */
public class BenzSportCar implements BenzCar {
    public void gasUp() {
        System.out.println("给我的奔驰跑车加最好的汽油");
    }
}
