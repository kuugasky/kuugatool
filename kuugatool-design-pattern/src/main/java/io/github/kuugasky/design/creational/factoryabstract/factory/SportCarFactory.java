package io.github.kuugasky.design.creational.factoryabstract.factory;

import io.github.kuugasky.design.creational.factoryabstract.product.BenzCar;
import io.github.kuugasky.design.creational.factoryabstract.product.BenzSportCar;
import io.github.kuugasky.design.creational.factoryabstract.product.TeslaCar;
import io.github.kuugasky.design.creational.factoryabstract.product.TeslaSportCar;

/**
 * SportCarFactory
 * <p>
 * 具体工厂-跑车工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:41
 */
public class SportCarFactory implements CarFactory {

    public BenzCar getBenzCar() {
        return new BenzSportCar();
    }

    public TeslaCar getTeslaCar() {
        return new TeslaSportCar();
    }

}
