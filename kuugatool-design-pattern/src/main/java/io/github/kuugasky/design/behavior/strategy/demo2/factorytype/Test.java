package io.github.kuugasky.design.behavior.strategy.demo2.factorytype;

import io.github.kuugasky.design.behavior.strategy.demo2.HighPriceRank;
import io.github.kuugasky.design.behavior.strategy.demo2.Stock;
import io.github.kuugasky.design.behavior.strategy.demo2.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Test
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:22
 */
public class Test {

    /**
     * 利用注解@Resource和@Autowired特性,直接获取所有策略类
     * key = @Service的value
     */
    // @Resource
    // private Map<String, Strategy> rankMap;
    // 在spring中可以用上面的方式获取策略
    // private Strategy strategy = new HighPriceRank();
    public static void main(String[] args) {
        List<Stock> list = new ArrayList<>();
        list.add(Stock.builder().code("A1").price(1D).rise(1D).build());
        list.add(Stock.builder().code("A3").price(3D).rise(3D).build());
        list.add(Stock.builder().code("A2").price(2D).rise(5D).build());

        Strategy strategy = new HighPriceRank();
        strategy.sort(list);
    }

}
