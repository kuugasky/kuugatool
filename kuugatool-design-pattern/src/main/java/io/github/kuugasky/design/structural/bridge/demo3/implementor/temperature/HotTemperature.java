package io.github.kuugasky.design.structural.bridge.demo3.implementor.temperature;

/**
 * HotTemperature
 * <p>
 * Temperature实现类-热
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:18
 */
public class HotTemperature implements ITemperature {
    @Override
    public void setTemperature() {
        System.out.println("加热温度至：Hot");
    }
}
