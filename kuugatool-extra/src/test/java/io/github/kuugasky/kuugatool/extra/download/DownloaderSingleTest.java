package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DownloaderSingleTest {

    List<String> todoList = new ArrayList<>(10) {{
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/42c2ffeb20564cb0a9fd05fc38fb5abe.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/b5ff86e57d384c97867604b3d7bddbc4.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/bd143fba01c14953bae02e1e2a93f177.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/0bba06ffc34a44d3a23b9749911c68cf.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/23cfee8625604ba3b1b3b6da420622c1.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/d59b597c2275407486b2dde73f9d214b.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/1fec26c1b7f14e769ad1c11e198d4114.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/681a594cca1a4d019a28368a99dd6b81.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/f3c4a595d5914b58b113bd5cd788e094.jpeg-f800x600");
        add("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/3858976d969242bda35519d4e546acd4.jpeg-f800x600");
    }};

    @Test
    public void test() {
        todoList.parallelStream().forEach(url ->
                DownloaderSingle.init()
                        .useProxy(false)
                        .cover(true)
                        .referer("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/")
                        .savePath("/Users/kuuga/Downloads/DownloaderSingleTest/")
                        .download(url));
    }

    @Test
    void download() {
        DownloaderSingle.init()
                .useProxy(false)
                .cover(true)
                .referer("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/")
                .savePath("/Users/kuuga/Downloads/DownloaderSingleTest/")
                .download(todoList);
    }

    @Test
    void download1() {
        DownloaderSingle.init()
                .useProxy(false)
                .cover(true)
                .referer("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/")
                .savePath("/Users/kuuga/Downloads/DownloaderSingleTest/")
                .download(todoList.get(0), "Kuuga.jpg");
    }

    @Test
    void download2() throws IOException {
        DownloaderSingle.init()
                .useProxy(false)
                .cover(true)
                .referer("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/")
                .savePath("/Users/kuuga/Downloads/DownloaderSingleTest/")
                .downloading(todoList.get(0), FileUtil.file("/Users/kuuga/Downloads/DownloaderSingleTest/kuuga2.jpg"));
    }

    @Test
    void downloadFile() {
        String fileUrl = "https://test-file.kfangcdn.com/test/2_1652926901753.pdf";
        DownloaderSingle.init()
                .useProxy(false)
                .cover(true)
                .referer("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/")
                .savePath("/Users/kuuga/Downloads/DownloaderSingleTest/")
                .download(fileUrl);
    }

}