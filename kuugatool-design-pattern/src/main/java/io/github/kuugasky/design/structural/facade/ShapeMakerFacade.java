package io.github.kuugasky.design.structural.facade;

import io.github.kuugasky.design.structural.facade.product.Circle;
import io.github.kuugasky.design.structural.facade.product.Rectangle;
import io.github.kuugasky.design.structural.facade.product.Shape;
import io.github.kuugasky.design.structural.facade.product.Square;

/**
 * ShapeMakerFacade
 * <p>
 * 形状制造者外观类
 * <p>
 * 外观类-一个单一类，封装了所有需要对外提供的逻辑，让调用方简单调用，开发中的feign/api也是类似的概念，不同的是demo提供的是一个类，而feign/api是提供了一个接口。
 *
 * @author kuuga
 * @since 2023/6/9-06-09 14:09
 */
public class ShapeMakerFacade {
    private final Shape circle;
    private final Shape rectangle;
    private final Shape square;

    public ShapeMakerFacade() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawSquare() {
        square.draw();
    }
}
