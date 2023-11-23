package io.github.kuugasky.design.structural.bridge.demo1.draw;

/**
 * RedCircle
 * <p>
 * 实现了 DrawAPI 接口的实体桥接实现类-红色圆圈-绿色圆圈
 *
 * @author kuuga
 * @since 2023/6/8-06-08 19:24
 */
public class GreenCircle implements DrawApi {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: " + radius + ", x: " + x + ", " + y + "]");
    }
}
