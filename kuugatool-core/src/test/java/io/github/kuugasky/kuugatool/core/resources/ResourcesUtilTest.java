package io.github.kuugasky.kuugatool.core.resources;

import junit.framework.TestCase;

import java.io.IOException;

public class ResourcesUtilTest extends TestCase {

    public void testGetFileContent() throws IOException {
        String fileContent = ResourcesUtil.getFileContent("Kuuga/Kuuga.txt");
        System.out.println(fileContent);
    }
}