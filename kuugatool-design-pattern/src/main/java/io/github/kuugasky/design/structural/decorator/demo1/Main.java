package io.github.kuugasky.design.structural.decorator.demo1;

import io.github.kuugasky.design.structural.decorator.demo1.decorator.EggDecorator;
import io.github.kuugasky.design.structural.decorator.demo1.decorator.SausageDecorator;
import io.github.kuugasky.design.structural.decorator.demo1.product.AbstractPancake;
import io.github.kuugasky.design.structural.decorator.demo1.product.Pancake;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/9-06-09 13:23
 */
public class Main {

    public static void main(String[] args) {
        // 创建一个煎饼类
        AbstractPancake abstractPancake = new Pancake();
        System.out.println(abstractPancake.getDesc() + ", 销售价格: " + abstractPancake.cost());

        EggDecorator eggDecorator = new EggDecorator(abstractPancake);
        System.out.println(eggDecorator.getDesc() + ", 销售价格: " + eggDecorator.cost());

        SausageDecorator sugarAdditives = new SausageDecorator(abstractPancake);
        System.out.println(sugarAdditives.getDesc() + ", 销售价格: " + sugarAdditives.cost());
    }

}
