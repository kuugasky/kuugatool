package io.github.kuugasky.kuugatool.extra.zip;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * {@link ZipUtil}对应的解压缩工具类
 *
 * @author kuuga
 * @since 2021-04-24
 */
public class UnZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(UnZipUtil.class);

    /**
     * 解压缩zip包
     *
     * @param zipFile       zip文件
     * @param unzipFilePath 解压后的文件保存的路径
     */
    public static void unzip(File zipFile, String unzipFilePath) throws Exception {
        unzip(zipFile, unzipFilePath, false);
    }

    /**
     * 解压缩zip包
     *
     * @param zipFilePath   zip文件的全路径
     * @param unzipFilePath 解压后的文件保存的路径
     */
    public static void unzip(String zipFilePath, String unzipFilePath) throws Exception {
        unzip(FileUtil.file(zipFilePath), unzipFilePath, false);
    }

    /**
     * 解压缩zip包
     *
     * @param zipFilePath        zip文件路径
     * @param unzipFilePath      解压后的文件保存的路径
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
     */
    public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception {
        unzip(FileUtil.file(zipFilePath), unzipFilePath, includeZipFileName);
    }

    /**
     * 解压缩zip包
     *
     * @param zipFile            zip文件
     * @param unzipFilePath      解压后的文件保存的路径
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
     */
    public static void unzip(File zipFile, String unzipFilePath, boolean includeZipFileName) throws Exception {
        if (ObjectUtil.isNull(zipFile) || !zipFile.exists()) {
            throw new RuntimeException("zip file is no exists!");
        }
        if (StringUtil.isEmpty(unzipFilePath)) {
            throw new RuntimeException("unzip file path is null!");
        }
        // 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName) {
            String fileName = zipFile.getName();
            if (StringUtil.hasText(fileName)) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        // 创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
            boolean mkdirs = unzipFileDir.mkdirs();
            logger.info("mkdir file {}.[{}]", mkdirs, unzipFileDir);
        }

        // 开始解压
        ZipEntry entry;
        String entryFilePath;
        String entryDirPath;
        File entryFile;
        File entryDir;
        int index, count, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis;
        BufferedOutputStream bos;
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        // 循环对压缩包里的每一个文件进行解压
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            // 构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            // 构建解压后保存的文件夹路径
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1) {
                entryDirPath = entryFilePath.substring(0, index);
            } else {
                entryDirPath = StringUtil.EMPTY;
            }
            entryDir = new File(entryDirPath);
            // 如果文件夹路径不存在，则创建文件夹
            if (!entryDir.exists() || !entryDir.isDirectory()) {
                boolean mkdirs = entryDir.mkdirs();
                logger.info("mkdir file {}.[{}]", mkdirs, entryDir);
            }

            // 创建解压文件
            entryFile = new File(entryFilePath);
            if (entryFile.exists()) {
                // 删除已存在的目标文件
                boolean delete = entryFile.delete();
                logger.info("delete file {}.[{}]", delete, entryDir);
            }

            // 写入文件
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        }

    }

}
