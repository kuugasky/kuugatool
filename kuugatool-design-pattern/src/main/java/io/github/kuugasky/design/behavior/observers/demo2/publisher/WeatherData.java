package io.github.kuugasky.design.behavior.observers.demo2.publisher;

import io.github.kuugasky.design.behavior.observers.demo2.subscriber.Observer;
import lombok.Getter;

import java.util.ArrayList;

/**
 * 具体被观察者-天气信息发布
 * <p>
 * 类是核心
 * 1. 包含最新的天气情况信息
 * 2. 含有 观察者集合，使用ArrayList管理
 * 3. 当数据有更新时，就主动的调用   ArrayList, 通知所有的（接入方）就看到最新的信息
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:37
 */
public class WeatherData implements Subject {

    /**
     * 温度
     */
    @Getter
    private float temperature;
    /**
     * 气压
     */
    @Getter
    private float pressure;
    /**
     * 湿度
     */
    @Getter
    private float humidity;

    /**
     * 观察者集合-发布者内部常量属性，集中管理所有订阅者/观察者
     */
    private final ArrayList<Observer> observers;

    // 加入新的第三方

    public WeatherData() {
        observers = new ArrayList<>();
    }

    /**
     * 当数据有更新时，就调用 setData
     *
     * @param temperature 温度
     * @param pressure    气压
     * @param humidity    湿度
     */
    public void setData(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;

        // 调用 接入方的 update，将最新的信息 推送给 接入方 currentConditions
        notifyObservers();
    }

    /**
     * 注册一个观察者
     *
     * @param o 观察者
     */
    @Override
    public void registerObserver(Observer o) {
        // TODO Auto-generated method stub
        observers.add(o);
    }

    /**
     * 移除一个观察者
     *
     * @param o 观察者
     */
    @Override
    public void removeObserver(Observer o) {
        // TODO Auto-generated method stub
        observers.remove(o);
    }

    /**
     * 遍历所有的观察者，并通知
     */
    @Override
    public void notifyObservers() {
        // TODO Auto-generated method stub
        for (Observer observer : observers) {
            observer.update(this.temperature, this.pressure, this.humidity);
        }
    }

}
