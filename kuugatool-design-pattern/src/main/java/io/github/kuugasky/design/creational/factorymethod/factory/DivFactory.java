package io.github.kuugasky.design.creational.factorymethod.factory;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;
import io.github.kuugasky.design.creational.factorysimple.wrongdesign.OperationDiv;

/**
 * DivFactory
 * <p>
 * 除法工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:24
 */
public class DivFactory implements IFactory {
    @Override
    public Operation createOption() {
        return new OperationDiv();
    }
}
