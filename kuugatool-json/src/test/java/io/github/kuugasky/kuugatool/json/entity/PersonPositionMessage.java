package io.github.kuugasky.kuugatool.json.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PersonPositionMessage
 *
 * @author zhongyuan
 * @since 2021/12/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonPositionMessage extends AgentConsumeQueueMessage {

    private String id;

    private OperateTypeEnum operateType;

    private String positionId;

    private String orgId;

    private Long date;

    private String isMain;

    private PersonPositionChangeDiffDto changeDiff;

}
