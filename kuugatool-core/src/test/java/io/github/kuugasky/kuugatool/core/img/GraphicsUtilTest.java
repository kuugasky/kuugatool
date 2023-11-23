package io.github.kuugasky.kuugatool.core.img;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class GraphicsUtilTest {

    @Test
    void createGraphics() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics = GraphicsUtil.createGraphics(read, color);
        System.out.println(StringUtil.formatString(graphics));
    }

    @Test
    void getCenterY() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics = GraphicsUtil.createGraphics(read, color);
        int centerY = GraphicsUtil.getCenterY(graphics, 100);
        System.out.println(centerY);
    }

    @Test
    void drawStringColourful() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);

        File file = FileUtil.file("/System/Library/Fonts/Thonburi.ttc");
        Font font = FontUtil.createFont(file);

        Graphics graphics = GraphicsUtil.drawStringColourful(graphics2D, "kuuga", font, 100, 100);
        System.out.println(graphics);
    }

    @Test
    void drawString() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);

        File file = FileUtil.file("/System/Library/Fonts/Thonburi.ttc");
        Font font = FontUtil.createFont(file);

        Point point = new Point();
        point.setLocation(100, 100);
        Graphics graphics = GraphicsUtil.drawString(graphics2D, "kuuga", font, color, point);
        System.out.println(StringUtil.formatString(graphics));
    }

    @Test
    void testDrawString() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);

        File file = FileUtil.file("/System/Library/Fonts/Thonburi.ttc");
        Font font = FontUtil.createFont(file);

        Point point = new Point();
        point.setLocation(100, 100);
        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(20, 20);
        Graphics graphics = GraphicsUtil.drawString(graphics2D, "kuuga", font, color, rectangle);
        System.out.println(StringUtil.formatString(graphics));
    }

    @Test
    void testDrawString1() {
    }

    @Test
    void drawImg() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);

        Point point = new Point();
        point.setLocation(100, 100);
        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(20, 20);
        Graphics graphics = GraphicsUtil.drawImg(graphics2D, read, point);
        System.out.println(StringUtil.formatString(graphics));
    }

    @Test
    void testDrawImg() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);

        Point point = new Point();
        point.setLocation(100, 100);
        Rectangle rectangle = new Rectangle();
        rectangle.setLocation(100, 100);
        rectangle.setSize(20, 20);
        Graphics graphics = GraphicsUtil.drawImg(graphics2D, read, rectangle);
        System.out.println(StringUtil.formatString(graphics));
    }

    @Test
    void setAlpha() {
        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage read = ImgUtil.read(imageFile);
        Color color = new Color(255, 255, 255);
        Graphics2D graphics2D = GraphicsUtil.createGraphics(read, color);
        Graphics graphics = GraphicsUtil.setAlpha(graphics2D, 1.0f);
        System.out.println(StringUtil.formatString(graphics));
    }
}