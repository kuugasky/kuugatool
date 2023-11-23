package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FilesUtilTest {

    @Test
    public void isDirectory() {
        File file = new File("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/main/java/cn/kuugatool/core/file");
        System.out.println(FilesUtil.isDirectory(file));
    }

    @Test
    public void isFile() {
        File file = new File("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/main/java/cn/kuugatool/core/file/FilesUtil.java");
        System.out.println(FilesUtil.isFile(file));
    }

    @Test
    public void write() throws IOException {
        File file = new File("/Users/kuuga/Downloads/FilesWrite.txt");
        FilesUtil.write("Kuuga is me.".getBytes(), file);
    }

    @Test
    public void readLines() throws IOException {
        File file = new File("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/file/FilesUtilTest.java");
        List<String> strings = FilesUtil.readLines(file, Charset.defaultCharset());
        ListUtil.optimize(strings).forEach(System.out::println);
    }

    @Test
    public void copy() throws IOException {
        File file = new File("/Users/kuuga/Downloads/FilesWrite.txt");
        File file1 = new File("/Users/kuuga/Downloads/FilesWrite1.txt");
        FilesUtil.copy(file, file1);
    }

    @Test
    public void move() throws IOException {
        File file = new File("/Users/kuuga/Downloads/FilesWrite.txt");
        File file1 = new File("/Users/kuuga/Downloads/FilesWrite2.txt");
        FilesUtil.move(file, file1);
    }

    @Test
    public void getFileExtension() {
        File file = new File("/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-core/src/main/java/cn/kuugatool/core/file/FilenameUtil.java");
        System.out.println(FilesUtil.getFileExtension(file.getPath()));
    }

    @Test
    public void getNameWithoutExtension() {
        File file = new File("/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-core/src/main/java/cn/kuugatool/core/file/FilenameUtil.java");
        System.out.println(FilesUtil.getNameWithoutExtension(file.getPath()));
    }

}