package io.github.kuugasky.design.structural.bridge.demo1;

import io.github.kuugasky.design.structural.bridge.demo1.draw.DrawApi;

/**
 * Shape
 * <p>
 * 使用 DrawAPI 接口创建抽象类 Shape-形状抽象类
 * <p>
 * 桥接类，组合了DrawApi，又提供了抽象方法draw给子类，使得Shape的子类可以调用DrawApi实现类的drawCircle方法。
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:25
 */
public abstract class Shape {

    protected DrawApi drawApi;

    protected Shape(DrawApi drawApi) {
        this.drawApi = drawApi;
    }

    /**
     * 绘画
     */
    public abstract void draw();

}
