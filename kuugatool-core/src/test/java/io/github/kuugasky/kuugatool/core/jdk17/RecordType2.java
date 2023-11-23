package io.github.kuugasky.kuugatool.core.jdk17;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * RecordType2
 *
 * @author kuuga
 * @since 2022/8/8 11:22
 */
public class RecordType2 {

    static class Merchant {

    }

    double computeSales(Merchant merchant, int month) {
        return 0D;
    }

    List<Merchant> findTopMerchants(List<Merchant> merchants, int month) {
        /*
         * 本地record类是嵌套record类的一种特殊情况。与嵌套record类一样，本地record类是隐式静态的。
         * 这意味着它们自己的方法不能访问封闭方法的任何变量；
         * 反过来，这避免了捕获一个立即封闭的实例，该实例会默默地向record类添加状态。
         * 本地record类是隐式静态的事实与本地类相反，本地类不是隐式静态的。
         * 事实上，局部类从来都不是静态的——隐式或显式——并且总是可以访问封闭方法中的变量。
         */
        record MerchantSales(Merchant merchant, double sales) {
        }

        return merchants.stream()
                .map(merchant -> new MerchantSales(merchant, computeSales(merchant, month)))
                .sorted((m1, m2) -> Double.compare(m2.sales(), m1.sales()))
                .map(MerchantSales::merchant)
                .collect(toList());
    }

}
