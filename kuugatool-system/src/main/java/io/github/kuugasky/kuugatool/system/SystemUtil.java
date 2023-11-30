package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.lang.single.Singleton;
import io.github.kuugasky.kuugatool.core.numerical.BigDecimalUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.system.core.*;
import lombok.Data;
import org.apache.commons.lang3.SystemUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统常用工具类
 *
 * @author kuuga
 * @since 2017-09-13
 */
public final class SystemUtil {

    private static final String MAC = "Mac";
    private static final String WINDOWS = "Windows";

    /**
     * 获取调用的java路径
     *
     * @return javaHome路径
     */
    public static String getJavaHome() {
        return getProperty(SystemEnvParam.JAVA_HOME);
    }

    /**
     * 获取调用的java版本
     *
     * @return java版本
     */
    public static String getJavaVersion() {
        return getProperty(SystemEnvParam.JAVA_VERSION);
    }

    /**
     * 获取系统环境属性值
     *
     * @param envParam envParam
     * @return String
     */
    public static String getProperty(SystemEnvParam envParam) {
        return System.getProperty(envParam.getCode(), StringUtil.EMPTY);
    }

    /**
     * 获取系统环境属性值
     * key描述观看下文
     *
     * @return String
     */
    public static String getProperty(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        // 获得系统属性
        return getProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        // 获得系统属性
        return getProperties().getProperty(key, defaultValue);
    }

    /**
     * 取得系统属性，如果因为Java安全的限制而失败，则将错误打在Log中，然后返回 {@code null}
     *
     * @param key   属性名
     * @param quiet 安静模式，不将出错信息打在<code>System.err</code>中
     * @return 属性值或{@code null}
     * @see System#getProperty(String)
     * @see System#getenv(String)
     */
    public static String getProperty(String key, boolean quiet) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        String value = null;
        try {
            value = System.getProperty(key);
        } catch (SecurityException e) {
            if (!quiet) {
                Console.error("Caught a SecurityException reading the system property '{}'; " +
                        "the SystemUtil property value will default to null.", key);
            }
        }

