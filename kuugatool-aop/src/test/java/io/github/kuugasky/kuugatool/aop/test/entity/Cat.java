package io.github.kuugasky.kuugatool.aop.test.entity;

import io.github.kuugasky.kuugatool.core.lang.Console;

/**
 * @author kuuga
 * @since 2022-05-17 14:12:39
 */
public class Cat implements Animal {

    @Override
    public String eat() {
        return "猫吃鱼";
    }

    @Override
    public void seize() {
        Console.log("抓了条鱼");
    }

}