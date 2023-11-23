package io.github.kuugasky.design.apply.demo1;

import io.github.kuugasky.design.apply.demo1.iservice.PayService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PayServiceFactory
 * <p>
 * 工厂模式
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:25
 */
// @Service
public class PayServiceFactory {

    // @Autowired
    public Map<String, PayService> payServiceMap = new ConcurrentHashMap<>();

    /**
     * 策略模式（根据不同入参获取不同的支付服务）
     * <p>
     * - 不同service可以在启动时注入
     *
     * @param payChannel 支付渠道
     * @return 支付接口类
     */
    public PayService getPayService(String payChannel) {
        // alipay -> alipayPayService
        // wechat -> wechatPayService
        return payServiceMap.get(payChannel + "PayService");
    }

}