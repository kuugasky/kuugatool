package io.github.kuugasky.kuugatool.core.io.unit;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteModel;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.RoundingMode;

public class DataSizeUtilTest {

    @Test
    void parse() {
        // 解析数据大小字符串，转换为bytes大小
        System.out.println(DataSizeUtil.parse("10000000000"));
    }

    @Test
    void format() {
        // 可读的文件大小
        System.out.println(DataSizeUtil.format(10_000_000_000L));
    }

    @Test
    void test() {
        File file = FileUtil.file("/Users/kuuga/Downloads/iNodeManager_H3C_MacOS_7.30(E0583).tar(1).gz");
        long fileSize = FileUtil.getFileSize(file);
        DataSize dataSize = DataSize.of(fileSize, DataUnit.BYTES);
        long megabytes = dataSize.toMegabytes();
        System.out.println(megabytes + "MB");
        String format = DataSizeUtil.format(fileSize);
        System.out.println(format);

        System.out.println(ByteSizeConvert.model().format(fileSize));
        System.out.println(ByteSizeConvert.model(ByteModel.MAC).setScale(1, RoundingMode.FLOOR).format(fileSize));
    }

    @Test
    void isNegative() {
        DataSize dataSize = DataSize.ofBytes(-1);
        System.out.println(dataSize.isNegative());
    }

    @Test
    void ofKilobytes() {
        DataSize dataSize = DataSize.ofKilobytes(-1);
        System.out.println(dataSize.isNegative());
    }

    @Test
    void ofMegabytes() {
        DataSize dataSize = DataSize.ofMegabytes(-1);
        System.out.println(dataSize.isNegative());
    }

    @Test
    void ofGigabytes() {
        DataSize dataSize = DataSize.ofGigabytes(-1);
        System.out.println(dataSize.isNegative());
    }

    @Test
    void ofTerabytes() {
        DataSize dataSize = DataSize.ofTerabytes(-1);
        System.out.println(dataSize.isNegative());
    }

    @Test
    void of() {
        DataSize dataSize = DataSize.of(1000, DataUnit.GIGABYTES);
        System.out.println(dataSize.isNegative());
        System.out.println(dataSize.toKilobytes());
        System.out.println(dataSize.toGigabytes());
        System.out.println(dataSize.toTerabytes());
        System.out.println(dataSize);
    }

}