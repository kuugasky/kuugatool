package io.github.kuugasky.design.apply.demo3;

/**
 * CashRebateReturnFactory
 * <p>
 * 先满减再打折工厂
 *
 * @author kuuga
 * @since 2023/10/13 10:51
 */
public class CashReturnRebateFactory implements IFactory {

    // 折扣率
    private final double moneyRebate;
    // 返利条件
    private final double moneyCondition;
    // 返利值
    private final double moneyReturn;

    public CashReturnRebateFactory(double moneyRebate, double moneyCondition, double moneyReturn) {
        this.moneyRebate = moneyRebate;
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public ISale createSalesModel() {
        CashNormal cn2 = new CashNormal();
        CashRebate cr3 = new CashRebate(moneyRebate);
        CashReturn cr4 = new CashReturn(moneyCondition, moneyReturn);
        cr3.decorate(cn2);
        cr4.decorate(cr3);
        return cr4;
    }

}
