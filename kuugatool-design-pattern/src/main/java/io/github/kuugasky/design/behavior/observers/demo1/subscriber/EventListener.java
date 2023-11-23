package io.github.kuugasky.design.behavior.observers.demo1.subscriber;

import java.io.File;

/**
 * 通用观察者接口
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:01
 */
public interface EventListener {

    /**
     * 修改监听，用于发布者变更内容或状态后，调用此接口来通知所有的订阅者
     *
     * @param eventType 事件类型
     * @param file      文件
     */
    void update(String eventType, File file);

}
