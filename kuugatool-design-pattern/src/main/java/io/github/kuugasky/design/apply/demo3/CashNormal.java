package io.github.kuugasky.design.apply.demo3;

/**
 * 现金正常
 *
 * @author kuuga
 * @since 2023/10/12 18:38
 */
public class CashNormal implements ISale {

    @Override
    public double acceptCash(double price, int num) {
        // 正常收费，原价返回
        return price * num;
    }

}
