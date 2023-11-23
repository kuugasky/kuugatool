package io.github.kuugasky.design.creational.factorysimple.gooddesign;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.*;

/**
 * OperationFactory
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:54
 */
public class OperationFactory {

    public static Operation createOperation(String operation) {
        return switch (operation) {
            case "+" -> new OperationAdd();
            case "-" -> new OperationSub();
            case "*" -> new OperationMul();
            case "/" -> new OperationDiv();
            default -> throw new UnsupportedOperationException("不支持该操作");
        };
    }

}
