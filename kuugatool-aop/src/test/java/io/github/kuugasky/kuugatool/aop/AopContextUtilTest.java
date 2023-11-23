package io.github.kuugasky.kuugatool.aop;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

class AopContextUtilTest {

    @Test
    void currentProxy() {
        Object o = AopContextUtil.currentProxy();
        System.out.println(StringUtil.formatString(o));
    }

}