package io.github.kuugasky.design.structural.facade;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/9-06-09 14:11
 */
public class Main {

    public static void main(String[] args) {
        ShapeMakerFacade shapeMaker = new ShapeMakerFacade();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }

}
