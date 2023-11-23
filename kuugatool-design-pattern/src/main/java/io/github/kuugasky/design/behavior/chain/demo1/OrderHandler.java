package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * OrderHandler
 * <p>
 * 订单处理接口
 *
 * @author kuuga
 * @since 2023/6/5-06-05 08:56
 */
public interface OrderHandler {

    void handle(Order order);

    void setNext(OrderHandler nextHandler);
}
