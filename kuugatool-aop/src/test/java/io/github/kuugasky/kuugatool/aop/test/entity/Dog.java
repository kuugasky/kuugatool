package io.github.kuugasky.kuugatool.aop.test.entity;

import io.github.kuugasky.kuugatool.core.lang.Console;

/**
 * @author kuuga
 * @since 2022-05-17 12:58:26
 */
public class Dog implements Animal {

    public String eat() {
        return "狗吃肉";
    }

    public void seize() {
        Console.log("抓了只鸡");
    }

}
