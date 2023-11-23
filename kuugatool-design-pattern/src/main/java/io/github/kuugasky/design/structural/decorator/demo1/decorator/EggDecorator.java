package io.github.kuugasky.design.structural.decorator.demo1.decorator;

import io.github.kuugasky.design.structural.decorator.demo1.product.AbstractPancake;

/**
 * EggDecorator
 * <p>
 * 具体装饰类-鸡蛋装饰器
 *
 * @author kuuga
 * @since 2023/6/9-06-09 12:47
 */
public class EggDecorator extends AbstractDecorator {
    /**
     * 具体装饰类继承抽象装饰类，也聚合抽象产品类
     *
     * @param abstractPancake 抽象产品类
     */
    public EggDecorator(AbstractPancake abstractPancake) {
        super(abstractPancake);
    }

    @Override
    protected void doSomething() {

    }

    @Override
    public String getDesc() {
        return super.getDesc() + " 加一个鸡蛋";
    }

    @Override
    public int cost() {
        return super.cost() + 1;
    }

    public void egg() {
        System.out.println("增加了一个鸡蛋");
    }
}
