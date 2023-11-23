package io.github.kuugasky.design.creational.builder.demo1;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/5-06-05 19:12
 */
public class Main {

    public static void main(String[] args) {
        // 创建具体生成器
        HouseBuilder builder = new ConcreteHouseBuilder();
        // 创建指挥者
        HouseDirector director = new HouseDirector();
        // 指挥者指挥生成器进行构建
        House house = director.constructHouse(builder);
        // 得到产品
        System.out.println(house);
    }

}