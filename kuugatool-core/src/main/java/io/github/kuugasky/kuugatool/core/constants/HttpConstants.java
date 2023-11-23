package io.github.kuugasky.kuugatool.core.constants;

/**
 * Http常量
 *
 * @author kuuga
 */
public interface HttpConstants {

    /**
     * 代理信息【需开启Shadowsocks】
     * PROXY_IP：本地IP
     * PROXY_PORT：代理接口
     */
    String PROXY_IP = "127.0.0.1";
    int PROXY_PORT = 1087;

    /**
     * MAX_TOTAL_POOL：最大线程池数
     * MAX_CON_PER_ROUTE：最大连接数
     * SOCKET_TIMEOUT：套接字连接超时
     * CONNECTION_REQUEST_TIMEOUT：连接请求超时
     * CONNECT_TIMEOUT：连接超时
     */
    int MAX_TOTAL_POOL = 200;
    int MAX_CON_PER_ROUTE = 20;
    int SOCKET_TIMEOUT = 10000;
    int CONNECTION_REQUEST_TIMEOUT = 2000;
    int CONNECT_TIMEOUT = 10000;

    /**
     * 用户代理
     */
    String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36";

    /**
     * 请求协议
     */
    String HTTP = "http";
    String HTTPS = "https";

    String SSL = "SSL";

}