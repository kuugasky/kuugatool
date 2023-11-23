package io.github.kuugasky.kuugatool.core.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

class FlowPublisherTest {

    @Test
    void send() {
        FlowMessageInfo messageInfo = new FlowMessageInfo("天地玄黄，宇宙洪荒");

        FlowPublisher topicPublisher1 = new FlowPublisher("测试");
        FlowPublisher topicPublisher2 = new FlowPublisher("生产");

        FlowSubscriber1 flowSubscriber1 = new FlowSubscriber1(11);
        FlowSubscriber1 flowSubscriber2 = new FlowSubscriber1(22);

        topicPublisher1.subscribe(flowSubscriber1);
        topicPublisher1.subscribe(flowSubscriber2);

        topicPublisher2.subscribe(flowSubscriber1);

        topicPublisher1.send(messageInfo);
        topicPublisher2.send(new FlowMessageInfo("夏语蝉鸣"));

    }

}

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
class FlowMessageInfo extends FlowMessage {

    private String message;

}

class FlowSubscriber1 extends FlowSubscriber {

    private long demandIncrement;

    public FlowSubscriber1() {
        super();
    }

    public FlowSubscriber1(long demandIncrement) {
        super(demandIncrement);
        this.demandIncrement = demandIncrement;
    }

    @Override
    void consume(FlowMessage flowMessage) {
        System.out.printf("消费者[%s],接收到了主题[%s]消息：%s | %s%n",
                this.demandIncrement,
                flowMessage.getTopic(), flowMessage, flowMessage.getVersion());
    }

    @Override
    void error(Throwable throwable) {

    }

    @Override
    void complete() {

    }

}