package io.github.kuugasky.design.behavior.memento.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ChessmanMemento-象棋棋子备忘录
 *
 * @author kuuga
 * @since 2023/6/27-06-27 15:28
 */
@Data
@AllArgsConstructor
public class ChessmanMemento {

    /**
     * 标签-象棋
     */
    private String label;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;

}
