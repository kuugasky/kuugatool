package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

public class IdUtilTest {

    @Test
    void test() {
        System.out.println("32位UUID：" + IdUtil.randomUUID());
        System.out.println("简化UUID：" + IdUtil.simpleUUID());
        System.out.println("根据字符串生成UUID：" + IdUtil.randomUUID("kuuga"));
        System.out.println("根据日期生成长整型ID：" + IdUtil.getLongId());
        System.out.println("雪花ID：" + IdUtil.getSnowflakeId());
    }

}