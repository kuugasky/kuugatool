package io.github.kuugasky.design.structural.facade.product;

/**
 * Rectangle
 * <p>
 * 具体产品-圆形
 *
 * @author kuuga
 * @since 2023/6/9-06-09 14:08
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
