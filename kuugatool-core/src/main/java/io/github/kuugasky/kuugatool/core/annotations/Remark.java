package io.github.kuugasky.kuugatool.core.annotations;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.lang.annotation.*;

/**
 * Remark
 *
 * @author kuuga
 * @since 2021/6/21
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Remark {

    /**
     * 类、方法、字段备注信息，没什么实际用处，主要是用于写一些重要的备注信息，避免像常规注释偶尔不小心被删除
     *
     * @return 备注信息
     */
    String remark() default StringUtil.EMPTY;

}
