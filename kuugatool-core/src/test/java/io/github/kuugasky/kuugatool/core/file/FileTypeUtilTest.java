package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.io.IoUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;

class FileTypeUtilTest {

    @Test
    void putFileType() {
        String Kuuga = FileTypeUtil.putFileType("kuuga", ".Kuuga");
        System.out.println(Kuuga);
    }

    @Test
    void removeFileType() {
        String ffd8ff = FileTypeUtil.removeFileType("ffd8ff");
        System.out.println(ffd8ff);
    }

    @Test
    void getType() {
        File file = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/file/FileTypeUtilTest.java");
        String type = FileTypeUtil.getType(file);
        System.out.println(type);
    }

    @Test
    void testGetType() {
        File file = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/file/FileTypeUtilTest.java");
        FileInputStream fileInputStream = IoUtil.toInputStream(file);
        System.out.println(FileTypeUtil.getType(fileInputStream));

        File file1 = FileUtil.file("/Users/kuuga/Downloads/iShot_2022-06-23_17.10.54.png");
        FileInputStream fileInputStream1 = IoUtil.toInputStream(file1);
        System.out.println(FileTypeUtil.getType(fileInputStream1));
    }

    @Test
    void testGetType1() {
        File file = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/file/FileTypeUtilTest.java");
        FileInputStream fileInputStream = IoUtil.toInputStream(file);
        String readHex28Lower = IoUtil.readHex28Lower(fileInputStream);
        System.out.println(FileTypeUtil.getType(readHex28Lower));
    }

    @Test
    void getTypeByPath() {
        File file = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/file/FileTypeUtilTest.java");
        System.out.println(FileTypeUtil.getTypeByPath(file.getPath()));
    }

}