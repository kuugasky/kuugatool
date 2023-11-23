package io.github.kuugasky.design.behavior.observers.demo2.subscriber;

/**
 * 具体观察者-当前天气
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:40
 */
public class CurrentConditions implements Observer {

    /**
     * 温度
     */
    private float temperature;
    /**
     * 气压
     */
    private float pressure;
    /**
     * 湿度
     */
    private float humidity;

    /**
     * 更新 天气情况，是由 WeatherData 来调用，我使用推送模式
     *
     * @param temperature 温度
     * @param pressure    气压
     * @param humidity    湿度
     */
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    /**
     * 显示
     */
    public void display() {
        System.out.println("***当前天气***");
        System.out.println("***Today mTemperature: " + temperature + "***");
        System.out.println("***Today mPressure: " + pressure + "***");
        System.out.println("***Today mHumidity: " + humidity + "***");
    }

}