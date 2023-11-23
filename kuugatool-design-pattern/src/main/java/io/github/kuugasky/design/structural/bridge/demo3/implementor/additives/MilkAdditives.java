package io.github.kuugasky.design.structural.bridge.demo3.implementor.additives;

/**
 * MilkAdditives
 * <p>
 * Additives实现类-加奶
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:19
 */
public class MilkAdditives implements IAdditives {
    @Override
    public void addAdditives() {
        System.out.println("添加 MILK 成功！");
    }
}
