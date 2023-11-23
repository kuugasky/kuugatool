package io.github.kuugasky.kuugatool.core.io;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;

public class IoUtilTest {

    public static void main(String[] args) throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target));
    }

    @Test
    public void copy() throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target));
    }

    @Test
    public void testCopy() throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target), 1024);
    }

    @Test
    public void testCopy1() throws IOException {
        File source = new File("/Users/kuuga/Downloads/3.png");
        File target = new File("/Users/kuuga/Downloads/4.png");
        long copy = IoUtil.copy(new FileReader(source), new FileWriter(target), NioUtil.DEFAULT_BUFFER_SIZE, new StreamProgress() {
            @Override
            public void start() {
                System.out.println("progress start");
            }

            @Override
            public void progress(long progressSize) {
                System.out.println("progress" + progressSize);
            }

            @Override
            public void finish() {
                System.out.println("progress finish");
            }
        });
        System.out.println("copy = " + copy);
    }

    @Test
    public void testCopy2() {
        File source = new File("/Users/kuuga/Downloads/11.jpg");
        File target = new File("/Users/kuuga/Downloads/22.jpg");
        // FileUtil.copyFile(source.getPath(), "/Users/kuuga/Downloads/", "22.jpg");
        // FileUtil.copyFile(source, target);
        FileUtil.copyFileByBufferedWriter(source, target);
    }

    @Test
    public void testCopy3() {
        File source = new File("/Users/kuuga/Downloads/33.txt");
        File target = new File("/Users/kuuga/Downloads/44.txt");
        FileUtil.copyFileByFileWriter(source, target);
    }

    @Test
    public void testCopy4() {
    }

    @Test
    public void testCopy5() {
    }

    @Test
    public void getUtf8Reader() {
    }

    @Test
    public void getReader() {
    }

    @Test
    public void testGetReader() {
    }

    @Test
    public void testGetReader1() {
    }

    @Test
    public void getPushBackReader() {
    }

    @Test
    public void getUtf8Writer() {
    }

    @Test
    public void getWriter() {
    }

    @Test
    public void testGetWriter() {
    }

    @Test
    public void readUtf8() {
    }

    @Test
    public void read() {
    }

    @Test
    public void testRead() {
    }

    @Test
    public void testRead1() {
    }

    @Test
    public void testRead2() {
    }

    @Test
    public void testRead3() {
    }

    @Test
    public void testRead4() {
    }

    @Test
    public void readBytes() {
    }

    @Test
    public void testReadBytes() {
    }

    @Test
    public void testReadBytes1() {
    }

    @Test
    public void readHex() {
    }

    @Test
    public void readHex28Upper() {
    }

    @Test
    public void readHex28Lower() {
    }

    @Test
    public void readObj() {
    }

    @Test
    public void testReadObj() {
    }

    @Test
    public void testReadObj1() {
    }

    @Test
    public void readUtf8Lines() {
    }

    @Test
    public void readLines() {
    }

    @Test
    public void testReadLines() {
    }

    @Test
    public void testReadLines1() {
    }

    @Test
    public void testReadUtf8Lines() {
    }

    @Test
    public void testReadLines2() {
    }

    @Test
    public void testReadLines3() {
    }

    @Test
    public void toStream() {
    }

    @Test
    public void testToStream() {
    }

    @Test
    public void toUtf8Stream() {
    }

    @Test
    public void testToStream1() {
    }

    @Test
    public void testToStream2() {
    }

    @Test
    public void toBuffered() {
    }

    @Test
    public void testToBuffered() {
    }

    @Test
    public void toMarkSupportStream() {
    }

    @Test
    public void toPushbackStream() {
    }

    @Test
    public void toAvailableStream() {
    }

    @Test
    public void write() {
    }

    @Test
    public void writeUtf8() {
    }

    @Test
    public void testWrite() {
    }

    @Test
    public void testWrite1() {
    }

    @Test
    public void writeObj() {
    }

    @Test
    public void writeObjects() {
    }

    @Test
    public void flush() {
        // IoUtil.flush();
    }

    @Test
    public void close() {
    }

    @Test
    public void closeIfPosible() {
    }

    @Test
    public void contentEquals() {
    }

    @Test
    public void testContentEquals() {
    }

    @Test
    public void contentEqualsIgnoreEOL() {
    }

    @Test
    public void checksumCRC32() {
    }

    @Test
    public void checksum() {
    }

    @Test
    public void checksumValue() {
    }

    @Test
    void copyTransferTo() throws IOException {
        String path = "/Users/kuuga/Downloads/ioUtil/copyTransferTo";
        FileUtil.createDir(path);
        File inputFile = FileUtil.file(path + "/input.txt");
        FileUtil.createFile(inputFile, "kuuga");
        FileInputStream fileInputStream = IoUtil.toInputStream(inputFile);
        FileOutputStream fileOutputStream = IoUtil.toOutputStream(FileUtil.file("out.txt"));
        IoUtil.copyTransferTo(fileInputStream, fileOutputStream);
        BufferedReader utf8Reader = IoUtil.getUtf8Reader(fileInputStream);
        System.out.println(utf8Reader);
        Reader reader = IoUtil.getReader(fileInputStream, Charset.defaultCharset());
        System.out.println(reader);
        PushbackReader pushBackReader = IoUtil.getPushBackReader(reader, 10);
        System.out.println(pushBackReader);
        OutputStreamWriter utf8Writer = IoUtil.getUtf8Writer(fileOutputStream);
        System.out.println(utf8Writer);
        OutputStreamWriter writer = IoUtil.getWriter(fileOutputStream, Charset.defaultCharset());
        System.out.println(writer);
        String s = IoUtil.readUtf8(fileInputStream);
        System.out.println(s);


    }

}