package io.github.kuugasky.design.creational.builder.demo1;

/**
 * HouseBuilder
 * <p>
 * 抽象生成器-定义各部件任务
 *
 * @author kuuga
 * @since 2023/6/5-06-05 19:05
 */
public interface HouseBuilder {
    /**
     * 构建地基
     */
    void buildFoundation();

    /**
     * 构建结构
     */
    void buildStructure();

    /**
     * 构建屋顶
     */
    void buildRoof();

    /**
     * 获得房屋产品
     *
     * @return House
     */
    House getHouse();
}
