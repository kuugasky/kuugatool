package io.github.kuugasky.design.structural.bridge.demo3;

import io.github.kuugasky.design.structural.bridge.demo3.implementor.additives.SugarAdditives;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.capacity.MiddleCapacity;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.temperature.ColdTemperature;
import io.github.kuugasky.design.structural.bridge.demo3.refinedabstraction.AbstractCoffe;
import io.github.kuugasky.design.structural.bridge.demo3.refinedabstraction.MochaCoffe;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:20
 */
public class Main {
    public static void main(String[] args) {
        // 客户端传入想要的咖啡各个属性的具体对象
        AbstractCoffe mocaCoffe = new MochaCoffe(new MiddleCapacity(), new SugarAdditives(), new ColdTemperature());
        mocaCoffe.order(4);
    }
}
