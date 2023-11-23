package io.github.kuugasky.design.behavior.strategy.demo1.strategyimpl;

import io.github.kuugasky.design.behavior.chain.demo1.Order;

/**
 * BaseOrderProcessStrategy
 * <p>
 * 公共基础订单处理策略抽象类，模版方法模式封装共性处理逻辑
 *
 * @author kuuga
 * @since 2023/6/7-06-07 09:51
 */
public abstract class BaseOrderProcessStrategy implements OrderProcessStrategy {

    // 实现策略接口方法
    @Override
    public void process(Order order) {
        // 前置处理
        checkOrder(order);

        // 策略核心处理逻辑
        doProcess(order);

        // 后置处理
        // doLog(order);
    }

    // 策略基础抽象方法是用`模版方法模式`将逻辑共性进行封装，再利用抽象类特性提供抽象方法交由具体策略子类去实现
    public abstract void doProcess(Order order);

    public void checkOrder(Order order) {
        // 订单检查业务逻辑
    }

    public void doLog(Order order) {
        // 记录日志相关代码
    }
}