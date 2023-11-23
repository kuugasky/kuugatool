package io.github.kuugasky.kuugatool.core.random;

import org.junit.jupiter.api.Test;

public class RandomCodeUtilTest {

    @Test
    public void getCode() {
        // 随机6位数密码
        System.out.println(RandomCodeUtil.getCode());
    }

    @Test
    public void getCode1() {
        // 自定义密码长度密码
        System.out.println(RandomCodeUtil.getCode(4));
    }

    @Test
    public void getMixedCode() {
        // 随机6位数非纯数字密码
        System.out.println(RandomCodeUtil.getMixedCode());
    }

    @Test
    public void getMixedCode1() {
        // 随机6位数密码
        System.out.println(RandomCodeUtil.getMixedCode(4));
    }

}