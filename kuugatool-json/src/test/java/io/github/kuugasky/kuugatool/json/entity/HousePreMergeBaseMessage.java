package io.github.kuugasky.kuugatool.json.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 预录入房源并网form
 *
 * @author pengqinglong
 * @since 2021/7/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HousePreMergeBaseMessage extends AgentConsumeQueueMessage {

    private String id;

    private String orgId;

    private Date date;

    private String operateType;
}