package io.github.kuugasky.design.structural.flyweight.demo2.state;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Coordinates
 * <p>
 * 非共享享元类-外部状态-棋子坐标类
 *
 * @author kuuga
 * @since 2023/6/6-06-06 09:31
 */
@Data
@AllArgsConstructor
public class Coordinates {

    private int x;
    private int y;

}
