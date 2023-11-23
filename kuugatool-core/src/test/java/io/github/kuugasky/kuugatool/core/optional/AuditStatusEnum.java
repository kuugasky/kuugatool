package io.github.kuugasky.kuugatool.core.optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态
 *
 * @author kuuga
 * @since 2018/12/13
 */
@AllArgsConstructor
public enum AuditStatusEnum {

    /**
     *
     */
    TO_AUDIT("待审核"),

    ;

    @Getter
    private final String desc;


}
