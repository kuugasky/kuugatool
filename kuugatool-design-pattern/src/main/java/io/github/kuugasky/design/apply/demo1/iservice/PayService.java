package io.github.kuugasky.design.apply.demo1.iservice;

import io.github.kuugasky.design.apply.demo1.PayRequest;

/**
 * PayService
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:00
 */
public interface PayService {

    /**
     * 定义支付接口
     *
     * @param payRequest 支付请求
     */
    void pay(PayRequest payRequest);

}
