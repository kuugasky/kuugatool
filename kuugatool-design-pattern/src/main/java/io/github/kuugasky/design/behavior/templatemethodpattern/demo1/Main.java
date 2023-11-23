package io.github.kuugasky.design.behavior.templatemethodpattern.demo1;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:15
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("海洋公馆旅游");
        Trip oceanarium = new Oceanarium();
        oceanarium.play();

        System.out.println("------------------------");

        System.out.println("动物园旅游");
        Trip zoo = new Zoo();
        zoo.play();
    }

}
