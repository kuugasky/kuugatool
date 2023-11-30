package io.github.kuugasky.kuugatool.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.file.FilenameUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.core.string.snow.SnowflakeIdUtil;
import io.github.kuugasky.kuugatool.pdf.component.builder.HeaderFooterBuilder;
import io.github.kuugasky.kuugatool.pdf.component.builder.PDFBuilder;
import io.github.kuugasky.kuugatool.pdf.exception.PDFException;
import io.github.kuugasky.kuugatool.pdf.util.FreeMarkerUtil;
import lombok.Setter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * KuugaPdf
 *
 * @author kuuga
 * @since 2023/6/12-06-12 16:46
 */
public class KuugaPdf {

    /**
     * PDF页眉、页脚定制工具
     */
    @Setter
    private HeaderFooterBuilder headerFooterBuilder;
    /**
     * 模板文件
     */
    @Setter
    private File templateFile;
    /**
     * 模板数据
     */
    @Setter
    private Object templateData;
    /**
     * 输出路径
     */
    @Setter
    private File outputFile;
    /**
     * 输出文件如果存在是否覆盖
     */
    @Setter
    private boolean overwriteIfExist;

    public static KuugaPdfBuilder builder() {
        return new KuugaPdfBuilder();
    }

    /**
     * pdf构建器
     */
    public static class KuugaPdfBuilder {
        private HeaderFooterBuilder headerFooterBuilder;
        private File templateFile;
        private Object templateData;
        private File outputFile;
        private boolean overwriteIfExist;

        private KuugaPdfBuilder() {
        }

        public KuugaPdfBuilder headerFooterBuilder(HeaderFooterBuilder headerFooterBuilder) {
            this.headerFooterBuilder = headerFooterBuilder;
            return this;
        }

        public KuugaPdfBuilder templateFile(File templateFile) {
            this.templateFile = templateFile;
            return this;
        }

        public KuugaPdfBuilder templateData(Object templateData) {
            this.templateData = templateData;
            return this;
        }

        public KuugaPdfBuilder outputFile(File outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public KuugaPdfBuilder overwriteIfExist(boolean overwriteIfExist) {
            this.overwriteIfExist = overwriteIfExist;
            return this;
        }

        public KuugaPdf build() {
            checkTemplateFile(this.templateFile);
            checkOutputFile(this.outputFile);

            KuugaPdf kuugaPdf = new KuugaPdf();
            kuugaPdf.setHeaderFooterBuilder(this.headerFooterBuilder);
            kuugaPdf.setTemplateFile(this.templateFile);
            kuugaPdf.setTemplateData(this.templateData);
            kuugaPdf.setOutputFile(this.outputFile);
            kuugaPdf.setOverwriteIfExist(this.overwriteIfExist);
            return kuugaPdf;
        }

        private void checkOutputFile(File outputFile) {
            if (outputFile.exists() && !outputFile.isFile()) {
                throw new RuntimeException("输出文件不是文件格式");
            }
            String baseName = FilenameUtil.getBaseName(outputFile);
            String extension = FilenameUtil.getExtension(outputFile);
            boolean isPdfFile = StringUtil.endsWithIgnoreCase(extension, "pdf");
            if (!isPdfFile) {
                throw new RuntimeException("输出文件格式不是PDF");
            }
            if (!overwriteIfExist) {
                String pathPrefix = FilenameUtil.getPathPrefix(outputFile);
                long snowflakeId = SnowflakeIdUtil.getSnowflakeId();
                this.outputFile = FileUtil.file(pathPrefix + baseName + "_" + snowflakeId + "." + extension);
            }
        }

        private void checkTemplateFile(File templateFile) {
            if (!templateFile.exists()) {
                throw new RuntimeException("模板文件不存在");
            }
            if (!templateFile.isFile()) {
                throw new RuntimeException("模板文件不是文件格式");
            }
        }
    }

    /**
     * 导出PDF到文件
     */
    public void exportToFile() {
        // freeMarker组装模板和数据
        String htmlData = FreeMarkerUtil.packTemplateData(templateFile, templateData);

        if (!outputFile.getParentFile().exists()) {
            boolean mkdirs = outputFile.getParentFile().mkdirs();
            System.out.println(mkdirs);
        }

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            // 设置文档大小
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // 设置页眉页脚
            PDFBuilder builder = new PDFBuilder(headerFooterBuilder);
            builder.setPresentFontSize(10);
            writer.setPageEvent(builder);

            // 输出为PDF文件
            convertToPdf(writer, document, htmlData);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PDFException("PDF export to File fail", ex);
        }
    }

    // /**
    //  * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
    //  * @param ftlPath ftl模板文件的路径（不含文件名）
    //  * @param data 输入到FTL中的数据
    //  * @param response HttpServletResponse
    //  * @return
    //  */
    // public  OutputStream exportToResponse(String ftlPath,Object data,
    //                                                  HttpServletResponse response){
    //
    //     String html= FreeMarkerUtil.getContent(ftlPath,data);
    //
    //     try{
    //         OutputStream out = null;
    //         ITextRenderer render = null;
    //         out = response.getOutputStream();
    //         //设置文档大小
    //         Document document = new Document(PageSize.A4);
    //         PdfWriter writer = PdfWriter.getInstance(document, out);
    //         //设置页眉页脚
    //         PDFBuilder builder = new PDFBuilder(headerFooterBuilder,data);
    //         writer.setPageEvent(builder);
    //         //输出为PDF文件
    //         convertToPDF(writer,document,html);
    //         return out;
    //     }catch (Exception ex){
    //         throw  new PDFException("PDF export to response fail",ex);
    //     }
    //
    // }

    /**
     * PDF文件生成
     *
     * @param writer     PdfWriter
     * @param document   Document
     * @param htmlString html模板内容
     */
    private void convertToPdf(PdfWriter writer, Document document, String htmlString) {
        // 获取字体路径
        String fontPath = getFontPath();
        document.open();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(htmlString.getBytes());

            InputStream resourceAsStream = XMLWorkerHelper.class.getResourceAsStream("/default.css");

            XMLWorkerFontProvider xmlWorkerFontProvider = new XMLWorkerFontProvider(fontPath);

            XMLWorkerHelper.getInstance()
                    .parseXHtml(writer, document, byteArrayInputStream, resourceAsStream, StandardCharsets.UTF_8, xmlWorkerFontProvider);

        } catch (IOException e) {
            e.printStackTrace();
            throw new PDFException("PDF文件生成异常", e);
        } finally {
            document.close();
        }
    }

    /**
     * 获取字体设置路径
     */
    public static String getFontPath() {
        String classpath = Objects.requireNonNull(KuugaPdf.class.getClassLoader().getResource("")).getPath();
        return classpath + "fonts";
    }

}