        if (null == value) {
            try {
                value = System.getenv(key);
            } catch (SecurityException e) {
                if (!quiet) {
                    Console.error("Caught a SecurityException reading the system env '{}'; " +
                            "the SystemUtil env value will default to null.", key);
                }
            }
        }
        return value;
    }

    /**
     * 获取系统主屏分辨率
     *
     * @return 1920x1080
     */
    public static String getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        return new BigDecimal(width).intValue() + "x" + new BigDecimal(height).intValue();
    }

    /**
     * 获取电脑屏幕每英寸的像素。（相当于密度）
     *
     * @return int
     */
    public static int getScreenDpi() {
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

    /**
     * 获取电脑屏幕的物理尺寸【英寸】
     *
     * @return String
     */
    public static String getScreenInches() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = screenSize.getWidth();
        Double height = screenSize.getHeight();
        int dpi = getScreenDpi();
        // 根据dpi和像素，可以计算物理尺寸
        BigDecimal widthRatio = BigDecimalUtil.divide(width, dpi);
        BigDecimal heightRatio = BigDecimalUtil.divide(height, dpi);
        return widthRatio.doubleValue() + "x" + heightRatio.doubleValue();
    }

    /**
     * 获取当前系统属性
     *
     * @return 属性列表
     */
    public static Properties props() {
        return System.getProperties();
    }

    /**
     * 获取当前进程 PID
     *
     * @return 当前进程 ID
     */
    public static long getCurrentPID() {
        return Long.parseLong(getRuntimeMXBean().getName().split("@")[0]);
    }

    /**
     * 获取当前进程启动时长
     *
     * @return 当前进程 ID
     */
    public static long getCurrentUptime() {
        return getRuntimeMXBean().getUptime();
    }

    /**
     * 返回Java虚拟机运行时系统相关属性
     *
     * @return {@link RuntimeMXBean}
     * @since 4.1.4
     */
    public static RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean();
    }

    /**
     * 返回Java虚拟机可用的处理器核心数量
     *
     * @return int
     */
    public static int getCpuAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 取得OS的信息。
     *
     * @return <code>OsInfo</code>对象
     */
    public static OsInfo getOsInfo() {
        return Singleton.get(OsInfo.class);
    }

    /**
     * 取得User的信息。
     *
     * @return <code>UserInfo</code>对象
     */
    public static UserInfo getUserInfo() {
        return Singleton.get(UserInfo.class);
    }

    /**
     * 取得Host的信息。
     *
     * @return <code>HostInfo</code>对象
     */
    public static HostInfo getHostInfo() {
        return Singleton.get(HostInfo.class);
    }

    /**
     * 取得Runtime的信息。
     *
     * @return <code>RuntimeInfo</code>对象
     */
    public static RuntimeInfo getRuntimeInfo() {
        return Singleton.get(RuntimeInfo.class);
    }

    @Data
    static class StackScope {
        private String stackXms;
        private String stackXmx;
    }

    /**
     * 获取最小堆栈
     *
     * @return stackXms
     */
    public static String stackXms() {
        return stackScope().getStackXms();
    }

    /**
     * 获取最大堆栈
     *
     * @return stackXmx
     */
    public static String stackXmx() {
        return stackScope().getStackXmx();
    }

    /**
     * 获取堆栈范围
     *
     * @return StackScope
     */
    public static StackScope stackScope() {
        String stackMin = "-Xms";
        String stackMax = "-Xmx";
        AtomicReference<String> stackXms = new AtomicReference<>(StringUtil.EMPTY);
        AtomicReference<String> stackXmx = new AtomicReference<>(StringUtil.EMPTY);
        StackScope stackScope = new StackScope();
        RuntimeMxUtil.getInputArguments().forEach(inputArgument -> {
            if (inputArgument.startsWith(stackMin)) {
                stackXms.set(StringUtil.removeStart(inputArgument, stackMin));
            }
            if (inputArgument.startsWith(stackMax)) {
                stackXmx.set(StringUtil.removeStart(inputArgument, stackMax));
            }
        });
        if (StringUtil.isEmpty(stackXmx.get())) {
            stackXmx.set(ByteSizeConvert.model().format(SystemUtil.getTotalMemory()));
        }
        stackScope.setStackXms(stackXms.get().toUpperCase());
        stackScope.setStackXmx(stackXmx.get().toUpperCase());
        return stackScope;
    }

    /**
     * 获取JVM中内存总大小
     *
     * @return 内存总大小
     * @since 4.5.4
     */
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 获取JVM中内存剩余大小
     *
     * @return 内存剩余大小
     * @since 4.5.4
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取JVM可用的内存总大小
     *
     * @return JVM可用的内存总大小
     * @since 4.5.4
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取总线程数
     *
     * @return 总线程数
     */
    public static int getTotalThreadCount() {
        ThreadGroup parentThread = Thread.currentThread().getThreadGroup();
        while (null != parentThread.getParent()) {
            parentThread = parentThread.getParent();
        }
        return parentThread.activeCount();
    }

    /**
     * 是否mac系统
     *
     * @return boolean
     */
    public static boolean isMacOs() {
        return SystemUtils.IS_OS_MAC;
        // return getSystemType().startsWith("Mac");
    }

    /**
     * 是否linux系统
     *
     * @return boolean
     */
    public static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
        // return getSystemType().startsWith("Mac");
    }

    /**
     * 是否windows系统
     *
     * @return boolean
     */
    public static boolean isWindows() {
        // org.apache.commons.lang.SystemUtils提供了很多获取系统级别信息的方法
        return SystemUtils.IS_OS_WINDOWS;
        // return getSystemType().startsWith("Windows");
    }

    /**
     * 获得网卡地址
     * 兼容Windows和Mac
     *
     * @return String
     */
    public static String getMACAddress() throws IOException {
        String address = StringUtil.EMPTY;
        String os = getProperty(SystemEnvParam.OS_NAME);

        if (null == os) {
            return address;
        }
        if (os.startsWith(MAC)) {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuilder stringBuilder = new StringBuilder();
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface != null) {
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuilder.append(":");
                            }
                            // 字节转换为整数
                            int tmp = bytes[i] & 0xff;
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuilder.append("0").append(str);
                            } else {
                                stringBuilder.append(str);
                            }
                        }
                        return stringBuilder.toString().toUpperCase();
                    }
                }
            }
        } else if (os.startsWith(WINDOWS)) {
            String command = "cmd.exe /c ipconfig /all";
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("Physical Address") > 0) {
                    int index = line.indexOf(":");
                    index += 2;
                    address = line.substring(index);
                    break;
                }
            }
            br.close();
            return address.trim();
        }
        return address;
    }

    /**
     * 获取系统环境属性
     *
     * @return Properties
     */
    public static Properties getProperties() {
        // 获得系统属性集
        return System.getProperties();
    }

    /**
     * @param ip 目标ip
     * @return Mac Address
     */
    public static String getMacInLinux(String ip) {
        String result = null;
        if (isLinux()) {
            String[] cmd = {"/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a"};
            String cmdResult = callCmd(cmd);
            result = filterMacAddress(ip, cmdResult, ":");
        }
        return result;
    }

    /**
     * Mac地址过滤
     *
     * @param ip           目标ip,一般在局域网内
     * @param sourceString 命令处理的结果字符串
     * @param macSeparator mac分隔符号
     * @return mac地址，用上面的分隔符号表示
     */

    private static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = StringUtil.EMPTY;
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            result = matcher.group(1);
            if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break; // 如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result;
    }

    /**
     * 命令获取mac地址
     *
     * @param cmd cmd
     * @return String
     */
    private static String callCmd(String[] cmd) {
        StringBuilder result = new StringBuilder();
        String line;
        InputStream inputStream;
        Process proc;

        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec(cmd);
            inputStream = proc.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtil.EMPTY;
        }

        try (InputStreamReader is = new InputStreamReader(inputStream);
             BufferedReader br = new BufferedReader(is)) {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            return StringUtil.EMPTY;
        }
        return result.toString();
    }

    /**
     * 以文本形式返回IP地址字符串
     */
    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取此IP地址的主机名
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前系统的换行分隔符
     *
     * <pre>
     * Windows: \r\n
     * Mac: \r
     * Linux: \n
     * </pre>
     *
     * @return 换行符
     * @since 4.0.5
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
        // return System.getProperty("line.separator");
    }

    /**
     * 输出到<code>StringBuilder</code>。
     *
     * @param builder <code>StringBuilder</code>对象
     * @param caption 标题
     * @param value   值
     */
    public static void append(StringBuilder builder, String caption, Object value) {
        builder.append(caption).append(ObjectUtil.getOrElse(StringUtil.toString(value), "[n/a]")).append("\n");
    }

}
