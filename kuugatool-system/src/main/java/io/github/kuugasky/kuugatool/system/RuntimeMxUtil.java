package io.github.kuugasky.kuugatool.system;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Map;

/**
 * RuntimeMXUtil
 *
 * @author kuuga
 * @since 2022/7/7
 */
public final class RuntimeMxUtil {

    private static final RuntimeMXBean RUNTIME_MX_BEAN;

    static {
        RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();
    }

    /**
     * 获取当前进程ID
     *
     * @return 进程id
     */
    public static long getPid() {
        return RUNTIME_MX_BEAN.getPid();
    }

    /**
     * 返回表示正在运行的Java虚拟机的名称。
     * 返回的名称字符串可以是任意字符串，Java虚拟机实现可以选择在返回的名称字符串中嵌入特定于平台的有用信息。
     * 每个运行的虚拟机可以有不同的名称。
     *
     * @return 虚拟机的名称
     */
    public static String getName() {
        return RUNTIME_MX_BEAN.getName();
    }

    /**
     * 返回Java虚拟机实现名称。这个方法等价于{@link System#getProperty System.getProperty("java.vm.name")}。
     *
     * @return 虚拟机实现名称
     */
    public static String getVmName() {
        return RUNTIME_MX_BEAN.getVmName();
    }

    /**
     * 返回Java虚拟机规范名称。这个方法等价于{@link System#getProperty System.getProperty("java.vm.specification.name")}。
     *
     * @return 虚拟机规范名称
     */
    public static String getSpecName() {
        return RUNTIME_MX_BEAN.getSpecName();
    }

    /**
     * 以毫秒为单位返回Java虚拟机的正常运行时间
     *
     * @return 运行时间
     */
    public static long getUptime() {
        return RUNTIME_MX_BEAN.getUptime();
    }

    /**
     * 返回Java虚拟机的开始时间，以毫秒为单位。这个方法返回Java虚拟机启动的大约时间。
     *
     * @return 开始时间
     */
    public static long getStartTime() {
        return RUNTIME_MX_BEAN.getStartTime();
    }

    /**
     * 返回传递给Java虚拟机的输入参数，其中不包括{@code main}方法的参数。如果Java虚拟机没有输入参数，这个方法返回一个空列表。
     *
     * @return 开始时间
     */
    public static List<String> getInputArguments() {
        return RUNTIME_MX_BEAN.getInputArguments();
    }

    /**
     * 返回系统类装入器用于搜索类文件的Java类路径
     *
     * @return java类路径
     */
    public static String getClassPath() {
        return RUNTIME_MX_BEAN.getClassPath();
    }

    /**
     * 返回引导类装入器用于搜索类文件的引导类路径
     *
     * @return 引导类路径
     */
    public static String getBootClassPath() {
        return RUNTIME_MX_BEAN.getBootClassPath();
    }

    /**
     * 返回Java库路径
     *
     * @return Java库路径
     */
    public static String getLibraryPath() {
        return RUNTIME_MX_BEAN.getLibraryPath();
    }

    /**
     * 返回正在运行的Java虚拟机实现的管理接口的规范版本
     *
     * @return 规范版本
     */
    public static String getManagementSpecVersion() {
        return RUNTIME_MX_BEAN.getManagementSpecVersion();
    }

    /**
     * 返回Java虚拟机规格供应商。这个方法等价于{@link System#getProperty System.getProperty("java.vm.specification.vendor")}。
     *
     * @return 虚拟机规格供应商
     */
    public static String getSpecVendor() {
        return RUNTIME_MX_BEAN.getSpecVendor();
    }

    /**
     * 返回Java虚拟机实现版本。这个方法等价于{@link System#getProperty System.getProperty("java.vm.version")}。
     *
     * @return 虚拟机实现版本
     */
    public static String getVmVersion() {
        return RUNTIME_MX_BEAN.getVmVersion();
    }

    /**
     * 返回Java虚拟机规范版本。这个方法等价于{@link System#getProperty System.getProperty("java.vm.specification.version")}。
     *
     * @return 虚拟机规范版本
     */
    public static String getSpecVersion() {
        return RUNTIME_MX_BEAN.getSpecVersion();
    }

    /**
     * 平台管理对象是一个用于监视和管理Java平台中的组件的{@linkplain javax.management.MXBean JMX MXBean}。
     *
     * @return 平台管理对象
     */
    public static ObjectName getObjectName() {
        return RUNTIME_MX_BEAN.getObjectName();
    }

    /**
     * 测试Java虚拟机是否支持引导类装入器用于搜索类文件的引导类路径机制。
     *
     * @return boolean
     */
    public static boolean isBootClassPathSupported() {
        return RUNTIME_MX_BEAN.isBootClassPathSupported();
    }

    /**
     * 返回所有系统属性的名称和值的映射。这个方法调用{@link System#getProperties}来获取所有系统属性。省略名称或值不是{@code String}的属性。
     *
     * @return 所有系统属性的名称和值的映射
     */
    public static Map<String, String> getSystemProperties() {
        return RUNTIME_MX_BEAN.getSystemProperties();
    }

}
