package io.github.kuugasky.design.behavior.observers.demo1.publisher;

import io.github.kuugasky.design.behavior.observers.demo1.subscriber.EventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础发布者
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:00
 */
public class EventManager {

    /**
     * 监听中心
     * <p>
     * key:事件类型<br>
     * value:监听集合
     */
    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    /**
     * 构造函数
     *
     * @param operations 业务操作
     */
    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * 订阅
     *
     * @param eventType 事件类型
     * @param listener  监听
     */
    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    /**
     * 取消订阅
     *
     * @param eventType 事件类型
     * @param listener  监听
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    /**
     * 通知
     * <p>
     * 当具体发布者执行了相关事件业务时，调用此接口，会查出该事件类型的所有订阅者，然后调用其订阅者的update方法
     *
     * @param eventType 事件类型
     * @param file      变更文件
     */
    public void notify(String eventType, File file) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, file);
        }
    }

}
