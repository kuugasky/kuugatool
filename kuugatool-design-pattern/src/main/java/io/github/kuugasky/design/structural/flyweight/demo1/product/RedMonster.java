package io.github.kuugasky.design.structural.flyweight.demo1.product;

/**
 * RedMonster
 * <p>
 * 具体产品-红色怪兽
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:57
 */
public class RedMonster extends Monster {

    @Override
    public void draw() {
        String color = "red";
        System.out.println("Draw a " + color + " monster");
    }

}