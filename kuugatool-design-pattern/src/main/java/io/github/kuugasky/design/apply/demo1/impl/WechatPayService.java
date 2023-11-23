package io.github.kuugasky.design.apply.demo1.impl;

import io.github.kuugasky.design.apply.demo1.AbstractPayService;
import io.github.kuugasky.design.apply.demo1.PayRequest;

/**
 * WechatPayService
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:20
 */
// @Service
public class WechatPayService extends AbstractPayService {
    @Override
    public void doPay(PayRequest payRequest) {
        // 微信支付逻辑
    }
}
