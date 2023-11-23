package io.github.kuugasky.kuugatool.mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQTransactionExample
 * <p>
 * RabbitMQ的事务机制
 * <p>
 * 采用rabbitmq事务方式来保证发送者一定能把消息发送给RabbitMQ，对性能有一定的影响。
 *
 * @author kuuga
 * @since 2023/9/25-09-25 15:28
 */
public class RabbitMQTransactionExample {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 启用事务
            channel.txSelect();

            String exchangeName = "my_exchange";
            String routingKey = "my_routing_key";

            try {
                // 发送第一条消息
                String message1 = "Transaction Message 1";
                channel.basicPublish(exchangeName, routingKey, null, message1.getBytes());

                // 发送第二条消息
                String message2 = "Transaction Message 2";
                channel.basicPublish(exchangeName, routingKey, null, message2.getBytes());

                // 模拟一个错误
                int x = 1 / 0;

                // 提交事务（如果没有发生错误）
                channel.txCommit();

                System.out.println("Transaction committed.");
            } catch (Exception e) {
                // 发生错误，回滚事务
                channel.txRollback();
                System.err.println("Transaction rolled back.");
            }
        }
    }

}
