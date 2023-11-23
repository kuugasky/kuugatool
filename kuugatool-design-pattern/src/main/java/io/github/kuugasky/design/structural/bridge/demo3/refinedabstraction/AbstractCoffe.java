package io.github.kuugasky.design.structural.bridge.demo3.refinedabstraction;

import io.github.kuugasky.design.structural.bridge.demo3.implementor.additives.IAdditives;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.capacity.ICapacity;
import io.github.kuugasky.design.structural.bridge.demo3.implementor.temperature.ITemperature;

/**
 * AbstractCoffe
 * <p>
 * 抽象化(Abstraction)角色：Coffe抽象类-AbstractCoffe类的定义
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:12
 */
public abstract class AbstractCoffe {

    // 重点：此处就是连接其它属性的关键，通过成员变量的方式聚合了其它属性

    ICapacity capacity;
    IAdditives additives;
    ITemperature temperature;

    // 此处通过有参构造的方式接受客户端想要的咖啡属性

    public AbstractCoffe(ICapacity capacity, IAdditives additives, ITemperature temperature) {
        this.capacity = capacity;
        this.additives = additives;
        this.temperature = temperature;
    }

    /**
     * 下单
     *
     * @param count 几杯
     */
    public abstract void order(int count);

}
