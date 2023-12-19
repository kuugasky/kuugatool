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

}