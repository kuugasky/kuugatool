package io.github.kuugasky.kuugatool.core.file;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Predicate;

/**
 * FilesUtil
 * <p>
 * Guava files.
 *
 * @author kuuga
 * @since 2021/7/13
 */
public final class FilesUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private FilesUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isDirectory(final File file) {
        Predicate<File> directory = Files.isDirectory()::apply;
        return directory.test(file);
    }

    public static boolean isFile(final File file) {
        Predicate<File> directory = Files.isFile()::apply;
        return directory.test(file);
    }

    public static void write(final byte[] from, final File to) throws IOException {
        Files.write(from, to);
    }

    /**
     * 读取file文件内容，多行返回
     *
     * @param from    文件
     * @param charset 编码
     * @return 多行文件内容
     * @throws IOException IOException
     */
    public static List<String> readLines(final File from, final Charset charset) throws IOException {
        return Files.readLines(from, charset);
    }

    /**
     * 拷贝文件
     * ps:同文件名不能拷贝
     *
     * @param from 源文件
     * @param to   目标文件
     * @throws IOException IOException
     */
    public static void copy(final File from, final File to) throws IOException {
        Files.copy(from, to);
    }

    /**
     * 移动文件
     * ps:可以用于文件改名，同路径不同文件名移动
     *
     * @param from 源文件
     * @param to   目标文件
     * @throws IOException IOException
     */
    public static void move(final File from, final File to) throws IOException {
        Files.move(from, to);
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 文件
     * @return 扩展名
     */
    public static String getFileExtension(final String file) {
        return Files.getFileExtension(file);
    }

    /**
     * 获取不带扩展名的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getNameWithoutExtension(final String file) {
        return Files.getNameWithoutExtension(file);
    }

}
