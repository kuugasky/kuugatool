package io.github.kuugasky.kuugatool.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * PublisherCallbacksExample
 * <p>
 * 如何保障消息一定能发送到RabbitMQ
 *
 * @author kuuga
 * @since 2023/9/25-09-25 15:17
 */
public class PublisherCallbacksExample {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 启用Publisher Confirms
            channel.confirmSelect();

            // 设置Publisher Confirms回调（Product发送到exchange确认回调：用于确保消息成功发送到exchange）
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    System.out.println("消息已通过交付标签确认: " + deliveryTag);
                    // 在这里处理消息确认
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    System.out.println("消息未通过交付标签确认: " + deliveryTag);
                    // 在这里处理消息未确认
                }
            });

            // 启用Publisher Returns（消息由exchange发送到queue回调：用于确保消息由exchange成功发送到queue）
            channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
                System.out.println("带有回复代码返回的消息: " + replyCode);
                // 在这里处理消息发送到Queue失败的返回
            });

            String exchangeName = "my_exchange";
            String routingKey = "my_routing_key";
            String message = "Hello, RabbitMQ!";

            // 发布消息到Exchange
            channel.basicPublish(exchangeName, routingKey, true, null, message.getBytes());

            // 等待Publisher Confirms
            if (!channel.waitForConfirms()) {
                System.out.println("消息未确认!");
            }

            // 关闭通道和连接
            // channel.close();
        }
    }

}
