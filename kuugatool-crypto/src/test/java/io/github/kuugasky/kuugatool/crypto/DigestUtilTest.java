package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class DigestUtilTest {

    @Test
    public void md5() throws NoSuchAlgorithmException {
        System.out.println(DigestUtil.md5("kuugasky"));
    }

    @Test
    public void sha1() throws NoSuchAlgorithmException {
        System.out.println(DigestUtil.sha1("kuugasky"));
    }

    @Test
    public void testSha1() throws NoSuchAlgorithmException {
        System.out.println(DigestUtil.sha1("kuugasky", DigestUtil.ShaAlgorithmEnum.SHA_1));
        System.out.println(DigestUtil.sha1("kuugasky", DigestUtil.ShaAlgorithmEnum.SHA_256));
        System.out.println(DigestUtil.sha1("kuugasky", DigestUtil.ShaAlgorithmEnum.SHA_512));
    }
}