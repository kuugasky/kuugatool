package io.github.kuugasky.kuugatool.extra.alibaba.excel.analysis;

import com.alibaba.excel.EasyExcel;
import io.github.kuugasky.kuugatool.extra.file.MultipartFileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * EasyExcelAnalysisUtil
 * <p>
 * 阿里巴巴EasyExcel解析工具类
 *
 * @author kuuga
 * @since 2023/4/3-04-03 19:39
 */
public class EasyExcelAnalysisUtil {

    /**
     * 自定义解析{@link MultipartFile}
     *
     * @param multipartFile        excel文件
     * @param clazz                解析对象Class
     * @param numberOfEachExecuted 每次从解析内容中读取出的行数
     * @param <T>                  解析对象Class泛型
     * @param <U>                  U extends AnalysisDto
     * @return EasyExcelListener
     * @throws IOException IOException
     */
    public static <T, U extends EasyExcelAnalysisDto> EasyExcelListener<U> read(MultipartFile multipartFile, Class<T> clazz, long numberOfEachExecuted) throws IOException {
        EasyExcelListener<U> customizationListener = new EasyExcelListener<>(numberOfEachExecuted);
        EasyExcel.read(multipartFile.getInputStream(), clazz, customizationListener).doReadAll();
        return customizationListener;
    }

    /**
     * 自定义解析{@link MultipartFile}
     *
     * @param multipartFile         excel文件
     * @param clazz                 解析对象Class
     * @param maximumImportQuantity 文件支持导入最大行数
     * @param numberOfEachExecuted  每次从解析内容中读取出的行数
     * @param <T>                   解析对象Class泛型
     * @param <U>                   U extends AnalysisDto
     * @return EasyExcelListener
     * @throws IOException IOException
     */
    public static <T, U extends EasyExcelAnalysisDto> EasyExcelListener<U> read(MultipartFile multipartFile, Class<T> clazz, long maximumImportQuantity, long numberOfEachExecuted) throws IOException {
        EasyExcelListener<U> customizationListener = new EasyExcelListener<>(maximumImportQuantity, numberOfEachExecuted);
        EasyExcel.read(multipartFile.getInputStream(), clazz, customizationListener).doReadAll();
        return customizationListener;
    }

    /**
     * 自定义解析{@link MultipartFile}
     *
     * @param file                 excel文件
     * @param clazz                解析对象Class
     * @param numberOfEachExecuted 每次从解析内容中读取出的行数
     * @param <T>                  解析对象Class泛型
     * @param <U>                  U extends AnalysisDto
     * @return EasyExcelListener
     * @throws IOException IOException
     */
    public static <T, U extends EasyExcelAnalysisDto> EasyExcelListener<U> read(File file, Class<T> clazz, long numberOfEachExecuted) throws IOException {
        EasyExcelListener<U> customizationListener = new EasyExcelListener<>(numberOfEachExecuted);
        MultipartFile multipartFile = MultipartFileUtil.fileToMultipartFile(file);
        EasyExcel.read(multipartFile.getInputStream(), clazz, customizationListener).doReadAll();
        return customizationListener;
    }

    /**
     * 自定义解析{@link MultipartFile}
     *
     * @param file                  excel文件
     * @param clazz                 解析对象Class
     * @param maximumImportQuantity 文件支持导入最大行数
     * @param numberOfEachExecuted  每次从解析内容中读取出的行数
     * @param <T>                   解析对象Class泛型
     * @param <U>                   U extends AnalysisDto
     * @return EasyExcelListener
     * @throws IOException IOException
     */
    public static <T, U extends EasyExcelAnalysisDto> EasyExcelListener<U> read(File file, Class<T> clazz, long maximumImportQuantity, long numberOfEachExecuted) throws IOException {
        EasyExcelListener<U> customizationListener = new EasyExcelListener<>(maximumImportQuantity, numberOfEachExecuted);
        MultipartFile multipartFile = MultipartFileUtil.fileToMultipartFile(file);
        EasyExcel.read(multipartFile.getInputStream(), clazz, customizationListener).doReadAll();
        return customizationListener;
    }

}
