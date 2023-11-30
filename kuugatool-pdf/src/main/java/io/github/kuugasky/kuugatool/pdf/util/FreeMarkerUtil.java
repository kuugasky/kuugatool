package io.github.kuugasky.kuugatool.pdf.util;

import com.google.common.collect.Maps;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.resources.ResourcesUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.pdf.exception.FreeMarkerException;

import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * FreeMarkerUtil
 * <p>
 * 模板工具类
 *
 * @author kuuga
 * @since 2023-06-12
 */
public class FreeMarkerUtil {

    private static final String UTF_8 = "UTF-8";

    /**
     * 模板文件缓存
     */
    private static final Map<String, Configuration> CONFIGURATION_CACHE = Maps.newConcurrentMap();
    private static final String RESOURCES = "/resources";

    /**
     * 查找模板文件，并进行缓存，返回模板配置
     *
     * @param templateFile 模板文件
     * @return 模板配置
     * @throws FileNotFoundException 模版文件未找到
     */
    public static Configuration getConfiguration(File templateFile) throws FileNotFoundException {
        if (ObjectUtil.isNull(templateFile) || !templateFile.exists()) {
            throw new FileNotFoundException("模板文件不存在");
        }
        String templateFilePath = templateFile.getAbsolutePath();
        if (null != CONFIGURATION_CACHE.get(templateFilePath)) {
            return CONFIGURATION_CACHE.get(templateFilePath);
        }
        Configuration config = new Configuration(Configuration.VERSION_2_3_25);
        config.setDefaultEncoding(UTF_8);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        FileTemplateLoader fileTemplateLoader;
        try {
            String templateDir = FilenameUtil.getPathPrefix(templateFile);
            fileTemplateLoader = new FileTemplateLoader(new File(templateDir));
        } catch (IOException e) {
            throw new FreeMarkerException("fileTemplateLoader init error!", e);
        }
        config.setTemplateLoader(fileTemplateLoader);
        CONFIGURATION_CACHE.put(templateFilePath, config);
        return config;
    }

    // public static String packTemplateData(File templateFile, Object data) {
    //     if (StringUtil.isEmpty(templateContent)) {
    //         throw new FreeMarkerException("template content can not be empty!");
    //     }
    //     try {
    //         Template template = getConfiguration(templateFilePath).getTemplate(templateFileName);
    //         StringWriter writer = new StringWriter();
    //         template.process(data, writer);
    //         writer.flush();
    //         return writer.toString();
    //     } catch (Exception ex) {
    //         throw new FreeMarkerException("FreeMarkerUtil process fail", ex);
    //     }
    // }

    public static void main(String[] args) {
        String s = packTemplateData(FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/main/resources/templates/invoice.ftl"), MapUtil.newHashMap("payee", "黄泽源"));
        System.out.println(s);
    }

    /**
     * 读取resources中某个文件内容
     *
     * @param fileRelativePaPth 带后缀名文件相对路径，如：dir/x.txt或x.txt
     * @return 文件内容
     * @throws IOException IO异常
     */
    public static String getFileContent(String fileRelativePaPth) throws IOException {
        ObjectUtil.requireHasText(fileRelativePaPth, new RuntimeException("模板文件不能为空"));

        StringBuilder fileContentBuilder = new StringBuilder();
        fileRelativePaPth = StringUtil.removeStart(fileRelativePaPth, "/");
        URL resource = ResourcesUtil.class.getClassLoader().getResource(fileRelativePaPth);
        if (ObjectUtil.isNull(resource)) {
            throw new FileNotFoundException("resources目录中不存在文件" + fileRelativePaPth);
        }
        BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
        String tempString;
        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            fileContentBuilder.append(tempString);
        }
        reader.close();

        return fileContentBuilder.toString();
    }

    /**
     * 查找模板文件，并将数据与模板组装得到完整的内容
     *
     * @param templateFile 模板文件
     * @param data         数据对象
     * @return 模版文件内容组装
     */
    public static String packTemplateData(File templateFile, Object data) {
        if (ObjectUtil.isNull(data)) {
            try {
                if (StringUtil.contains(templateFile.getAbsolutePath(), RESOURCES)) {
                    String templateFileRelativePath = templateFile.getAbsolutePath().split(RESOURCES)[1];
                    return ResourcesUtil.getFileContent(templateFileRelativePath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String templateFileName = FilenameUtil.getName(templateFile);
            Template template = getConfiguration(templateFile).getTemplate(templateFileName);
            StringWriter writer = new StringWriter();
            // 将数据对象写入模板中，得到组装好数据的模板内容
            template.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FreeMarkerException("FreeMarkerUtil process fail", ex);
        }
    }

}
