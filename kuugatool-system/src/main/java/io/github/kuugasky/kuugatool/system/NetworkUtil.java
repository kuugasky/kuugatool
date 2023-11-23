package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.jar.JarUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * NetUtil
 * lo0:loopback回环地址，一般是127.0.0.1
 * 【loopback指本地环回接口（或地址），亦称回送地址()。此类接口是应用最为广泛的一种虚接口，几乎在每台路由器上都会使用。】
 * gif0: software Network Interface【软件网络接口】
 * stf0: 6to4 tunnel interface【ipv6->ipv4通道接口】
 * en0: Ethernet【以太网0 有线网卡】
 * p2po: Point-to-Point 协议
 * bridge0: 第2层桥接
 * utun0:networksetup -listallhardwareports
 *
 * @author kuuga
 * @since 2021-01-21 下午5:51
 */
@Slf4j
public class NetworkUtil {

    /**
     * 获取本地IP地址【本地以太网IP】
     * loopback：回环网卡就是微软的一种类似于虚拟网卡的一种设备，它能够被安装在一个没有网卡（这里是硬件网卡），的环境下，或者用于测试多个宿主环境。
     *
     * @return string
     */
    public static String getLocalIpAddr() {
        String clientIp = null;
        Enumeration<NetworkInterface> networks;
        try {
            // 获取所有网卡设备
            networks = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            log.error("获取所有网卡设备失败:{}", e.getMessage());
            return null;
        }
        InetAddress ip;
        Enumeration<InetAddress> address;
        // 遍历网卡设备
        while (networks.hasMoreElements()) {
            NetworkInterface ni = networks.nextElement();
//            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
//            StringBuilder stringBuilder = StringUtil.builder();
//            while (inetAddresses.hasMoreElements()) {
//                InetAddress inetAddress = inetAddresses.nextElement();
//                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress() && !inetAddress.getHostAddress().contains(":")) {
//                    stringBuilder.append(inetAddress).append(",");
//                }
//            }
            try {
                // Console.error("网卡:{}, 在线:{}, 回环网卡:{}, 虚拟网卡:{}, 点对点:{}, IP:{}",
                //         ni.getDisplayName(), ni.isUp(), ni.isLoopback(), ni.isVirtual(), ni.isPointToPoint(), StringUtil.removeEnd(stringBuilder.toString(), ","));
                // 过滤掉 不在线网卡、loopback回环设备、虚拟网卡
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }
            } catch (SocketException e) {
                log.error("NetUtil.SocketException:{}", e.getMessage());
            }
            address = ni.getInetAddresses();
            // 遍历InetAddress信息
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                // 不是回环地址&是站点本地地址&主机地址不包含:
                if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && !ip.getHostAddress().contains(":")) {
                    try {
                        clientIp = ip.toString().split("/")[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        clientIp = null;
                    }
                }
            }
        }
        return clientIp;
    }

    /**
     * 获取本地网卡IP【外网IP】
     *
     * @return hostIp
     */
    public static String getLocalNetworkCardIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() // loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && !ip.getHostAddress().contains(":")) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地网卡ip
     */
    public static String getLocalNetworkCardIP() {
        try {
            // Traversal Network interface to get the first non-loopback and non-private address
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            // ipv4 地址集
            ArrayList<String> ipv4Result = new ArrayList<>();
            // ipv6 地址集
            ArrayList<String> ipv6Result = new ArrayList<>();

            // 遍历网卡
            while (enumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = enumeration.nextElement();
                final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(normalizeHostAddress(address));
                        } else {
                            ipv4Result.add(normalizeHostAddress(address));
                        }
                    }
                }
            }
            // prefer ipv4
            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0") || ip.startsWith("192.168")) {
                        continue;
                    }
                    return ip;
                }
                // 存在多个ip的时候，获取最后一个
                return ipv4Result.get(ipv4Result.size() - 1);
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            }
            // If failed to find,fall back to localhost
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (Exception e) {
            log.error("Failed to obtain local address", e);
        }
        return null;
    }

    /**
     * 正常主机地址
     *
     * @param localHost localHost
     * @return String
     */
    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

    /**
     * 获取当前进程的端口号
     *
     * @return 端口号
     * @throws MalformedObjectNameException 对象名称异常
     */
    public static Integer getLocalPort() throws MalformedObjectNameException {
        Integer port = null;
        String javaVersion = SystemUtil.getJavaVersion();

        if (StringUtil.hasText(javaVersion)) {
            int jdkVersion = Integer.parseInt(StringUtil.firstText(javaVersion, "\\."));
            if (jdkVersion <= KuugaConstants.ELEVEN) {
                port = getLocalPortByMBeanServer();
            } else {
                // 通过spring获取启动容器的端口
                AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
                if (ObjectUtil.nonNull(applicationContext)) {
                    port = ((ServletWebServerApplicationContext) applicationContext).getWebServer().getPort();
                }
            }
        }
        return port;
    }

    /**
     * 通过MBeanServer获取当前进程端口端口
     *
     * @return 端口号
     * @throws MalformedObjectNameException 错误的对象名称异常
     */
    public static Integer getLocalPortByMBeanServer() throws MalformedObjectNameException {
        boolean isFileRun = JarUtil.isFileRun(NetworkUtil.class);
        if (isFileRun) {
            throw new RuntimeException("仅支持jar包启动时获取");
        }
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("*:type=Connector,*");
        QueryExp queryExp = Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"));

        Set<ObjectName> objectNames = beanServer.queryNames(objectName, queryExp);
        Iterator<ObjectName> iterator = objectNames.iterator();

        if (iterator.hasNext()) {
            String port = iterator.next().getKeyProperty("port");
            return Integer.parseInt(port);
        } else {
            return null;
        }
    }

    /**
     * 根据host获取ip
     *
     * @return ip地址
     * @throws UnknownHostException 未知主机异常
     */
    public static String getIpByHost(String host) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(host);
        return inetAddress.getHostAddress();
    }

}
