package io.github.kuugasky.design.behavior.memento.demo1;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/27-06-27 14:38
 */
public class Main {

    public static void main(String[] args) {
        // 创建原对象
        Originator originator = new Originator();
        originator.setState("State #1");
        originator.setState("State #2");

        // 创建备忘录管理者
        CareTaker careTaker = new CareTaker();
        // 将State #2存入备忘录
        careTaker.add(originator.saveStateToMemento());
        // 重新给原对象state赋值#3
        originator.setState("State #3");
        // 将State #3存入备忘录
        careTaker.add(originator.saveStateToMemento());
        // 重新给原对象state赋值#4
        originator.setState("State #4");

        System.out.println("当前状态: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println("首次保存状态: " + originator.getState());
        originator.getStateFromMemento(careTaker.get(1));
        System.out.println("第二个保存状态: " + originator.getState());
    }

}
