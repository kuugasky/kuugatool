package io.github.kuugasky.kuugatool.core.swing.clipboard.kuuga;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * ClipboardInputOutput
 *
 * @author kuuga
 * @since 2022/10/9-10-09 16:34
 */
public class ClipboardInputOutput {

    public static void main(String[] args) throws Exception {
        // 把文本设置到剪贴板（复制）
        setClipboardString("感谢您的关注！");
        // 从剪贴板中获取文本（粘贴）
        System.out.println("当前剪贴板数据: " + io.github.kuugasky.kuugatool.core.swing.clipboard.kuuga.MonitorClipboard.getSysClipboardText());
    }

    private static void setClipboardString(String str) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(str);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

}
