package io.github.kuugasky.design.creational.factorysimple.wrongdesign;

/**
 * Operation
 * <p>
 * 抽象产品-计算类的基类
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:50
 */
public abstract class Operation {

    private double value1 = 0;
    private double value2 = 0;

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    /**
     * 计算
     *
     * @return double
     */
    public abstract double getResult();
}