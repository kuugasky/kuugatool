package io.github.kuugasky.design.creational.factoryabstract.factory;

import io.github.kuugasky.design.creational.factoryabstract.product.BenzBusinessCar;
import io.github.kuugasky.design.creational.factoryabstract.product.BenzCar;
import io.github.kuugasky.design.creational.factoryabstract.product.TeslaBusinessCar;
import io.github.kuugasky.design.creational.factoryabstract.product.TeslaCar;

/**
 * BusinessCarFactory
 * <p>
 * 具体工厂-商务车工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:42
 */
public class BusinessCarFactory implements CarFactory {
    public BenzCar getBenzCar() {
        return new BenzBusinessCar();
    }

    public TeslaCar getTeslaCar() {
        return new TeslaBusinessCar();
    }
}