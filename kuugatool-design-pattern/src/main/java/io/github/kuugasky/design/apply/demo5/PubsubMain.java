package io.github.kuugasky.design.apply.demo5;

import java.util.Date;

/**
 * PubsubMain
 *
 * @author kuuga
 * @since 2023/10/14 19:25
 */
public class PubsubMain {

    public static void main(String[] args) throws InterruptedException {
        // 创建发布者
        FlowPublisher sender = new FlowPublisher();

        // 创建订阅者1
        FlowSubscriber receiver1 = new FlowSubscriber(1);
        // 添加订阅1
        sender.subscribe(receiver1);

        // 创建订阅者2
        FlowSubscriber receiver2 = new FlowSubscriber(1);
        // 添加订阅2
        sender.subscribe(receiver2);

        for (int i = 0; i < 1; i++) {
            MessageInfo2 messageInfo = new MessageInfo2(i, "这是第" + i + "条消息", new Date().toString());
            // 发布消息
            sender.send(messageInfo);
        }
        sender.getPublisher().close();
    }

}
