package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * CheckStockHandler
 * <p>
 * 检查库存处理器
 *
 * @author kuuga
 * @since 2023/6/5-06-05 09:00
 */
public class CheckStockHandler implements OrderHandler {

    private OrderHandler next;

    @Override
    public void handle(Order order) {
        // 检查商品库存是否充足
        if (order.getStock() >= order.getQuantity()) {
            // 如果库存充足，则将请求传递给下一个处理者
            next.handle(order);
        } else {
            // 如果库存不足，则直接返回错误信息
            throw new RuntimeException("商品库存不足");
        }
    }

    @Override
    public void setNext(OrderHandler nextHandler) {
        this.next = nextHandler;
    }

}
