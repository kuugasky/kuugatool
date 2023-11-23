package io.github.kuugasky.kuugatool.nacos.parse;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 元数据来源枚举
 *
 * @author pengqinglong
 * @since 2022/2/19
 */
@Getter
@AllArgsConstructor
public enum MetaSourceEnum {

    /**
     *
     */
    JD("jd", "京东"),
    ;

    private final String pinyin;
    private final String desc;

}