package io.github.kuugasky.kuugatool.extra.zip;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class UnZipUtilTest {

    private final File file = FileUtil.file("/Users/kuuga/Downloads/UnZipUtilTest.txt");
    private final File zipFile = FileUtil.file("/Users/kuuga/Downloads/UnZipUtilTest.zip");

    @BeforeEach
    void beforeEach() throws IOException {
        FileUtil.createFile(file, "test");
        ZipUtil.zip(file, zipFile);
        FileUtil.deleteFile(file);
    }

    @Test
    void unzip() throws Exception {
        UnZipUtil.unzip(zipFile, "/Users/kuuga/Downloads/unzip/");
        UnZipUtil.unzip(zipFile.getPath(), "/Users/kuuga/Downloads/unzip/");
    }

    @Test
    void testUnzip() throws Exception {
        UnZipUtil.unzip("/Users/kuuga/Downloads/kuuga1.zip", "/Users/kuuga/Downloads/unzip/", true);
    }

    @Test
    void testUnzip2() throws Exception {
        UnZipUtil.unzip("/Users/kuuga/Downloads/Kuuga-jpg.zip", "/Users/kuuga/Downloads/unzip/", true);
    }

}