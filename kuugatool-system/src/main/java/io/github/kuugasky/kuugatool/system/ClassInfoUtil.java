package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.net.URL;
import java.util.Objects;

/**
 * class工具类
 * 用于获取类的绝对路径等信息
 *
 * @author kuuga
 */
public final class ClassInfoUtil {

    /**
     * 起源堆栈指数
     */
    private static final int ORIGIN_STACK_INDEX = 2;

    /**
     * 获取当前执行代码文件名（ClassInfoUtil.java）
     *
     * @return String
     */
    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[ORIGIN_STACK_INDEX].getFileName();
    }

    /**
     * 获取当前执行代码类名（Kuuga.util.system.ClassInfoUtil）
     *
     * @return String
     */
    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[ORIGIN_STACK_INDEX].getClassName();
    }

    /**
     * 获取当前执行代码方法名
     *
     * @return String
     */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[ORIGIN_STACK_INDEX].getMethodName();
    }

    /**
     * 获取当前执行代码行数
     *
     * @return int
     */
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[ORIGIN_STACK_INDEX].getLineNumber();
    }

    /**
     * 获取当前类文件的URI目录,不包括自己
     * /Users/kuuga/intellijProject/gitos/Kuuga-git/Kuuga-parent/Kuuga-utils/target/classes/kuuga/utils/
     *
     * @return Object
     */
    public static Object getCurrentFileUriFolder() {
        return SystemUtil.class.getResource(StringUtil.EMPTY);
    }

    /**
     * 推荐使用Thread.currentThread().getContextClassLoader().getResource("")来得到当前的classpath的绝对路径的URI表示法
     * 获取当前类文件的classPath的绝对URI路径
     * /Users/kuuga/intellijProject/gitos/Kuuga-git/Kuuga-parent/Kuuga-admin/target/classes/
     *
     * @return URL
     */
    public static URL getProjectTargetClasses() {
        return Thread.currentThread().getContextClassLoader().getResource(StringUtil.EMPTY);
        // SystemUtil.class.getClassLoader().getResource(StringUtil.EMPTY);
        // return SystemUtil.class.getResource("/");
    }

    /**
     * 获取当前执行文件所在的/target/class路径
     *
     * @param clazz 对象class类
     * @return String
     */
    public static String getCurrentClassFilePath(Class<?> clazz) {
        return Objects.requireNonNull(clazz.getResource("/")).getPath();
    }


    /**
     * 获取当前类文件的URI目录,不包括自己
     * /Users/kuuga/intellijProject/gitos/Kuuga-git/Kuuga-parent/Kuuga-utils/target/classes/kuuga/utils/
     *
     * @return String
     */
    public static String getCurrentFileUriFolderNoTarget() {
        return getCurrentClassFilePath(ClassInfoUtil.class).replaceAll("target/classes/", StringUtil.EMPTY);
    }

}
