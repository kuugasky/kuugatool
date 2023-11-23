package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.io.unit.DataSizeUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 单文件下载器【同步任务】
 * <p>
 * 支持：<br>
 * 1.代理<br>
 * 2.自定义存储路径，默认下载路径/Users/kuuga/Downloads/<br>
 * 3.自定义referer防屏蔽
 *
 * @author kuuga
 */
@Slf4j
public final class DownloaderSingle {

    /**
     * 可关闭的 Http 客户端
     */
    private final CloseableHttpClient closeableHttpClient;
    /**
     * 是否使用代理
     */
    private boolean useProxy = false;
    /**
     * 同名文件是否覆盖
     */
    private boolean cover = false;
    /**
     * 文件保存目录
     */
    private String savePath;
    private String referer = StringUtil.EMPTY;

    private DownloaderSingle() {
        closeableHttpClient = getCloseableHttpClient();
    }

    public static DownloaderSingle init() {
        return new DownloaderSingle();
    }

    public void download(List<String> fileUrls) {
        ListUtil.optimize(fileUrls).stream().filter(StringUtil::hasText).forEach(fileUrl -> {
            String fileName = FilenameUtil.getName(fileUrl);
            download(fileUrl, fileName);
        });
    }

    /**
     * 根据url创建文件(不覆盖)
     * 文件名从fileUrl中获取
     *
     * @param networkFileUrl eg：<a href="http://desk.fd.zol-img.com.cn/wD4XDsABThY990.jpg">...</a>
     */
    public void download(String networkFileUrl) {
        String fileName = FilenameUtil.getName(networkFileUrl);
        if (StringUtil.containsEmpty(networkFileUrl, fileName)) {
            return;
        }
        download(networkFileUrl, fileName);
    }

    /**
     * 根据url创建文件
     *
     * @param networkFileUrl eg：<a href="http://desk.fd.zol-img.com.cn/wD4XDsABThY990.jpg">...</a>
     * @param fileName       文件名
     */
    public void download(String networkFileUrl, String fileName) {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();

        File file = new File(this.savePath + fileName);

        log.info("【" + networkFileUrl + "】单文件下载器开始下载...");
        try {
            file = DownloaderBreakpoint.getFile(fileName, file, this.cover, this.savePath);
            downloading(networkFileUrl, file);
        } catch (IOException e) {
            log.error("【" + networkFileUrl + "】单文件下载器下载异常：" + e.getMessage(), e);
            log.error("【" + networkFileUrl + "】单文件下载器下载异常！！！耗时:({})", timeInterval.intervalPretty());
        }
        long fileSize = FileUtil.getFileSize(file);
        log.info("【" + networkFileUrl + "】单文件下载器下载完成！！！文件大小:({}) 耗时:({})", DataSizeUtil.format(fileSize), timeInterval.intervalPretty());
    }

    public void downloading(String networkFileUrl, File file) throws IOException {
        if (StringUtil.isEmpty(savePath)) {
            throw new RuntimeException("下载目录不能为空.");
        } else {
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                try {
                    FileUtil.createDir(savePathFile);
                } catch (IOException e) {
                    if (!savePathFile.exists()) {
                        log.error("目录[{}}]创建失败", e.getMessage());
                    }
                }
            }
        }
        if (!file.exists()) {
            FileUtil.createFile(file);
        }
        HttpGet httpget = getHttpGet(networkFileUrl);
        HttpResponse response = closeableHttpClient.execute(httpget);
        HttpEntity entity = response.getEntity();

        try (InputStream inputStream = entity.getContent(); FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpGet getHttpGet(String networkFileUrl) {
        HttpGet httpget = new HttpGet(networkFileUrl);
        httpget.addHeader("Referer", referer);
        httpget.addHeader("User-Agent", HttpConstants.USER_AGENT);
        return httpget;
    }

    /**
     * 获取Closeable Http Client
     *
     * @return CloseableHttpClient
     */
    private CloseableHttpClient getCloseableHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        if (this.useProxy) {
            HttpHost proxy = new HttpHost(HttpConstants.PROXY_IP, HttpConstants.PROXY_PORT);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClientBuilder.setRoutePlanner(routePlanner);
        }
        return httpClientBuilder.build();
    }

    //================================================================================================================================================

    public DownloaderSingle useProxy(boolean useProxy) {
        this.useProxy = useProxy;
        return this;
    }

    public DownloaderSingle cover(boolean cover) {
        this.cover = cover;
        return this;
    }

    public DownloaderSingle savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public DownloaderSingle referer(String referer) {
        this.referer = referer;
        return this;
    }

}