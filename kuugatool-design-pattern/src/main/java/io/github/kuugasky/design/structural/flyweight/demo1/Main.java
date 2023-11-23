package io.github.kuugasky.design.structural.flyweight.demo1;

import io.github.kuugasky.design.structural.flyweight.demo1.factory.MonsterFlyweightFactory;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/4-06-04 18:02
 */
public class Main {

    public static void main(String[] args) {
        MonsterFlyweightFactory.getMonster("red").draw();
        MonsterFlyweightFactory.getMonster("red").draw();
        MonsterFlyweightFactory.getMonster("blue").draw();
        MonsterFlyweightFactory.getMonster("square").draw();
        MonsterFlyweightFactory.getMonster("circle").draw();
    }

}
