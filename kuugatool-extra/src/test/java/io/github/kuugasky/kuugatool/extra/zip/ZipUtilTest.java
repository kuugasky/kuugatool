package io.github.kuugasky.kuugatool.extra.zip;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

class ZipUtilTest {

    static String filePath = "/Users/kuuga/Downloads/zip.txt";

    @BeforeEach
    void before() throws IOException {
        File file = new File(filePath);
        FileUtil.deleteFile(file);
        FileUtil.writeStringToFile(file, "Kuuga is a boy.", null);
    }

    // 压缩
    @Test
    void zip() throws IOException {
        ZipUtil.zip(filePath, "/Users/kuuga/Downloads/Kuuga.zip");
    }

    // 压缩
    @Test
    void testZip() throws IOException {
        ZipUtil.zip(FileUtil.file(filePath), FileUtil.file("/Users/kuuga/Downloads/kuuga1.zip"));
    }

    @Test
    void zipUrl() throws IOException {
        String url = "https://test-image.kfangcdn.com/test/survey/52980b2b3a3b45488017e908374c3997.jpeg-l1024x768w";

        File file = FileUtil.file("/Users/kuuga/Downloads/zipUrl.zip");
        ZipOutputStream out = IoUtil.toZipOutputStream(file);
        ZipUtil.zipUrl(url, out);
    }

    @Test
    void zipUrl2() throws IOException {
        String url = "https://test-image.kfangcdn.com/test/survey/52980b2b3a3b45488017e908374c3997.jpeg-l1024x768w";

        File file = FileUtil.file("/Users/kuuga/Downloads/zipUrl1.zip");
        ZipUtil.zipUrl(url, file);
    }

    @Test
    void testZipUrl() throws IOException {
        byte[] bytes = IoUtil.fileUrlToBytes("http://infocdn.3dnest.cn/24cf9828_tEYN_b6f9/2022-10-24-09-38-02/htcbbba358-53f3-11ed-a0de-0242ac11001c_1440_1080.jpg");
        System.out.println(bytes.length);
        byte[] bytes1 = IoUtil.fileUrlToBytes("https://infocdn.3dnest.cn/24cf9828_tEYN_b6f9/2022-10-24-09-38-02/htcbbba358-53f3-11ed-a0de-0242ac11001c_1440_1080.jpg");
        System.out.println(bytes1.length);
        // String pathname = "/Users/kuuga/Downloads/" + IdUtil.fastSimpleUUID() + ".jpeg";
        // ImgUtil.write(ImgUtil.toImage(bytes), new File(pathname));
        // ZipUtil.zip(new File(pathname), new File("/Users/kuuga/Downloads/Kuuga-jpg.zip"));
    }

    @Test
    void testZip1() throws IOException {
        String url = "https://test-image.kfangcdn.com/test/survey/52980b2b3a3b45488017e908374c3997.jpeg-l1024x768w";

        File file = FileUtil.file("/Users/kuuga/Downloads/zipUrl2.zip");
        ZipUtil.zipUrl(url, "Kuuga.jpg", IoUtil.toZipOutputStream(file));
    }

    @Test
    void testZip2() throws IOException {
        File file = FileUtil.file("/Users/kuuga/Downloads/zipUrl3.zip");
        ZipUtil.zip(filePath, IoUtil.toZipOutputStream(file));
    }

    @Test
    void doZipUrls() {
        List<String> objects = ListUtil.newArrayList();
        objects.add("https://test-image.kfangcdn.com/test/survey/52980b2b3a3b45488017e908374c3997.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/c93910d743344313a4b51c28c79788e8.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/9aa884fa78f34f3487c673dfcb0fafb2.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/7c6ff5e277ba4c249b09341329a45ca0.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/f66f85eff0a54354a298e611433dc9f6.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/bcfe149d32be4fec8da0458123dd9181.jpeg-l1024x768w");
        objects.add("https://test-image.kfangcdn.com/test/survey/39a2bcee71bc48148896cd323a06f880.jpeg-l1024x768w");

        ZipUtil.doZip("室内图", objects, null);
    }

}