package io.github.kuugasky.design.creational.factoryabstract.factory;

import io.github.kuugasky.design.creational.factoryabstract.product.BenzCar;
import io.github.kuugasky.design.creational.factoryabstract.product.TeslaCar;

/**
 * CarFactory
 * <p>
 * 抽象工厂-汽车工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:40
 */
public interface CarFactory {

    /**
     * @return 获取抽象产品-奔驰车
     */
    BenzCar getBenzCar();

    /**
     * @return 获取抽象产品-特斯拉车
     */
    TeslaCar getTeslaCar();

}
