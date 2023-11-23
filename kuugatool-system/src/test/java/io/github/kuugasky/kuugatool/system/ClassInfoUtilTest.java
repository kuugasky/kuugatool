package io.github.kuugasky.kuugatool.system;

import org.junit.jupiter.api.Test;

class ClassInfoUtilTest {

    @Test
    void test() {
        System.out.println("获取当前执行代码文件名:" + ClassInfoUtil.getFileName());
        System.out.println("获取当前执行代码类名:" + ClassInfoUtil.getClassName());
        System.out.println("获取当前执行代码方法名:" + ClassInfoUtil.getMethodName());
        System.out.println("获取当前执行代码行数:" + ClassInfoUtil.getLineNumber());
        System.out.println("获取当前类文件的URI目录,不包括自己:" + ClassInfoUtil.getCurrentFileUriFolder());
        System.out.println("获取当前类文件的classPath的绝对URI路径:" + ClassInfoUtil.getProjectTargetClasses());
        System.out.println("获取当前执行文件所在的/target/class路径:" + ClassInfoUtil.getCurrentClassFilePath(ClassInfoUtil.class));
        System.out.println("获取当前类文件的URI目录,不包括自己:" + ClassInfoUtil.getCurrentFileUriFolderNoTarget());
    }

}