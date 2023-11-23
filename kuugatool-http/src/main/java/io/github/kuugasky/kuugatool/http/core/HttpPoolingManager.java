package io.github.kuugasky.kuugatool.http.core;

import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * Http客户端连接管理器
 *
 * @author kuuga
 */
public enum HttpPoolingManager {

    /**
     *
     */
    INSTANCE;

    /**
     * 创建池Http客户端连接管理器
     *
     * @return PoolingHttpClientConnectionManager
     */
    public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
        try {
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
            SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry
                    = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register(HttpConstants.HTTP, PlainConnectionSocketFactory.getSocketFactory())
                    .register(HttpConstants.HTTPS, ssl).build();
            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 将最大连接数增加到200
            poolingHttpClientConnectionManager.setMaxTotal(HttpConstants.MAX_TOTAL_POOL);
            // 将每个路由基础的连接增加到20
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(HttpConstants.MAX_CON_PER_ROUTE);
        } catch (Exception e) {
            throw new RuntimeException("Http连接器初始化异常: " + e.getMessage());
        }
        poolingHttpClientConnectionManager.setMaxTotal(HttpConstants.MAX_TOTAL_POOL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(HttpConstants.MAX_CON_PER_ROUTE);
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(HttpConstants.SOCKET_TIMEOUT).build();
        poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
        return poolingHttpClientConnectionManager;
    }

}