package io.github.kuugasky.kuugatool.http;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kuuga Http自定义
 *
 * @author kuuga
 */
public final class KuugaHttpCustom extends KuugaHttp {

    private final Logger logger = LoggerFactory.getLogger(KuugaHttpCustom.class);

    private KuugaHttpCustom() {
        super();
    }

    public static KuugaHttpCustom init() {
        return new KuugaHttpCustom();
    }

    /**
     * 发起HTTP请求
     *
     * @param request HTTP请求
     * @return 响应结果
     */
    @Override
    public HttpResult execute(HttpUriRequest request) {
        HttpResult httpResult;
        try {
            CloseableHttpClient closeableHttpClient = super.createCloseableHttpClient();
            httpResult = super.executeServer(request, closeableHttpClient);
            return httpResult;
        } catch (Exception e) {
            logger.error("HTTP访问异常：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}