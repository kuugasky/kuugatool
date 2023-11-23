package io.github.kuugasky.design.creational.builder.demo1;

import lombok.Setter;

/**
 * House
 * <p>
 * 产品类
 *
 * @author kuuga
 * @since 2023/6/5-06-05 19:04
 */
@Setter
public class House {

    private String foundation;
    private String structure;
    private String roof;

    @Override
    public String toString() {
        return "Foundation: " + foundation + ", Structure: " + structure + ", Roof: " + roof;
    }

}
