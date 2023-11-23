package io.github.kuugasky.kuugatool.core.file;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FilenameUtilTest {

    public static final File FILE_CLASS = new File("/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-captcha/target/classes/cn/kuugatool/captcha/AbstractCaptcha.class");
    public static final File FILE_JAVA = new File("/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-captcha/src/main/java/cn/kuugatool/captcha/AbstractCaptcha.java");

    @Test
    public void isClass() {
        boolean aClass = FilenameUtil.isClass(FILE_CLASS);
        System.out.println(aClass);
    }

    @Test
    public void isJava() {
        boolean aClass = FilenameUtil.isJava(FILE_JAVA);
        System.out.println(aClass);
    }

    @Test
    public void isJar() {
        boolean aClass = FilenameUtil.isJar(FILE_JAVA);
        System.out.println(aClass);
    }

    @Test
    public void getName() {
        System.out.println(FilenameUtil.getName(FILE_JAVA));
    }

    @Test
    public void testGetName() {
        System.out.println(FilenameUtil.getName(FILE_JAVA.getPath()));
    }

    @Test
    public void testGetName1() {
        System.out.println(FilenameUtil.getName(FILE_JAVA.getPath(), "/"));
    }

    @Test
    public void getBaseName() {
        System.out.println(FilenameUtil.getBaseName(FILE_JAVA));
    }

    @Test
    public void testGetBaseName() {
        System.out.println(FilenameUtil.getBaseName(FILE_JAVA.getPath()));
    }

    @Test
    public void getExtension() {
        System.out.println(FilenameUtil.getExtension(FILE_JAVA));
    }

    @Test
    public void testGetExtension() {
        System.out.println(FilenameUtil.getExtension(FILE_JAVA.getPath()));
    }

    @Test
    public void getPathPrefix() {
        System.out.println(FilenameUtil.getPathPrefix(FILE_JAVA));
    }

    @Test
    public void getPath() {
        System.out.println(FilenameUtil.getPath(FILE_JAVA));
    }

    @Test
    public void isExtension() {
        System.out.println(FilenameUtil.isExtension(FILE_JAVA.getName(), "java"));
    }

    @Test
    public void testIsExtension() {
        System.out.println(FilenameUtil.isExtension(FILE_JAVA.getName(), new String[]{"java", "class"}));
    }

    @Test
    public void testIsExtension1() {
        System.out.println(FilenameUtil.isExtension(FILE_JAVA.getName(), ListUtil.newArrayList("java", "class")));
    }

    @Test
    public void removeExtension() {
        System.out.println(FilenameUtil.removeExtension(FILE_JAVA.getName()));
    }

    @Test
    public void cleanInvalid() {
        System.out.println(FilenameUtil.cleanInvalid(FILE_JAVA.getName()));
    }

}