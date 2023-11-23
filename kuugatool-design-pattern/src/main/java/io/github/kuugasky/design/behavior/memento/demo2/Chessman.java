package io.github.kuugasky.design.behavior.memento.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Chessman-象棋棋子
 *
 * @author kuuga
 * @since 2023/6/27-06-27 15:27
 */
@Data
@AllArgsConstructor
public class Chessman {

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

    /**
     * 保存状态-创建一个备忘录对象
     *
     * @return ChessmanMemento 棋子备忘录
     */
    public ChessmanMemento save() {
        return new ChessmanMemento(this.label, this.x, this.y);
    }

    /**
     * 恢复状态
     *
     * @param memento ChessmanMemento 棋子备忘录
     */
    public void restore(ChessmanMemento memento) {
        this.label = memento.getLabel();
        this.x = memento.getX();
        this.y = memento.getY();
    }

    public void show() {
        System.out.printf("棋子<%s>：当前位置为：<%d, %d>%n", this.getLabel(), this.getX(), this.getY());
    }

}
