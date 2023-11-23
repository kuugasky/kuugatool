package io.github.kuugasky.design.apply.demo3;


/**
 * CashRebateReturnFactory
 * <p>
 * 先打折再满减工厂
 *
 * @author kuuga
 * @since 2023/10/13 10:51
 */
public class CashRebateReturnFactory implements IFactory {

    // 折扣率
    private final double moneyRebate;
    // 返利条件
    private final double moneyCondition;
    // 返利值
    private final double moneyReturn;


    public CashRebateReturnFactory(double moneyRebate, double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
        this.moneyRebate = moneyRebate;
    }

    @Override
    public ISale createSalesModel() {
        CashNormal cn = new CashNormal(); // 原价
        CashReturn cr1 = new CashReturn(moneyCondition, moneyReturn); // 满返
        CashRebate cr2 = new CashRebate(moneyRebate); // 打x折
        cr1.decorate(cn); // 用满m返n算法包装基本的原价算法
        cr2.decorate(cr1); // 打x折算法装饰满m返n算法
        return cr2;
    }

}
