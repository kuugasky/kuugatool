package io.github.kuugasky.kuugatool.core.io.watch;

import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.uri.URLUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class WatchUtilTest {

    public static void main(String[] args) throws MalformedURLException {
        Watcher watcher = new SimpleWatcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        };
        // WatchUtil.createAll("/Users/kuuga/Downloads", 0, watcher).start();

        URL url = URLUtil.getURL(new File("/Users/kuuga/Downloads"));
        WatchUtil.create(url, WatchKind.ALL).setWatcher(watcher).start();
    }

    @Test
    public void test() throws MalformedURLException {
        // System.out.println(URLUtil.toURI("/Users/kuuga/Downloads").toURL());
        System.out.println(URLUtil.getURL(new File("/Users/kuuga/Downloads")));
    }

}