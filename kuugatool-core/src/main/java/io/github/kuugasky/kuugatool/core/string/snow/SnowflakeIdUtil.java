package io.github.kuugasky.kuugatool.core.string.snow;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * SnowflakeIdUtil
 *
 * @author kuuga
 * @since 2021/3/29
 */
@Slf4j
public class SnowflakeIdUtil {

    // ==============================Fields===========================================
    /**
     * 开始时间截 (2015-01-01) 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long START_TIMESTAMP = 1420041600000L;
    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;
    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;
    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = ~(-1L << workerIdBits);
    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;
    /**
     * 机器ID向左移12位
     */
    @SuppressWarnings("all")
    private final long workerIdShift = sequenceBits;
    /**
     * 数据标识id向左移17位(12+5)
     */
    @SuppressWarnings("all")
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间截向左移22位(5+5+12)
     */
    @SuppressWarnings("all")
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    @SuppressWarnings("all")
    private final long sequenceMask = ~(-1L << sequenceBits);
    /**
     * 工作机器ID(0~31)
     */
    @SuppressWarnings("all")
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    @SuppressWarnings("all")
    private long datacenterId;
    /**
     * 毫秒内序列(0~4095) 【并发控制】
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 以SnowFlake算法，获取唯一有序id
     *
     * @return id
     */
    public static long getSnowflakeId() {
        return getIdWorker().nextId();
    }

    /**
     * 以SnowFlake算法，获取唯一有序id文本
     *
     * @return id
     */
    public static String getSnowflakeIdStr() {
        return String.valueOf(getIdWorker().nextId());
    }

    /**
     * 枚举实现单例
     */
    private enum SnowflakeSingleton {
        /**
         * 线程池单例枚举
         */
        INSTANCE;

        private final SnowflakeIdUtil idWorker;

        SnowflakeSingleton() {
            idWorker = new SnowflakeIdUtil();
        }

        public SnowflakeIdUtil getSingleton() {
            return idWorker;
        }
    }

    /**
     * 单例：静态常量类
     */
    private static final class IdWorkerHolder {
        /**
         * ID工作者
         */
        private static final SnowflakeIdUtil ID_WORKER = SnowflakeSingleton.INSTANCE.getSingleton();
    }

    /**
     * 单例
     */
    private static SnowflakeIdUtil getIdWorker() {
        return IdWorkerHolder.ID_WORKER;
    }

    //==============================Constructors=====================================

    /**
     * 构造函数
     * 工作ID (0~31)
     * 数据中心ID (0~31)
     */
    private SnowflakeIdUtil() {
        this.datacenterId = getDatacenterId();
        this.workerId = getMaxWorkerId(datacenterId);

        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long getNextMillisecond(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = currentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = getNextMillisecond(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIMESTAMP) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 获取 maxWorkerId
     * 根据数据中心ID和支持的最大机器ID&PID计算最大机器ID，该值常变
     */
    private long getMaxWorkerId(long datacenterId) {
        StringBuilder pidBuffer = new StringBuilder();
        pidBuffer.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            pidBuffer.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (pidBuffer.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识id部分
     * 根据IP&网络接口&MAC信息计算出数据中心ID
     */
    private long getDatacenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                // 获得网卡的硬件地址
                byte[] mac = network.getHardwareAddress();
                if (null == mac) {
                    mac = getMacAddress();
                }
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            log.error("SnowflakeIdUtil.getDatacenterId() exception:{}", e.getMessage(), e);
        }
        return id;
    }

    private static byte[] getMacAddress() throws SocketException {
        byte[] bytes = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            if (hardwareAddress != null && hardwareAddress.length > 0) {
                bytes = bytesToHexMac(hardwareAddress).getBytes();
            }
        }
        return bytes;
    }

    private static String bytesToHexMac(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            buf.append(String.format("%02x", bytes[i] & 0xff));
            if (i != bytes.length - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }

}
