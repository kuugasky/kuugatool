package io.github.kuugasky.kuugatool.core.io.bytes;

import io.github.kuugasky.kuugatool.core.enums.ByteType;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

class ByteSizeConvertTest {

    @Test
    void test() {
        ByteSizeConvert byteSizeConvert = ByteSizeConvert.model(ByteModel.NORMAL).setScale(2, RoundingMode.UP);
        for (ByteType value : ByteType.values()) {
            System.out.println(byteSizeConvert.convertTo(new BigDecimal("0010000000000000000"), value) + value.name());
            System.out.println(byteSizeConvert.convertTo(1, value) + value.name());
            System.out.println(byteSizeConvert.convertTo(10000L, value) + value.name());
        }
    }

    @Test
    void test1() {
        Collection<File> files = FileUtil.listFiles(FileUtil.file("/Users/kuuga/Downloads/"), null, false);
        ByteSizeConvert byteSizeConvert = ByteSizeConvert.model(ByteModel.MAC).setScale(2, RoundingMode.HALF_EVEN);
        ByteSizeConvert byteSizeConvert1 = ByteSizeConvert.model(ByteModel.MAC).setScale(2);
        ByteSizeConvert byteSizeConvert2 = ByteSizeConvert.model(ByteModel.MAC).setScale(RoundingMode.HALF_EVEN);
        files.forEach(file -> {
            if (file.isFile()) {
                long fileSizeForPath = FileUtil.getFileSize(file);
                System.out.println(file.getName() + "--->" + byteSizeConvert.format(fileSizeForPath));
                System.out.println(file.getName() + "--->" + byteSizeConvert1.format(1));
                System.out.println(file.getName() + "--->" + byteSizeConvert2.format(new BigDecimal(fileSizeForPath)));
            }
        });
    }

    @Test
    void test2() {
        File file = FileUtil.file("/Users/kuuga/Downloads/Win10_21H1_Chinese(Simplified)_x64.iso");
        ByteSizeConvert byteSizeConvert = ByteSizeConvert.model(ByteModel.MAC).setScale(2, RoundingMode.HALF_EVEN);
        long fileSizeForPath = FileUtil.getFileSize(file);
        System.out.println(file.getName() + "--->" + byteSizeConvert.format(fileSizeForPath));
    }

}