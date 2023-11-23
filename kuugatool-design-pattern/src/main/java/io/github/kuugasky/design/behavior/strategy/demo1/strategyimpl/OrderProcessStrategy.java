package io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl;

import io.github.kuugasky.design.behavior.chain.demo1.Order;

/**
 * OrderProcessStrategy
 * <p>
 * 订单策略接口
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:44
 */
public interface OrderProcessStrategy {

    /**
     *
     */
    void process(Order order);

}
