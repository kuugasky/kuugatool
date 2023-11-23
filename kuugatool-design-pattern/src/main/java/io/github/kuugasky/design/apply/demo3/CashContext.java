package io.github.kuugasky.design.apply.demo3;

/**
 * 现金上下文
 *
 * @author kuuga
 * @since 2023/10/12 18:52
 */
public class CashContext {

    private final ISale cs;

    /**
     * 工厂方法模式
     * <p>
     * 策略类这里只剩工厂接口和具体工厂，高内聚，低耦合，具体业务逻辑封装到类具体到工厂内部
     * <p>
     * 类和代码是比普通工厂多了，但是更符合开闭原则，也实现了高内聚
     *
     * @param cashType 现金类型
     */
    public CashContext(int cashType) {
        IFactory factory = switch (cashType) {
            // 原价
            case 1 -> new CashRebateReturnFactory(1, 0, 0);
            // 打8折
            case 2 -> new CashRebateReturnFactory(0.8, 0, 0);
            // 打7折
            case 3 -> new CashRebateReturnFactory(0.7, 0, 0);
            // 满300返100
            case 4 -> new CashRebateReturnFactory(1, 300, 100);
            // 先打8折，再满300返100
            case 5 -> new CashRebateReturnFactory(0.8, 300, 100);
            // 先满200返50，再打7折
            case 6 -> new CashReturnRebateFactory(0.7, 200, 50);
            default -> throw new IllegalStateException("Unexpected value: " + cashType);
        };
        this.cs = factory.createSalesModel();

    }

    public double getResult(double price, int num) {
        return this.cs.acceptCash(price, num);
    }

}

