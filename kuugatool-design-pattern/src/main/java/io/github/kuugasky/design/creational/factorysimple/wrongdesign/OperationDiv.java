package io.github.kuugasky.design.creational.factorysimple.wrongdesign;

/**
 * OperationDiv
 * <p>
 * 具体产品-除法类
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:56
 */
public class OperationDiv extends Operation {
    @Override
    public double getResult() {
        if (getValue2() != 0) {
            return getValue1() / getValue2();
        }
        throw new IllegalArgumentException("除数不能为零");
    }
}
