package io.github.kuugasky.kuugatool.pdf.component.builder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 页眉页脚生成器
 *
 * @author kuuga
 * @date 2023-06-12
 */
public interface HeaderFooterBuilder {

    /**
     * 写页眉
     *
     * @param writer   PdfWriter
     * @param document Document
     * @param font     Font
     * @param template PdfTemplate
     */
    void writeHeader(PdfWriter writer, Document document, Font font, PdfTemplate template);

    /**
     * 写页脚
     *
     * @param writer   PdfWriter
     * @param document Document
     * @param font     Font
     * @param template PdfTemplate
     */
    void writeFooter(PdfWriter writer, Document document, Font font, PdfTemplate template);

    /**
     * 关闭文档前, 获取替换页眉页脚处设置模板的文本
     *
     * @param writer   PdfWriter
     * @param document Document
     * @return 模板的文本
     */
    String getReplaceOfTemplate(PdfWriter writer, Document document);

}
