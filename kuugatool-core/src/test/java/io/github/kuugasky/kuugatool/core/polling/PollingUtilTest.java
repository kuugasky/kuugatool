package io.github.kuugasky.kuugatool.core.polling;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import org.junit.jupiter.api.Test;

public class PollingUtilTest {

    @Test
    public void test() {
        PollingUtil<Object> init = PollingUtil.init(ListUtil.newArrayList("aaa", "bbb", "ccc"));
        for (int i = 0; i < KuugaConstants.TEN; i++) {
            System.out.println(init.get());
        }
    }
}