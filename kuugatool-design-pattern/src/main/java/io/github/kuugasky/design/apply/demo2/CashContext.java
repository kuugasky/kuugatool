package io.github.kuugasky.design.apply.demo2;

/**
 * 现金上下文
 *
 * @author kuuga
 * @since 2023/10/12 18:52
 */
public class CashContext {

    private ISale cs;

    /**
     * 简单工厂模式
     *
     * @param cashType 现金类型
     */
    public CashContext(int cashType) {
        switch (cashType) {
            case 1 ->
                // 正常价格无折扣
                    this.cs = new CashNormal();
            case 2 -> {
                // 先打8折，再满300返100
                CashNormal cn = new CashNormal();
                CashReturn cr1 = new CashReturn(300, 100);
                CashRebate cr2 = new CashRebate(0.8);
                cr1.decorate(cn);
                cr2.decorate(cr1);
                this.cs = cr2;
            }
            case 3 -> {
                // 先满200返50，再打7折
                CashNormal cn2 = new CashNormal();
                CashRebate cr3 = new CashRebate(0.7);
                CashReturn cr4 = new CashReturn(200, 50);
                cr3.decorate(cn2);
                cr4.decorate(cr3);
                this.cs = cr4;
            }
        }
    }

    public double getResult(double price, int num) {
        return this.cs.acceptCash(price, num);
    }

}

