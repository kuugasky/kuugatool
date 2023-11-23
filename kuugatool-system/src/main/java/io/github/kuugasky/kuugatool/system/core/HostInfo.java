package io.github.kuugasky.kuugatool.system.core;

import io.github.kuugasky.kuugatool.system.NetworkUtil;
import io.github.kuugasky.kuugatool.system.SystemUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代表当前主机的信息。
 *
 * @author kuuga
 */
public class HostInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String HOST_NAME;
    private final String HOST_ADDRESS;

    public HostInfo() {
        final String localIpAddress = NetworkUtil.getLocalIpAddr();
        String hostName = SystemUtil.getLocalHostName();
        if (null != localIpAddress) {
            HOST_NAME = hostName;
            HOST_ADDRESS = localIpAddress;
        } else {
            HOST_NAME = null;
            HOST_ADDRESS = null;
        }
    }

    /**
     * 取得当前主机的名称。
     *
     * <p>
     * 例如：<code>"webserver1"</code>
     * </p>
     *
     * @return 主机名
     */
    public final String getName() {
        return HOST_NAME;
    }

    /**
     * 取得当前主机的地址。
     *
     * <p>
     * 例如：<code>"192.168.0.1"</code>
     * </p>
     *
     * @return 主机地址
     */
    public final String getAddress() {
        return HOST_ADDRESS;
    }

    /**
     * 将当前主机的信息转换成字符串。
     *
     * @return 主机信息的字符串表示
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        SystemUtil.append(builder, "Host Name:    ", getName());
        SystemUtil.append(builder, "Host Address: ", getAddress());

        return builder.toString();
    }

}
