package io.github.kuugasky.design.behavior.memento.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * MementoCaretaker-备忘录管理者
 *
 * @author kuuga
 * @since 2023/6/27-06-27 15:28
 */
public class MementoCaretaker {

    /**
     * 定义一个集合来存储备忘录
     */
    private final List<ChessmanMemento> mementoList = new ArrayList<>();

    public ChessmanMemento getMemento(int i) {
        return mementoList.get(i);
    }

    public void addMemento(ChessmanMemento memento) {
        mementoList.add(memento);
    }

}
