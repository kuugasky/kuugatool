package io.github.kuugasky.design.apply.demo5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MessageInfo
 *
 * @author kuuga
 * @since 2023/10/14 21:39
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageInfo extends FlowMessage {

    private int i;
    private String message;

}
