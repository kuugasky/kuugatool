package io.github.kuugasky.kuugatool.core.swing.clipboard.kuuga;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * MonitorClipboard
 *
 * @author kuuga
 * @since 2022/10/9-10-09 16:26
 */
public class MonitorClipboard {

    public static void main(String[] args) throws Exception {
        System.out.println(getSysClipboardText());
    }

    public static String getSysClipboardText() throws Exception {
        String ret = "";

        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf == null) {
            return null;
        }
        // 检查内容是否是文本类型
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String) clipTf.getTransferData(DataFlavor.stringFlavor);
        }
        return ret;
    }

}
