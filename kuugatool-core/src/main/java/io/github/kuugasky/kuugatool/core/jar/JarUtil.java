package io.github.kuugasky.kuugatool.core.jar;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
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
     * class文件启动：file:/Users/kuuga/IdeaProjects/kuuga/micro-services/services/open/service-open/target/classes/com/kuuga/service/agent/open/OpenServiceApplication.class
     * jar文件启动：jar:file:/Users/kuuga/IdeaProjects/kuuga/micro-services/services/open/service-open/target/service-open-AGENT-1.0.0-SNAPSHOT.jar!/BOOT-INF/classes!/com/kuuga/service/agent/open/OpenServiceApplication.class
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
            // System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
            System.out.println(DateUtil.formatDate(DateUtil.of(time)));
        }
    }

    /**
     * 读取自身jar包内classes目录下的文件
     *
     * @param classLoader         类加载器
     * @param fileNameOfInClasses classes目录下的文件名，如："application.properties" 或 "config/application.yml"
     * @return 文件内容
     */
    public static String readFileContentUnderJarClasses(ClassLoader classLoader, String fileNameOfInClasses) {
        return readFileContentUnderJarClasses(classLoader, fileNameOfInClasses, false);
    }

    /**
     * 读取自身jar包内classes目录下的文件
     *
     * @param classLoader         类加载器
     * @param fileNameOfInClasses classes目录下的文件名，如："application.properties" 或 "config/application.yml"
     * @return 文件内容
     */
    public static String readFileContentUnderJarClasses(ClassLoader classLoader, String fileNameOfInClasses, boolean lineFeed) {
        try {
            InputStream resourceAsStream = classLoader.getResourceAsStream(fileNameOfInClasses);
            String content = readFileContent(lineFeed, resourceAsStream);
            log.info("jar内的{}文件内容:\n{}", fileNameOfInClasses, content);
            return content;
        } catch (IOException e) {
            log.error("jar内的{}文件内容读取异常:{}", fileNameOfInClasses, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取文件内容
     *
     * @param lineFeed         读取内容是否保持换行
     * @param resourceAsStream 输入流
     * @return 文件内容
     * @throws IOException IO异常
     */
    private static String readFileContent(boolean lineFeed, InputStream resourceAsStream) throws IOException {
        InputStream inputStream = Objects.requireNonNull(resourceAsStream);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(inputStreamReader);
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
            if (lineFeed) {
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    /**
     * 获取指定jar文件的manifest信息
     *
     * @param jarPath jar的路径
     * @throws IOException IO异常
     */
    public static void getManiFest(String jarPath) throws IOException {
        // 通过传入的jarPath参数创建一个JarFile对象
        try (JarFile jarFile = new JarFile(jarPath)) {
            // 调用getManifest()方法获取Manifest对象，该对象包含了jar文件的元数据信息。
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                // 通过getMainAttributes()方法获取manifest的主属性集，然后通过Attributes.Name指定的属性名获取Class-Path属性值，该属性指定了jar文件运行所需的其他依赖jar文件。
                // String classPaths = (String) manifest.getMainAttributes().get(new Attributes.Name("Class-Path"));
                // if (classPaths != null && !classPaths.isEmpty()) {
                //     String[] classPathArray = classPaths.split(" ");
                // }
                // 获取JDK版本
                String jdkVersion = (String) manifest.getMainAttributes().get(new Attributes.Name("Build-Jdk"));
                if (StringUtil.isEmpty(jdkVersion)) {
                    jdkVersion = (String) manifest.getMainAttributes().get(new Attributes.Name("Build-Jdk-Spec"));
                }
                String manifestVersion = (String) manifest.getMainAttributes().get(new Attributes.Name("Manifest-Version"));
                String createdBy = manifest.getMainAttributes().getValue("Created-By");
                System.out.println("JDK Version : " + jdkVersion);
                System.out.println("Manifest Version : " + manifestVersion);
                System.out.println("Created By : " + createdBy);
                // 还可以获取其它内容，比如Main-Class等等
            }
        }
    }

}
