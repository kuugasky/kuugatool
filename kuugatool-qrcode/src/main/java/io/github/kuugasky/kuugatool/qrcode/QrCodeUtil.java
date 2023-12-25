package io.github.kuugasky.kuugatool.qrcode;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import io.github.kuugasky.kuugatool.qrcode.zxing.QrCodeBuilder;
import io.github.kuugasky.kuugatool.qrcode.zxing.QrImage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 二维码 工具类
 * <p>
 * 说明: 二维码是一种编码形式， 将内容encode，得到二维码;， 将内容decode,得到数据内容。
 * @author kuuga
 */
@Slf4j
@SuppressWarnings("unused")
public class QrCodeUtil {

    /**
     * 二维码颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 二维码颜色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成二维码
     */
    public static void generateQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws IOException, WriterException {
        BitMatrix bitMatrix = QrCodeBuilder.encodeQrCode(content, qrCodeSize, qrCodeSize);
        BufferedImage image = new BufferedImage(qrCodeSize, qrCodeSize, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < qrCodeSize; i++) {
            for (int j = 0; j < qrCodeSize; j++) {
                image.setRGB(i, j, bitMatrix.get(i, j) ? BLACK : WHITE);
            }
        }
        ImageIO.write(image, imageFormat, outputStream);
    }

    /**
     * 生成二维码
     */
    public static void generateQrCodeRich(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws IOException, WriterException {
        BufferedImage bufferedImage = QrCodeBuilder.genBufferedImage(content, qrCodeSize, qrCodeSize);
        ImageIO.write(bufferedImage, imageFormat, outputStream);
    }

    /**
     * 生成带图片的二维码
     */
    public static void generateQrCodeWithPicture(OutputStream outputStream, String content, int qrCodeSize, File embeddedImageFile, String imageFormat) throws IOException, WriterException {
        BufferedImage bufferedImage = QrCodeBuilder.genBufferedImageWithEmbeddedImg(content, qrCodeSize, qrCodeSize, embeddedImageFile);
        ImageIO.write(bufferedImage, imageFormat, outputStream);
    }

    /**
     * 生成带文字、带图片的二维码
     */
    public static void generateQrCodeWithTextAndPicture(OutputStream outputStream, String content, String wordContent, int qrCodeSize, File embeddedImageFile, String imageFormat) throws IOException, WriterException {
        QrImage para = QrImage.builder()
                .qrCodeContent(content).qrCodeWidth(qrCodeSize).qrCodeHeight(qrCodeSize)
                .embeddedImgFile(embeddedImageFile).wordContent(wordContent).wordSize(20).build();
        BufferedImage bufferedImage = QrCodeBuilder.encodeQrCodeWithEmbeddedImgAndFontsForBufferedImage(para);
        ImageIO.write(bufferedImage, imageFormat, outputStream);
    }

    /**
     * 生成带文字、不带图片的二维码
     */
    public static void generateQrCodeWithTextAndWithoutPicture(OutputStream outputStream, String content, String wordContent, int qrCodeSize, String imageFormat) throws IOException, WriterException {
        QrImage para = QrImage.builder()
                .qrCodeContent(content).qrCodeWidth(qrCodeSize).qrCodeHeight(qrCodeSize).wordContent(wordContent).wordSize(20).build();
        BufferedImage bufferedImage = QrCodeBuilder.encodeQrCodeWithEmbeddedImgAndFontsForBufferedImage(para);
        ImageIO.write(bufferedImage, imageFormat, outputStream);
    }

    /**
     * 识别二维码
     */
    public static String identifyTheQrCode(InputStream inputStream) throws IOException, NotFoundException {
        return QrCodeBuilder.decodeQrCode(inputStream);
    }

}
