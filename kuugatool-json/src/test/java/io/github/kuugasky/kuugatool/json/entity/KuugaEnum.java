package io.github.kuugasky.kuugatool.json.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * KuugaEnum
 *
 * @author kuuga
 * @since 2022/6/6 15:23
 */
@AllArgsConstructor
@Getter
public enum KuugaEnum {

    /**
     *
     */
    GOOD("好的"),
    BAD("坏的"),
    ;

    private final String desc;

}
