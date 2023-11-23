package io.github.kuugasky.design.structural.proxy.staticproxy;

/**
 * CatImpl
 *
 * @author kuuga
 * @since 2023/6/7-06-07 12:51
 */
public class Cat implements ICat {
    @Override
    public void eat(String food) {
        System.out.println("猫吃" + food);
    }
}
