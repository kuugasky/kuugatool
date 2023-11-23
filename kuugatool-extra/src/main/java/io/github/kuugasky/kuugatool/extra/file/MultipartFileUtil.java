package io.github.kuugasky.kuugatool.extra.file;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.lang.UUID;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.http.entity.ContentType;
import org.springframework.lang.Nullable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * MockMultipartFileUtil
 *
 * @author kuuga
 */
public class MultipartFileUtil {

    // ===========================================================================================================
    // Multipart
    // ===========================================================================================================

    /**
     * 文件转换为Multipart
     *
     * @param file 文件
     * @return Multipart
     * @throws IOException io异常
     */
    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        if (null == file) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
    }

    /**
     * 文件base64转BufferedImage后，再转换为Multipart
     *
     * @param bufferedImage 图片BufferedImage
     * @return Multipart
     * @throws IOException io异常
     */
    public static MultipartFile fileToMultipartFile(BufferedImage bufferedImage) throws IOException {
        String fileSuffix = ".png";
        String name = UUID.fastUUID() + fileSuffix;
        String originalFilename = UUID.fastUUID() + fileSuffix;
        return fileToMultipartFile(bufferedImage, name, originalFilename, null);
    }

    /**
     * 文件base64转BufferedImage后，再转换为Multipart
     *
     * @param bufferedImage 图片BufferedImage
     * @param name          图片名
     * @return Multipart
     * @throws IOException io异常
     */
    public static MultipartFile fileToMultipartFile(BufferedImage bufferedImage, String name) throws IOException {
        String fileSuffix = ".png";
        return fileToMultipartFile(bufferedImage, name, UUID.fastUUID() + fileSuffix, null);
    }

    /**
     * 文件base64转BufferedImage后，再转换为Multipart
     *
     * @param bufferedImage    文件
     * @param name             图片名
     * @param originalFilename 原始文件名
     * @param contentType      原始文件类型
     * @return Multipart
     * @throws IOException io异常
     */
    public static MultipartFile fileToMultipartFile(BufferedImage bufferedImage, String name, @Nullable String originalFilename, @Nullable String contentType) throws IOException {
        if (null == bufferedImage) {
            return null;
        }
        String nameOfExtension = FilenameUtil.getExtension(name);
        String originalFilenameOfExtension = FilenameUtil.getExtension(originalFilename);
        if (StringUtil.containsEmpty(nameOfExtension, originalFilenameOfExtension)) {
            throw new RuntimeException("文件名需要带上文件格式后缀");
        }
        InputStream inputStream = IoUtil.bufferedImageToInputStream(bufferedImage);
        if (StringUtil.hasText(contentType)) {
            return new MockMultipartFile(name, originalFilename, contentType, inputStream);
        }
        return new MockMultipartFile(name, originalFilename, ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
    }

    /**
     * 网络图片地址转换为Multipart
     *
     * @param fileUrl 网络图片地址
     * @return Multipart
     * @throws IOException io异常
     */
    public static MultipartFile fileUrlToMultipartFile(String fileUrl) throws IOException {
        byte[] bytes = IoUtil.fileUrlToBytes(fileUrl);
        if (null == bytes) {
            return null;
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        if (fileUrl.contains(KuugaConstants.QUESTION_MARK)) {
            String[] split = fileUrl.split("\\?");
            fileUrl = split[0];
        }
        String fileName = FilenameUtil.getName(fileUrl);
        return new MockMultipartFile(fileName, fileName, ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
    }

    /**
     * multipartFile多媒体文件提取输入流转换成file文件
     *
     * @param multipartFile 多媒体文件
     * @param newFile       file
     */
    public static void multipartFileToFile(MultipartFile multipartFile, File newFile) {
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream os = new FileOutputStream(newFile)) {

            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = inputStream.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
