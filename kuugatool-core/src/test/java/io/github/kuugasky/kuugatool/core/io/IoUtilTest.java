package io.github.kuugasky.kuugatool.core.io;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;

class IoUtilTest {

    public static void main(String[] args) throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target));
    }

    @Test
    void copy() throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target));
    }

    @Test
    void testCopy() throws IOException {
        File source = new File("/Users/kuuga/Downloads/1.jpg");
        File target = new File("/Users/kuuga/Downloads/2.jpg");
        IoUtil.copy(new FileReader(source), new FileWriter(target), 1024);
    }

    @Test
    void testCopy1() throws IOException {
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
    void testCopy2() {
        File source = new File("/Users/kuuga/Downloads/11.jpg");
        File target = new File("/Users/kuuga/Downloads/22.jpg");
        // FileUtil.copyFile(source.getPath(), "/Users/kuuga/Downloads/", "22.jpg");
        // FileUtil.copyFile(source, target);
        FileUtil.copyFileByBufferedWriter(source, target);
    }

    @Test
    void testCopy3() {
        File source = new File("/Users/kuuga/Downloads/33.txt");
        File target = new File("/Users/kuuga/Downloads/44.txt");
        FileUtil.copyFileByFileWriter(source, target);
    }

    @Test
    void testCopy4() {
    }

    @Test
    void testCopy5() {
    }

    @Test
    void getUtf8Reader() {
    }

    @Test
    void getReader() {
    }

    @Test
    void testGetReader() {
    }

    @Test
    void testGetReader1() {
    }

    @Test
    void getPushBackReader() {
    }

    @Test
    void getUtf8Writer() {
    }

    @Test
    void getWriter() {
    }

    @Test
    void testGetWriter() {
    }

    @Test
    void readUtf8() {
    }

    @Test
    void read() {
    }

    @Test
    void testRead() {
    }

    @Test
    void testRead1() {
    }

    @Test
    void testRead2() {
    }

    @Test
    void testRead3() {
    }

    @Test
    void testRead4() {
    }

    @Test
    void readBytes() {
    }

    @Test
    void testReadBytes() {
    }

    @Test
    void testReadBytes1() {
    }

    @Test
    void readHex() {
    }

    @Test
    void readHex28Upper() {
    }

    @Test
    void readHex28Lower() {
    }

    @Test
    void readObj() {
    }

    @Test
    void testReadObj() {
    }

    @Test
    void testReadObj1() {
    }

    @Test
    void readUtf8Lines() {
    }

    @Test
    void readLines() {
    }

    @Test
    void testReadLines() {
    }

    @Test
    void testReadLines1() {
    }

    @Test
    void testReadUtf8Lines() {
    }

    @Test
    void testReadLines2() {
    }

    @Test
    void testReadLines3() {
    }

    @Test
    void toStream() {
    }

    @Test
    void testToStream() {
    }

    @Test
    void toUtf8Stream() {
    }

    @Test
    void testToStream1() {
    }

    @Test
    void testToStream2() {
    }

    @Test
    void toBuffered() {
    }

    @Test
    void testToBuffered() {
    }

    @Test
    void toMarkSupportStream() {
    }

    @Test
    void toPushbackStream() {
    }

    @Test
    void toAvailableStream() {
    }

    @Test
    void write() {
    }

    @Test
    void writeUtf8() {
    }

    @Test
    void testWrite() {
    }

    @Test
    void testWrite1() {
    }

    @Test
    void writeObj() {
    }

    @Test
    void writeObjects() {
    }

    @Test
    void flush() {
        // IoUtil.flush();
    }

    @Test
    void close() {
    }

    @Test
    void closeIfPosible() {
    }

    @Test
    void contentEquals() {
    }

    @Test
    void testContentEquals() {
    }

    @Test
    void contentEqualsIgnoreEOL() {
    }

    @Test
    void checksumCRC32() {
    }

    @Test
    void checksum() {
    }

    @Test
    void checksumValue() {
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