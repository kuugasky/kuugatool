package io.github.kuugasky.kuugatool.core.flow;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * FlowMessage
 *
 * @author kuuga
 * @since 2023/10/14 21:37
 */
@Getter
public abstract class FlowMessage implements Serializable {

    /**
     * 消息ID
     */
    private final long version = System.currentTimeMillis();

    @Setter
    private String topic;

}
