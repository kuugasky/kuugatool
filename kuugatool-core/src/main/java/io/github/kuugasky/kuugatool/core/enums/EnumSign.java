package io.github.kuugasky.kuugatool.core.enums;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.lang.annotation.*;

/**
 * EnumSign
 *
 * @author kuuga
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumSign {

    String value() default StringUtil.EMPTY;

}
