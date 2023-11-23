package io.github.kuugasky.kuugatool.extra.zip;

import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 *
 * @author kuuga
 */
public final class ZipUtil {

    private final static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    private static final String CONTENT_TYPE = "content-type";
    private static final String CONTENT_DISPOSITION = "Content-disposition";
    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    private static final String ATTACHMENT_FILENAME = "attachment;filename=";

    private ZipUtil() {
    }

    /**
     * 文件压缩
     *
     * @param filePath    源文件filePath
     * @param zipFilePath 压缩文件filePath
     * @throws IOException IOException
     */
    public static void zip(String filePath, String zipFilePath) throws IOException {
        zip(FileUtil.file(filePath), FileUtil.file(zipFilePath));
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void zip(File srcFile, File zipFile) throws IOException {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
            zip(srcFile, out);
        }
    }

    /**
     * 文件压缩
     *
     * @param url     待压缩文件url
     * @param outFile 压缩输出文件
     */
    public static void zipUrl(String url, File outFile) throws IOException {
        ZipOutputStream out = IoUtil.toZipOutputStream(outFile);
        zipUrl(url, out);
    }

    /**
     * 文件压缩
     *
     * @param url 待压缩文件url
     * @param out 压缩输出流
     */
    public static void zipUrl(String url, ZipOutputStream out) throws IOException {
        // First step: get data by the uri
        HttpResponse httpResponse = doGetHttpResponse(url);

        InputStream stream = httpResponse.getEntity().getContent();

        String fileName = FilenameUtil.getName(url, "\\?");

        zip(stream, out, fileName);
    }

    /**
     * 单个url文件压缩
     *
     * @param url          待压缩文件url
     * @param saveFileName 保存文件名，注意带上格式后缀
     * @param out          压缩输出流
     */
    public static void zipUrl(String url, String saveFileName, ZipOutputStream out) throws IOException {
        // First step: get data by the uri
        HttpResponse httpResponse = doGetHttpResponse(url);

        InputStream stream = httpResponse.getEntity().getContent();

        zip(stream, out, saveFileName);
    }

    /**
     * 文件压缩
     *
     * @param filePath 源文件filePath
     * @param out      压缩输出流
     * @throws IOException IOException
     */
    public static void zip(String filePath, ZipOutputStream out) throws IOException {
        zip(FileUtil.file(filePath), out);
    }

    /**
     * 文件压缩
     *
     * @param file 目录或者单个文件
     * @param out  压缩输出流
     * @throws IOException IOException
     */
    public static void zip(File file, ZipOutputStream out) throws IOException {
        zip(file, out, StringUtil.EMPTY);
    }

    /**
     * 文件压缩
     *
     * @param inFile        目录或者单个文件
     * @param out           压缩输出流
     * @param directoryPath 目录路径
     * @throws IOException IOException
     */
    public static void zip(File inFile, ZipOutputStream out, String directoryPath) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!StringUtil.EMPTY.equals(directoryPath)) {
                        name = directoryPath + "/" + name;
                    }
                    zip(file, out, name);
                }
            }
        } else {
            doZip(inFile, out, directoryPath);
        }
    }

    /**
     * 文件压缩
     *
     * @param stream   源文件输入流
     * @param out      压缩输出流
     * @param fileName 文件名
     * @throws IOException IOException
     */
    public static void zip(InputStream stream, ZipOutputStream out, String fileName) throws IOException {
        doZip(stream, out, fileName);
    }

    /**
     * 通过uri获取文件响应内容
     *
     * @param uri 源文件输入流
     * @throws IOException IOException
     */
    private static HttpResponse doGetHttpResponse(String uri) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(uri);
        return client.execute(get);
    }

    /**
     * 压缩
     *
     * @param sourceFile    源文件
     * @param out           压缩输出流
     * @param directoryPath 目录地址
     * @throws IOException IOException
     */
    public static void doZip(File sourceFile, ZipOutputStream out, String directoryPath) throws IOException {
        String entryName;
        if (!StringUtil.EMPTY.equals(directoryPath)) {
            entryName = directoryPath + "/" + sourceFile.getName();
        } else {
            entryName = sourceFile.getName();
        }

        FileInputStream fis = new FileInputStream(sourceFile);

        doZip(fis, out, entryName);
    }

    /**
     * 网络文件压缩
     *
     * @param zipName  压缩文件名称，不带压缩文件格式后缀
     * @param fileUrls 文件网络urls
     * @param response response
     */
    public static void doZip(String zipName, List<String> fileUrls, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, APPLICATION_OCTET_STREAM);
        response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + zipName);
        response.setCharacterEncoding(KuugaCharsets.UTF_8);


        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            fileUrls.forEach(fileUrl -> {
                try {
                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                    fileName = fileName.substring(0, fileName.lastIndexOf("-"));
                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    InputStream inputStream = IoUtil.toInputStream(fileUrl);

                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buff)) != -1) {
                        zipOutputStream.write(buff, 0, len);
                    }
                    zipOutputStream.closeEntry();
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩
     *
     * @param stream   源文件输入流
     * @param out      压缩输出流
     * @param fileName 文件名
     * @throws IOException IOException
     */
    public static void doZip(InputStream stream, ZipOutputStream out, String fileName) throws IOException {
        try (stream) {
            ZipEntry entry = new ZipEntry(fileName);
            out.putNextEntry(entry);

            int len;
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("ZipUtil工具类压缩异常：{}", e.getMessage(), e);
        } finally {
            out.closeEntry();
        }
    }

}
