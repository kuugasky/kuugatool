package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.enums.ByteType;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;

public class FileUtilTest {

    public static final String BASE_DIR = "/Users/kuuga/Downloads/files/";

    @BeforeAll
    static void beforeAll() throws IOException {
        FileUtil.forceMkdir(FileUtil.file(BASE_DIR));
    }

    @Test
    void file() {
        File file = FileUtil.file(BASE_DIR + "kuugaFile.txt");
        System.out.println(file.exists());
    }

    @Test
    void readBytes() {
        File file = FileUtil.file(BASE_DIR + "readBytes.txt");
        FileUtil.createFile(file, "big world");
        byte[] bytes = FileUtil.readBytes(file);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    void inputStreamToFile() throws IOException {
        File file = FileUtil.file(BASE_DIR + "inputStreamToFile.txt");
        FileUtil.createFile(file, "Kuuga-inputStreamToFile");
        File fileCopy = new File(BASE_DIR + "inputStreamToFileCopy.txt");
        FileInputStream fileInputStream = IoUtil.toInputStream(file);
        FileUtil.inputStreamToFile(fileInputStream, fileCopy);
    }

    @Test
    void createFile1() {
        File file = FileUtil.file(BASE_DIR + "kuugaFile.txt");
        FileUtil.createFile(file);
        System.out.println(file.exists());
    }

    @Test
    void createFile() {
        FileUtil.createFile(BASE_DIR + "kuugaFile1.txt");
        System.out.println(new File(BASE_DIR + "kuugaFile1.txt").exists());
    }

    @Test
    void testCreateFile() {
        File file = FileUtil.file(BASE_DIR + "kuugaFile2.txt");
        FileUtil.createFile(file, "kuuga");
        System.out.println(file.exists());
    }

    @Test
    void deleteFile() throws InterruptedException {
        FileUtil.createFile(FileUtil.file(BASE_DIR + "kuugaFile4.txt"));
        Thread.sleep(1000);
        boolean b = FileUtil.deleteFile(FileUtil.file(BASE_DIR + "kuugaFile4.txt"));
        System.out.println(b);
    }

    @Test
    void deleteAllFilesInDir() throws IOException {
        File dir = FileUtil.file(BASE_DIR + "deleteAllFilesInDir/");
        FileUtil.createDir(dir.getPath());
        for (int i = 0; i < 10; i++) {
            FileUtil.createFile(dir.getPath() + "/demo" + i + ".txt");
        }
        boolean noDelDir = FileUtil.deleteAllFilesInDir(dir, false);
        System.out.println(noDelDir);
        boolean delDir = FileUtil.deleteAllFilesInDir(dir, true);
        System.out.println(delDir);
    }

    @Test
    void createDir() throws IOException {
        FileUtil.createDir(BASE_DIR + "/createDir");
    }

    @Test
    void forceMkdir() throws IOException {
        FileUtil.forceMkdir(FileUtil.file(BASE_DIR + "/forceMkdir"));
    }

    @Test
    void forceDelete() throws IOException {
        FileUtil.createDir(FileUtil.file(BASE_DIR + "/forceDelete"));
        FileUtil.forceDelete(FileUtil.file(BASE_DIR + "/forceDelete"));
        FileUtil.createFile(FileUtil.file(BASE_DIR + "/Kuuga"));
        FileUtil.forceDelete(FileUtil.file(BASE_DIR + "/Kuuga"));
    }

    @Test
    void deleteDirectory() throws IOException {
        FileUtil.createDir(FileUtil.file(BASE_DIR + "/deleteDirectory"));
        FileUtil.forceDelete(FileUtil.file(BASE_DIR + "/deleteDirectory"));
    }

    @Test
    void getFileSizeForNetworkUrl() {
        long fileSizeForNetworkUrl = FileUtil.getFileSizeForNetworkUrl("https://imagenw.kfangcdn.com/prod/6d63c85f-bb53-486d-9a09-16c3bb77e170.jpg-f800x600");
        System.out.println(fileSizeForNetworkUrl);
        System.out.println(ByteSizeConvert.model().convertTo(fileSizeForNetworkUrl, ByteType.KB));
    }

    @Test
    void testGetFileSizeForNetworkUrl() {
        long fileSizeForNetworkUrl = FileUtil.getFileSizeForNetworkUrl("https://imagenw.kfangcdn.com/prod/6d63c85f-bb53-486d-9a09-16c3bb77e170.jpg-f800x600", "https://imagenw.kfangcdn.com");
        System.out.println(fileSizeForNetworkUrl);
        System.out.println(ByteSizeConvert.model().convertTo(fileSizeForNetworkUrl, ByteType.KB));
    }

    @Test
    void getFileSizeForPath() {
        File file = FileUtil.file(BASE_DIR + "/getFileSizeForPath.txt");
        FileUtil.createFile(file, "getFileSizeForPath");

        long fileSizeForPath = FileUtil.getFileSize(file);
        System.out.println(fileSizeForPath);
        System.out.println(ByteSizeConvert.model().convertTo(fileSizeForPath, ByteType.KB));
        FileUtil.deleteFile(file);
    }

    @Test
    void getFileSize() {
        File file = FileUtil.file("/Users/kuuga/Downloads/Win10_21H1_Chinese(Simplified)_x64.iso");
        long fileSize = FileUtil.getFileSize(file);
        System.out.println(fileSize);
        double fileSize1 = FileUtil.getFileSize(file, ByteType.MB);
        System.out.println(fileSize1 + "MB");
        double fileSize2 = FileUtil.getFileSize(file, ByteType.GB);
        System.out.println(fileSize2 + "GB");

    }

    @Test
    void generateCopyName() {
        File file = FileUtil.file(BASE_DIR + "/generateCopyName.txt");
        FileUtil.createFile(file);
        String generateCopyName = FileUtil.generateCopyName(file.getPath());
        System.out.println(generateCopyName);
    }

    @Test
    void rewriteStringToFile() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/rewriteStringToFile.txt");
        FileUtil.deleteFile(file);
        FileUtil.createFile(file, "kuuga");
        System.out.println(FileUtil.readFileToString(file));
        FileUtil.rewriteStringToFile(file, "Kuuga is me.", Charset.defaultCharset());
        System.out.println(FileUtil.readFileToString(file));
        FileUtil.rewriteStringToFile(file, "Kuuga is good boy.");
        System.out.println(FileUtil.readFileToString(file));
    }


