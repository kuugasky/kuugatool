package io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl;

import io.github.kuugasky.design.behavior.chain.demo1.Order;

/**
 * NormalOrderProcessStrategy
 * <p>
 * 普通订单处理策略
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:51
 */
public class NormalOrderProcessStrategy extends BaseOrderProcessStrategy {
    @Override
    public void doProcess(Order order) {
        // 普通订单处理逻辑
        System.out.println("普通订单，折扣为0%");
    }
}
