package io.github.kuugasky.design.behavior.strategy.demo1.factory;

import io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl.GroupOrderProcessStrategy;
import io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl.NormalOrderProcessStrategy;
import io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl.OrderProcessStrategy;
import io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl.SeckillOrderProcessStrategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * OrderProcessStrategyFactory
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:53
 */
public final class OrderProcessStrategyFactory {
    /**
     * 单例-构造函数私有，不提供public构造函数
     */
    private OrderProcessStrategyFactory() {

    }

    /**
     * 单例-饿汉
     */
    private static final OrderProcessStrategyFactory INSTANCE = new OrderProcessStrategyFactory();
    /**
     * 静态常量ConcurrentHashMap，static初始化
     */
    private static final ConcurrentHashMap<String, OrderProcessStrategy> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    static {
        CONCURRENT_HASH_MAP.put("normalOrderProcessStrategy", new NormalOrderProcessStrategy());
        CONCURRENT_HASH_MAP.put("groupOrderProcessStrategy", new GroupOrderProcessStrategy());
        CONCURRENT_HASH_MAP.put("seckillOrderProcessStrategy", new SeckillOrderProcessStrategy());
    }

    /**
     * 返回策略工厂类的唯一实例
     *
     * @return IgoChessmanFactory
     */
    public static OrderProcessStrategyFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 根据类别获取对应的策略对象
     *
     * @param type type
     * @return OrderProcessStrategy
     */
    public OrderProcessStrategy getStrategy(String type) {
        return CONCURRENT_HASH_MAP.get(type + "OrderProcessStrategy");
    }

}
