package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloaderSplitterTest {

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

    Map<String, String> todoMap = new HashMap<>(12) {{
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/42c2ffeb20564cb0a9fd05fc38fb5abe.jpeg-f800x600", "1.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/b5ff86e57d384c97867604b3d7bddbc4.jpeg-f800x600", "2.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/bd143fba01c14953bae02e1e2a93f177.jpeg-f800x600", "3.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/0bba06ffc34a44d3a23b9749911c68cf.jpeg-f800x600", "4.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/23cfee8625604ba3b1b3b6da420622c1.jpeg-f800x600", "5.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/d59b597c2275407486b2dde73f9d214b.jpeg-f800x600", "6.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/1fec26c1b7f14e769ad1c11e198d4114.jpeg-f800x600", "7.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/681a594cca1a4d019a28368a99dd6b81.jpeg-f800x600", "8.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/f3c4a595d5914b58b113bd5cd788e094.jpeg-f800x600", "9.jpg");
        put("https://kfang-image-test.oss-cn-beijing.aliyuncs.com/test/entrust/3858976d969242bda35519d4e546acd4.jpeg-f800x600", "10.jpg");
    }};

    @Test
    void todoList() {
        DownloaderSplitter.init()
                .useProxy(false)
                .threadCount(5)
                .cover(true)
                // .referer("https://www.pixiv.net/")
                .savePath("/Users/kuuga/Downloads/DownloaderSplitterTest/")
                .todoList(todoList)
                .download();
        DaemonThread.await();
    }

    @Test
    void todoMap() {
        DownloaderSplitter.init()
                .useProxy(true)
                .threadCount(10)
                .cover(true)
                .referer("https://www.pixiv.net/")
                .savePath("/Users/kuuga/Downloads/DownloaderSplitterTest/")
                //.toDownloadList(ListUtil.NEW("https://i.pximg.net/img-original/img/2018/04/27/00/42/19/68420926_p0.jpg"))
                // .toDownloadList(todoList)
                .todoMap(todoMap)
                //.fileUrl("https://i.pximg.net/img-original/img/2018/04/27/00/42/19/68420926_p0.jpg")
                .download();
        DaemonThread.await();
    }

    @Test
    void downloadFile() {
        String fileUrl = "https://test-file.kfangcdn.com/test/2_1652926901753.pdf";
        DownloaderSplitter.init()
                .useProxy(true)
                .threadCount(10)
                .cover(true)
                .referer("https://www.pixiv.net/")
                .savePath("/Users/kuuga/Downloads/DownloaderSplitterTest/")
                .fileUrl(fileUrl)
                .download();
        DaemonThread.await();
    }

}