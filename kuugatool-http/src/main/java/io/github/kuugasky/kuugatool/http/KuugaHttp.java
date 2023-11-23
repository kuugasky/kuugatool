package io.github.kuugasky.kuugatool.http;

import com.google.common.annotations.Beta;
import io.github.kuugasky.kuugatool.core.charset.CharsetUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.core.HttpPoolingManager;
import io.github.kuugasky.kuugatool.http.core.KuugaHttpInterface;
import io.github.kuugasky.kuugatool.http.exception.HttpException;
import io.github.kuugasky.kuugatool.http.function.KuugaConsumer;
import io.github.kuugasky.kuugatool.http.html.HtmlCompressorUtil;
import io.github.kuugasky.kuugatool.http.html.UrlUtil;
import io.github.kuugasky.kuugatool.json.KuugaJsonParser;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * KuugaHttp
 * Kuuga自定义HttpUtil
 *
 * @author kuuga
 */
public abstract class KuugaHttp implements KuugaHttpInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(KuugaHttp.class);

    private final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    public KuugaHttp() {
        poolingHttpClientConnectionManager = HttpPoolingManager.INSTANCE.getPoolingHttpClientConnectionManager();
    }

    /**
     * 请求头部信息
     */
    private static final Map<String, Object> DEFAULT_HEADER = new HashMap<>(16);

    static {
        DEFAULT_HEADER.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        DEFAULT_HEADER.put("Accept-Encoding", "gzip, deflate, sdch");
        DEFAULT_HEADER.put("Cache-Control", "max-age=0");
        DEFAULT_HEADER.put("Connection", "keep-alive");
        DEFAULT_HEADER.put("Cookie", StringUtil.EMPTY);
        DEFAULT_HEADER.put("Referer", StringUtil.EMPTY);
        // 417状态码
        // defaultHeader.put("Expect", "100-continue");
        DEFAULT_HEADER.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");
    }

    private Map<String, Object> requestHeader = null;
    /**
     * 是否使用代理
     */
    private boolean useProxy = false;
    /**
     * 超时毫秒
     */
    private int timeout = 10000;
    /**
     * 异常屏蔽【true:不抛出异常】
     */
    private boolean abnormalShielding = false;
    /**
     * 是否补全所有href
     */
    private boolean fixAllRelativeHref = false;
    /**
     * 是否压缩返回文本内容
     */
    private boolean compress = false;
    /**
     * 是否启用编码识别：暂不可用
     */
    private boolean encodingDetect = false;
    /**
     * 默认编码
     */
    protected String charset = KuugaCharsets.UTF_8;
    /**
     * 是否需要ssl
     */
    private boolean needSSL = false;
    /**
     * 是否需要打印日志
     */
    private boolean printLog = false;

    /**
     * 创建可关闭的Http客户端
     *
     * @return 可关闭的Http客户端
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyManagementException   KeyManagementException
     */
    protected CloseableHttpClient createCloseableHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = getHttpClientBuilder();

        if (useProxy) {
            // set proxy
            HttpHost proxy = new HttpHost(HttpConstants.PROXY_IP, HttpConstants.PROXY_PORT);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClientBuilder.setRoutePlanner(routePlanner);
        }

        if (needSSL) {
            return buildCloseableHttpClientBySSL(httpClientBuilder);
        }

        return httpClientBuilder.build();
    }

    /**
     * 通过SSL创建可关闭的Http客户端
     *
     * @param httpClientBuilder http客户端构建器
     * @return 可关闭的http客户端
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyManagementException   KeyManagementException
     */
    private CloseableHttpClient buildCloseableHttpClientBySSL(HttpClientBuilder httpClientBuilder) throws NoSuchAlgorithmException, KeyManagementException {
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = createIgnoreVerifySSL();


        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // 创建自定义的HttpClient对象
        return httpClientBuilder.setConnectionManager(connManager).build();
    }

    /**
     * 获取Http客户端构建器
     *
     * @return 构建器
     */
    private HttpClientBuilder getHttpClientBuilder() {
        RequestConfig requestConfig;
        if (this.timeout > 0) {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(this.timeout)
                    .setConnectTimeout(this.timeout)
                    .setSocketTimeout(this.timeout)
                    .build();
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(HttpConstants.CONNECTION_REQUEST_TIMEOUT)
                    .setConnectTimeout(HttpConstants.CONNECT_TIMEOUT)
                    .setSocketTimeout(HttpConstants.SOCKET_TIMEOUT)
                    .build();
        }

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig);
        return httpClientBuilder;
    }

    /**
     * 绕过验证
     *
     * @return SSLContext
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     * @throws KeyManagementException   密钥管理异常
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 发起HTTP请求
     *
     * @param request HTTP请求
     * @return 响应结果
     */
    protected abstract KuugaHttpCustom.HttpResult execute(HttpUriRequest request);

    /**
     * 发起HTTP请求
     *
     * @param request HTTP请求
     * @return 响应结果
     */
    protected HttpResult executeServer(HttpUriRequest request, CloseableHttpClient closeableHttpClient) throws io.github.kuugasky.kuugatool.http.exception.HttpException {
        long start = System.currentTimeMillis();
        if (printLog) {
            LOGGER.info("Http request start --> request: [{}]", StringUtil.formatString(request));
        }

        HttpResult httpResult = new HttpResult();
        httpResult.setRequest(request);

        String body;

        if (MapUtil.hasItem(this.requestHeader)) {
            requestHeader.forEach((key, value) -> request.setHeader(key, value.toString()));
        } else {
            DEFAULT_HEADER.forEach((key, value) -> request.setHeader(key, value.toString()));
        }
        try (CloseableHttpResponse response = closeableHttpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, StringUtil.hasText(this.charset) ? this.charset : KuugaCharsets.UTF_8);
                EntityUtils.consumeQuietly(entity);
            } else {
                String formatter = "HTTP请求失败: %s, status: %s %s";
                StatusLine statusLine = response.getStatusLine();
                String msg = String.format(formatter, request.getRequestLine(), statusLine.getStatusCode(), statusLine.getReasonPhrase());
                throw new io.github.kuugasky.kuugatool.http.exception.HttpException(msg);
            }

            if (this.encodingDetect) {
                // 获取html编码
                String htmlEncode = CharsetUtil.getHtmlEncode(body);
                if (!this.charset.equalsIgnoreCase(htmlEncode)) {
                    this.charset = htmlEncode;
                    return executeServer(request, closeableHttpClient);
                }
            }

            if (this.fixAllRelativeHref) {
                // 补全页面所有href
                body = UrlUtil.fixAllRelativeHref(body, request.getURI().toString());
            }
            String compressStr = body;
            if (this.compress) {
                // 压缩html
                compressStr = HtmlCompressorUtil.compress(body);
            }

            // 赋值返回对象
            Header contentType = response.getEntity().getContentType();
            httpResult.setStatus(statusCode).setContent(compressStr).setContentType(null == contentType ? StringUtil.EMPTY : contentType.toString()).setHeaders(response.getAllHeaders())
                    .setProtocolVersion(response.getProtocolVersion().toString()).setCostTime(System.currentTimeMillis() - start);

            return httpResult;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Http request exception.[{} - error:{}]", request.getURI(), e.getMessage());

            if (e instanceof ConnectTimeoutException) {
                httpResult.setStatus(HttpStatus.SC_REQUEST_TIMEOUT);
            } else {
                httpResult.setStatus(HttpStatus.SC_EXPECTATION_FAILED);
            }

            httpResult.setCostTime(System.currentTimeMillis() - start).setException(e);

            if (!abnormalShielding) {
                throw new HttpException(e.getMessage());
            }

            return httpResult;
        } finally {
            if (printLog) {
                LOGGER.info("Http request finished --> response:[{}]", StringUtil.formatString(httpResult));
            }
        }
    }

    /**
     * 发起HTTP GET请求
     *
     * @param url URL
     * @return 响应结果
     */
    @Override
    public HttpResult get(String url) {
        return execute(new HttpGet(url));
    }


    /**
     * 发起HTTP GET请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult get(String uri, List<NameValuePair> params) {
        // format：格式化参数，返回一个HTTP POST或者HTTP PUT可用application/x-www-form-urlencoded字符串
        // parse：把String或者URI等转换为List<NameValuePair>
        String queryString = URLEncodedUtils.format(params, this.charset);
        return get(uri + "?" + queryString);
    }

    /**
     * 发起HTTP GET请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult get(String uri, NameValuePair... params) {
        if (params.length == 0) {
            return get(uri);
        } else {
            return get(uri, Arrays.asList(params));
        }
    }

    /**
     * 发起HTTP GET请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult get(String uri, Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return get(uri);
        } else {
            return get(uri, NameValuePairUtil.toList(params));
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param url URL
     * @return 响应结果
     */
    @Override
    public HttpResult post(String url) {
        return execute(new HttpPost(url));
    }

    /**
     * 发起HTTP POST请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult post(String uri, List<NameValuePair> params) {
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, this.charset);
            HttpPost httppost = new HttpPost(uri);
            httppost.setEntity(entity);
            return execute(httppost);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码类型: " + this.charset, e);
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult post(String uri, NameValuePair... params) {
        if (params.length == 0) {
            return post(uri);
        } else {
            return post(uri, Arrays.asList(params));
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param uri    URI
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult post(String uri, Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return post(uri);
        } else {
            return post(uri, NameValuePairUtil.toList(params));
        }
    }

    /**
     * 发起HTTP POST请求
     *
     * @param uri        URI
     * @param jsonParams Json请求参数
     * @return 响应结果
     */
    @Override
    public HttpResult post(String uri, String jsonParams) {
        StringEntity stringEntity = new StringEntity(jsonParams, this.charset);
        HttpPost httppost = new HttpPost(uri);
        httppost.setEntity(stringEntity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-Type", "application/json");
        return execute(httppost);
    }

    public KuugaHttp requestHeader(Map<String, Object> requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    public KuugaHttp timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public KuugaHttp abnormalShielding(boolean abnormalShielding) {
        this.abnormalShielding = abnormalShielding;
        return this;
    }

    public KuugaHttp useProxy(boolean useProxy) {
        this.useProxy = useProxy;
        return this;
    }

    public KuugaHttp charset(String charset) {
        this.charset = charset;
        return this;
    }

    public KuugaHttp fixAllRelativeHref(boolean fixAllRelativeHref) {
        this.fixAllRelativeHref = fixAllRelativeHref;
        return this;
    }

    public KuugaHttp compress(boolean compress) {
        this.compress = compress;
        return this;
    }

    public KuugaHttp needSSL(boolean needSSL) {
        this.needSSL = needSSL;
        return this;
    }

    public KuugaHttp printLog(boolean printLog) {
        this.printLog = printLog;
        return this;
    }

    /**
     * 其他项目使用检测会报错：
     * java.lang.NoClassDefFoundError: info/monitorenter/cpdetector/io/ICodepageDetector
     * 目前解决方式是在对应的项目中也引入依赖，待有优雅的处理方式在使用
     *
     * @param encodingDetect encodingDetect
     * @return this
     */
    @Beta
    public KuugaHttp encodingDetect(boolean encodingDetect) {
        this.encodingDetect = encodingDetect;
        return this;
    }

    // HttpResult ========================================================================================================

    public static class HttpResult {
        private HttpUriRequest request;
        private Header[] headers;
        private String content;
        private int status = -1;
        private String contentType;
        private String protocolVersion;
        private long costTime;
        private Exception exception = null;

        public void exceptionHandler(Consumer<HttpResult> action) {
            if (ObjectUtil.nonNull(exception)) {
                action.accept(this);
            }
        }

        public void hasContext(Consumer<String> action) {
            if (StringUtil.hasText(content)) {
                action.accept(this.content);
            }
        }

        public void hasNoContext(Runnable emptyAction) {
            if (StringUtil.isEmpty(content)) {
                emptyAction.run();
            }
        }

        public void isOk(Runnable emptyAction) {
            if (status == HttpStatus.SC_OK) {
                emptyAction.run();
            }
        }

        public void isOkAndHasContext(Consumer<String> action) {
            if (status == HttpStatus.SC_OK && StringUtil.hasText(content)) {
                action.accept(this.content);
            }
        }

        public <T> void isOkAndHasContext(BiConsumer<String, T> action, T result) {
            if (status == HttpStatus.SC_OK && StringUtil.hasText(content)) {
                action.accept(this.content, result);
            }
        }

        public KuugaJsonParser isOkAndHasContext() {
            if (status == HttpStatus.SC_OK && StringUtil.hasText(content)) {
                return KuugaJsonParser.of(content);
            }
            return KuugaJsonParser.of(KuugaConstants.EMPTY_JSON);
        }

        public <T> T isOkAndHasContext(KuugaConsumer<String, T> action) {
            if (status == HttpStatus.SC_OK && StringUtil.hasText(content)) {
                return action.accept(this.content);
            }
            return null;
        }

        public void isFail(Runnable emptyAction) {
            if (status != HttpStatus.SC_OK) {
                emptyAction.run();
            }
        }

        public void isRequestTimeout(Runnable emptyAction) {
            if (status == HttpStatus.SC_REQUEST_TIMEOUT) {
                emptyAction.run();
            }
        }

        public long getCostTime() {
            return costTime;
        }

        public String getCostTimeDesc() {
            return costTime + "ms";
        }

        public HttpResult setCostTime(long costTime) {
            this.costTime = costTime;
            return this;
        }

        public String getProtocolVersion() {
            return protocolVersion;
        }

        public HttpResult setProtocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public HttpResult() {
        }

        public HttpResult setStatus(int status) {
            this.status = status;
            return this;
        }

        public HttpResult setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public String getContent() {
            return content;
        }

        public HttpResult setContent(String content) {
            this.content = content;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public String getContentType() {
            return contentType;
        }

        public HttpResult setHeaders(Header[] allHeaders) {
            this.headers = allHeaders;
            return this;
        }

        public Header[] getHeaders() {
            return headers;
        }

        public HttpUriRequest getRequest() {
            return request;
        }

        public HttpResult setRequest(HttpUriRequest request) {
            this.request = request;
            return this;
        }

        public Exception getException() {
            return exception;
        }

        public HttpResult setException(Exception exception) {
            this.exception = exception;
            return this;
        }
    }

}