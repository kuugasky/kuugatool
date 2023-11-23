package io.github.kuugasky.design.creational.builder.demo1;

/**
 * HouseDirector
 * <p>
 * 指挥者-负责具体调度任务和任务顺序
 *
 * @author kuuga
 * @since 2023/6/5-06-05 19:12
 */
public class HouseDirector {

    public House constructHouse(HouseBuilder builder) {
        builder.buildFoundation();
        builder.buildStructure();
        builder.buildRoof();
        return builder.getHouse();
    }

}
