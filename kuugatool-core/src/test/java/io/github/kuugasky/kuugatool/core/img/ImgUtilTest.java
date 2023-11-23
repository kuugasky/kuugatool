package io.github.kuugasky.kuugatool.core.img;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class ImgUtilTest {

    @Test
    void scale() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/1-1.jpg");
        // 缩放图像（按比例缩放）
        ImgUtil.scale(source, target, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/1-2.jpg");

        // 缩放图像（按比例缩放）
        ImgUtil.scale(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/1-3.jpg");

        // 缩放图像（按比例缩放）
        ImgUtil.scale(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/1-4.jpg");

        // 缩放图像（按比例缩放）
        ImgUtil.scale(ImgUtil.toImage(source), target, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/1-5.jpg");

        // 缩放图像（按比例缩放）
        ImgUtil.scale(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 缩放图像（按比例缩放）
        Image scale = ImgUtil.scale(ImgUtil.toImage(source), 0.5f);
        ImgUtil.write(scale, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale5() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 缩放图像（按比例缩放）
        Image scale = ImgUtil.scale(ImgUtil.toImage(source), 100, 100);
        ImgUtil.write(scale, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale6() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 缩放图像（按比例缩放）
        ImgUtil.scale(source, target, 100, 100, Color.red);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale7() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 缩放图像（按比例缩放）
        ImgUtil.scale(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), 100, 100, null);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale8() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 缩放图像（按比例缩放）
        ImgUtil.scale(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), 100, 100, null);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale9() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-6.jpg");

        System.out.println(FileUtil.getFileSize(source));
        // 缩放图像（按比例缩放）
        ImgUtil.scale(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), 100, 100, null);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testScale10() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/2.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/2-7.jpg");

        System.out.println(FileUtil.getFileSize(source));
        // 缩放图像（按比例缩放）
        Image scale = ImgUtil.scale(ImgUtil.toImage(source), 100, 100, null);
        ImgUtil.write(scale, target);
        System.out.println(FileUtil.getFileSize(target));
    }


    @Test
    void cut() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(20, 20);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(source, target, rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(ImgUtil.toImage(source), target, rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(ImgUtil.toImage(source), IoUtil.toOutputStream(target), rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-6.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        ImgUtil.cut(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), rectangle);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut5() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-7.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        Image cut = ImgUtil.cut(ImgUtil.toImage(source), rectangle);
        ImgUtil.write(cut, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut6() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-8.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        Image cut = ImgUtil.cut(ImgUtil.toImage(source), 300, 500);
        ImgUtil.write(cut, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testCut7() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/3-9.jpg");

        System.out.println(FileUtil.getFileSize(source));

        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(200, 200);

        // 图像切割(按指定起点坐标和宽高切割)
        Image cut = ImgUtil.cut(ImgUtil.toImage(source), 300, 500, 200);
        ImgUtil.write(cut, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void slice() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/slice1/4-1.jpg");

        FileUtil.createDir("/Users/kuuga/Downloads/img/slice1");

        System.out.println(FileUtil.getFileSize(source));

        // 图像切片（指定切片的宽度和高度）
        ImgUtil.slice(source, target, 100, 100);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testSlice() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/slice2/4-2.jpg");

        FileUtil.createDir("/Users/kuuga/Downloads/img/slice2");

        System.out.println(FileUtil.getFileSize(source));

        // 图像切片（指定切片的宽度和高度）
        ImgUtil.slice(ImgUtil.toImage(source), target, 100, 100);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void sliceByRowsAndCols() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/slice3/4-3.jpg");

        FileUtil.createDir("/Users/kuuga/Downloads/img/slice3");

        System.out.println(FileUtil.getFileSize(source));

        // 图像切割（指定切片的行数和列数）
        ImgUtil.sliceByRowsAndCols(source, target, 1, 2);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testSliceByRowsAndCols() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/slice4/4-4.jpg");

        FileUtil.createDir("/Users/kuuga/Downloads/img/slice4");

        System.out.println(FileUtil.getFileSize(source));

        // 图像切割（指定切片的行数和列数）
        ImgUtil.sliceByRowsAndCols(ImgUtil.toImage(source), target, 1, 2);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void convert() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 图像类型转换
        ImgUtil.convert(source, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testConvert() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/5-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 图像类型转换
        ImgUtil.convert(IoUtil.toInputStream(source), "jpg", IoUtil.toOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testConvert1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/5-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 图像类型转换
        ImgUtil.convert(ImgUtil.toImage(source), "jpg", ImgUtil.getImageOutputStream(target), true);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void gray() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        ImgUtil.gray(source, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testGray() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        ImgUtil.gray(IoUtil.toInputStream(source), IoUtil.toOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testGray1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        ImgUtil.gray(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testGray2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        ImgUtil.gray(ImgUtil.toImage(source), IoUtil.toOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testGray3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        ImgUtil.gray(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testGray4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/6-5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白
        Image gray = ImgUtil.gray(ImgUtil.toImage(source));
        ImgUtil.write(gray, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void binary() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(source, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), "jpg");
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), "jpg");
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(ImgUtil.toImage(source), target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(ImgUtil.toImage(source), IoUtil.toOutputStream(target), "jpg");
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        ImgUtil.binary(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), "jpg");
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testBinary5() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/7-6.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 彩色转为黑白二值化图片，根据目标文件扩展名确定转换后的格式
        Image binary = ImgUtil.binary(ImgUtil.toImage(source));
        ImgUtil.write(binary, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void pressText() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        ImgUtil.pressText(source, target, "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressText() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8-1.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        ImgUtil.pressText(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressText1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8-2.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        ImgUtil.pressText(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressText2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8-3.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        ImgUtil.pressText(ImgUtil.toImage(source), IoUtil.toOutputStream(target), "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressText3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8-4.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        ImgUtil.pressText(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressText4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/8-5.jpg");
        // File pressText = FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        Image Kuuga = ImgUtil.pressText(ImgUtil.toImage(source), "kuuga", Color.red, sansSerif, 0, 0, 0.5f);
        ImgUtil.write(Kuuga, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void pressImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(source, target, image, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-1.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(IoUtil.toInputStream(source), IoUtil.toOutputStream(target), image, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-2.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(ImgUtil.getImageInputStream(source), ImgUtil.getImageOutputStream(target), image, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-3.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target), image, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-4.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Image image1 = ImgUtil.pressImage(ImgUtil.toImage(source), image, 0, 0, 0.5f);
        ImgUtil.write(image1, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage4() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-5.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(ImgUtil.toImage(source), ImgUtil.toImage(target), 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage5() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-6.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        Rectangle rectangle = new Rectangle(10, 10);
        Image image = ImgUtil.pressImage(ImgUtil.toImage(source), ImgUtil.toImage(target), rectangle, 0.5f);
        ImgUtil.write(image, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testPressImage6() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/9-7.jpg");
        Image image = ImgUtil.toImage(FileUtil.file("/Users/kuuga/Downloads/img/2-3.jpg"));

        System.out.println(FileUtil.getFileSize(source));

        // 给图片添加文字水印
        ImgUtil.pressImage(ImgUtil.toImage(source), IoUtil.toOutputStream(target), image, 0, 0, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void rotate() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/10.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 旋转图片为指定角度
        ImgUtil.rotate(source, 90, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testRotate() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/10-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 旋转图片为指定角度
        ImgUtil.rotate(ImgUtil.toImage(source), 90, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testRotate1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/10-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 旋转图片为指定角度
        ImgUtil.rotate(ImgUtil.toImage(source), 90, IoUtil.toOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testRotate2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/10-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 旋转图片为指定角度
        ImgUtil.rotate(ImgUtil.toImage(source), 90, ImgUtil.getImageOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testRotate3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/10-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 旋转图片为指定角度
        Image rotate = ImgUtil.rotate(ImgUtil.toImage(source), 90);
        ImgUtil.write(rotate, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void flip() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/11.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 水平翻转图像
        ImgUtil.flip(source, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testFlip() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/11-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 水平翻转图像
        ImgUtil.flip(ImgUtil.toImage(source), target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testFlip1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/11-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 水平翻转图像
        ImgUtil.flip(ImgUtil.toImage(source), IoUtil.toOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testFlip2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/11-3.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 水平翻转图像
        ImgUtil.flip(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target));
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testFlip3() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/11-4.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 水平翻转图像
        Image flip = ImgUtil.flip(ImgUtil.toImage(source));
        ImgUtil.write(flip, target);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void compress() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/12.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 压缩图像，输出图像只支持jpg文件
        ImgUtil.compress(source, target, 0.5f);
        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void toRenderedImage() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/13.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 首先尝试强转，否则新建一个{@link BufferedImage}后重新绘制，使用 {@link BufferedImage#TYPE_INT_RGB} 模式。
        RenderedImage renderedImage = ImgUtil.toRenderedImage(ImgUtil.toImage(source));

        ImageIO.write(renderedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 1 + ".jpg"));

        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void toBufferedImage() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/14.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 首先尝试强转，否则新建一个{@link BufferedImage}后重新绘制，使用 {@link BufferedImage#TYPE_INT_RGB} 模式。
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(ImgUtil.toImage(source));

        ImageIO.write(bufferedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 2 + ".jpg"));

        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testToBufferedImage() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/14-1.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 首先尝试强转，否则新建一个{@link BufferedImage}后重新绘制，使用 {@link BufferedImage#TYPE_INT_RGB} 模式。
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(ImgUtil.toImage(source), "jpg");

        ImageIO.write(bufferedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 3 + ".jpg"));

        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void testToBufferedImage1() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/14-2.jpg");

        System.out.println(FileUtil.getFileSize(source));

        // 首先尝试强转，否则新建一个{@link BufferedImage}后重新绘制，使用 {@link BufferedImage#TYPE_INT_RGB} 模式。
        BufferedImage bufferedImage = ImgUtil.toBufferedImage(ImgUtil.toImage(source), 1);

        ImageIO.write(bufferedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 4 + ".jpg"));

        System.out.println(FileUtil.getFileSize(target));
    }

    @Test
    void copyImage() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/15.jpg");
        BufferedImage bufferedImage = ImgUtil.copyImage(ImgUtil.toImage(source), 1);
        ImageIO.write(bufferedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 5 + ".jpg"));
    }

    @Test
    void testCopyImage() throws IOException {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/16.jpg");
        BufferedImage bufferedImage = ImgUtil.copyImage(ImgUtil.toImage(source), 1, Color.BLUE);
        ImageIO.write(bufferedImage, ImgUtil.IMAGE_TYPE_JPEG, new File(target, "_r" + 1 + "_c" + 6 + ".jpg"));
    }

    @Test
    void toImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        BufferedImage bufferedImage = ImgUtil.toImage(source);
        System.out.println(StringUtil.formatString(bufferedImage));
    }

    @Test
    void testToImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        BufferedImage bufferedImage = ImgUtil.toImage(ImgUtil.toBase64(ImgUtil.toImage(source), "png"));
        System.out.println(StringUtil.formatString(bufferedImage));
    }

    @Test
    void toStream() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        ByteArrayInputStream jpg = ImgUtil.toStream(ImgUtil.toImage(source), "jpg");
        System.out.println(StringUtil.formatString(jpg));
    }

    @Test
    void toBase64() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        String jpg = ImgUtil.toBase64(ImgUtil.toImage(source), "jpg");
        System.out.println(jpg);
    }

    @Test
    void toBytes() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        byte[] jpgs = ImgUtil.toBytes(ImgUtil.toImage(source), "jpg");
        System.out.println(Arrays.toString(jpgs));
    }

    @Test
    void createImage() {
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        File target = FileUtil.file("/Users/kuuga/Downloads/img/17.jpg");
        ImgUtil.createImage("kuuga", sansSerif, Color.BLUE, Color.RED, ImgUtil.getImageOutputStream(target));
    }

    @Test
    void testCreateImage() {
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        File target = FileUtil.file("/Users/kuuga/Downloads/img/17-1.jpg");
        BufferedImage Kuuga = ImgUtil.createImage("kuuga", sansSerif, Color.BLUE, Color.RED, 1);
        ImgUtil.write(Kuuga, target);
    }

    @Test
    void getRectangle() {
        Font sansSerif = FontUtil.createFont("SansSerif", 50);
        Rectangle2D Kuuga = ImgUtil.getRectangle("kuuga", sansSerif);
        System.out.println(StringUtil.formatString(Kuuga));
    }

    @Test
    void createFont() {
        System.out.println(ImgUtil.createFont(FileUtil.file(null)));
    }

    @Test
    void testCreateFont() {
        System.out.println(ImgUtil.createFont(IoUtil.toInputStream(FileUtil.file(null))));
    }

    @Test
    void createGraphics() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        ImgUtil.createGraphics(ImgUtil.toImage(source), Color.BLACK);
    }

    @Test
    void writeJpg() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/20.png");
        ImgUtil.writeJpg(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target));
    }

    @Test
    void writePng() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/21.png");
        ImgUtil.writePng(ImgUtil.toImage(source), ImgUtil.getImageOutputStream(target));
    }

    @Test
    void testWriteJpg() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/20-1.png");
        ImgUtil.writeJpg(ImgUtil.toImage(source), IoUtil.toOutputStream(target));
    }

    @Test
    void testWritePng() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/21-1.png");
        ImgUtil.writePng(ImgUtil.toImage(source), IoUtil.toOutputStream(target));
    }

    @Test
    void write() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/22.png");
        ImgUtil.write(ImgUtil.toImage(source), target);
    }

    @Test
    void getReader() {
        ImageReader jpg = ImgUtil.getReader("jpg");
        System.out.println(StringUtil.formatString(jpg));
    }

    @Test
    void read() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        BufferedImage read = ImgUtil.read(source);
        System.out.println(StringUtil.formatString(read));
    }

    @Test
    void testRead() {
    }

    @Test
    void testRead1() {
    }

    @Test
    void testRead2() {
    }

    @Test
    void testRead3() {
    }

    @Test
    void testRead4() {
    }

    @Test
    void getImageOutputStream() {
    }

    @Test
    void testGetImageOutputStream() {
    }

    @Test
    void getImageInputStream() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        ImgUtil.getImageInputStream(source);
        ImageInputStream imageInputStream = ImgUtil.getImageInputStream(IoUtil.toInputStream(source));
        System.out.println(imageInputStream);
    }

    @Test
    void getWriter() {
        System.out.println(ImgUtil.getWriter("jpg"));
    }

    @Test
    void testGetWriter() {
    }

    @Test
    void toHex() {
        System.out.println(ImgUtil.toHex(10, 10, 10));
        System.out.println(ImgUtil.toHex(Color.BLACK));
    }

    @Test
    void testToHex() {
    }

    @Test
    void hexToColor() {
    }

    @Test
    void getColor() {
    }

    @Test
    void testGetColor() {
        System.out.println(ImgUtil.getColor(256));
        System.out.println(ImgUtil.getColor("red"));
    }

    @Test
    void randomColor() {
        for (int i = 0; i < 3; i++) {
            Color color = ImgUtil.randomColor();
            System.out.println(StringUtil.formatString(color));
            System.out.println(color.getRGB());
        }
    }

    @Test
    void testRandomColor() {
        Random random = new Random();
        random.setSeed(1);
        for (int i = 0; i < 3; i++) {
            Color color = ImgUtil.randomColor(random);
            System.out.println(StringUtil.formatString(color));
            System.out.println(color.getRGB());
        }
    }

    @Test
    void getPointBaseCentre() {
    }

    @Test
    void backgroundRemoval() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/22.png");
        System.out.println(ImgUtil.backgroundRemoval(source.getName(), target.getName(), 100));
    }

    @Test
    void testBackgroundRemoval() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/22-1.png");
        System.out.println(ImgUtil.backgroundRemoval(source, target, 100));
    }

    @Test
    void testBackgroundRemoval1() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/22-2.png");
        System.out.println(ImgUtil.backgroundRemoval(source, target, null, 100));
    }

    @Test
    void testBackgroundRemoval2() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        File target = FileUtil.file("/Users/kuuga/Downloads/img/22-3.png");
        BufferedImage x = ImgUtil.backgroundRemoval(ImgUtil.toBufferedImage(ImgUtil.toImage(source)), null, 100);
        ImgUtil.write(x, target);
        System.out.println(x);
    }

    @Test
    void testBackgroundRemoval3() {
    }

    @Test
    void toBase64DataUri() {
    }
}