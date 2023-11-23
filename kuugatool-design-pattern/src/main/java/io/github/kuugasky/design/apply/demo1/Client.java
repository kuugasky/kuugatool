package io.github.kuugasky.design.apply.demo1;

/**
 * Client
 *
 * @author kuuga
 * @since 2023/6/3-06-03 13:28
 */
public class Client {

    // @Autowired
    private PayServiceFactory payServiceFactory;

    public void pay(PayRequest payRequest) {
        String payChannel = payRequest.getPayChannel();
        payServiceFactory.getPayService(payChannel).pay(payRequest);
    }

}
