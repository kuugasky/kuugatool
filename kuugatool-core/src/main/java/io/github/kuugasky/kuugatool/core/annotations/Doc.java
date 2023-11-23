package io.github.kuugasky.kuugatool.core.annotations;

import java.lang.annotation.*;

/**
 * Doc
 *
 * @author kuuga
 * @since 2023-04-19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SuppressWarnings("unused")
public @interface Doc {

    /**
     * 用于功能文档联结
     *
     * @return 文档路径
     */
    String value();

}
