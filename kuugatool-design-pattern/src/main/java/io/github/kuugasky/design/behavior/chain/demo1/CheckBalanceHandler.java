package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * CheckBalanceHandler
 * <p>
 * 检查余额处理器
 *
 * @author kuuga
 * @since 2023/6/5-06-05 09:01
 */
public class CheckBalanceHandler implements OrderHandler {

    private OrderHandler next;

    @Override
    public void handle(Order order) {
        // 检查用户余额是否充足
        if (order.getBalance() >= order.getAmount()) {
            // 如果余额充足，则将请求传递给下一个处理者
            next.handle(order);
        } else {
            // 如果余额不足，则直接返回错误信息
            throw new RuntimeException("用户余额不足");
        }
    }

    @Override
    public void setNext(OrderHandler nextHandler) {
        this.next = nextHandler;
    }

}
