package io.github.kuugasky.kuugatool.core.properties;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;

/**
 * PropertiesUtil
 * <p>
 * [Java 读取 .properties 配置文件的几种方式]<br>
 * <a href="https://www.cnblogs.com/sebastian-tyd/p/7895182.html">...</a>
 *
 * @author kuuga
 * @since 2021/4/9
 */
public class PropertiesUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private PropertiesUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // 读取resources目录下的properties文件属性 ================================================================================

    /**
     * 获取项目中resources目录下的properties文件内容<br>
     * ps：文件名需要后缀
     *
     * @param propertiesFileName 项目中resources目录下的properties文件相对路径
     * @return Properties
     * @throws IOException IOException
     */
    public static Properties getFilePropertiesInSourceModule(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFileName);
        // 使用properties对象加载输入流
        // properties.load(in); 引出的中文乱码问题
        // https://www.cnblogs.com/zno2/p/4613401.html
        /*
         * 1. 从byte流中读取键值对
         * 2. 流应该是规范的properties文件，流被假定为ISO 8859-1 字符编码（即流编码为gbk时会产生乱码）
         * 3. 1byte对应8bit，即二进制 1111 1111 ，表示范围是十六进制 00-FF
         * 4. 默认字符之外的字符应该使用Unicode 逃脱（eclipse properties 自带该功能），例如：小明 → \u5c0f\u660e
         * 5. 该方法执行完毕后，流依旧开着，需要调用close 将流关闭
         */
        properties.load(new InputStreamReader(Objects.requireNonNull(in)));
        return properties;
    }

    public static String getProperty(String propertiesFilePath, String key) throws IOException {
        Properties properties = getFilePropertiesInSourceModule(propertiesFilePath);
        return properties.getProperty(key);
    }

    public static String getProperty(String propertiesFilePath, String key, String defaultValue) throws IOException {
        Properties properties = getFilePropertiesInSourceModule(propertiesFilePath);
        return properties.getProperty(key, defaultValue);
    }

    // 读取任意路径下的properties文件属性 ================================================================================

    /**
     * 读取任意路径下的properties文件属性<br>
     * eg:fileName = "E:/config.properties"<br>
     * ps：文件名需要后缀
     *
     * @param fileName 文件完整路径
     * @throws IOException IOException
     */
    public static Properties getFileProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        properties.load(bufferedReader);
        return properties;
    }

    // Other ================================================================================

    /**
     * 获取项目生成的target/classpath/绝对路径
     *
     * @return target/classpath绝对路径
     */
    public static String getTargetClasspath() {
        try {
            return ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return StringUtil.EMPTY;
    }

    // Other ================================================================================

    /**
     * 输出所有配置信息
     *
     * @param props 配置属性对象
     */
    public static void printAllProperty(Properties props) {
        @SuppressWarnings("rawtypes")
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = props.getProperty(key);
            System.out.println(key + " : " + value);
        }
    }

}
