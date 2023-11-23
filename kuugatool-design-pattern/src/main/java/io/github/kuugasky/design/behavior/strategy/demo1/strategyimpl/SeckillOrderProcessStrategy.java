package io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl;

import io.github.kuugasky.design.behavior.chain.demo1.Order;

/**
 * SeckillOrderProcessStrategy
 * <p>
 * 秒杀订单处理策略
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:53
 */
public class SeckillOrderProcessStrategy extends BaseOrderProcessStrategy {
    @Override
    public void doProcess(Order order) {
        // 秒杀订单处理逻辑
        System.out.println("秒杀订单，折扣为50%");
    }
}