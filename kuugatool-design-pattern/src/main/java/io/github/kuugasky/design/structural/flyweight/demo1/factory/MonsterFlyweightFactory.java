package io.github.kuugasky.design.structural.flyweight.demo1.factory;

import io.github.kuugasky.design.structural.flyweight.demo1.product.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MonsterFlyweightFactory
 * <p>
 * 享元工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 18:00
 */
public class MonsterFlyweightFactory {

    /**
     * 享元模式+简单工厂模式
     * <p>
     * 在获取怪兽对象时，如果该对象已经存在，直接返回已有的对象，否则创建一个新的对象并将其存储到 HashMap 中。
     * <p>
     * 这样，我们就可以确保每种属性的怪兽只创建一次，从而实现共享。
     */
    private static final Map<String, Monster> MONSTERS = new ConcurrentHashMap<>();

    public static Monster getMonster(String key) {
        Monster monster = MONSTERS.get(key);
        if (monster == null) {
            switch (key) {
                case "red" -> monster = new RedMonster();
                case "blue" -> monster = new BlueMonster();
                case "square" -> monster = new SquareMonster();
                case "circle" -> monster = new CircleMonster();
            }
            MONSTERS.put(key, monster);
        }
        return monster;
    }

}
