package io.github.kuugasky.kuugatool.core.jar;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class JarUtilTest {

    @Test
    public void isJarRun() {
        System.out.println(JarUtil.isJarRun(JarUtilTest.class));
        System.out.println(JarUtil.isFileRun(JarUtilTest.class));
    }

    @Test
    public void printJarAllSources() throws IOException {
        File file = new File("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/target/kuugatool-core-2.2.0-SNAPSHOT.jar");
        // File file = new File("/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-core/src/main/java/cn/kuugatool/core/jar/JarUtil.java");
        JarUtil.printJarAllSources(file);
    }

    @Test
    public void readFileContentInJarClasses() {
    }

    @Test
    public void getManiFest() throws IOException {
        File file = new File("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/target/kuugatool-core-2.2.0-SNAPSHOT.jar");
        JarUtil.getManiFest(file.getPath());
    }
}