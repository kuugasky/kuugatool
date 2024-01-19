package io.github.kuugasky.kuugatool.extra.alibaba.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import io.github.kuugasky.kuugatool.core.collection.CollectionUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.web.CommonWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcelUtil
 *
 * @author kuuga
 * @since 2021/5/25
 */
@Slf4j
public final class EasyExcelUtil {

    private static final ReadSheet READ_SHEET;

    public static final int DEFAULT_SHEET_NO = 0;

    public static final String DEFAULT_SHEET_NAME = "sheet";
    public static final String CONTENT_TYPE = "application/octet-stream;charset=utf-8";
    public static final String PRAGMA = "Pragma";
    public static final String NO_CACHE = "No-cache";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String EXPIRES = "Expires";
    public static final String CONTENT_DISPOSITION = "Content-disposition";
    public static final String ATTACHMENT_FILENAME = "attachment;filename=";

    static {
        READ_SHEET = new ReadSheet(DEFAULT_SHEET_NO);
        READ_SHEET.setSheetName(DEFAULT_SHEET_NAME);
    }

    /**
     * 设置head请求头
     *
     * @param fileName 文件名
     */
    private static void setHeaderParams(String fileName) {
        HttpServletResponse response = CommonWebUtil.getResponse();
        // 设置类型
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setContentType(CONTENT_TYPE);
        // 设置头
        response.setHeader(PRAGMA, NO_CACHE);
        // 设置头
        response.setHeader(CACHE_CONTROL, NO_CACHE);
        // 设置日期头
        response.setDateHeader(EXPIRES, 0);
        response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + fileName);
    }

    /**
     * 设置sheet样式
     *
     * @param headColor    表头背景色
     * @param contentColor 内容背景色
     */
    private static HorizontalCellStyleStrategy getSheetStyle(IndexedColors headColor, IndexedColors contentColor) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(headColor.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(contentColor.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 写Excel文件
     *
     * @param filePath 文件路径
     * @param list     数据集，对象需要继承{@link MultiData}
     */
    public static <T> void writeExcelByFilePath(String filePath, List<T> list) throws Exception {
        writeExcelByFilePath(filePath, 0, DEFAULT_SHEET_NAME, list);
    }

    /**
     * 写Excel文件
     *
     * @param filePath  文件路径
     * @param sheetNo   sheet编号
     * @param sheetName sheet名称
     */
    public static <T> void writeExcelByFilePath(String filePath, int sheetNo, String sheetName, List<T> list) throws Exception {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            ExcelWriter writer = EasyExcel.write(outputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName).head(list.get(0).getClass()).build();
            try {
                writer.write(list, writeSheet);
            } finally {
                if (writer != null) {
                    writer.finish();
                }
            }
        } catch (Exception e) {
            log.error("EasyExcelUtil writeExcelByFilePath error:{}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 写Excel文件，支持多sheet
     *
     * @param filePath       文件路径
     * @param multiSheetList MultiSheet
     */
    public static void writeExcelMultiSheetByFilePath(String filePath, List<MultiSheet> multiSheetList) throws Exception {
        if (ListUtil.isEmpty(multiSheetList)) {
            return;
        }
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            ExcelWriter writer = null;
            try {
                writer = EasyExcel.write(outputStream).build();
                setMultiSheet(multiSheetList, writer);
            } finally {
                if (writer != null) {
                    writer.finish();
                }
            }
        } catch (Exception e) {
            log.error("EasyExcelUtil writeExcelMultiSheetByFilePath error:{}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 设置多sheet
     */
    private static void setMultiSheet(List<MultiSheet> multiSheetList, ExcelWriter writer) {
        if (CollectionUtil.isEmpty(multiSheetList)) {
            return;
        }
        int size = multiSheetList.size();
        for (int i = 0; i < size; i++) {
            WriteSheet writeSheet;
            MultiSheet multiSheet = multiSheetList.get(i);
            List<? extends MultiData> dataList = multiSheet.getDataList();
            if (ListUtil.isEmpty(dataList)) {
                return;
            }
            ExcelWriterSheetBuilder head = EasyExcel.writerSheet(i, multiSheet.getSheetName()).head(dataList.get(0).getClass());
            if (multiSheet.isNeedStyle()) {
                writeSheet = head.registerWriteHandler(getSheetStyle(multiSheet.getHeadColor(), multiSheet.getContentColor())).build();
            } else {
                writeSheet = head.build();
            }
            writer.write(dataList, writeSheet);
        }
    }

    // fileIO =====================================================================================================================

    /**
     * 写Excel文件
     *
     * @param fileName 文件名
     */
    public static <T> void writeExcel(String fileName, List<T> list) throws Exception {
        writeExcel(fileName, 0, fileName, list);
    }

    /**
     * 写Excel文件
     *
     * @param fileName 文件名
     */
    public static <T> void writeExcel(String fileName, List<T> list, Class<T> clazz) throws Exception {
        writeExcel(fileName, 0, fileName, list, clazz);
    }

    /**
     * 写Excel文件
     *
     * @param fileName  文件名路径
     * @param sheetNo   sheet编号
     * @param sheetName sheet名称
     */
    public static <T> void writeExcel(String fileName, int sheetNo, String sheetName, List<T> list) throws Exception {
        writeExcel(fileName, sheetNo, sheetName, list, null);
    }

    /**
     * 写Excel文件
     *
     * @param fileName  文件名路径
     * @param sheetNo   sheet编号
     * @param sheetName sheet名称
     */
    public static <T> void writeExcel(String fileName, int sheetNo, String sheetName, List<T> list, Class<T> clazz) throws Exception {
        if (ListUtil.isEmpty(list)) {
            list = ListUtil.emptyList();
        }

        ExcelWriter writer = null;
        OutputStream outputStream = null;
        try {
            HttpServletResponse response = CommonWebUtil.getResponse();
            setHeaderParams(fileName);
            outputStream = response.getOutputStream();
            writer = EasyExcel.write(outputStream).build();
            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet(sheetNo, sheetName);
            WriteSheet writeSheet;
            if (null == clazz) {
                writeSheet = excelWriterSheetBuilder.build();
            } else {
                if (ListUtil.hasItem(list)) {
                    writeSheet = excelWriterSheetBuilder.head(ListUtil.findFirst(list).getClass()).build();
                } else {
                    writeSheet = excelWriterSheetBuilder.head(clazz).build();
                }
            }
            writer.write(list, writeSheet);
        } catch (Exception e) {
            log.error("EasyExcelUtil writeExcel error:{}", e.getMessage(), e);
            throw e;
        } finally {
            if (null != writer) {
                writer.finish();
            }
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    /**
     * @param fileName 文件名
     * @param headList 文件头信息
     * @param data     写入的数据
     */
    public <T> void writeExcel(String fileName, List<List<String>> headList, List<T> data) throws Exception {
        this.writeExcel(fileName, 0, fileName, headList, data);
    }

    /**
     * @param fileName  文件名
     * @param sheetNo   sheet编号
     * @param sheetName sheet名称
     * @param headList  文件头集合
     * @param dataList  数据集合
     */

    public <T> void writeExcel(String fileName, int sheetNo, String sheetName, List<List<String>> headList, List<T> dataList) throws Exception {
        if (ListUtil.isEmpty(headList)) {
            headList = ListUtil.emptyList();
        }
        if (ListUtil.isEmpty(dataList)) {
            dataList = ListUtil.emptyList();
        }
        ExcelWriter writer = null;
        OutputStream outputStream = null;
        try {
            HttpServletResponse response = CommonWebUtil.getResponse();
            setHeaderParams(fileName);
            outputStream = response.getOutputStream();
            writer = EasyExcel.write(outputStream).build();
            ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet(sheetNo, sheetName);
            WriteSheet writeSheet = excelWriterSheetBuilder.head(headList).build();
            writer.write(dataList, writeSheet);
        } catch (Exception e) {
            log.error("EasyExcelUtil writeExcel error:{}", e.getMessage(), e);
            throw e;
        } finally {
            if (null != writer) {
                writer.finish();
            }
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    /**
     * 写Excel文件，多页签
     *
     * @param filePath       文件名
     * @param multiSheetList 多个sheet
     */
    public static void writeExcelMultiSheet(String filePath, List<MultiSheet> multiSheetList) throws Exception {
        if (CollectionUtil.isEmpty(multiSheetList)) {
            return;
        }

        ExcelWriter writer = null;
        HttpServletResponse response = CommonWebUtil.getResponse();
        setHeaderParams(filePath);
        try (OutputStream outputStream = response.getOutputStream()) {
            try {
                writer = EasyExcel.write(outputStream).build();
            } finally {
                if (null != writer) {
                    writer.finish();
                }
            }
            setMultiSheet(multiSheetList, writer);
        } catch (Exception e) {
            log.error("EasyExcelUtil writeExcelMultiSheet error:{}", e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 读excel文件
     *
     * @param filePath 文件路径
     * @param clazz    返回参数类型
     */
    public static <T> List<T> readExcelByPath(String filePath, Class<T> clazz) throws Exception {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        }
        return readExcelByPath(filePath, null, clazz);
    }

    /**
     * 读excel文件
     *
     * @param filePath 文件路径
     * @param clazz    返回参数类型
     */
    public static <T> List<T> readExcelByPath(String filePath, ReadSheet sheet, Class<T> clazz) throws Exception {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        }
        if (null == sheet) {
            sheet = READ_SHEET;
        }
        InputStream fileStream = null;
        List<T> dataList;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener<T> listener = new ExcelListener<>();
            EasyExcel.read(fileStream, clazz, listener).sheet(sheet.getSheetNo()).doRead();
            dataList = listener.getDataList();
        } catch (FileNotFoundException e) {
            log.error("找不到文件或者文件路径错误，文件url:{}", filePath);
            throw e;
        } finally {
            if (fileStream != null) {
                fileStream.close();
            }
        }
        return dataList;
    }

    /**
     * 读excel文件
     *
     * @param file  文件
     * @param sheet ReadSheet
     * @param clazz 返回参数类型
     */
    public static <T> List<T> readExcelByFile(File file, ReadSheet sheet, Class<T> clazz) {
        if (file == null) {
            return null;
        }
        if (null == sheet) {
            sheet = READ_SHEET;
        }
        List<T> dataList;
        ExcelListener<T> listener = new ExcelListener<>();
        EasyExcel.read(file, clazz, listener).sheet(sheet.getSheetNo()).doRead();
        dataList = listener.getDataList();
        return dataList;
    }

    public static <T> List<T> readExcelByMultipartFile(MultipartFile multipartFile, ReadSheet sheet, Class<T> clazz) throws Exception {
        if (null == multipartFile) {
            return null;
        }
        if (null == sheet) {
            sheet = READ_SHEET;
        }
        List<T> dataList;
        InputStream inputStream = null;
        try {
            ExcelListener<T> listener = new ExcelListener<>();
            inputStream = multipartFile.getInputStream();
            EasyExcel.read(multipartFile.getInputStream(), clazz, listener).sheet(sheet.getSheetNo()).doRead();
            dataList = listener.getDataList();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return dataList;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class ExcelListener<T> extends AnalysisEventListener<T> {

        private List<T> dataList = new ArrayList<>();

        /**
         * @param data    当前行数据
         * @param context 解析上下文
         */
        @Override
        public void invoke(T data, AnalysisContext context) {
            if (data != null) {
                dataList.add(data);
            }
        }

        /**
         * 解析完所有数据后调用该方法
         *
         * @param context 解析上下文
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {

        }
    }

    /**
     * 历史代码有使用
     * <p>
     * // response.setContentType("application/vnd.ms-excel;charset=utf-8");
     *
     * @param fileName fileName
     * @param response response
     */
    @Deprecated
    public static void setHeaderParams(String fileName, HttpServletResponse response) {
        fileName = String.format(fileName, DateUtil.today());
        // 设置类型
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        // 设置内容类型
        response.setContentType(CONTENT_TYPE);
        // 设置头
        response.setHeader(PRAGMA, NO_CACHE);
        // 设置头
        response.setHeader(CACHE_CONTROL, NO_CACHE);
        // 设置日期头
        response.setDateHeader(EXPIRES, 0);
        response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILENAME + fileName);
    }

}
