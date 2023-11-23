package io.github.kuugasky.design.structural.proxy.staticproxy;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/7-06-07 12:53
 */
public class Main {

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat("猫粮");

        System.out.println("-------------------");

        ICat catProxy = new CatProxy(cat);
        catProxy.eat("喵粮");
    }

}
