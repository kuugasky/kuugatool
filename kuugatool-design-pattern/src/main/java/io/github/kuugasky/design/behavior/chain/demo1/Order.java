package io.github.kuugasky.design.behavior.chain.demo1;

import lombok.Data;

/**
 * Order
 *
 * @author kuuga
 * @since 2023/6/5-06-05 08:59
 */
@Data
public class Order {

    /**
     * 订单字段是否完整
     */
    private boolean infoComplete;

    /**
     * 货品库存数量
     */
    private int stock;

    /**
     * 货品数量
     */
    private int quantity;

    /**
     * 订单总价
     */
    private int amount;

    /**
     * 用户余额
     */
    private int balance;

    public void confirm() {
        System.out.println("订单正常，下单成功。");
    }
}
