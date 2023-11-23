package io.github.kuugasky.kuugatool.core.swing.clipboard.kuuga;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * ReadClipboard
 *
 * @author kuuga
 * @since 2022/10/9-10-09 17:46
 */
public class ReadClipboard implements ClipboardOwner {

    public ReadClipboard() {
        /*
         // 将剪切板的所有者设置为自己
         // 当所有者为自己时，才能监控下一次剪切板的变动
         // clipboard.getContents(null) 获取当前剪切板的内容
         * 获取系统剪切板
         */
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(clipboard.getContents(null), this);
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadClipboard();
        Thread.sleep(1000000);
    }


    /**
     * 重写 lostOwnership 方法
     * <p>
     * 当有内容写入剪切板时会调用该方法
     *
     * @param clipboard the clipboard that is no longer owned
     * @param contents  the contents which this owner had placed on the
     *                  {@code clipboard}
     */
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // 延迟1s执行，如果立即执行会报错，系统还没使用完剪切板，直接操作会报错
        // IllegalStateException: cannot open system clipboard
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String text = null;
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            try {
                // 获取文本数据
                text = (String) clipboard.getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(text);

        // 不影响剪切板内容
        // 每次剪切板变动，剪切板的所有者会被剥夺，所以要重新设置自己为所有者，才能监听下一次剪切板变动
        clipboard.setContents(clipboard.getContents(null), this);
    }

}
