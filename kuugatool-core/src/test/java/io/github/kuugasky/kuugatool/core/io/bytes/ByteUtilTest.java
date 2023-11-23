package io.github.kuugasky.kuugatool.core.io.bytes;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.unit.DataSizeUtil;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

class ByteUtilTest {

    @Test
    void isEmpty() {
        System.out.println(ByteUtil.isEmpty(new byte[]{}));
    }

    @Test
    void isNotEmpty() {
        System.out.println(ByteUtil.hasItem(new byte[]{1}));
    }

    @Test
    void getBytes() {
        System.out.println(Arrays.toString(ByteUtil.getBytes("kuuga")));
    }

    @Test
    void testGetBytes() {
        System.out.println(Arrays.toString(ByteUtil.getBytes("kuuga", Charset.defaultCharset())));
    }

    @Test
    void convert() {
        long fileSizeForPath = FileUtil.getFileSize(FileUtil.file("/Users/kuuga/Downloads/wc13221.rar"));
        System.out.println(ByteSizeConvert.model().format(fileSizeForPath));
        System.out.println(DataSizeUtil.format(fileSizeForPath));
    }

}