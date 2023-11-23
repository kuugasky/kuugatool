package io.github.kuugasky.kuugatool.qrcode;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import io.github.kuugasky.kuugatool.qrcode.zxing.QrCodeBuilder;
import io.github.kuugasky.kuugatool.qrcode.zxing.QrImage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class QrCodeUtilTest {

    /**
     * 生成二维码
     */
    @Test
    public void test1() throws IOException, WriterException {
        String result = QrCodeBuilder.encodeQrCode("java开发", 500, 500, "src/main/resources/static/img/二维码(通过方式一生成).jpg");
        System.out.println(result);
    }

    /**
     * 生成二维码
     */
    @Test
    public void test2() throws IOException, WriterException {
        String result = QrCodeBuilder.encodeQrCodeAnotherWay("java开发", 500, 500, "src/main/resources/static/img/二维码(通过方式二生成).jpg");
        System.out.println(result);
    }

    /**
     * 生成带图片的二维码
     */
    @Test
    public void test3() throws IOException, WriterException {
        String result = QrCodeBuilder.encodeQrCodeWithEmbeddedImg("java开发", 500, 500, new File("src/main/resources/static/img/1.jpg"),
                "src/main/resources/static/img/带图片的二维码.jpg");
        System.out.println(result);
    }

    /**
     * 生成带文字、带图片的二维码
     */
    @Test
    public void test4() throws IOException, WriterException {
        QrImage para = QrImage.builder().qrCodeFileOutputPath("src/main/resources/static/img/带文字带图片的二维码.jpg")
                .qrCodeContent("https://blog.csdn.net/weixin_45730091").qrCodeWidth(500).qrCodeHeight(500)
                .embeddedImgFile(new File("src/main/resources/static/img/1.jpg")).wordContent("我的博客").wordSize(20).build();
        String s = QrCodeBuilder.encodeQrCodeWithEmbeddedImgAndFonts(para);
        System.out.println(s);
    }

    /**
     * 生成带文字、不带图片的二维码
     */
    @Test
    public void test5() throws IOException, WriterException {
        QrImage para = QrImage.builder().qrCodeFileOutputPath("src/main/resources/static/img/带文字不带图片的二维码.jpg")
                .qrCodeContent("二维码").qrCodeWidth(500).qrCodeHeight(500).wordContent("java开发").wordSize(20).build();
        String result = QrCodeBuilder.encodeQrCodeWithEmbeddedImgAndFonts(para);
        System.out.println(result);
    }

    /**
     * 识别二维码
     */
    @Test
    public void test6() throws IOException, NotFoundException {
        String s = QrCodeBuilder.decodeQrCode(new File("src/main/resources/static/img/二维码(通过方式一生成).jpg"));
        System.out.println(s);
    }

    /**
     * 识别二维码
     */
    @Test
    public void test7() throws IOException, NotFoundException {
        String s = QrCodeBuilder.decodeQrCode(new FileInputStream("src/main/resources/static/img/带文字带图片的二维码.jpg"));
        System.out.println(s);
    }


}
