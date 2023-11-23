package io.github.kuugasky.design.structural.decorator.demo1.decorator;

import io.github.kuugasky.design.structural.decorator.demo1.product.AbstractPancake;

/**
 * AbstractDecorator
 * <p>
 * 抽象装饰类<br>
 * - 聚合抽象产品类<br>
 * - 重写或复用抽象产品类抽象方法，以便具体装饰类能调用具体产品的方法，以达到具体装饰类的装饰作用。
 *
 * @author kuuga
 * @since 2023/6/9-06-09 12:43
 */
public abstract class AbstractDecorator extends AbstractPancake {
    /**
     * 抽象装饰类注入产品抽象类-煎饼抽象类，以获得产品实现类的方法
     */
    private final AbstractPancake abstractPancake;

    public AbstractDecorator(AbstractPancake abstractPancake) {
        this.abstractPancake = abstractPancake;
    }

    protected abstract void doSomething();

    @Override
    public String getDesc() {
        return this.abstractPancake.getDesc();
    }

    @Override
    public int cost() {
        return this.abstractPancake.cost();
    }

}
