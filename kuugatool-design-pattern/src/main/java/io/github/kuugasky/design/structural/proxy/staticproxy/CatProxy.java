package io.github.kuugasky.design.structural.proxy.staticproxy;

/**
 * CatProxy
 *
 * @author kuuga
 * @since 2023/6/7-06-07 12:52
 */
public class CatProxy implements ICat {

    private final Cat cat;

    public CatProxy(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void eat(String food) {
        System.out.println("静态代理前置任务");
        cat.eat(food);
        System.out.println("静态代理后置任务");
    }

}
