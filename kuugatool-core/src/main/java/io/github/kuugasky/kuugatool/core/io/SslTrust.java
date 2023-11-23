package io.github.kuugasky.kuugatool.core.io;

import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * SslTrust
 *
 * @author kuuga
 * @since 2022/10/25-10-25 10:15
 */
public final class SslTrust {

    public static final String GET = "GET";
    public static final int CONNECT_TIMEOUT = 5 * 1_000;

    static {
        try {
            trustAllHttpsCertificates();
            // 设置默认主机名验证器
            HttpsURLConnection.setDefaultHostnameVerifier((urlHostName, session) -> true);
        } catch (Exception ignored) {
        }
    }

    /**
     * 信任所有 HTTPS 证书
     *
     * @throws NoSuchAlgorithmException 没有这样的算法例外
     * @throws KeyManagementException   密钥管理异常
     */
    private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = SSLContext.getInstance(HttpConstants.SSL);
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    /**
     * 信任所有证书管理器，继承自X509信任管理器
     */
    private static class TrustAllManager implements X509TrustManager {

        /**
         * 获得接受的发行人
         */
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        /**
         * 检查服务器受信任
         *
         * @param certs    X509证书
         * @param authType 认证类型
         */
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        /**
         * 检查受信任的客户
         *
         * @param certs    X509证书
         * @param authType 认证类型
         */
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

    }

    /**
     * 网络文件地址转换为字节数组
     * 结合IoUtil.byteToFile,可以将网络文件url转换为file
     *
     * @param fileUrl 网络文件地址
     * @return byte[]
     * @throws IOException io异常
     */
    public static byte[] fileUrlToBytes(String fileUrl) throws IOException {
        if (StringUtil.isEmpty(fileUrl)) {
            return null;
        }
        // new一个URL对象
        URL url = new URL(fileUrl);
        // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式为"GET"
        conn.setRequestMethod(GET);
        // 超时响应时间为5秒
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        try (InputStream inStream = conn.getInputStream()) {
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            return IoUtil.inputStreamToBytes(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
