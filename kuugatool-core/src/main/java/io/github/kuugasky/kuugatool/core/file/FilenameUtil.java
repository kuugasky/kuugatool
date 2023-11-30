package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.constants.KuugaCharConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * FilenameUtil
 *
 * @author kuuga
 * @since 2021/6/25
 */
public final class FilenameUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private FilenameUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * .java文件扩展名
     */
    public static final String EXT_JAVA = ".java";
    /**
     * .class文件扩展名
     */
    public static final String EXT_CLASS = ".class";
    /**
     * .jar文件扩展名
     */
    public static final String EXT_JAR = ".jar";

    /**
     * 类Unix路径分隔符
     */
    @SuppressWarnings("unused")
    public static final char UNIX_SEPARATOR = KuugaCharConstants.SLASH;
    /**
     * Windows路径分隔符
     */
    @SuppressWarnings("unused")
    public static final char WINDOWS_SEPARATOR = KuugaCharConstants.BACKSLASH;

    /**
     * Windows下文件名中的无效字符
     */
    private static final Pattern FILE_NAME_INVALID_PATTERN_WIN = Pattern.compile("[\\\\/:*?\"<>|]");

    // ================================================================================================================

    /**
     * 文件名是.class后缀
     *
     * @param file file
     * @return isClass
     */
    public static boolean isClass(File file) {
        return getName(file).endsWith(EXT_CLASS);
    }

    /**
     * 文件名是.java后缀
     *
     * @param file file
     * @return isJava
     */
    public static boolean isJava(File file) {
        return getName(file).endsWith(EXT_JAVA);
    }

    /**
     * 文件名是.jar后缀
     *
     * @param file file
     * @return isJar
     */
    public static boolean isJar(File file) {
        return getName(file).endsWith(EXT_JAR);
    }

    // ===========================================================================================================
    // 获取文件名、后缀等
    // ===========================================================================================================

    /*
        org.apache.commons.io.FilenameUtils
        getExtension：返回文件后缀名
        getBaseName：返回文件名，不包含后缀名
        getName：返回文件全名
        concat：按命令行风格组合文件路径(详见方法注释)
        removeExtension：删除后缀名
        normalize：使路径正常化
        wildcardMatch：匹配通配符
        seperatorToUnix：路径分隔符改成unix系统格式的，即/
        getFullPath：获取文件路径，不包括文件名
        isExtension：检查文件后缀名是不是传入参数(List<String>)中的一个
     */

    /**
     * 返回文件名，含后缀
     *
     * @param file file
     * @return String
     */
    public static String getName(File file) {
        return (null != file) ? file.getName() : null;
    }

    /**
     * 返回文件名，含后缀
     *
     * @param fileFullName fileFullName
     * @return String
     */
    public static String getName(String fileFullName) {
        return getName(new File(fileFullName));
    }

    /**
     * 返回文件全名
     *
     * @param fileFullName 文件全名
     * @param separator    分隔符，用于移除文件名后拼接信息
     * @return string
     */
    public static String getName(String fileFullName, String separator) {
        String fileName = getName(fileFullName);
        if (StringUtil.hasText(fileName) && StringUtil.hasText(separator)) {
            return fileName.split(separator)[0];
        }
        return fileName;
    }

    /**
     * 返回文件名，不包含后缀名
     * <p>
     * {@link FilenameUtils#getBaseName(String)} ;}
     *
     * @param file file
     * @return String
     */
    public static String getBaseName(File file) {
        if (ObjectUtil.isNull(file)) {
            return StringUtil.EMPTY;
        }
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * 返回文件名，不包含后缀名
     *
     * @param fileFullName fileFullName
     * @return String
     */
    public static String getBaseName(String fileFullName) {
        return getBaseName(new File(fileFullName));
    }

    /**
     * 返回文件后缀名
     *
     * @param file 文件
     * @return 文件后缀 jpg/pdf...
     */
    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    /**
     * 返回文件后缀名
     *
     * @param fileFullName 文件后缀
     * @return 文件后缀 jpg/pdf...
     */
    public static String getExtension(String fileFullName) {
        return FilenameUtils.getExtension(fileFullName);
    }

    /**
     * 从完整文件名中获取路径前缀。
     * /Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-captcha/src/main/java/cn/kuugatool/captcha/
     *
     * @param file 文件
     * @return 完整路径
     */
    public static String getPathPrefix(File file) {
        return FilenameUtils.getFullPath(file.getPath());
    }

    /**
     * 从完整文件名中获取完整路径。
     * /Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-captcha/src/main/java/cn/kuugatool/captcha/AbstractCaptcha.java
     *
     * @param file 文件
     * @return 完整路径
     */
    public static String getPath(File file) {
        return file.getPath();
    }

    /**
     * 检查文件后缀名是不是等于extension
     *
     * @param filename  文件名
     * @param extension 后缀名
     * @return 如果文件名是扩展名extension，则为True
     */
    public static boolean isExtension(final String filename, final String extension) {
        return FilenameUtils.isExtension(filename, extension);
    }

    /**
     * 检查文件后缀名是不是传入参数(String[])中的一个
     *
     * @param filename   文件名
     * @param extensions 后缀名数组
     * @return 如果文件名是其中一个扩展名，则为True
     */
    public static boolean isExtension(final String filename, final String[] extensions) {
        return FilenameUtils.isExtension(filename, extensions);
    }

    /**
     * 检查文件后缀名是不是传入参数extensions中的一个
     *
     * @param filename   文件名
     * @param extensions 后缀名集合
     * @return 如果文件名是其中一个扩展名，则为True
     */
    public static boolean isExtension(final String filename, final Collection<String> extensions) {
        return FilenameUtils.isExtension(filename, extensions);
    }

    /**
     * 删除文件后缀名
     *
     * @param fileName 文件名
     * @return 移除后缀名的的文件名
     */
    public static String removeExtension(String fileName) {
        return FilenameUtils.removeExtension(fileName);
    }

    /**
     * 清除文件名中的在Windows下不支持的非法字符，包括： \ / : * ? " &lt; &gt; |
     *
     * @param fileName 文件名（必须不包括路径，否则路径符将被替换）
     * @return 清理后的文件名
     * @since 3.3.1
     */
    public static String cleanInvalid(String fileName) {
        return StringUtil.isEmpty(fileName) ? fileName : RegexUtil.delAll(FILE_NAME_INVALID_PATTERN_WIN, fileName);
    }

}
