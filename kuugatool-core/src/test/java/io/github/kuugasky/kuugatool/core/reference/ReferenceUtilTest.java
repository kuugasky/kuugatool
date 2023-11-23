package io.github.kuugasky.kuugatool.core.reference;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.lang.ref.Reference;

public class ReferenceUtilTest {

    @Test
    public void create() {
        KuugaDTO kuugaDTO = new KuugaDTO("kuuga", 1);
        Reference<KuugaDTO> tReference = ReferenceUtil.create(ReferenceUtil.ReferenceType.SOFT, kuugaDTO);
        System.out.println(StringUtil.formatString(tReference));
    }

    @Test
    public void testCreate() {
    }
}