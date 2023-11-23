package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * ConfirmOrderHandler
 * <p>
 * 确认订单处理器
 *
 * @author kuuga
 * @since 2023/6/5-06-05 09:02
 */
public class ConfirmOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        // 确认订单，更新商品库存和用户余额
        order.confirm();
    }

    @Override
    public void setNext(OrderHandler nextHandler) {
    }

}