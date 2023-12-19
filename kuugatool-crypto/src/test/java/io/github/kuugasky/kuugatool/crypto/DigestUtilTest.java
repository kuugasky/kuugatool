package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class DigestUtilTest {

    @Test
    public void md5() throws NoSuchAlgorithmException {
        System.out.println(DigestUtil.md5("kuugasky"));
        System.out.println(MD5Util.getMd5("kuugasky"));
    }

    @Test
    public void sha1() throws NoSuchAlgorithmException {
        // 40长度
        System.out.println(DigestUtil.sha1("kuugasky"));
        // 32长度
        System.out.println(MD5Util.getMd5("kuugasky"));
    }

    @Test
    public void testSha1() throws NoSuchAlgorithmException {
        String randomString = RandomUtil.randomString(RandomUtil.randomInt(10, 100));
        System.out.println("random string:" + randomString);
        String sha1 = DigestUtil.sha1(randomString, DigestUtil.ShaAlgorithmEnum.SHA_1);
        System.out.println(sha1);
        System.out.println(sha1.length() == DigestUtil.ShaAlgorithmEnum.SHA_1.getCiphertextLength());
        String sha256 = DigestUtil.sha1(randomString, DigestUtil.ShaAlgorithmEnum.SHA_256);
        System.out.println(sha256);
        System.out.println(sha256.length() == DigestUtil.ShaAlgorithmEnum.SHA_256.getCiphertextLength());
        String sha512 = DigestUtil.sha1(randomString, DigestUtil.ShaAlgorithmEnum.SHA_512);
        System.out.println(sha512);
        System.out.println(sha512.length() == DigestUtil.ShaAlgorithmEnum.SHA_512.getCiphertextLength());
    }
}