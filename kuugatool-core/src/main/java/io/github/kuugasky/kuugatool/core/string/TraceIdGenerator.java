package io.github.kuugasky.kuugatool.core.string;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考 sofa-tracer 项目中traceId生成策略
 *
 * @author https://github.com/alipay/sofa-tracer
 * @author quding
 * @since 2018-12-16
 */
public class TraceIdGenerator {

    private static String IP_16 = "ffffffff";

    private static String P_ID_CACHE = null;

    private static final ThreadLocal<StringBuilder> SB = ThreadLocal.withInitial(() -> new StringBuilder(35));

    /**
     * 全局计数器
     */
    private static final AtomicInteger COUNT = new AtomicInteger(1000);

    static {
        try {
            String ipAddress = getInetAddress();
            if (null != ipAddress) {
                IP_16 = getIP_16(ipAddress);
            }
            P_ID_CACHE = getPID();
        } catch (Throwable e) {
            /*
             * empty catch block
             */
        }
    }

    /**
     * 生成traceId  机器IP + 时间戳 + 随机序列 + PID
     *
     * @return traceId
     */
    public static String generateTraceId() {
        StringBuilder builder = newLocalStringBuilder();
        return builder.append(IP_16)
                .append(System.currentTimeMillis())
                .append(nextId())
                .append(P_ID_CACHE).toString();
    }


    /**
     * 下一个随机数
     *
     * @return 1000-9999之内的数字
     */
    private static Integer nextId() {
        for (; ; ) {
            int current = COUNT.get();
            int value = current > 9000 ? 1000 : current + 1;
            if (COUNT.compareAndSet(current, value)) {
                return value;
            }
        }
    }

    /**
     * 拿到threadLocal中的StringBuilder复用
     *
     * @return 内部的StringBuilder
     */
    private static StringBuilder newLocalStringBuilder() {
        StringBuilder builder = SB.get();

        if (builder.capacity() > 500) {
            builder.setLength(32);
            builder.trimToSize();
        }

        builder.setLength(0);
        return builder;
    }

    /**
     * 进程的PID,解决单机多实例问题
     */
    private static String getPID() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();

        if (null == processName) {
            return StringUtil.EMPTY;
        }

        String[] processSplitName = processName.split("@");

        if (processSplitName.length == 0) {
            return StringUtil.EMPTY;
        }

        String pid = processSplitName[0];

        if (null == pid) {
            return StringUtil.EMPTY;
        }
        return pid;
    }

    /**
     * 得到本机IP地址对应十六进制编码,解决集群问题
     */
    private static String getIP_16(String ip) {
        String[] ips = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String column : ips) {
            String hex = Integer.toHexString(Integer.parseInt(column));
            if (hex.length() == 1) {
                sb.append('0').append(hex);
            } else {
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    /**
     * 获取网卡地址
     *
     * @see {NetworkUtil#getLocalIpAddr}
     */
    private static String getInetAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress address;
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && !address.getHostAddress().contains(":")) {
                        return address.getHostAddress();
                    }
                }
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }

}
