package io.github.kuugasky.design.behavior.chain.demo1;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/5-06-05 09:03
 */
public class Main {

    /**
     * 在责任链模式中，通常将处理请求的对象称为处理器或者链的节点，每个节点都包含了处理该请求的逻辑以及指向下一个节点的引用。<br>
     * 当请求到达一个节点时，如果该节点无法处理该请求，它会将请求转发给下一个节点，直到有一个节点处理该请求或者整个链都无法处理该请求。
     * <p>
     * 责任链模式在实际开发中有很多应用场景，比如：<br>
     * 1. 过滤器链：在Web开发中，可以通过责任链模式来实现过滤器链，例如Spring框架中的FilterChain就是一条责任链，每个过滤器都有机会对请求进行处理，直到最后一个过滤器处理完毕。<br>
     * 2. 日志记录器：在日志系统中，可以使用责任链模式来将日志记录器组成一条链，从而实现多种日志记录方式的灵活组合。<br>
     * 3. 异常处理器：在应用程序中，可以使用责任链模式来实现异常处理器的链式调用，从而灵活地处理各种异常情况。<br>
     * 4. 授权认证：在系统中，可以使用责任链模式来实现授权认证的链式调用，从而灵活地控制不同用户对系统的访问权限。
     */
    public static void main(String[] args) {
        // 客户端代码示例
        CheckOrderHandler checkOrderHandler = new CheckOrderHandler();
        CheckStockHandler checkStockHandler = new CheckStockHandler();
        CheckBalanceHandler checkBalanceHandler = new CheckBalanceHandler();
        ConfirmOrderHandler confirmOrderHandler = new ConfirmOrderHandler();

        // 将处理器按照一定顺序组成责任链

        // 2.检查库存
        checkOrderHandler.setNext(checkStockHandler);
        // 3.检查余额
        checkStockHandler.setNext(checkBalanceHandler);
        // 4.提交订单
        checkBalanceHandler.setNext(confirmOrderHandler);

        // 处理订单
        Order order = new Order();
        order.setInfoComplete(true);
        order.setAmount(1);
        order.setBalance(1);

        // 1.检查订单完整性
        checkOrderHandler.handle(order);
    }

}
