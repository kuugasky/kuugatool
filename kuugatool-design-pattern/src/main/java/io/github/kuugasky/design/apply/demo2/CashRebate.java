package io.github.kuugasky.design.apply.demo2;

/**
 * 现金回扣
 *
 * @author kuuga
 * @since 2023/10/12 18:38
 */
public class CashRebate extends CashSuper {

    // 折扣率
    private final double moneyRebate;

    public CashRebate(double moneyRebate) {
        // 打折收费。初始化时必须输入折扣率。八折就输入0.8
        this.moneyRebate = moneyRebate;

    }

    @Override
    public double acceptCash(double price, int num) {
        // 计算收费时需要在原价基础上乘以折扣率
        double result = price * num * moneyRebate;
        return super.acceptCash(result, 1);
    }

}
