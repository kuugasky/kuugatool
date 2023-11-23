package io.github.kuugasky.kuugatool.core.flow;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * 主题发布者-观察者模式
 *
 * @author kuuga
 * @since 2023/10/14 22:17
 */
@Getter
@Slf4j
public class FlowPublisher implements Flow.Publisher<FlowMessage> {

    private final String topic;

    /**
     * 主题发布
     */
    private final SubmissionPublisher<FlowMessage> submissionPublisher;

    FlowPublisher() {
        this.topic = null;
        this.submissionPublisher = new SubmissionPublisher<>();
    }

    FlowPublisher(String topic) {
        this.topic = topic;
        // int threadPoolSize = 5;
        // // 用于异步交付的执行器，支持创建至少一个独立线程
        // ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
        // // maxBufferCapacity - 每个用户缓冲区的最大容量（强制容量可以四舍五入到最接近的2和/或受此实现支持的最大值限制；
        // // 方法getMaxBufferCapacity返回实际值）
        // int maxBufferCapacity = Flow.defaultBufferSize();
        // // 创建发布者
        // submissionPublisher = new SubmissionPublisher<>(service, maxBufferCapacity);
        // 默认forkJoin异步线程池，最大缓冲区容量为256
        this.submissionPublisher = new SubmissionPublisher<>();
    }

    /**
     * 添加订阅
     *
     * @param subscriber the subscriber
     */
    @Override
    public void subscribe(Flow.Subscriber<? super FlowMessage> subscriber) {
        getSubmissionPublisher().subscribe(subscriber);
    }

    /**
     * 发布消息
     *
     * @param flowMessage 消息
     */
    public void send(FlowMessage flowMessage) {
        log.debug(String.format("主题:%s,消息发布:%s", topic, flowMessage.toString()));
        flowMessage.setTopic(topic);
        getSubmissionPublisher().submit(flowMessage);
    }

}
