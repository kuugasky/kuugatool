package io.github.kuugasky.kuugatool.core.lang.single;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    public void test() {
        KuugaDTO dto1 = Singleton.get(KuugaDTO.class);
        KuugaDTO dto2 = Singleton.get(KuugaDTO.class);
        KuugaDTO dto3 = Singleton.get(KuugaDTO.class);
        System.out.println(dto1 == dto2);
        System.out.println(dto1 == dto3);
        System.out.println(dto2 == dto3);
    }

}