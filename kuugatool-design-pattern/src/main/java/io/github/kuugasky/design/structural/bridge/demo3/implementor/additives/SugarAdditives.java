package io.github.kuugasky.design.structural.bridge.demo3.implementor.additives;

/**
 * SugarAdditives
 * <p>
 * Additives实现类-加糖
 *
 * @author kuuga
 * @since 2023/6/9-06-09 09:19
 */
public class SugarAdditives implements IAdditives {


    @Override
    public void addAdditives() {
        System.out.println("添加 Sugar 成功！");
    }
}
