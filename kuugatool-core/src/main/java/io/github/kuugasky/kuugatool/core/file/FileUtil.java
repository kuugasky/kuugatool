package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.constants.HttpConstants;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.enums.ByteType;
import io.github.kuugasky.kuugatool.core.exception.IORuntimeException;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author kuuga
 */
@Slf4j
public final class FileUtil {

    private static final String REGEX = "\\.";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private FileUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // 创建 ======================================================================================================

    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (StringUtil.isEmpty(path)) {
            throw new RuntimeException("文件路径不能为空");
        }
        return new File(path);
    }

    // 读取字节 ======================================================================================================

    /**
     * 读取file转字节数组
     *
     * @param file 文件
     * @return File
     */
    public static byte[] readBytes(File file) {
        if (!file.exists()) {
            return null;
        }
        return IoUtil.fileToByte(file);
    }

    // ===========================================================================================================
    // 输入流 -> 文件
    // ===========================================================================================================

    /**
     * 输入流转换为文件对象
     *
     * @param inputStream 输入流
     * @param file        文件对象
     * @throws IOException IOException
     */
    public static void inputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = inputStream.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        }
    }

    // ===========================================================================================================
    // 创建目录、文件等
    // ===========================================================================================================

    /**
     * 创建一个空文件
     *
     * @param file 文件
     */
    public static void createFile(File file) {
        createFile(file.getPath());
    }

    /**
     * 创建一个空文件(支持创建文件夹)
     *
     * @param filePath 文件路径
     * @return newFile
     */
    public static File createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    log.info("文件创建成功:{}", filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("文件创建异常:{}", e.getMessage(), e);
            }
        } else {
            log.warn("文件已存在:{}", filePath);
        }
        return file;
    }

    /**
     * 创建一个空文件，并写入内容
     *
     * @param file        文件
     * @param fileContext 写入内容
     */
    public static void createFile(File file, String fileContext) {
        try {
            // 如果文件不存在
            if (!file.exists()) {
                // 创建文件
                boolean newFile = file.createNewFile();
                if (newFile) {
                    log.info("文件创建成功:{}", file.getPath());
                } else {
                    log.info("文件创建失败:{}", file.getPath());
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            // 在创建好的文件中写入context
            bw.write(fileContext);
            bw.close();// 一定要关闭文件
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件创建异常:{}", e.getMessage(), e);
        }
    }

    public static void createDir(File folderFile) throws IOException {
        createDir(folderFile.getPath());
    }

    /**
     * 创建文件目录
     * Linux递归创建多层文件目录
     *
     * @param folderPath eg：/Users/kuuga/demo
     * @throws IOException IOException
     */
    public static void createDir(String folderPath) throws IOException {
        if (StringUtil.isEmpty(folderPath)) {
            return;
        }

        String[] split = folderPath.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for (String path : split) {
            if (StringUtil.isEmpty(path)) {
                continue;
            }
            stringBuilder.append("/").append(path);
            Path target = Paths.get(stringBuilder.toString());
            // 文件夹不存在则创建
            if (!java.nio.file.Files.isReadable(target)) {
                java.nio.file.Files.createDirectory(target);
            }
        }
    }

    /**
     * 强制创建文件夹，如果该文件夹父级目录不存在，则创建父级
     * 创建一个目录，包括任何必要但不存在的父目录。如果指定的文件名已经存在，但它不是一个目录，则抛出IOException。
     * 如果目录无法创建(或不存在)，则抛出IOException。
     *
     * @param directory 要创建的目录，不能是{@code null}
     * @throws NullPointerException 如果目录是{@code null}
     * @throws IOException          如果目录不能创建，或者文件已经存在，但不是目录
     */
    public static void forceMkdir(final File directory) throws IOException {
        FileUtils.forceMkdir(directory);
    }

    // ================================================================================================================
    // 删除
    // ================================================================================================================

    /**
     * 删除文件
     *
     * @param file 源文件或文件夹绝对路径
     * @return 是否成功删除文件或文件夹
     */
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        String filePath = file.getPath();
        if (file.isFile()) {
            boolean delete = file.delete();
            if (delete) {
                log.info("文件删除成功:{}", filePath);
            } else {
                log.warn("文件删除失败:{}", filePath);
            }
            return delete;
        } else {
            log.warn("文件删除失败，{}不是文件", filePath);
            return false;
        }
    }

    /**
     * 删除目录下的所有文件
     *
     * @param folder       目录
     * @param deleteFolder 删除完目录下所有文件后，是否要删除目录
     * @return boolean
     */
    public static boolean deleteAllFilesInDir(File folder, boolean deleteFolder) {
        if (!folder.exists()) {
            return false;
        }
        String folderPath = folder.getPath();
        if (folder.isFile()) {
            log.warn("目录删除失败，{}不是目录", folderPath);
            return false;
        }
        for (File srcSub : Objects.requireNonNull(folder.listFiles())) {
            // 递归删除源文件夹下子文件和文件夹
            deleteFile(srcSub);
        }
        if (deleteFolder) {
            boolean delete = folder.delete();
            if (delete) {
                log.info("目录删除成功:{}", folderPath);
            } else {
                log.warn("目录删除失败:{}", folderPath);
            }
        }
        return true;
    }

    /**
     * 强制删除文件夹或文件
     *
     * @param file 文件夹或文件
     * @throws IOException IOException
     */
    public static void forceDelete(File file) throws IOException {
        FileUtils.forceDelete(file);
    }

    /**
     * 递归地删除一个文件夹【慎用】
     * eg: /Users/kuuga/test
     *
     * @param folderPath 最外层文件夹路径
     * @throws IOException IOException
     */
    public static void deleteDirectory(String folderPath) throws IOException {
        FileUtils.deleteDirectory(new File(folderPath));
    }

    /**
     * 如果文件或目录存在，则删除
     *
     * @param file file
     * @throws IOException IOException
     */
    public static void deleteIfExists(File file) throws IOException {
        Files.deleteIfExists(file.toPath());
    }

    /**
     * 删除文件或目录
     *
     * @param file file
     * @throws IOException IOException
     */
    public static void delete(File file) throws IOException {
        Files.delete(file.toPath());
    }

    /**
     * 退出jvm时删除file
     *
     * @param file file
     */
    public static void deleteOnExit(File file) {
        file.deleteOnExit();
    }

    // 计算文件大小 ==========================================================================================================================

    /**
     * 计算网络文件size大小
     *
     * @param fileUrl fileUrl
     * @return 文件大小 byte
     */
    public static long getFileSizeForNetworkUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10 * 1000);
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                return connection.getContentLength();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 计算文件大小文件大小，使用代理
     *
     * @param fileUrl fileUrl
     * @param referer referer
     * @return 文件大小 byte
     */
    public static long getFileSizeForNetworkUrl(String fileUrl, String referer) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection;
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HttpConstants.PROXY_IP, HttpConstants.PROXY_PORT));
            connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("GET");
            // 必须设置false，否则会自动redirect到Location的地址
            connection.setInstanceFollowRedirects(false);

            connection.addRequestProperty("Accept-Charset", KuugaCharsets.UTF_8);
            connection.setRequestProperty("User-Agent", HttpConstants.USER_AGENT);
            connection.setRequestProperty("Referer", referer);
            connection.setConnectTimeout(10 * 1000);
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                return connection.getContentLength();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算文件大小
     *
     * @param file 文件
     * @return 文件大小 byte
     */
    public static long getFileSize(File file) {
        if (ObjectUtil.isNull(file)) {
            return 0L;
        }

        long fileSize = 0L;

        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                FileChannel fc = fis.getChannel();
                fileSize = fc.size();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("file doesn't exist or is not a file, file:{}", file.getPath());
        }
        return fileSize;
    }

    /**
     * 指定类型计算文件大小
     *
     * @param file     文件
     * @param byteType 字节单位类型
     * @return double
     */
    public static double getFileSize(File file, ByteType byteType) {
        long fileSizeForPath = getFileSize(file);
        return ByteSizeConvert.model().setScale(2, RoundingMode.HALF_UP).convertTo(fileSizeForPath, byteType);
    }

    /**
     * 生成副本名称
     *
     * @param fileName 文件名
     * @return String
     */
    public static String generateCopyName(String fileName) {
        if (StringUtil.isEmpty(fileName) || fileName.split(REGEX).length != KuugaConstants.TWO) {
            return fileName;
        }
        String fileNameWithoutExtension = FilenameUtil.getBaseName(fileName);
        String fileExtension = FilenameUtil.getExtension(fileName);
        // return fileNameWithoutExtension + "_" + System.currentTimeMillis() + "." + fileExtension;
        return fileNameWithoutExtension + "_" + Instant.now().toEpochMilli() + "." + fileExtension;
    }

    // ===========================================================================================================
    // 文件写入内容
    // ===========================================================================================================

    /**
     * Writes a String to a file creating the file if it does not exist.
     * 如果文件不存在，则将字符串写入创建文件的文件。内容追加到原文件内容末尾。
     *
     * @param file the file to write
     * @param data the content to write to the file
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void writeStringToFile(final File file, final String data) throws IOException {
        FileUtils.writeStringToFile(file, data, Charset.defaultCharset(), true);
    }

    /**
     * 如果存在，重写文件内容。如果文件不存在，则创建文件后写入。
     *
     * @param file the file to write
     * @param data the content to write to the file
     * @throws IOException in case of an I/O error
     */
    public static void rewriteStringToFile(final File file, final String data) throws IOException {
        FileUtils.writeStringToFile(file, data, Charset.defaultCharset(), false);
    }

    /**
     * Writes a String to a file creating the file if it does not exist.
     * 如果文件不存在，则将字符串写入创建文件的文件。内容追加到原文件内容末尾。
     *
     * @param file     the file to write
     * @param data     the content to write to the file
     * @param encoding the charset to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void writeStringToFile(final File file, final String data, final Charset encoding) throws IOException {
        FileUtils.writeStringToFile(file, data, encoding, true);
    }

    /**
     * 如果存在，重写文件内容。如果文件不存在，则创建文件后写入。
     *
     * @param file     the file to write
     * @param data     the content to write to the file
     * @param encoding the charset to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     */
    public static void rewriteStringToFile(final File file, final String data, final Charset encoding) throws IOException {
        FileUtils.writeStringToFile(file, data, encoding, false);
    }

    /**
     * 如果文件不存在，则将字符串写入创建文件的文件。内容追加到原文件内容末尾且换行。
     *
     * @param file the file to write
     * @param data the content to write to the file
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void writeStringToFileNewline(final File file, final String data) throws IOException {
        FileUtils.writeStringToFile(file, "\n" + data, Charset.defaultCharset(), true);
    }

    /**
     * 如果文件不存在，则将字符串写入创建文件的文件。内容追加到原文件内容末尾且换行。
     *
     * @param file     the file to write
     * @param data     the content to write to the file
     * @param encoding the charset to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void writeStringToFileNewline(final File file, final String data, final Charset encoding) throws IOException {
        FileUtils.writeStringToFile(file, "\n" + data, encoding, true);
    }

    /**
     * writer写入文件
     * 用FileWriter写入文件
     *
     * @param file    文件
     * @param context 写入内容
     */
    public static void writeStringToFileByWriter(File file, String context) throws IOException {
        try (FileWriter fWriter = new FileWriter(file)) {
            fWriter.write(context);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * writer写入文件
     * 用BufferedWriter写入文件
     *
     * @param content 写入内容
     * @param file    文件
     * @throws IOException io异常
     */
    public static void writeStringToFileByBuffered(File file, String content) throws IOException {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(file))) {
            bWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ===========================================================================================================
    // 文件写入内容lines
    // ===========================================================================================================

    public static void writeLines(File file, Collection<?> lines) throws IOException {
        FileUtils.writeLines(file, null, lines, null, false);
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, null, lines, lineEnding, false);
    }

    public static void writeLines(File file, Charset charset, Collection<?> lines) throws IOException {
        FileUtils.writeLines(file, String.valueOf(charset), lines, null, false);
    }

    public static void writeLines(File file, Charset charset, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, String.valueOf(charset), lines, lineEnding, false);
    }

    public static void rewriteLines(File file, Collection<?> lines) throws IOException {
        FileUtils.writeLines(file, String.valueOf(Charset.defaultCharset()), lines, null, true);
    }

    public static void rewriteLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, String.valueOf(Charset.defaultCharset()), lines, lineEnding, true);
    }

    public static void rewriteLines(File file, Charset charset, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(file, String.valueOf(charset), lines, lineEnding, true);
    }

    // ===========================================================================================================
    // 文件读取内容
    // ===========================================================================================================

    /**
     * 读取file文件内容，单行返回
     *
     * @param file    文件
     * @param charset 编码
     * @return 一行文件内容
     * @throws IOException IOException
     */
    public static String readFileToString(final File file, final Charset charset) throws IOException {
        return FileUtils.readFileToString(file, charset);
    }

    public static String readFileToString(final File file) throws IOException {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }

    /**
     * 用FileReader读取文件
     *
     * @param file 文件
     * @return str
     */
    public static String readFileToStringByFileReader(File file) {
        String data = StringUtil.EMPTY;
        try (FileReader fis = new FileReader(file)) {
            char[] arr = new char[1024 * 1000 * 6];
            int len = fis.read(arr);
            data = new String(arr, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 用FileInputStream读取文件
     *
     * @param file 文件
     * @return str
     */
    public static String readFileToStringByFileInputStream(File file) {
        String data = StringUtil.EMPTY;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] arr = new byte[1024 * 1000 * 6];
            int len = fis.read(arr);
            data = new String(arr, 0, len, Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 用BufferedReader读取文件
     *
     * @param file 文件
     * @return str
     */
    public static String readFileToStringByBufferedReader(File file) {
        String line;
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader bReader = new BufferedReader(new FileReader(file.getPath()))) {
            while ((line = bReader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String readFileToStringByBufferedReader(File file, Charset charset) {
        String line;
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            while ((line = bReader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtil.str(buffer.toString(), charset);
    }

    // ===========================================================================================================
    // 复制文件
    // ===========================================================================================================

    /**
     * 复制文件
     *
     * @param sourceFile 原文件
     * @param targetFile 目标文件
     * @throws IOException IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        FileCopyUtils.copy(sourceFile, targetFile);
    }

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param isOverride 是否覆盖
     * @throws IOException IOException
     */
    public static void copyFile(File sourceFile, File targetFile, boolean isOverride) throws IOException {
        if (!isOverride && targetFile.exists()) {
            return;
        }
        FileCopyUtils.copy(sourceFile, targetFile);
    }

    /**
     * 复制文件【字符流处理方式】
     *
     * @param sourceFile readFile
     * @param targetFile writeFile
     */
    public static void copyFileByFileWriter(File sourceFile, File targetFile) {
        try (FileReader input = new FileReader(sourceFile.getPath());
             FileWriter output = new FileWriter(targetFile.getPath())) {
            int read = input.read();
            while (read != -1) {
                output.write(read);
                read = input.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 复制文件【字节流处理方式】
     *
     * @param sourceFile readFile
     * @param targetFile writeFile
     */
    public static void copyFileByFileOutputStream(File sourceFile, File targetFile) {
        try (FileInputStream input = new FileInputStream(sourceFile.getPath());
             FileOutputStream output = new FileOutputStream(targetFile.getPath())) {
            int read = input.read();
            while (read != -1) {
                output.write(read);
                read = input.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 复制文件【BufferedReader流处理方式】
     * PS:不支持处理图片
     *
     * @param sourceFile readFilePath
     * @param targetFile writeFilePath
     */
    public static void copyFileByBufferedWriter(File sourceFile, File targetFile) {
        String line;
        try (BufferedReader bReader = new BufferedReader(new FileReader(sourceFile.getPath()));
             BufferedWriter bWriter = new BufferedWriter(new FileWriter(targetFile.getPath()))) {
            while ((line = bReader.readLine()) != null) {
                bWriter.write(line);
                bWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    // ===========================================================================================================
    // 复制目录
    // ===========================================================================================================

    /**
     * 目录拷贝
     *
     * @param srcDir  原目录
     * @param destDir 新目录
     * @throws IOException IOException
     */
    public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, true);
    }

    /**
     * 目录拷贝
     *
     * @param srcDir           原目录
     * @param destDir          新目录
     * @param preserveFileDate 保留文件的日期和时间
     * @throws IOException IOException
     */
    public static void copyDirectory(final File srcDir, final File destDir, final boolean preserveFileDate)
            throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    /**
     * 目录拷贝
     *
     * @param srcDir  原目录
     * @param destDir 新目录
     * @param filter  过滤出要拷贝的文件
     * @throws IOException IOException
     */
    public static void copyDirectory(final File srcDir, final File destDir, final FileFilter filter)
            throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, filter, true);
    }

    /**
     * 将整个目录复制到新位置。
     * 此方法将指定源目录的内容复制到指定的目标目录中。
     * 如果目标目录不存在，则创建目标目录。如果目标目录确实存在，则此方法将源与目标合并，源优先。
     *
     * @param srcDir           要复制的现有目录，不能是{@code null}
     * @param destDir          新目录不能是{@code null}
     * @param preserveFileDate true 复印件的归档日期是否应与原件相同。
     * @throws NullPointerException     如果任何给定的{@code file}是{@code null}
     * @throws IllegalArgumentException 如果源或目标无效
     * @throws FileNotFoundException    如果源不存在
     * @throws IOException              如果发生错误或设置最后修改时间没有成功
     * @since 1.1
     */
    public static void copyDirectory(final File srcDir, final File destDir, final FileFilter filter,
                                     final boolean preserveFileDate) throws IOException {
        FileUtils.copyDirectory(srcDir, destDir, filter, preserveFileDate, StandardCopyOption.REPLACE_EXISTING);
    }


    // ===========================================================================================================
    // 文件追加内容
    // ===========================================================================================================

    /**
     * 追加内容到文件头部
     *
     * @param file    file
     * @param content content
     */
    public static boolean appendToFirstPlace(File file, String content) {
        if (ObjectUtil.isNull(file)) {
            return false;
        }
        if (!file.exists()) {
            return false;
        }
        byte[] header = content.getBytes();
        try (RandomAccessFile src = new RandomAccessFile(file.getPath(), "rw")) {
            int srcLength = (int) src.length();
            byte[] buff = new byte[srcLength];
            src.read(buff, 0, srcLength);
            src.seek(0);
            src.write(header);
            src.seek(header.length);
            src.write(buff);
            return true;
        } catch (Exception e) {
            log.error("追加内容到文件头部异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 追加内容到文件尾部
     *
     * @param file    file
     * @param content content
     */
    public static boolean appendToLastPlace(File file, String content) {
        // 打开一个随机访问文件流，按读写方式
        try (RandomAccessFile randomFile = new RandomAccessFile(file, KuugaConstants.RW)) {
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("追加内容到文件尾部异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 如果打开文件以进行 WRITE 访问，则字节将被写入文件的末尾而不是开头。
     * <p>
     * 如果文件被其他程序打开以进行写访问，那么如果写入文件末尾是原子的，则它是文件系统特定的。
     * <p>
     * PS:仅拼接到末尾，不会自动生成文件
     *
     * @param file    file
     * @param content content
     * @throws IOException IOException
     */
    public static void append(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.APPEND);
    }

    // ===========================================================================================================
    // 获取输出流
    // ===========================================================================================================

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     * @throws IORuntimeException IO异常
     */
    public static BufferedOutputStream getOutputStream(File file) throws IORuntimeException {
        return IoUtil.toBuffered(IoUtil.toOutputStream(file));
    }

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     * @throws IORuntimeException 文件未找到
     */
    public static BufferedInputStream getInputStream(File file) throws IORuntimeException {
        return IoUtil.toBuffered(IoUtil.toInputStream(file));
    }

    // ===========================================================================================================
    // 是否目录
    // ===========================================================================================================

    /**
     * 判断是否是目录
     *
     * @param file file
     * @return boolean
     */
    public static boolean isDirectory(File file) {
        if (ObjectUtil.isNull(file)) {
            return false;
        }
        return file.exists() && file.isDirectory();
    }

    // ===========================================================================================================
    // 文件列表
    // ===========================================================================================================

    /**
     * 查找给定目录(或其子目录)中匹配扩展名数组的文件。
     *
     * @param directory  要搜索的目录
     * @param extensions 扩展的数组，例如{"java"，"xml"}。如果该参数为{@code null}，则返回所有文件。
     * @param recursive  如果为真，则搜索所有子目录
     * @return 包含匹配文件的java.io.File集合
     */
    public static Collection<File> listFiles(final File directory, final String[] extensions, final boolean recursive) {
        return FileUtils.listFiles(directory, extensions, recursive);
    }

    public static Collection<File> listFiles(final File directory, final boolean recursive) {
        return FileUtils.listFiles(directory, null, recursive);
    }

    // ===========================================================================================================
    // 获取子路径部分
    // ===========================================================================================================

    /**
     * 获取指定位置的最后一个子路径部分
     *
     * @param path 路径
     * @return 获取的最后一个子路径
     * @since 3.1.2
     */
    public static Path getLastPathEle(Path path) {
        return getPathEle(path, path.getNameCount() - 1);
    }

    /**
     * 获取指定位置的子路径部分，支持负数，例如index为-1表示从后数第一个节点位置
     *
     * @param path  路径
     * @param index 路径节点位置，支持负数（负数从后向前计数）
     * @return 获取的子路径
     * @since 3.1.2
     */
    public static Path getPathEle(Path path, int index) {
        return subPath(path, index, index == -1 ? path.getNameCount() : index + 1);
    }

    /**
     * 判断value是否小于0
     *
     * @param value value
     * @return boolean
     */
    private static boolean valueLessThanZero(int value) {
        return value < 0;
    }

    /**
     * 获取指定位置的子路径部分，支持负数，例如起始为-1表示从后数第一个节点位置
     *
     * @param path      路径
     * @param fromIndex 起始路径节点（包括）
     * @param toIndex   结束路径节点（不包括）
     * @return 获取的子路径
     * @since 3.1.2
     */
    public static Path subPath(Path path, int fromIndex, int toIndex) {
        if (null == path) {
            return null;
        }
        final int len = path.getNameCount();

        if (valueLessThanZero(fromIndex)) {
            fromIndex = len + fromIndex;
            if (valueLessThanZero(fromIndex)) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return null;
        }
        return path.subpath(fromIndex, toIndex);
    }

    // ================================================================================================================
    // 匹配和不匹配
    // ================================================================================================================

    /**
     * 校验两个文件是否匹配
     * 两个文件如果满足以下条件之一就被认为是匹配的:
     * - 这两个路径定位同一个文件，即使两个相等的路径定位文件不存在，或者
     * - 这两个文件大小相同，第一个文件中的每个字节都与第二个文件中的相应字节相同。
     *
     * @param fileA 文件A
     * @param fileB 文件B
     * @return 两个文件是否不匹配(不是同一个文件并且文件内容不同)
     * @throws IOException IOException
     */
    public static boolean match(File fileA, File fileB) throws IOException {
        return Files.mismatch(fileA.toPath(), fileB.toPath()) == -1;
    }

    public static boolean match(Path filePathA, Path filePathB) throws IOException {
        return Files.mismatch(filePathA, filePathB) == -1;
    }

    /**
     * 查找并返回两个文件内容中第一个不匹配字节的位置，如果没有不匹配，则返回-1L。该位置将在0L的范围内，直到较小文件的大小(以字节为单位)。
     * -1表示完全匹配
     * 两个文件如果满足以下条件之一就被认为是匹配的:
     * - 这两个路径定位同一个文件，即使两个相等的路径定位文件不存在，或者
     * - 这两个文件大小相同，第一个文件中的每个字节都与第二个文件中的相应字节相同。
     * 否则两个文件不匹配，该方法返回的值为:
     * - 第一个不匹配字节的位置，或
     * - 当文件大小不同且小文件的每个字节与大文件的对应字节相同时，小文件的大小(以字节为单位)。
     *
     * @param fileA 文件A
     * @param fileB 文件B
     * @return 两个文件是否不匹配(不是同一个文件并且文件内容不同)
     * @throws IOException IOException
     */
    public static boolean mismatch(File fileA, File fileB) throws IOException {
        return Files.mismatch(fileA.toPath(), fileB.toPath()) != -1;
    }

    public static boolean mismatch(Path filePathA, Path filePathB) throws IOException {
        return Files.mismatch(filePathA, filePathB) != -1;
    }

}
/*
org.apache.commons.io.FileUtils
deleteDirectory：删除文件夹
readFileToString：以字符形式读取文件内容
deleteQueitly：删除文件或文件夹且不会抛出异常
copyFile：复制文件
writeStringToFile：把字符写到目标文件，如果文件不存在，则创建
forceMkdir：强制创建文件夹，如果该文件夹父级目录不存在，则创建父级
write：把字符写到指定文件中
listFiles：列举某个目录下的文件(根据过滤器)
copyDirectory：复制文件夹
forceDelete：强制删除文件
 */