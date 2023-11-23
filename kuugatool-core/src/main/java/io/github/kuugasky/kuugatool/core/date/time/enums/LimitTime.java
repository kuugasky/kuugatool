package io.github.kuugasky.kuugatool.core.date.time.enums;

import lombok.AllArgsConstructor;

/**
 * limitTime枚举
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
@AllArgsConstructor
public enum LimitTime {

    /**
     * 当天 到第二天0点为止相差秒数
     */
    TODAY,

    /**
     * 从当前时间开始 满24小时 也就是一天 相差秒数
     */
    TODAY_NOW

}