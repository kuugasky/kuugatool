package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

class HexUtilTest {

    @Test
    void hexStringToBytes() {
        byte[] bytes = HexUtil.hexStringToBytes("kuugasky");
        System.out.println(bytes.length);
        System.out.println(HexUtil.byteToHexString(bytes));
    }

    @Test
    void byteToHexString() {
        byte[] bytes = HexUtil.hexStringToBytes("kuugasky");
        String string = HexUtil.byteToHexString(bytes);
        System.out.println(string);
    }

    @Test
    void test() {
        String text = "kuuga";
        // 二进制转16进制字符串
        String string = HexUtil.byteToHexString(text.getBytes());
        System.out.println(string);
        // 16进制转bytes
        byte[] bytes = HexUtil.hexStringToBytes(string);
        System.out.println(new String(bytes));
    }

    @Test
    void test1() {
        String text = "kuuga";
        // 二进制转16进制字符串
        String hexString = HexUtil.stringToHexString(text);
        System.out.println(hexString);
        String string = HexUtil.hexStringToString(hexString);
        System.out.println(string);
    }

}