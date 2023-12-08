package io.github.kuugasky.kuugatool.core.resources;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.*;
import java.net.URL;

/**
 * ResourcesUtil
 *
 * @author kuuga
 * @since 2023/6/12-06-12 11:02
 */
public final class ResourcesUtil {

    public static String getFileContent(String fileRelativePaPth) throws IOException {
        return getFileContent(fileRelativePaPth, true);
    }

    /**
     * 读取resources中某个文件内容
     *
     * @param fileRelativePaPth 带后缀名文件相对路径，如：dir/x.txt或x.txt
     * @param keepFormat        保持格式，保留换行符
     * @return 文件内容
     * @throws IOException IO异常
     */
    public static String getFileContent(String fileRelativePaPth, boolean keepFormat) throws IOException {
        File file = getFile(fileRelativePaPth);

        StringBuilder fileContentBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString;
        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            fileContentBuilder.append(tempString);
            if (keepFormat) {
                fileContentBuilder.append("\n");
            }
        }
        reader.close();

        return fileContentBuilder.toString();
    }

    /**
     * 获取resources中某个文件
     *
     * @param fileRelativePaPth 带后缀名文件相对路径，如：dir/x.txt或x.txt
     * @return 文件
     * @throws FileNotFoundException FileNotFoundException
     */
    public static File getFile(String fileRelativePaPth) throws FileNotFoundException {
        ObjectUtil.requireHasText(fileRelativePaPth, new RuntimeException("模板文件不能为空"));

        fileRelativePaPth = StringUtil.removeStart(fileRelativePaPth, "/");
        URL resource = ResourcesUtil.class.getClassLoader().getResource(fileRelativePaPth);
        if (ObjectUtil.isNull(resource)) {
            throw new FileNotFoundException("resources目录中不存在文件" + fileRelativePaPth);
        }
        return FileUtil.file(resource.getFile());
    }

}
