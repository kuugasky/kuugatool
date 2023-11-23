package io.github.kuugasky.kuugatool.core.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * GuavaEventBus
 * <p>
 * guava event bus
 *
 * @author kuuga
 * @since 2022/8/1 17:19
 */
public class GuavaEventBus {

    @Data
    @AllArgsConstructor
    static class OrderMessage {
        String message;
    }

    // 使用 @Subscribe 注解,表明使用dealWithEvent 方法处理 OrderMessage类型对应的消息
    // 可以注解多个方法,不同的方法 处理不同的对象消息
    static class OrderEventListener {
        @Subscribe
        public void dealWithEvent(OrderMessage event) {
            System.out.println("内容：" + event.getMessage());
        }
    }

    public static void main(String[] args) {
        // new AsyncEventBus(String identifier, Executor executor);
        // 根据标识符创建事件总线
        EventBus eventBus = new EventBus("lwl");
        // 将事件监听注入
        eventBus.register(new OrderEventListener());
        // 发布消息
        eventBus.post(new OrderMessage("csc"));
    }

}
