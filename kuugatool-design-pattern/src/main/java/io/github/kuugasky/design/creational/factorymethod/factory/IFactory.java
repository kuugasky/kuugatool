package io.github.kuugasky.design.creational.factorymethod.factory;

import io.github.kuugasky.design.creational.factorysimple.wrongdesign.Operation;

/**
 * IFactory
 * <p>
 * 工厂接口
 *
 * @author kuuga
 * @since 2023/6/4-06-04 17:08
 */
public interface IFactory {

    /**
     * @return 获取抽象产品
     */
    Operation createOption();

}
