package io.github.kuugasky.design.creational.factorysimple.wrongdesign;

/**
 * OperationMul
 * <p>
 * 具体产品-乘法类
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:55
 */
public class OperationMul extends Operation {
    @Override
    public double getResult() {
        return getValue1() * getValue2();
    }
}