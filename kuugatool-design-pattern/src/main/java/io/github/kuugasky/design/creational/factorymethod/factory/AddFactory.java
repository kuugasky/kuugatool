package io.github.kuugasky.design.creational.factorymethod.factory;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;
import io.github.kuugasky.design.creational.factorysimple.wrongdesign.OperationAdd;

/**
 * AddFactory
 * <p>
 * 加法工厂
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:08
 */
public class AddFactory implements IFactory {
    @Override
    public Operation createOption() {
        return new OperationAdd();
    }
}
