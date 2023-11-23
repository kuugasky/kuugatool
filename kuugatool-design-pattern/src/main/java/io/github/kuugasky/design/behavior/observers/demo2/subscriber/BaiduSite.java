package io.github.kuugasky.design.behavior.observers.demo2.subscriber;

/**
 * 具体观察者-百度网站
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:38
 */
public class BaiduSite implements Observer {

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
        System.out.println("===百度网站====");
        System.out.println("***百度网站 气温 : " + temperature + "***");
        System.out.println("***百度网站 气压: " + pressure + "***");
        System.out.println("***百度网站 湿度: " + humidity + "***");
    }

}