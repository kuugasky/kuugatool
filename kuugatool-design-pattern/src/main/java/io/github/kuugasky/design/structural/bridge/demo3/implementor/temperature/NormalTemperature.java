package io.github.kuugasky.design.structural.bridge.demo3.implementor.temperature;

/**
 * NormalTemperature
 * <p>
 * Temperature实现类-常温
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:18
 */
public class NormalTemperature implements ITemperature {
    @Override
    public void setTemperature() {
        System.out.println("加热温度至：Normal");
    }
}
