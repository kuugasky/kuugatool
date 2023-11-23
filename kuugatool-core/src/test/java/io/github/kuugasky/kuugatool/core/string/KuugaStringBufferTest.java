package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

public class KuugaStringBufferTest {

    @Test
    public void builder() {
        KuugaStringBuffer Kuuga = KuugaStringBuffer.builder().filterEmpty(true).append(null).append(StringUtil.EMPTY).append("kuuga");
        System.out.println(Kuuga.toString());
    }

}