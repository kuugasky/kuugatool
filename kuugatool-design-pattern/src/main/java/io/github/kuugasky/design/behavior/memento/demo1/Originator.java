package io.github.kuugasky.design.behavior.memento.demo1;

import lombok.Getter;
import lombok.Setter;

/**
 * Originator-发起者
 *
 * @author kuuga
 * @since 2023/6/27-06-27 14:23
 */
public class Originator {

    @Getter
    @Setter
    private String state;

    /**
     * 将状态保存为备忘录
     * <p>
     * 原对象要点：提供内部方法用于将某个字段存储到备忘录<br>
     * 其实就是将原对象的某个属性拿来创建一个备忘录对象，用于放入到CareTaker中管理
     *
     * @return Memento
     */
    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    /**
     * 从备忘录中获取状态
     *
     * @param memento Memento
     */
    public void getStateFromMemento(Memento memento) {
        state = memento.state();
    }

}
