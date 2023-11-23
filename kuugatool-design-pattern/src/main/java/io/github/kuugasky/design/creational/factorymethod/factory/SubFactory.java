package io.github.kuugasky.design.creational.factorymethod.factory;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;
import io.github.kuugasky.design.creational.factorysimple.wrongdesign.OperationSub;

/**
 * SubFactory
 * <p>
 * 减法工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:25
 */
public class SubFactory implements IFactory {
    @Override
    public Operation createOption() {
        return new OperationSub();
    }
}
