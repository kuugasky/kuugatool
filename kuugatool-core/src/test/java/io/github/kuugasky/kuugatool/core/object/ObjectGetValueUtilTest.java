package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.enums.HouseDoorModelEnum;
import org.junit.jupiter.api.Test;

public class ObjectGetValueUtilTest {

    @Test
    public void testObject() {

    }

    @Test
    public void getValue() {
        HouseDoorModelEnum houseDoorModelEnum = HouseDoorModelEnum.BACHELOR_APARTMENT;
        Object o = ObjectGetValueUtil.source(houseDoorModelEnum).expectation(HouseDoorModelEnum::getDesc).get();
        System.out.println(o);
        KuugaDTO kuugaDTO = null;
        Object Kuuga = ObjectGetValueUtil.source(kuugaDTO).expectation(KuugaDTO::getName).orElse("kuuga");
        System.out.println(Kuuga);
    }

    @Test
    public void orElse() {
    }
}