package io.github.kuugasky.design.structural.bridge.demo1;

import io.github.kuugasky.design.structural.bridge.demo1.draw.GreenCircle;
import io.github.kuugasky.design.structural.bridge.demo1.draw.RedCircle;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:27
 */
public class Main {

    public static void main(String[] args) {
        // 使用 Shape 和 DrawAPI 类画出不同颜色的圆。

        // 红色圆圈
        Shape redCircle = new Circle(100, 100, 10, new RedCircle());
        // 绿色圆圈
        Shape greenCircle = new Circle(100, 100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }

}
