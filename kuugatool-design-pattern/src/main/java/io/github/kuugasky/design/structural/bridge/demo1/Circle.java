package io.github.kuugasky.design.structural.bridge.demo1;

import io.github.kuugasky.design.structural.bridge.demo1.draw.DrawApi;

/**
 * Circle
 * <p>
 * 创建实现了 Shape 抽象类的实体类-圆形
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:26
 */
public class Circle extends Shape {
    private final int x;
    private final int y;
    private final int radius;

    public Circle(int x, int y, int radius, DrawApi drawApi) {
        super(drawApi);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw() {
        drawApi.drawCircle(radius, x, y);
    }
}
