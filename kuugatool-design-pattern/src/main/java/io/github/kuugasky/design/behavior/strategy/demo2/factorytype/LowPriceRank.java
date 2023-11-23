package io.github.kuugasky.design.behavior.strategy.demo2.factorytype;

import io.github.kuugasky.design.behavior.strategy.demo2.Stock;
import io.github.kuugasky.design.behavior.strategy.demo2.Strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LowPriceRank
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:20
 */
public class LowPriceRank implements Strategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparing(Stock::getPrice))
                .collect(Collectors.toList());
    }
}