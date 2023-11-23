package io.github.kuugasky.design.apply.demo2;

/**
 * 现金-超类
 *
 * @author kuuga
 * @since 2023/10/12-10-12 18:35
 */
public class CashSuper implements ISale {

    /**
     * 组成部份
     */
    protected ISale component;

    /**
     * 装饰模式
     *
     * @param component 待装饰对象
     */
    public void decorate(ISale component) {
        this.component = component;
    }

    @Override
    public double acceptCash(double price, int num) {
        double result = 0;
        if (this.component != null) {
            // 根据装饰对象执行对应的策略
            result = this.component.acceptCash(price, num);
        }
        return result;
    }

}
