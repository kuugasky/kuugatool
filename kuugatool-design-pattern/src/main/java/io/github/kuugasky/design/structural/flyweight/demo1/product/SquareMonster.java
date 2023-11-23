package io.github.kuugasky.design.structural.flyweight.demo1.product;

/**
 * RedMonster
 * <p>
 * 具体产品-方形怪兽
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:57
 */
public class SquareMonster extends Monster {

    @Override
    public void draw() {
        String shape = "square";
        System.out.println("Draw a " + shape + " monster");
    }

}