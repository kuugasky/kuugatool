package io.github.kuugasky.design.behavior.strategy.demo2;

import lombok.Builder;
import lombok.Getter;

/**
 * Stock
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:04
 */
@Builder
@Getter
public class Stock {

    // 股票交易代码
    private String code;

    // 现价
    private Double price;

    // 涨幅
    private Double rise;
}
