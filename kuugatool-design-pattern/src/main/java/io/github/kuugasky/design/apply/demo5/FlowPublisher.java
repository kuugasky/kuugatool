package io.github.kuugasky.design.apply.demo5;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

/**
 * 发布
 *
 * @author kuuga
 * @since 2023/10/14 19:24
 */
@Getter
public class FlowPublisher implements Flow.Publisher<FlowMessage> {

    /**
     * 发布者
     */
    private final SubmissionPublisher<FlowMessage> publisher;

    FlowPublisher() {
        int threadPoolSize = 5;
        // 用于异步交付的执行器，支持创建至少一个独立线程
        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
        // maxBufferCapacity - 每个用户缓冲区的最大容量（强制容量可以四舍五入到最接近的2和/或受此实现支持的最大值限制；
        // 方法getMaxBufferCapacity返回实际值）
        int maxBufferCapacity = Flow.defaultBufferSize();
        // 创建发布者
        publisher = new SubmissionPublisher<>(service, maxBufferCapacity);
    }

    /**
     * 添加订阅
     *
     * @param subscriber the subscriber
     */
    @Override
    public void subscribe(Subscriber<? super FlowMessage> subscriber) {
        getPublisher().subscribe(subscriber);
    }

    /**
     * 发布消息
     *
     * @param flowMessage 消息
     */
    public void send(FlowMessage flowMessage) {
        System.out.println("发布者发送消息 : " + flowMessage.toString());
        getPublisher().submit(flowMessage);
    }

}