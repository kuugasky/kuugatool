package io.github.kuugasky.design.behavior.observers.demo1;

import io.github.kuugasky.design.behavior.observers.demo1.publisher.Editor;
import io.github.kuugasky.design.behavior.observers.demo1.subscriber.EmailNotificationListener;
import io.github.kuugasky.design.behavior.observers.demo1.subscriber.LogOpenListener;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:03
 */
public class Main {

    public static void main(String[] args) {
        Editor editor = new Editor("open");
        editor.events.subscribe("open", new LogOpenListener("/Users/kuuga/Downloads/observer-log-file.txt"));
        // editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));
        Editor editor2 = new Editor("open", "save");
        editor2.events.subscribe("open", new LogOpenListener("/Users/kuuga/Downloads/observer-log-file-2.txt"));
        editor2.events.subscribe("save", new EmailNotificationListener("admin@example-2.com"));

        try {
            editor.openFile("test.txt");
            // editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            editor2.openFile("test-2.txt");
            editor2.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
