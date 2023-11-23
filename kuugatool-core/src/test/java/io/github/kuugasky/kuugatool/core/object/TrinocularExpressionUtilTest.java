package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import org.junit.jupiter.api.Test;

public class TrinocularExpressionUtilTest {

    @Test
    public void judge() {
        Integer judge = TrinocularExpressionUtil.judge(() -> {
            String string = RandomUtil.randomString(10);
            System.out.println(string);
            return string.contains("a");
        }, 100, 1000);
        System.out.println(judge);
    }

}