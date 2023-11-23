package io.github.kuugasky.design.structural.bridge.demo3.implementor.capacity;

/**
 * LargeCapacity
 * <p>
 * Capacity容量实现类-大杯
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:15
 */
public class LargeCapacity implements ICapacity {
    @Override
    public void setCupCapacity() {
        System.out.println("制作杯子尺寸：Large");
    }
}
