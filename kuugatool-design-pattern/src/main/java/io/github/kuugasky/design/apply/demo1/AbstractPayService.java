package io.github.kuugasky.design.apply.demo1;


import io.github.kuugasky.design.apply.demo1.iservice.PayService;

/**
 * AbstractPayService【模板模式】
 * <p>
 * 所有支付渠道中公共的代码抽出来(如前置校验和后置处理)，定义一个抽象类
 * <p>
 * 这个抽象类中首先把pay方法给实现了，然后编排了几个其他的方法，这些公共的方法在抽象类中直接实现了，具体的支付核心实现，留给实现类去实现就行了。
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:06
 */
public abstract class AbstractPayService implements PayService {

    /**
     * 抽象类实现通用业务逻辑
     * - 支付前置检查
     * - 支付核心逻辑
     * - 支付后置处理
     *
     * @param payRequest 支付请求
     */
    @Override
    public void pay(PayRequest payRequest) {
        // 前置检查
        validateRequest(payRequest);
        // 支付核心逻辑
        doPay(payRequest);
        // 后置处理
        postPay(payRequest);
    }

    /**
     * 抽象支付核心逻辑接口，交由具体类实现
     *
     * @param payRequest 支付请求
     */
    public abstract void doPay(PayRequest payRequest);

    private void postPay(PayRequest payRequest) {
        // 支付成功的后置处理
    }

    public void validateRequest(PayRequest payRequest) {
        // 参数检查
    }

}
