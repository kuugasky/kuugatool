package io.github.kuugasky.design.behavior.strategy.demo1;

import io.github.kuugasky.design.behavior.chain.demo1.Order;
import io.github.kuugasky.design.behavior.strategy.demo1.factory.OrderProcessStrategyFactory;
import io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl.OrderProcessStrategy;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:57
 */
public class Main {

    public static void main(String[] args) {
        Order order = new Order();
        // 选择处理策略
        OrderProcessStrategyFactory instance = OrderProcessStrategyFactory.getInstance();
        OrderProcessStrategy normalStrategy = instance.getStrategy("normal");
        // 执行处理流程
        normalStrategy.process(order);
        OrderProcessStrategy groupStrategy = instance.getStrategy("group");
        // 执行处理流程
        groupStrategy.process(order);
        OrderProcessStrategy seckillStrategy = instance.getStrategy("seckill");
        // 执行处理流程
        seckillStrategy.process(order);
    }

}
