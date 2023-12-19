package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

class WordOfCommandUtilTest {

    @Test
    void generateSalt() throws NoSuchAlgorithmException {
        String salt = WordOfCommandUtil.generateSalt();
        System.out.println(salt);
    }

    @Test
    void encryptAndDecrypt() throws GeneralSecurityException {
        String password = "kuugaX0@4~1&0!";
        String text = "新年新气象.";

        String salt = WordOfCommandUtil.generateSalt();
        System.out.printf("加密盐:%s%n", salt);
        String ciphertext = WordOfCommandUtil.encrypt(password, salt, text);
        System.out.printf("密文:%s%n", ciphertext);
        String plaintext = WordOfCommandUtil.decrypt(password, salt, ciphertext);
        System.out.printf("明文:%s%n", plaintext);
    }

    @Test
    void encryptAndDecrypt2() throws GeneralSecurityException {
        String password = "kuugaX0@4~1&0!";
        String text = "新年新气象.";

        String ciphertext = WordOfCommandUtil.encrypt(password, text);
        System.out.printf("密文:%s%n", ciphertext);
        String plaintext = WordOfCommandUtil.decrypt(password, ciphertext);
        System.out.printf("明文:%s%n", plaintext);
    }

}
