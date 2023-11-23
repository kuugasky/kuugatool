package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.concurrent.forkjoin.ForkJoinPool;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分发下载器【多文件集合下载】
 * <p>
 * ThreadPool&ForkJoin异步任务
 * <p>
 * 支持：<br>
 * 1.代理<br>
 * 2.支持集合list和map【map模式可自定义保存文件名】<br>
 * 3.多线程下载<br>
 * 4.自定义存储路径，默认下载路径/Users/kuuga/Downloads/<br>
 * 5.自定义referer防屏蔽
 *
 * @author kuuga
 */
public final class DownloaderSplitter {

    private static final int MAX_THREAD_COUNT = 10;

    private String savePath = "/Users/kuuga/Downloads/";
    private boolean useProxy = false;
    private boolean cover = false;
    private String referer = StringUtil.EMPTY;
    private int splitCount = 1;

    private List<String> todoList = new ArrayList<>();
    private Map<String, String> todoMap = new HashMap<>();

    private DownloaderSplitter() {

    }

    public static DownloaderSplitter init() {
        return new DownloaderSplitter();
    }

    public void download() {
        try {
            FileUtil.createDir(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ListUtil.hasItem(todoList)) {
            ThreadPool.execute(this::toDownloadListWork);
        }
        if (MapUtil.hasItem(todoMap)) {
            ThreadPool.execute(this::toDownloadMapWork);
        }
    }

    private void toDownloadListWork() {
        DownloaderSingle downloaderSingle = DownloaderSingle.init().savePath(savePath).useProxy(useProxy).cover(this.cover).referer(referer);
        if (splitCount <= 1) {
            todoList.parallelStream().forEach(downloaderSingle::download);
        } else {
            ForkJoinPool.submit(todoList, TaskSplitType.TASK_AMOUNT, splitCount, value -> {
                downloaderSingle.download(value);
                return null;
            }).getResult();
        }
    }

    private void toDownloadMapWork() {
        DownloaderSingle downloaderSingle = DownloaderSingle.init().savePath(savePath).useProxy(useProxy).cover(this.cover).referer(referer);
        if (splitCount <= 1) {
            List<Map<String, String>> splits = CollectionSplitter.split(todoMap, splitCount);
            splits.parallelStream().forEach(fileUrlMap -> fileUrlMap.forEach(downloaderSingle::download));
        } else {
            List<Map<String, String>> toDownloadMapToList = MapUtil.toList(todoMap);
            ForkJoinPool instance = ForkJoinPool.submit(toDownloadMapToList, TaskSplitType.TASK_AMOUNT, splitCount, fileUrlMapList -> {
                fileUrlMapList.forEach(downloaderSingle::download);
                return null;
            });
            instance.getResult();
        }
    }

    //================================================================================================================================================

    public DownloaderSplitter threadCount(int threadCount) {
        if (threadCount <= 0) {
            return this;
        }
        if (threadCount > MAX_THREAD_COUNT) {
            this.splitCount = MAX_THREAD_COUNT;
            return this;
        }
        this.splitCount = threadCount;
        return this;
    }

    public DownloaderSplitter todoMap(Map<String, String> map) {
        this.todoMap = map;
        return this;
    }

    public DownloaderSplitter todoList(List<String> list) {
        this.todoList = list;
        return this;
    }

    public DownloaderSplitter useProxy(boolean useProxy) {
        this.useProxy = useProxy;
        return this;
    }

    public DownloaderSplitter cover(boolean cover) {
        this.cover = cover;
        return this;
    }

    public DownloaderSplitter fileUrl(String fileUrl) {
        if (StringUtil.hasText(fileUrl)) {
            todoList.add(fileUrl);
        }
        return this;
    }

    public DownloaderSplitter savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public DownloaderSplitter referer(String referer) {
        this.referer = referer;
        return this;
    }

}