package io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl;

import io.github.kuugasky.design.behavior.chain.demo1.Order;

/**
 * GroupOrderProcessStrategy
 * <p>
 * 团购订单处理策略
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:52
 */
public class GroupOrderProcessStrategy extends BaseOrderProcessStrategy {
    @Override
    public void doProcess(Order order) {
        // 团购订单处理逻辑
        System.out.println("团购订单，折扣为20%");
    }
}
