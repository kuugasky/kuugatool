package io.github.kuugasky.design.behavior.observers.demo2.publisher;

import io.github.kuugasky.design.behavior.observers.demo2.subscriber.Observer;

/**
 * （抽象被观察者）接口, 让（具体观察者）WeatherData 来实现
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:33
 */
public interface Subject {

    /**
     * 订阅
     *
     * @param o 观察者
     */
    void registerObserver(Observer o);

    /**
     * 取消订阅
     *
     * @param o 观察者
     */
    void removeObserver(Observer o);

    /**
     * 被观察者变更时，通知所有观察者
     */
    void notifyObservers();

}
