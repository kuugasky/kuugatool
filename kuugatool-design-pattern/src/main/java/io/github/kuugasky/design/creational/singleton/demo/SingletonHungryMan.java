package io.github.kuugasky.design.creational.singleton.demo;

/**
 * 单例模式-饿汉
 * <p>
 * 通过定义静态的成员变量，以保证instance可以在类初始化的时候被实例化。
 *
 * @author kuuga
 * @since 2023/6/3-06-03 14:55
 */
public class SingletonHungryMan {

    private static final SingletonHungryMan INSTANCE = new SingletonHungryMan();

    private SingletonHungryMan() {
    }

    public static SingletonHungryMan getInstance() {
        return INSTANCE;
    }

}

/**
 * 单例模式-饿汉另一种写法
 */
class SingletonHungryMan2 {

    private static final SingletonHungryMan2 INSTANCE;

    static {
        INSTANCE = new SingletonHungryMan2();
    }

    private SingletonHungryMan2() {
    }

    public static SingletonHungryMan2 getInstance() {
        return INSTANCE;
    }

}
