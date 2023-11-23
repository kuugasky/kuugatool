package io.github.kuugasky.kuugatool.core.img;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

class FontUtilTest {

    @Test
    void createFont() {
        Font font = FontUtil.createFont();
        System.out.println(StringUtil.formatString(font));
    }

    @Test
    void createSansSerifFont() {
        Font font = FontUtil.createSansSerifFont(14);
        System.out.println(StringUtil.formatString(font));
    }

    @Test
    void testCreateFont() {
        Font font = FontUtil.createFont("Default", 16);
        System.out.println(StringUtil.formatString(font));
        Font fontSansSerif = FontUtil.createFont("SansSerif", 16);
        System.out.println(StringUtil.formatString(fontSansSerif));
    }

    @Test
    void testCreateFont1() {
        Font font = FontUtil.createFont(FileUtil.file("/System/Library/Fonts/Thonburi.ttc"));
        System.out.println(StringUtil.formatString(font));
    }

    @Test
    void testCreateFont2() {
        File file = FileUtil.file("/System/Library/Fonts/Thonburi.ttc");
        FileInputStream fileInputStream = IoUtil.toInputStream(file);
        Font font = FontUtil.createFont(fileInputStream);
        System.out.println(StringUtil.formatString(font));
    }

    @Test
    void getDimension() {
        File file = FileUtil.file("/System/Library/Fonts/Thonburi.ttc");
        Font font = FontUtil.createFont(file);

        File imageFile = FileUtil.file("/Users/kuuga/Downloads/java.png");
        BufferedImage image = ImgUtil.read(imageFile);
        final Graphics2D g = image.createGraphics();

        Dimension dimension = FontUtil.getDimension(g.getFontMetrics(font), "kuuga");
        System.out.println(StringUtil.formatString(dimension));
    }
}