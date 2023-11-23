package io.github.kuugasky.design.behavior.observers.demo1.subscriber;

import java.io.File;

/**
 * 收到通知后发送邮件
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:02
 */
public class EmailNotificationListener implements EventListener {

    private final String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("发送邮件给 " + email + ": 有人执行了 " + eventType + " 操作，文件: " + file.getName());
    }

}