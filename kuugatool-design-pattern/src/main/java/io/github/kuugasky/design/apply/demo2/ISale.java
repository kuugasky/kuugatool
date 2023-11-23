package io.github.kuugasky.design.apply.demo2;

/**
 * ISale
 * <p>
 * 销售接口
 *
 * @author kuuga
 * @since 2023/10/12-10-12 18:34
 */
public interface ISale {

    /**
     * 接受现金
     *
     * @param price 单价
     * @param num   数量
     * @return 一类商品金额
     */
    double acceptCash(double price, int num);

}
