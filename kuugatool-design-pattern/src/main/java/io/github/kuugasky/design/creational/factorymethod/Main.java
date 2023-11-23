package io.github.kuugasky.design.creational.factorymethod;

import io.github.kuugasky.design.creational.factorymethod.factory.AddFactory;
import io.github.kuugasky.design.creational.factorymethod.factory.IFactory;
import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:26
 */
public class Main {

    /**
     * 从代码量上看，这种工厂方法模式比简单工厂方法模式更加复杂。针对不同的操作（Operation）类都有对应的工厂。很多人会有以下疑问：
     * <p>
     * 貌似工厂方法模式比简单工厂模式要复杂的多？
     * <p>
     * 工厂方法模式和我自己创建对象没什么区别？为什么要多搞出一些工厂来？
     */
    public static void main(String[] args) {
        // 创建加法工厂
        IFactory factory = new AddFactory();
        // 用加法工厂创建加法对象
        Operation operationAdd = factory.createOption();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResult());
    }

}
