package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.system.core.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class SystemUtilTest {

    @Test
    public void getRuntimeMXBean() {
        // 返回Java虚拟机运行时系统相关属性
        RuntimeMXBean runtimeMXBean = SystemUtil.getRuntimeMXBean();
        System.out.println(StringUtil.formatString(runtimeMXBean.getObjectName()));
        // System.out.println(StringUtil.formatString(runtimeMXBean.getBootClassPath()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getClassPath()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getClass()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getInputArguments()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getLibraryPath()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getManagementSpecVersion()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getName()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getPid()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getSpecName()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getSpecVendor()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getSpecVersion()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getStartTime()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getSystemProperties()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getUptime()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getVmName()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getVmVendor()));
        System.out.println(StringUtil.formatString(runtimeMXBean.getVmVersion()));
    }

    @Test
    public void getCpuAvailableProcessors() {
        // 返回Java虚拟机可用的处理器数量
        int cpuAvailableProcessors = SystemUtil.getCpuAvailableProcessors();
        System.out.println(cpuAvailableProcessors);
    }

    @Test
    public void getOsInfo() {
        // 取得OS的信息
        OsInfo osInfo = SystemUtil.getOsInfo();
        System.out.println(StringUtil.formatString(osInfo));
    }

    @Test
    public void getUserInfo() {
        // 取得User的信息
        UserInfo userInfo = SystemUtil.getUserInfo();
        System.out.println(StringUtil.formatString(userInfo));
    }

    @Test
    public void getHostInfo() {
        // 取得Host的信息
        HostInfo hostInfo = SystemUtil.getHostInfo();
        System.out.println(StringUtil.formatString(hostInfo));
    }

    @Test
    public void getRuntimeInfo() {
        // 取得Runtime的信息
        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
        // 获得JVM已分配内存中的剩余空间
        System.out.println("获得JVM已分配内存中的剩余空间 = " + runtimeInfo.getFreeMemory());
        // 获得JVM最大内存
        System.out.println("获得JVM最大内存 = " + runtimeInfo.getMaxMemory());
        // 获得运行时对象
        System.out.println("获得运行时对象 = " + StringUtil.formatString(runtimeInfo.getRuntime()));
        // 获得JVM已分配内存
        System.out.println("获得JVM已分配内存 = " + runtimeInfo.getTotalMemory());
        // 获得JVM最大可用内存
        System.out.println("获得JVM最大可用内存 = " + runtimeInfo.getUsableMemory());
        System.out.println("运行类 = " + runtimeInfo.getClass());

        System.out.println("运行时信息 = " + runtimeInfo);
    }

    @Test
    public void getTotalMemory() {
        // 获取JVM中内存总大小
        long totalMemory = SystemUtil.getTotalMemory();
        System.out.println("获取JVM中内存总大小 = " + ByteSizeConvert.model().format(totalMemory));
    }

    @Test
    public void getFreeMemory() {
        // 获取JVM中内存剩余大小
        long freeMemory = SystemUtil.getFreeMemory();
        System.out.println(ByteSizeConvert.model().format(freeMemory));
    }

    @Test
    public void getMaxMemory() {
        // 获取JVM可用的内存总大小
        long maxMemory = SystemUtil.getMaxMemory();
        System.out.println(ByteSizeConvert.model().format(maxMemory));
    }

    @Test
    public void getTotalThreadCount() {
        // 获取总线程数
        long totalThreadCount = SystemUtil.getTotalThreadCount();
        System.out.println(totalThreadCount);
    }

    @Test
    public void isMacOs() {
        System.out.println(SystemUtil.isMacOs());
    }

    @Test
    public void isLinux() {
        System.out.println(SystemUtil.isLinux());
    }

    @Test
    public void isWindows() {
        System.out.println(SystemUtil.isWindows());
    }

    @Test
    public void getMACAddress() throws IOException {
        // 获得网卡地址
        System.out.println(SystemUtil.getMACAddress());
        System.out.println(getInetAddress());
        System.out.println(NetworkUtil.getLocalIpAddr());
    }

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

    @Test
    public void getProperties() {
        // 获取系统环境属性
        System.out.println(StringUtil.formatString(SystemUtil.getProperties()));
    }

    @Test
    public void getProperty() {
        System.out.println(SystemUtil.getProperty("os.name"));
        System.out.println(SystemUtil.getProperty("os.name", "macOS"));
    }

    @Test
    public void testGetProperty() {
        System.out.println(SystemUtil.getProperty(SystemEnvParam.USER_NAME));
        System.out.println(SystemUtil.getProperty(null, true));
    }

    @Test
    public void getLocalHostName() {
        System.out.println(SystemUtil.getHostAddress());
        System.out.println(SystemUtil.getLocalHostName());
    }

    @Test
    public void getLocalHost() throws UnknownHostException {
        // 获取本地主机
        InetAddress ia = InetAddress.getLocalHost();
        // 获得主机地址
        System.out.println(ia.getHostAddress());
        System.out.println(ia.getHostName());
        System.out.println(Arrays.toString(ia.getAddress()));
        System.out.println(ia.getCanonicalHostName());
    }

    @Test
    void getCurrentPID() {
        System.out.println(SystemUtil.getCurrentPID());
    }

    @Test
    void getCurrentUptime() {
        System.out.println(SystemUtil.getCurrentUptime());
    }

    @Test
    void props() {
        Properties props = SystemUtil.props();
        System.out.println(StringUtil.formatString(props));
    }

    @Test
    void getLineSeparator() {
        System.out.println(Arrays.toString(SystemUtil.getLineSeparator().getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void test() throws IOException {
        System.out.println("本机IP ====> " + SystemUtil.getHostAddress());
        System.err.println("本机真实IP ====> " + NetworkUtil.getLocalNetworkCardIP());
        System.out.println("本机名称 ====> " + SystemUtil.getLocalHostName());

        System.out.println("网卡地址 ====> " + SystemUtil.getMACAddress());
        System.out.println("ping ip ====> " + SystemUtil.getMacInLinux("10.152.2.212"));

        System.out.println("显示器分辨率：" + SystemUtil.getScreenSize());
        System.out.println("显示器像素：" + SystemUtil.getScreenDpi());
        System.out.println("显示器尺寸：" + SystemUtil.getScreenInches());

        Arrays.stream(SystemEnvParam.values()).forEach(env ->
                System.out.println(env.getDesc() + " ====> " + SystemUtil.getProperty(env.getCode())));
    }

    @Test
    void stackXms() {
        System.out.println(SystemUtil.stackXms());
    }

    @Test
    void stackXmx() {
        System.out.println(SystemUtil.stackXmx());
    }

    @Test
    void stackScope() {
        System.out.println(SystemUtil.stackScope());
    }

}