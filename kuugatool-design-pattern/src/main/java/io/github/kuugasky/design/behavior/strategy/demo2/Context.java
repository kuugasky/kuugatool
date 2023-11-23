package io.github.kuugasky.design.behavior.strategy.demo2;

import java.util.List;

/**
 * Context
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:06
 */
public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Stock> getRank(List<Stock> source) {
        return strategy.sort(source);
    }
}