    @Test
    void writeStringToFile() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeStringToFile.txt");
        FileUtil.writeStringToFile(file, "Kuuga is me.", Charset.defaultCharset());
    }

    @Test
    void testWriteStringToFile() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeStringToFile.txt");
        FileUtil.writeStringToFile(file, "\ngood day.");
        FileUtil.writeStringToFile(file, "\ngood day.", Charset.defaultCharset());
    }

    @Test
    void writeStringToFileNewline() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeStringToFileNewlineAppend.txt");
        FileUtil.deleteFile(file);
        FileUtil.createFile(file, "kuuga1");
        FileUtil.writeStringToFileNewline(file, "kuuga2");
        FileUtil.writeStringToFileNewline(file, "kuuga2", Charset.defaultCharset());
    }

    @Test
    void writeStringToFileByWriter() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeStringToFileByWriter.txt");
        FileUtil.writeStringToFileByWriter(file, "kuuga");
    }

    @Test
    void writeStringToFileByBuffered() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeStringToFileByBuffered.txt");
        FileUtil.writeStringToFileByBuffered(file, "kuuga");
    }

    @Test
    void writeLines() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeLines.txt");
        FileUtil.writeLines(file, ListUtil.newArrayList(1, 2, 3, 4, 5));
    }

    @Test
    void testWriteLines() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeLines.txt");
        FileUtil.writeLines(file, ListUtil.newArrayList(1, 2, 3, 4, 5), "-x");
    }

    @Test
    void testWriteLines1() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeLines.txt");
        FileUtil.writeLines(file, Charset.defaultCharset(), ListUtil.newArrayList(1, 2, 3, 4, 5));
    }

    @Test
    void testWriteLines2() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/writeLines.txt");
        FileUtil.writeLines(file, Charset.defaultCharset(), ListUtil.newArrayList(1, 2, 3, 4, 5), "-x");
    }

    @Test
    void rewriteLines() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/rewriteLines.txt");
        FileUtil.rewriteLines(file, ListUtil.newArrayList(1, 2, 3, 4, 5), "-x");
    }

    @Test
    void rewriteLines1() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/rewriteLines1.txt");
        FileUtil.rewriteLines(file, ListUtil.newArrayList(1, 2, 3, 4, 5));
    }

    @Test
    void rewriteLines2() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/rewriteLines2.txt");
        FileUtil.rewriteLines(file, Charset.defaultCharset(), ListUtil.newArrayList(1, 2, 3, 4, 5), "-x");
    }

    @Test
    void readFileToString() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/readFileToString.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToString(file));
        System.out.println(FileUtil.readFileToString(file, Charset.defaultCharset()));
    }

    @Test
    void readFileToStringByFileReader() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/readFileToStringByFileReader.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToStringByFileReader(file));
    }

    @Test
    void readFileToStringByFileInputStream() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/readFileToStringByFileInputStream.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToStringByFileInputStream(file));
    }

    @Test
    void readFileToStringByBufferedReader() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/readFileToStringByBufferedReader.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToStringByBufferedReader(file));
        System.out.println(FileUtil.readFileToStringByBufferedReader(file, Charset.defaultCharset()));
    }

    @Test
    void copyFile() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyFile.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        FileUtil.copyFile(file, new File(BASE_DIR + "/copyFile1.txt"));
        FileUtil.copyFile(file, new File(BASE_DIR + "/copyFile2.txt"), true);
    }

    @Test
    void copyFileByFileWriter() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyFileByFileWriter.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        FileUtil.copyFileByFileWriter(file, new File(BASE_DIR + "/copyFile1.txt"));
    }

    @Test
    void copyFileByFileOutputStream() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyFileByFileOutputStream.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        FileUtil.copyFileByFileOutputStream(file, new File(BASE_DIR + "/copyFile1.txt"));
    }

    @Test
    void copyFileByBufferedWriter() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyFileByBufferedWriter.txt");
        FileUtil.writeStringToFile(file, "kuuga");
        FileUtil.copyFileByBufferedWriter(file, new File(BASE_DIR + "/copyFile1.txt"));
    }

    @Test
    void copyDirectory() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyDirectory");
        FileUtil.createDir(file);
        FileUtil.copyDirectory(file, new File(BASE_DIR + "/copyDirectory2"));
    }

    @Test
    void testCopyDirectory() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyDirectory");
        FileUtil.createDir(file);
        FileUtil.copyDirectory(file, new File(BASE_DIR + "/copyDirectory2"), true);
    }

    @Test
    void testCopyDirectory1() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyDirectory");
        FileUtil.createDir(file);
        FileFilter filter = pathname -> pathname.getName().equals("kuuga");
        FileUtil.copyDirectory(file, new File(BASE_DIR + "/copyDirectory2"), filter);
    }

    @Test
    void testCopyDirectory2() throws IOException {
        File file = FileUtil.file(BASE_DIR + "/copyDirectory");
        FileUtil.createDir(file);
        FileFilter filter = pathname -> pathname.getName().equals("kuuga");
        FileUtil.copyDirectory(file, new File(BASE_DIR + "/copyDirectory2"), filter, true);
    }

    @Test
    void appendToFirstPlace() {
        File file = FileUtil.file(BASE_DIR + "/appendToFirstPlace.txt");
        FileUtil.createFile(file, "kuuga");
        boolean xyz = FileUtil.appendToFirstPlace(file, "xyz");
        System.out.println(xyz);
    }

    @Test
    void appendToLastPlace() {
        File file = FileUtil.file(BASE_DIR + "/appendToLastPlace.txt");
        FileUtil.createFile(file, "kuuga");
        boolean xyz = FileUtil.appendToLastPlace(file, "xyz");
        System.out.println(xyz);
    }

    @Test
    void getOutputStream() {
        File file = FileUtil.file(BASE_DIR + "/getOutputStream.txt");
        BufferedOutputStream outputStream = FileUtil.getOutputStream(file);
        System.out.println(outputStream);
    }

    @Test
    void getInputStream() {
        File file = FileUtil.file(BASE_DIR + "/getInputStream.txt");
        FileUtil.createFile(file);
        System.out.println(FileUtil.getInputStream(file));
    }

    @Test
    void isDirectory() {
        File file = FileUtil.file(BASE_DIR + "/getInputStream.txt");
        FileUtil.createFile(file);
        System.out.println(FileUtil.isDirectory(file));
    }

    @Test
    void listFiles() {
        Collection<File> files = FileUtil.listFiles(FileUtil.file("/Users/kuuga/Downloads"), null, false);
        files.forEach(file -> System.out.println(file.getName()));
    }

    @Test
    void listFiles1() {
        Collection<File> files = FileUtil.listFiles(FileUtil.file("/Users/kuuga/Downloads"), false);
        files.forEach(file -> System.out.println(file.getName()));
    }

    @Test
    void getLastPathEle() {
        File file = FileUtil.file(BASE_DIR + "getLastPathEle.txt");
        FileUtil.createFile(file);
        Path path = file.toPath();
        Path lastPathEle = FileUtil.getLastPathEle(path);
        System.out.println(lastPathEle.toString());
    }

    @Test
    void getPathEle() {
        File file = FileUtil.file(BASE_DIR + "getLastPathEle.txt");
        FileUtil.createFile(file);
        Path path = file.toPath();
        System.out.println(FileUtil.getPathEle(path, -1).toString());
        System.out.println(FileUtil.getPathEle(path, 0).toString());
        System.out.println(FileUtil.getPathEle(path, 1).toString());
        System.out.println(FileUtil.getPathEle(path, 2).toString());
        System.out.println(FileUtil.getPathEle(path, 3).toString());
        System.out.println(FileUtil.getPathEle(path, 4).toString());
        System.out.println(FileUtil.getPathEle(path, 5).toString());
    }

    @Test
    void subPath() {
        File file = FileUtil.file(BASE_DIR + "subPath.txt");
        FileUtil.createFile(file);
        Path path = file.toPath();
        System.out.println(FileUtil.subPath(path, 0, 1).toString());
        System.out.println(FileUtil.subPath(path, 1, 2).toString());
        System.out.println(FileUtil.subPath(path, 2, 3).toString());
    }

    @Test
    void mismatch() throws IOException {
        // 创建两个文件
        Path pathA = Files.createFile(Paths.get("/Users/kuuga/Downloads/a.txt"));
        Path pathB = Files.createFile(Paths.get("/Users/kuuga/Downloads/b.txt"));

        // 写入相同内容
        Files.write(pathA, "abc".getBytes(), StandardOpenOption.WRITE);
        Files.write(pathB, "abc".getBytes(), StandardOpenOption.WRITE);

        boolean match = FileUtil.match(pathA, pathB);
        System.out.println("文件是否匹配:" + match);
        System.out.println("文件是否匹配:" + FileUtil.match(pathA.toFile(), pathB.toFile()));

        // 追加不同内容
        Files.write(pathA, "123".getBytes(), StandardOpenOption.APPEND);
        Files.write(pathB, "321".getBytes(), StandardOpenOption.APPEND);
        // 文件内容是否不匹配
        boolean mismatch = FileUtil.mismatch(pathA, pathB);
        System.out.println("文件是否不匹配:" + mismatch);
        System.out.println("文件是否不匹配:" + FileUtil.mismatch(pathA.toFile(), pathB.toFile()));

        // 删除创建的文件
        pathA.toFile().deleteOnExit();
        pathB.toFile().deleteOnExit();
    }

    @Test
    void deleteIfExists() throws IOException {
        File file = FileUtil.file("/Users/kuuga/Downloads/deleteIfExists.txt");
        FileUtil.createFile(file);
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToString(file));
        FileUtil.deleteIfExists(file);
        System.out.println(file.exists());
    }

    @Test
    void delete() throws IOException {
        File file = FileUtil.file("/Users/kuuga/Downloads/delete.txt");
        FileUtil.createFile(file);
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToString(file));
        FileUtil.delete(file);
        System.out.println(file.exists());

        File folder = FileUtil.file("/Users/kuuga/Downloads/delete");
        FileUtil.createFile(folder);
        System.out.println(folder.exists());
        FileUtil.delete(folder);
        System.out.println(folder.exists());
    }

    @Test
    void deleteOnExit() throws IOException {
        File file = FileUtil.file("/Users/kuuga/Downloads/delete.txt");
        FileUtil.createFile(file);
        FileUtil.writeStringToFile(file, "kuuga");
        System.out.println(FileUtil.readFileToString(file));
        FileUtil.deleteOnExit(file);
        System.out.println(file.exists());
    }

    @Test
    void append() throws IOException {
        File file = FileUtil.file("/Users/kuuga/Downloads/append.txt");
        FileUtil.createFile(file);
        FileUtil.append(file, "kuuga");
        FileUtil.append(file, " ");
        FileUtil.append(file, "is os.");
    }
}