package io.github.kuugasky.design.behavior.memento.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * CareTaker-备忘录管理者
 *
 * @author kuuga
 * @since 2023/6/27-06-27 14:36
 */
public class CareTaker {

    /**
     * 提供一个常量备忘录集合，用于存放所有备忘录
     */
    private final List<Memento> mementoList = new ArrayList<>();

    /**
     * 添加备忘录对象到备忘录管理集合中
     *
     * @param state Memento
     */
    public void add(Memento state) {
        mementoList.add(state);
    }

    /**
     * 从备忘录管理集合中获取对应到备忘录对象
     *
     * @param index index
     * @return Memento
     */
    public Memento get(int index) {
        return mementoList.get(index);
    }

}
