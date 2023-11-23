package io.github.kuugasky.design.behavior.templatemethodpattern.demo1;

/**
 * Oceanarium
 * <p>
 * 海洋公馆 -模版方法抽象类的具体子类
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:12
 */
public class Oceanarium extends Trip {
    @Override
    public void enjoy() {
        System.out.println("观看兔子睡觉");
        System.out.println("观看鸭子戏水");
        System.out.println("玩蹦蹦床");
    }
}
