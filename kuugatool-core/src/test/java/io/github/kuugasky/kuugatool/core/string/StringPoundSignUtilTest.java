package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.enums.ByteType;
import org.junit.jupiter.api.Test;

/**
 * 井号符文本工具类
 */
public class StringPoundSignUtilTest {

    @Test
    public void joinSign() {
        System.out.println(StringPoundSignUtil.joinSign(ListUtil.newArrayList("1", StringUtil.EMPTY, null, "3", "2")));
    }

    @Test
    public void joinSignEnum() {
        System.out.println(StringPoundSignUtil.joinSignEnum(ListUtil.newArrayList(ByteType.MB, null, ByteType.GB, null)));
    }

    @Test
    public void parseSign() {
        System.out.println(StringPoundSignUtil.parseSign("#1#3#2#"));
    }

    @Test
    public void parseSignEnum() {
        System.out.println(StringPoundSignUtil.parseSignEnum("#MB##G#KB#", ByteType.class));
    }

}