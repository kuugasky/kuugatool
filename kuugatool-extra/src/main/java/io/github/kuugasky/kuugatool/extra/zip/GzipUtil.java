package io.github.kuugasky.kuugatool.extra.zip;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip压缩工具类
 *
 * @author kuuga
 * @since 2021-04-24
 */
public class GzipUtil {

    private static final String TAR = "tar";

    private final String zipFileName;

    public GzipUtil(String fileName) {
        this.zipFileName = fileName;
    }

    // 字符串压缩解压 ================================================================================================

    /**
     * 使用gzip压缩字符串
     *
     * @param str 要压缩的字符串
     * @return 压缩后的字符串
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     *
     * @param compressedStr 压缩字符串
     * @return 解压后的字符串
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        String decompressed = null;
        byte[] compressed = Base64.getDecoder().decode(compressedStr);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(compressed);
             GZIPInputStream ginZipInputStream = new GZIPInputStream(in)) {

            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = ginZipInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decompressed;
    }

    // 压缩包解压 ============================================================================================================

    /**
     * gzip解压压缩包
     *
     * @param rarFileName 压缩包文件
     * @param destDir     解压目录
     */
    public static void unGzipFile(String rarFileName, String destDir) {
        GzipUtil gzip = new GzipUtil(rarFileName);
        File file = new File(destDir);
        if (!file.exists()) {
            System.out.println(file.mkdir());
        }
        gzip.unzipOarFile(destDir);
    }

    private void unzipOarFile(String outputDirectory) {
        FileInputStream fis;
        ArchiveInputStream in;
        BufferedInputStream bufferedInputStream = null;
        try {
            fis = new FileInputStream(zipFileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream(TAR, is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName();
                int lastIndex = entryName.lastIndexOf("/");
                String name = entryName;
                if (lastIndex > 0) {
                    name = entryName.substring(entryName.lastIndexOf("/"));
                }
                String[] names = name.split("/");
                StringBuilder fileName = new StringBuilder(outputDirectory);
                for (String str : names) {
                    fileName.append(File.separator).append(str);
                }
                if (name.endsWith("/")) {
                    mkFolder(fileName.toString());
                } else {
                    File file = mkFile(fileName.toString());
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                            new FileOutputStream(file));
                    int b;
                    while ((b = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(b);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = (TarArchiveEntry) in.getNextEntry();
            }

        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void mkFolder(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println(f.mkdir());
        }
    }

    private File mkFile(String fileName) {
        File f = new File(fileName);
        try {
            System.out.println(f.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

}
