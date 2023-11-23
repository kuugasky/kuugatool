package io.github.kuugasky.design.behavior.templatemethodpattern.demo1;

/**
 * Trip
 * <p>
 * 旅行抽象类-模版方法模式，定义通用算法逻辑
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:09
 */
public abstract class Trip {

    /**
     * 旅游通用步骤方法
     * - 1.检票
     * - 2.进园
     * - 3.具体玩了些啥
     * - 4.离园
     */
    public final void play() {
        checkTicket();
        enterPark();
        enjoy();
        exitPark();
    }

    public void checkTicket() {
        System.out.println("检票");
    }

    public void enterPark() {
        System.out.println("入园");
    }

    /**
     * 具体玩了些啥作为抽象方法交由子类实现
     */
    public abstract void enjoy();

    public void exitPark() {
        System.out.println("出园");
    }

}
