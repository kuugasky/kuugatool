package io.github.kuugasky.kuugatool.core.properties;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtilTest {

    @Test
    public void getFilePropertiesInSourceModule() {
    }

    @Test
    public void getProperty() {
    }

    @Test
    public void testGetProperty() {
    }

    @Test
    public void getFileProperties() {
    }

    @Test
    public void printAllProperty() {
    }

    public static void main(String[] args) throws IOException {
        Properties filePropertiesInSourceModule = PropertiesUtil.getFilePropertiesInSourceModule("sensitive-words.properties");
        PropertiesUtil.printAllProperty(filePropertiesInSourceModule);

        Properties fileProperties = PropertiesUtil.getFileProperties("/Users/kuuga/IdeaProjects/kfang/agent-micro-services/web-agent-base/src/main/resources/sensitive-words.properties");
        PropertiesUtil.printAllProperty(fileProperties);
    }

    @Test
    public void getTargetClasspath() throws FileNotFoundException {
        System.out.println(PropertiesUtil.getTargetClasspath());
        // 可以获取target/classes下的application.yml文件
        new File(ResourceUtils.getURL("classpath:").getPath() + "application.yml");
    }

}