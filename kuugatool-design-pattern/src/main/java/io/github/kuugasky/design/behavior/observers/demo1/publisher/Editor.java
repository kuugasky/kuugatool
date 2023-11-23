package io.github.kuugasky.design.behavior.observers.demo1.publisher;

import java.io.File;

/**
 * 具体发布者
 *
 * @author kuuga
 * @since 2023/6/26-06-26 19:01
 */
public class Editor {

    public EventManager events;
    private File file;

    /**
     *
     */
    public Editor(String... operations) {
        this.events = new EventManager(operations);
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }

}
