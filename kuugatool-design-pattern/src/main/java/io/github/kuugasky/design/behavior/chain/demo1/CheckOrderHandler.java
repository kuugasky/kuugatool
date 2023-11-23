package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * CheckOrderHandler
 * <p>
 * 检查订单处理器
 *
 * @author kuuga
 * @since 2023/6/5-06-05 08:58
 */
public class CheckOrderHandler implements OrderHandler {

    private OrderHandler next;

    @Override
    public void handle(Order order) {
        // 检查订单信息是否完整
        if (order.isInfoComplete()) {
            // 如果订单信息完整，则将请求传递给下一个处理者
            next.handle(order);
        } else {
            // 如果订单信息不完整，则直接返回错误信息
            throw new RuntimeException("订单信息不完整");
        }
    }

    @Override
    public void setNext(OrderHandler nextHandler) {
        this.next = nextHandler;
    }

}
