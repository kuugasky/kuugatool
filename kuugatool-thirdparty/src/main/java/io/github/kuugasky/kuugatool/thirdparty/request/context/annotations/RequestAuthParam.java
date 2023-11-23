package io.github.kuugasky.kuugatool.thirdparty.request.context.annotations;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * 第三方请求身份验证参数注解
 * <p>
 * 该注解直接打在{@link RequestForm}实现类的{@link Field}鉴权字段上
 *
 * @author pengqinglong
 * @since 2022/3/14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAuthParam {

    /**
     * 请求头鉴权keyValue
     * <p>
     * key：第三方请求头鉴权Key
     * value：第三方请求头鉴权Key对应值
     */
    String value() default StringUtil.EMPTY;

}
