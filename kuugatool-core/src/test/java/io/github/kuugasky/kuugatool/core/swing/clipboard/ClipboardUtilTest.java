package io.github.kuugasky.kuugatool.core.swing.clipboard;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.img.ImgUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;

public class ClipboardUtilTest {

    /**
     * 设置并获取剪切板的文本
     */
    @Test
    public void setAndGetStrTest() {
        try {
            ClipboardUtil.setStr("test");

            String test = ClipboardUtil.getStr();
            Assertions.assertEquals("test", test);
        } catch (java.awt.HeadlessException e) {
            // 忽略 No X11 DISPLAY variable was set, but this program performed an operation which requires it.
            // ignore
        }
    }

    /**
     * 获取剪切板的文本
     */
    @Test
    public void getStrTest() {
        try {
            String test = ClipboardUtil.getStr();
            System.out.println(test);
        } catch (java.awt.HeadlessException e) {
            // 忽略 No X11 DISPLAY variable was set, but this program performed an operation which requires it.
            // ignore
        }
    }

    @Test
    void getClipboard() {
        Clipboard clipboard = ClipboardUtil.getClipboard();
        System.out.println(StringUtil.formatString(clipboard));
    }

    @Test
    void setStr() {
        ClipboardUtil.setStr("kuugasky");
    }

    @Test
    void set() {
        ClipboardUtil.set(new StringSelection("kuuga-1"));
    }

    @Test
    void getStr() {
        System.out.println(ClipboardUtil.getStr());
    }

    @Test
    void testGetStr() {
        StringSelection stringSelection = new StringSelection("xxx");
        System.out.println(ClipboardUtil.getStr(stringSelection));
    }

    @Test
    void get() {
        System.out.println(ClipboardUtil.get(DataFlavor.stringFlavor));
    }

    @Test
    void testGet() {
        StringSelection stringSelection = new StringSelection("xxx");
        System.out.println(ClipboardUtil.get(stringSelection, DataFlavor.stringFlavor));
    }

    @Test
    void setImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        ClipboardUtil.setImage(ImgUtil.toImage(source));
    }

    @Test
    void getImage() {
        Image image = ClipboardUtil.getImage();
        System.out.println(image);
    }

    @Test
    void testGetImage() {
        File source = FileUtil.file("/Users/kuuga/Downloads/img/1.png");
        BufferedImage bufferedImage = ImgUtil.toImage(source);
        Image image = ClipboardUtil.getImage(new ImageSelection(bufferedImage));
        System.out.println(image);
    }

    @Test
    void listen() {
        KuugaClipboardListener listener = new KuugaClipboardListener();
        ClipboardUtil.listen(listener, false);
        DaemonThread.await();
    }

    @Test
    void testListen() {
    }

    @Test
    void testListen1() {
    }

    static class KuugaClipboardListener implements ClipboardListener {

        @SneakyThrows
        @Override
        public Transferable onChange(Clipboard clipboard, Transferable contents) {
            System.out.println(ClipboardUtil.getStr());
            return contents;
        }
    }

}