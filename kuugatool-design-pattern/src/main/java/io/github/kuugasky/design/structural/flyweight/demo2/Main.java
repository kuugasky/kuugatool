package io.github.kuugasky.design.structural.flyweight.demo2;

import io.github.kuugasky.design.structural.flyweight.demo2.product.GoChessPiece;
import io.github.kuugasky.design.structural.flyweight.demo2.state.Coordinates;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/6-06-06 09:34
 */
public class Main {

    public static void main(String[] args) {
        test1();
        System.out.println("------------------------------------");
        test2();
    }

    public static void test1() {
        GoChessPiece black1, black2, black3, white1, white2;
        GoChessPieceFlyweightFactory instance = GoChessPieceFlyweightFactory.getInstance();
        // 通过享元工厂获取三颗黑子
        black1 = instance.getGoChessPiece("黑");
        black2 = instance.getGoChessPiece("黑");
        black3 = instance.getGoChessPiece("黑");
        System.out.println("判断两颗黑子是否相同：" + (black1 == black2));
        // 通过享元工厂获取两颗白子
        white1 = instance.getGoChessPiece("白");
        white2 = instance.getGoChessPiece("白");
        System.out.println("判断两颗白子是否相同：" + (white1 == white2));
        // 显示棋子
        // 显示棋子，同时设置棋子的坐标位置
        black1.display(new Coordinates(1, 2));
        black2.display(new Coordinates(3, 4));
        black3.display(new Coordinates(1, 3));
        white1.display(new Coordinates(2, 5));
        white2.display(new Coordinates(2, 4));
    }

    public static void test2() {
        GoChessPieceFlyweightFactory instance = GoChessPieceFlyweightFactory.getInstance();
        GoChessPiece chess1 = instance.getGoChessPiece("黑");
        GoChessPiece chess2 = instance.getGoChessPiece("白");
        System.out.println("棋子内部状态");
        System.out.println(chess1.getColor());
        System.out.println(chess2.getColor());

        System.out.println("增加外部状态");
        chess1.display(new Coordinates(10, 10));
        chess2.display(new Coordinates(20, 20));
        chess1.display(new Coordinates(10, 20));
    }

}
