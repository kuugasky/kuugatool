package io.github.kuugasky.design.structural.facade.product;

/**
 * Rectangle
 * <p>
 * 具体产品-正方形
 *
 * @author kuuga
 * @since 2023/6/9-06-09 14:08
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}
