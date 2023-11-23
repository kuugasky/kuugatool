package io.github.kuugasky.kuugatool.core.convert;

import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ConvertTest {

    @Test
    void toSBC() {
        // 半角转全角
        String s = Convert.toSBC("~");
        System.out.println(s);
    }

    @Test
    void testToSBC() {
        Set<Character> ts = SetUtil.newHashSet(Character.MAX_HIGH_SURROGATE);
        String s = Convert.toSBC("～~～" + Character.MAX_HIGH_SURROGATE, ts);
        System.out.println(s);
    }

}