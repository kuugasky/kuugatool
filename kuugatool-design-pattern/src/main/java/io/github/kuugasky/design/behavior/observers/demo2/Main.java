package io.github.kuugasky.design.behavior.observers.demo2;

import io.github.kuugasky.design.behavior.observers.demo2.publisher.WeatherData;
import io.github.kuugasky.design.behavior.observers.demo2.subscriber.BaiduSite;
import io.github.kuugasky.design.behavior.observers.demo2.subscriber.CurrentConditions;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:41
 */
public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 创建一个发布者：WeatherData
        WeatherData weatherData = new WeatherData();

        // 创建具体观察者-当前天气
        CurrentConditions currentConditions = new CurrentConditions();
        // 创建具体观察者-百度天气
        BaiduSite baiduSite = new BaiduSite();

        // 将观察者注册到weatherData
        weatherData.registerObserver(currentConditions);
        weatherData.registerObserver(baiduSite);

        // 测试
        System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);

        System.out.println("---------------------------------------------------");

        System.out.println("-------> 移除当前天气订阅");
        weatherData.removeObserver(currentConditions);

        // 测试
        System.out.println();
        System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);
    }

}
