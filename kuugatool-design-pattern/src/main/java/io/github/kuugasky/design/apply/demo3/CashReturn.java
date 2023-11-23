package io.github.kuugasky.design.apply.demo3;

/**
 * 现金返还
 *
 * @author kuuga
 * @since 2023/10/12 18:44
 */
public class CashReturn extends CashSuper {

    // 返利条件
    private final double moneyCondition;

    // 返利值
    private final double moneyReturn;

    public CashReturn(double moneyCondition, double moneyReturn) {
        // 比如“满300返100”，就是moneyCondition=300，moneyReturn=100
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double price, int num) {
        double result = price * num;
        // 符合返利条件
        if (moneyCondition > 0 && result >= moneyCondition) {
            result = result - Math.floor(result / moneyCondition) * moneyReturn;
        }
        return super.acceptCash(result, 1);
    }

}
