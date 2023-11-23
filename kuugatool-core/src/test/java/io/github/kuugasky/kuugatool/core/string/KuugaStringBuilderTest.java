package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

public class KuugaStringBuilderTest {

    @Test
    public void builder() {
        KuugaStringBuilder Kuuga = KuugaStringBuilder.builder().filterEmpty(true).append(null).append(StringUtil.EMPTY).append("kuuga");
        System.out.println(Kuuga.toString());
    }
}