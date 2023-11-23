package io.github.kuugasky.design.structural.decorator.demo1.product;

/**
 * Pancake
 * <p>
 * 煎饼类
 *
 * @author kuuga
 * @since 2023/6/9-06-09 12:40
 */
public class Pancake extends AbstractPancake {
    @Override
    public String getDesc() {
        return "煎饼";
    }

    @Override
    public int cost() {
        return 8;
    }
}
