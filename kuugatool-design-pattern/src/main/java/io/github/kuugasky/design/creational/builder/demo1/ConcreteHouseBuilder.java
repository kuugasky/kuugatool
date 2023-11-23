package io.github.kuugasky.design.creational.builder.demo1;

/**
 * ConcreteHouseBuilder
 * <p>
 * 具体生成器-各部件任务实现
 *
 * @author kuuga
 * @since 2023/6/5-06-05 19:11
 */
public class ConcreteHouseBuilder implements HouseBuilder {
    private final House house;

    public ConcreteHouseBuilder() {
        house = new House();
    }

    @Override
    public void buildFoundation() {
        house.setFoundation("Concrete Foundation");
    }

    @Override
    public void buildStructure() {
        house.setStructure("Concrete Structure");
    }

    @Override
    public void buildRoof() {
        house.setRoof("Concrete Roof");
    }

    @Override
    public House getHouse() {
        return house;
    }
}
