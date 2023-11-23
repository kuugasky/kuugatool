package io.github.kuugasky.kuugatool.core.jar;

import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * JarUtil
 *
 * @author kuuga
 * @since 2021/5/19
 */
@Slf4j
public class JarUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private JarUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String JAR = "JAR";

    /**
     * 判断程序是否以jar包形式运行
     * <br>
     * class文件启动：file:/Users/kuuga/IdeaProjects/kfang/agent-micro-services/services/open/service-agent-open/target/classes/com/kfang/service/agent/open/OpenServiceApplication.class
     * jar文件启动：jar:file:/Users/kuuga/IdeaProjects/kfang/agent-micro-services/services/open/service-agent-open/target/service-agent-open-AGENT-1.0.0-SNAPSHOT.jar!/BOOT-INF/classes!/com/kfang/service/agent/open/OpenServiceApplication.class
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 是否
     */
    public static <T> boolean isJarRun(Class<T> clazz) {
        String className = clazz.getSimpleName() + ".class";
        String filePath = Objects.requireNonNull(clazz.getResource(className)).toString();
        return filePath.startsWith("jar:file");
    }

    /**
     * 判断文件是否以file形式运行
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 是否
     */
    public static <T> boolean isFileRun(Class<T> clazz) {
        String className = clazz.getSimpleName() + ".class";
        String filePath = Objects.requireNonNull(clazz.getResource(className)).toString();
        return filePath.startsWith("file:");
    }

    /**
     * 打印Jar所有来源
     *
     * @param file file
     * @throws IOException IOException
     */
    public static void printJarAllSources(File file) throws IOException {
        String fileExtension = FilenameUtil.getExtension(file);
        if (!JAR.equalsIgnoreCase(fileExtension)) {
            throw new RuntimeException("非jar包文件不支持");
        }

        JarFile jarFile = new JarFile(file);

        System.out.println(jarFile);

        Enumeration<JarEntry> enu = jarFile.entries();
        while (enu.hasMoreElements()) {
            JarEntry element = enu.nextElement();
            String name = element.getName();
            Long size = element.getSize();
            long time = element.getTime();
            // 压缩大小
            Long compressedSize = element.getCompressedSize();

            System.out.print(name + "\t");
            System.out.print(size + "\t");
            System.out.print(compressedSize + "\t");
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        }
    }

    /**
     * 读取自身jar包内classes目录下的文件
     *
     * @param classLoader         类加载器
     * @param fileNameOfInClasses classes目录下的文件名[按理说类似conf/filename这种应该也能读取，不过还没测试过]
     * @return 文件内容
     * @throws IOException IOException
     */
    public static String readFileContentInJarClasses(ClassLoader classLoader, String fileNameOfInClasses) throws IOException {
        log.info("读取jar内的{}文件内容", fileNameOfInClasses);
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileNameOfInClasses);
        InputStream inputStream = Objects.requireNonNull(resourceAsStream);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(inputStreamReader);
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        String content = buffer.toString();
        log.info("jar内的{}文件内容:{}", fileNameOfInClasses, content);
        return content;
    }

    public static void getManiFest(String jarPath) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                // 获取Class-Path
                String classPaths = (String) manifest.getMainAttributes().get(new Attributes.Name("Class-Path"));
                if (classPaths != null && !classPaths.isEmpty()) {
                    String[] classPathArray = classPaths.split(" ");

                }
                // 获取JDK版本
                String jdkVersion = (String) manifest.getMainAttributes().get(new Attributes.Name("Build-Jdk"));
                System.out.println(jdkVersion);
                // 还可以获取其它内容，比如Main-Class等等
            }
        }
    }


}
