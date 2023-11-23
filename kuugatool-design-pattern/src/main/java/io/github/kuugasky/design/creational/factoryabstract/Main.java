package io.github.kuugasky.design.creational.factoryabstract;

import io.github.kuugasky.design.creational.factoryabstract.factory.BusinessCarFactory;
import io.github.kuugasky.design.creational.factoryabstract.factory.SportCarFactory;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:43
 */
public class Main {

    public static void main(String[] args) {
        SportCarFactory sportCarFactory = new SportCarFactory();
        sportCarFactory.getBenzCar().gasUp();
        BusinessCarFactory businessCarFactory = new BusinessCarFactory();
        businessCarFactory.getTeslaCar().charge();
    }

}
