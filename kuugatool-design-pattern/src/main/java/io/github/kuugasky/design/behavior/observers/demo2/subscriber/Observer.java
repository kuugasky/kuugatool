package io.github.kuugasky.design.behavior.observers.demo2.subscriber;

/**
 * 抽象观察者接口，由具体观察者来实现
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:38
 */
public interface Observer {

    /**
     * 观察者接口方法，发布者内容或状态变更时会调用此接口通知所有订阅的观察者
     *
     * @param temperature 温度
     * @param pressure    气压
     * @param humidity    湿度
     */
    void update(float temperature, float pressure, float humidity);

}
