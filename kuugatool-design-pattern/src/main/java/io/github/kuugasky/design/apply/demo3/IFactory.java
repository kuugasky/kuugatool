package io.github.kuugasky.design.apply.demo3;


/**
 * IFactory
 *
 * @author kuuga
 * @since 2023/10/13 10:50
 */
public interface IFactory {

    /**
     * @return 创建销售模式
     */
    ISale createSalesModel();

}
