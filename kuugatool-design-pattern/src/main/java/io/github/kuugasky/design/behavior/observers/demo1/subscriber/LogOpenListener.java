package io.github.kuugasky.design.behavior.observers.demo1.subscriber;

import java.io.File;

/**
 * 收到通知后在日志中记录一条消息
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:02
 */
public class LogOpenListener implements EventListener {
    private final File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("保存到日志 " + log + ": 有人执行了 " + eventType + " 操作，文件: " + file.getName());
    }
}
