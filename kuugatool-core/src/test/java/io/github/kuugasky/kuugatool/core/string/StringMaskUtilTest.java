package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.enums.SignLocation;
import org.junit.jupiter.api.Test;

public class StringMaskUtilTest {

    @Test
    public void shieldStrCustomize() {
        // 1380**38000
        System.out.println(StringMaskUtil.mask("13800138000", 2));
        System.out.println(StringMaskUtil.mask("13800138000", 40));
        System.out.println(StringMaskUtil.mask("0755-8888888", 4));
    }

    @Test
    public void testShieldStrCustomize() {
        // 138*****000
        System.out.println(StringMaskUtil.mask("13800138000", SignLocation.CENTER, 5));
    }

    @Test
    public void testShieldStrCustomize1() {
        // 138-----000
        System.out.println(StringMaskUtil.mask("13800138000", SignLocation.CENTER, '-', 5));
    }

    @Test
    public void shieldMobile() {
        // 138****8000
        System.out.println(StringMaskUtil.mask("13800138000", 4));
        System.out.println(StringMaskUtil.mask("13800138000", SignLocation.CENTER, 4));
    }

    @Test
    public void testShieldMobile2() {
        // 1380***8000
        System.out.println(StringMaskUtil.mask("13800138000", SignLocation.CENTER, '*', 3));
    }

}