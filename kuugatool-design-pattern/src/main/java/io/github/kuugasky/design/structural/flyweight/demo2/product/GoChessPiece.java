package io.github.kuugasky.design.structural.flyweight.demo2.product;

import io.github.kuugasky.design.structural.flyweight.demo2.state.Coordinates;

/**
 * IgoChessman
 * <p>
 * 享元抽象类-围棋棋子
 *
 * @author kuuga
 * @since 2023/6/6-06-06 09:30
 */
public abstract class GoChessPiece {

    /**
     * 获取内部状态
     *
     * @return 内部状态
     */
    public abstract String getColor();

    /**
     * @param coordinates 传入外部状态
     */
    public void display(Coordinates coordinates) {
        System.out.println("棋子颜色：" + this.getColor() + ",棋子位置：(" + coordinates.getX() + "," + coordinates.getY() + ")");
    }

}
