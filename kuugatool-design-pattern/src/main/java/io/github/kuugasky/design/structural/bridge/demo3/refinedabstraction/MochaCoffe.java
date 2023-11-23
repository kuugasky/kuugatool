package io.github.kuugasky.design.structural.bridge.demo3.refinedabstraction;

import io.github.kuugasky.design.structural.bridge.demo3.implementor.additives.IAdditives;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.capacity.ICapacity;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.temperature.ITemperature;

/**
 * MochaCoffe
 * <p>
 * 修正抽象化(RefinedAbstraction)角色:口味实现类-MochaCoffe类定义
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:13
 */
public class MochaCoffe extends AbstractCoffe {

    public MochaCoffe(ICapacity capacity, IAdditives additives, ITemperature temperature) {
        super(capacity, additives, temperature);
    }

    public void order(int count) {
        System.out.println("开始制作摩卡咖啡：");
        capacity.setCupCapacity();
        additives.addAdditives();
        temperature.setTemperature();
        System.out.println(count + " 杯MochaCoffe制作完成！");
    }
}
