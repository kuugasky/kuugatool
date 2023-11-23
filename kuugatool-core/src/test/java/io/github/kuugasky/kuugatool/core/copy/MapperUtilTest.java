package io.github.kuugasky.kuugatool.core.copy;

import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapperUtilTest {

    private KuugaModel kuugaModel;
    private final List<KuugaModel> kuugaModels = new ArrayList<>();

    @Before
    public void setUp() {
        kuugaModel = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(2).build();
        kuugaModels.add(kuugaModel);
        kuugaModels.add(kuugaModel1);
    }

    @After
    public void tearDown() {
        kuugaModel = null;
    }

    @Test
    public void copy() {
        KuugaDTO kuugaDTO = new KuugaDTO();
        MapperUtil.copy(kuugaModel, kuugaDTO);
        System.out.println(kuugaDTO);
    }

    @Test
    public void testCopy() {
        KuugaDTO copy = MapperUtil.copy(kuugaModel, KuugaDTO.class);
        System.out.println(copy);
    }

    @Test
    public void testCopy1() {
        List<KuugaDTO> copy = MapperUtil.copy(kuugaModels, KuugaDTO.class);
        System.out.println(copy);
    }

    @Test
    public void testCopy2() {
        Set<List<KuugaModel>> aNew = SetUtil.newHashSet(kuugaModels);
        Set<KuugaDTO> copy = MapperUtil.copy(aNew, KuugaDTO.class);
        System.out.println(copy);
    }

}