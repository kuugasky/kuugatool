package io.github.kuugasky.design.creational.factorysimple.gooddesign;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:57
 */
public class Main {

    /**
     * 简单工厂模式存在的问题
     * <p>
     * 当我们需要增加一种计算时，例如开平方。这个时候我们需要先定义一个类继承Operation类，其中实现平方的代码。<br>
     * 除此之外我们还要修改OperationFactory类的代码，增加一个case。这显然是违背开闭原则的。可想而知对于新产品的加入，工厂类是很被动的。
     * <p>
     * 我们举的例子是最简单的情况。而在实际应用中，很可能产品是一个多层次的树状结构。 简单工厂可能就不太适用了。
     */
    public static void main(String[] args) {
        Operation operationAdd = OperationFactory.createOperation("+");
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResult());
    }

}
