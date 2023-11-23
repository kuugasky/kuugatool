package io.github.kuugasky.kuugatool.core.flow;

import java.util.concurrent.Flow;

/**
 * 主题订阅者-观察者模式
 *
 * @author kuuga
 * @since 2023/10/14 19:25
 */
public abstract class FlowSubscriber implements Flow.Subscriber<FlowMessage> {

    /**
     * 订阅
     */
    private Flow.Subscription subscription;
    /**
     * 需求增量
     */
    private final long demandIncrement;

    FlowSubscriber() {
        this.demandIncrement = Integer.MAX_VALUE;
    }

    FlowSubscriber(long demandIncrement) {
        this.demandIncrement = demandIncrement;
    }

    /**
     * 订阅消费者成功时
     *
     * @param subscription 一个新的订阅
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(demandIncrement);
    }

    /**
     * 与订阅的下一个项目一起调用的方法。如果此方法抛出异常，则无法保证由此产生的行为，但可能会导致订阅被取消。
     *
     * @param flowMessage the message
     */
    @Override
    public void onNext(FlowMessage flowMessage) {
        // 将给定数量的项目n添加到此订阅的当前未履行需求中。 如果n小于或等于零，则订户将收到带有IllegalArgumentException参数的onError信号。
        // 否则，订户将接收最多n额外的onNext调用（如果终止则更少）。
        // 参数
        // n - 需求增量; 值Long.MAX_VALUE可被视为实际无限制
        subscription.request(demandIncrement);
        consume(flowMessage);
    }

    abstract void consume(FlowMessage flowMessage);

    @Override
    public void onError(Throwable throwable) {
        error(throwable);
    }

    abstract void error(Throwable throwable);

    @Override
    public void onComplete() {
        complete();
    }

    abstract void complete();

}


