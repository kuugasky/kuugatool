package io.github.kuugasky.design.apply.demo1.impl;

import io.github.kuugasky.design.apply.demo1.AbstractPayService;
import io.github.kuugasky.design.apply.demo1.PayRequest;

/**
 * AlipayPayService
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:19
 */
// @Service
public class AlipayPayService extends AbstractPayService {

    @Override
    public void doPay(PayRequest payRequest) {
        // 支付宝支付逻辑
    }

}
