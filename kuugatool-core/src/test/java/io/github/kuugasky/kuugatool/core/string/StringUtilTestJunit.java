package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

/**
 * @author kuuga
 * @since 2022-05-04 15:39:59
 */
public class StringUtilTestJunit {

    @Test
    void isEmpty() {
        String str = StringUtil.EMPTY;
        System.out.println(StringUtil.isEmpty(str));
    }

}
