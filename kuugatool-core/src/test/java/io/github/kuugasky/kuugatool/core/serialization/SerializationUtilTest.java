package io.github.kuugasky.kuugatool.core.serialization;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

public class SerializationUtilTest {

    String FILE_NAME = "/Users/kuuga/Documents/serializationObject.bin";

    @Test
    public void writeObject() {
        KuugaModel kuugaModel = new KuugaModel();
        kuugaModel.setName("Kuuga@0410");
        kuugaModel.setSex(1);
        SerializationUtil.writeObject(kuugaModel, new File(FILE_NAME));
    }

    @Test
    public void readObject() {
        Object o = SerializationUtil.readObject(new File(FILE_NAME));
        System.out.println(StringUtil.formatString(o));
    }

    @Test
    public void testClone() {
        KuugaDTO kuugaDTO = new KuugaDTO("kuuga", 10);
        KuugaDTO clone = SerializationUtil.clone(kuugaDTO);
        System.out.println(StringUtil.formatString(clone));
    }

    @Test
    public void serialize() {
        KuugaDTO kuugaDTO = new KuugaDTO("kuuga", 10);
        byte[] serialize = SerializationUtil.serialize(kuugaDTO);
        System.out.println(Arrays.toString(serialize));
    }

    @Test
    public void deserialize() {
        KuugaDTO kuugaDTO = new KuugaDTO("kuuga", 10);
        byte[] serialize = SerializationUtil.serialize(kuugaDTO);
        Object deserialize = SerializationUtil.deserialize(serialize);
        System.out.println(StringUtil.formatString(deserialize));
    }
}