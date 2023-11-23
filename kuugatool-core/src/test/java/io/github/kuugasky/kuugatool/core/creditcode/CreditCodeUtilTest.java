package io.github.kuugasky.kuugatool.core.creditcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreditCodeUtilTest {

    @Test
    public void isCreditCodeSimple() {
        // 统一社会信用代码
        String testCreditCode = "91310115591693856A";
        Assert.assertTrue(CreditCodeUtil.isCreditCodeSimple(testCreditCode));
    }

    @Test
    public void isCreditCode() {
        // 统一社会信用代码
        String testCreditCode = "91310110666007217T";
        Assert.assertTrue(CreditCodeUtil.isCreditCode(testCreditCode));
    }

    @Test
    public void randomCreditCode() {
        final String s = CreditCodeUtil.randomCreditCode();
        System.out.println(s);
        System.out.println(CreditCodeUtil.isCreditCode(s));
    }

}