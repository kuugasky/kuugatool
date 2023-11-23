package io.github.kuugasky.design.structural.proxy.cglibproxy;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/7-06-07 13:04
 */
public class Main {

    public static void main(String[] args) {
        CatUnInterface cat = new CatUnInterface();
        cat.eat("猫粮");

        System.out.println("-------------------");
        // 接收对象只能是实例对象的接口，不能以实例对象接收，因为返回的是动态代理类，也是实现了接口
        CatUnInterface cglibProxy = (CatUnInterface) new CglibProxy(cat).getProxyInstance();
        cglibProxy.eat("喵粮");
    }

}
