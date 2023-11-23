package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.io.unit.DataSizeUtil;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 分段断点式分发下载【同步任务】
 * <p>
 * 支持：<br>
 * 1.自定义保存路径<br>
 * 2.支持单文件和多文件下载<br>
 * 3.自定义拆分值，将文件集合拆成多个管道流，将文件拆成多段下载合并
 *
 * @author kuuga
 */
@Slf4j
public final class DownloaderBreakpoint {
    /**
     * 待办集合
     */
    private List<String> todoList = new ArrayList<>();
    /**
     * 临时文件目录
     */
    private String tmpPath;
    /**
     * 保存文件目录
     */
    private String savePath;
    /**
     * 是否使用代理
     */

    private boolean useProxy = false;
    /**
     * 同名文件是否覆盖
     */
    private boolean cover = false;
    /**
     * 拆分个数-一个文件拆分成多少段，段数不是越多越好
     */
    private int splitCount = 1;
    private String referer = StringUtil.EMPTY;

    /**
     * 文件下载
     */
    public void download() {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();

        if (ListUtil.isEmpty(todoList)) {
            return;
        }
        if (StringUtil.isEmpty(savePath)) {
            throw new RuntimeException("下载目录不能为空.");
        } else {
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                try {
                    FileUtil.createDir(savePath);
                } catch (IOException e) {
                    log.error("目录[{}}]创建失败", e.getMessage());
                }
            }
        }
        todoList.parallelStream().forEach(networkFileUrl -> {
            // 创建最终合并文件
            String fileName = FilenameUtil.getName(networkFileUrl);
            File resultFile = new File(savePath + fileName);
            resultFile = getFile(fileName, resultFile, this.cover, this.savePath);
            log.info("【" + networkFileUrl + "】断点下载器开始下载...分段({})", this.splitCount);
            try {
                String templatePath = assembleTmpPath(networkFileUrl);
                FileUtil.createDir(templatePath);
                // 文件分段下标区间map
                Map<Long, Long> breakpointInterval = CollectionSplitter.getBreakpointInterval(networkFileUrl, splitCount, referer, useProxy);
                // 多线程下载tmp文件,按先后顺序返回map
                Map<Long, File> todoMap = downloadTmpFiles(networkFileUrl, templatePath, breakpointInterval);

                mergeFiles(todoMap, resultFile);
                FileUtil.deleteDirectory(templatePath);
                long fileSize = FileUtil.getFileSize(resultFile);
                log.info("【" + networkFileUrl + "】断点下载器下载结束！！！文件大小:({}) 耗时:({})", DataSizeUtil.format(fileSize), timeInterval.intervalPretty());
            } catch (IOException e) {
                log.error("【" + networkFileUrl + "】断点下载器下载异常：" + e.getMessage(), e);
                log.error("【" + networkFileUrl + "】断点下载器下载异常！！！耗时:({})", timeInterval.intervalPretty());
            }
        });
    }

    /**
     * 文件处理判断
     *
     * @param networkFileUrl 网络文件url
     * @param resultFile     返回文件
     * @param cover          是否覆盖
     * @param savePath       保存地址
     * @return File
     */
    static File getFile(String networkFileUrl, File resultFile, boolean cover, String savePath) {
        if (resultFile.exists()) {
            if (cover) {
                FileUtil.deleteFile(resultFile);
            } else {
                networkFileUrl = FileUtil.generateCopyName(networkFileUrl);
                resultFile = new File(savePath + networkFileUrl);
            }
        }
        return resultFile;
    }

    /**
     * 合并文件
     *
     * @param todoMap    多个临时文件
     * @param resultFile 汇总文件
     * @throws IOException IOException
     */
    private void mergeFiles(Map<Long, File> todoMap, File resultFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        todoMap.forEach((key, value) -> {
            try {
                output(fileOutputStream, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileOutputStream.close();
    }

    /**
     * 下载临时文件
     *
     * @param networkFileUrl     网络文件url
     * @param tmpPath            临时文件地址
     * @param breakpointInterval 续传区间-断点间隔
     * @return map
     */
    private Map<Long, File> downloadTmpFiles(String networkFileUrl, String tmpPath, Map<Long, Long> breakpointInterval) {
        new ArrayList<>(breakpointInterval.keySet()).parallelStream().forEach(start -> {
            try {
                File file = new File(tmpPath + start + "." + "tmp");
                long size = file.length();

                HttpURLConnection con = getHttpURLConnection(networkFileUrl, start, breakpointInterval.get(start));
                // 只要断点下载，返回的已经不是200，206
                int responseCode = con.getResponseCode();
                boolean imageTypeCode = responseCode == HttpURLConnection.HTTP_OK;
                boolean fileTypeCode = responseCode == HttpURLConnection.HTTP_PARTIAL;
                if (imageTypeCode || fileTypeCode) {
                    try (InputStream in = con.getInputStream()) {
                        // 必须要使用
                        RandomAccessFile out = new RandomAccessFile(file, "rw");
                        out.seek(size);

                        byte[] b = new byte[1024];
                        int len;
                        while ((len = in.read(b)) != -1) {
                            out.write(b, 0, len);
                        }
                        out.close();
                    } catch (Exception e) {
                        log.error("文件下载异常：{}", e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Map<Long, File> todoMap = new TreeMap<>();
        File folder = new File(tmpPath);
        ListUtil.optimize(folder.listFiles()).forEach(file -> {
            String[] split = file.getName().split("\\.");
            if (split.length <= 1) {
                return;
            }
            String fileNameIndex = split[0];
            if (StringUtil.isEmpty(fileNameIndex) || !NumberUtil.isPositiveInteger(fileNameIndex)) {
                return;
            }
            if (file.isFile() && StringUtil.hasText(fileNameIndex) && breakpointInterval.containsKey(Long.valueOf(fileNameIndex))) {
                todoMap.put(Long.valueOf(fileNameIndex), file);
            }
        });
        return todoMap;
    }

    /**
     * 组装Tmp路径
     *
     * @param networkFileUrl 网络文件url
     * @return String
     */
    private String assembleTmpPath(String networkFileUrl) {
        String fileNameWithoutExtension = FilenameUtil.getBaseName(networkFileUrl);
        return String.format(this.tmpPath, fileNameWithoutExtension);
    }

    /**
     * 输出
     *
     * @param fileOutputStream 文件输出流
     * @param file             文件
     * @throws IOException IOException
     */
    private void output(FileOutputStream fileOutputStream, File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // 创建FileInputStream对象，并关联对应的文件，创建对象的时候会出现异常
            // 这种方式效率更加高，因为不用频繁的操作硬盘，一次就读了1M
            // byte[] bytes = new byte[1024];//为了读取方便，一次读取1M
            // 将读取到的数据保存到bytes这个字节数组中
            int length;
            byte[] arr = new byte[1024];
            while ((length = fis.read(arr)) != -1) {
                fileOutputStream.write(arr, 0, length);
            }
        }
    }

    /**
     * 获取Http URL连接
     *
     * @param networkFileUrl 网络文件
     * @param startIndex     文件续传起始下标startIndex
     * @param endIndex       文件续传结束下标endIndex
     * @return HttpURLConnection
     * @throws IOException IOException
     */
    @SuppressWarnings("all")
    private HttpURLConnection getHttpURLConnection(String networkFileUrl, long startIndex, long endIndex) throws IOException {
        URL url = new URL(networkFileUrl);
        HttpURLConnection con;
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HttpConstants.PROXY_IP, HttpConstants.PROXY_PORT));
            con = (HttpURLConnection) url.openConnection(proxy);
        } else {
            con = (HttpURLConnection) url.openConnection();
        }

        con.setRequestMethod("GET");
        // 必须设置false，否则会自动redirect到Location的地址
        con.setInstanceFollowRedirects(false);
        con.addRequestProperty("Referer", referer);
        con.addRequestProperty("User-Agent", HttpConstants.USER_AGENT);
        // 设置下载区间
        con.setRequestProperty("range", "bytes=" + startIndex + "-" + endIndex);
        con.connect();
        return con;
    }

    //==========================================================================================================

    private DownloaderBreakpoint() {

    }

    public static DownloaderBreakpoint init() {
        return new DownloaderBreakpoint();
    }

    public DownloaderBreakpoint savePath(String savePath) {
        this.savePath = savePath;
        this.tmpPath = this.savePath + "%sTmp/";
        return this;
    }

    public DownloaderBreakpoint networkFileUrl(String networkFileUrl) {
        if (StringUtil.hasText(networkFileUrl)) {
            this.todoList.add(networkFileUrl);
        }
        return this;
    }

    public DownloaderBreakpoint splitCount(int threadCount) {
        this.splitCount = threadCount;
        return this;
    }

    public DownloaderBreakpoint todoList(List<String> todoList) {
        this.todoList = todoList;
        return this;
    }

    public DownloaderBreakpoint useProxy(boolean useProxy) {
        this.useProxy = useProxy;
        return this;
    }

    public DownloaderBreakpoint cover(boolean cover) {
        this.cover = cover;
        return this;
    }

    public DownloaderBreakpoint referer(String referer) {
        this.referer = referer;
        return this;
    }

}
