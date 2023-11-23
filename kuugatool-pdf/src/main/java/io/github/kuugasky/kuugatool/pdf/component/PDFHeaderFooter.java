package io.github.kuugasky.kuugatool.pdf.component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.pdf.component.builder.HeaderFooterBuilder;
import lombok.NoArgsConstructor;

/**
 * PDF页眉页脚
 *
 * @author fgm
 * @date 2017/4/22
 * 页眉页脚定制工具
 */
@NoArgsConstructor
public class PDFHeaderFooter implements HeaderFooterBuilder {

    /**
     * 页眉-只实现左边页眉
     */
    private String headerText;
    /**
     * 左边页脚
     */
    private String footerLeftText;
    /**
     * 右边页脚
     */
    private String footerRightText;

    public PDFHeaderFooter(String headerText, String footerLeftText, String footerRightText) {
        this.headerText = headerText;
        this.footerLeftText = footerLeftText;
        this.footerRightText = footerRightText;
    }

    /**
     * PDF页头设置类
     *
     * @param writer   PDF编写类
     * @param document PDF文档对象
     * @param font     字体设置
     * @param template PDF模板
     */
    @Override
    public void writeHeader(PdfWriter writer, Document document, Font font, PdfTemplate template) {
        if (StringUtil.isEmpty(headerText)) {
            return;
        }
        ColumnText.showTextAligned(
                writer.getDirectContent(),
                Element.ALIGN_LEFT,
                new Phrase(headerText, font),
                document.left(),
                document.top() + 20, 0);
    }

    /**
     * PDF页脚设置类
     *
     * @param writer   PDF编写类
     * @param document PDF文档对象
     * @param font     字体设置
     * @param template PDF模板
     */
    @Override
    public void writeFooter(PdfWriter writer, Document document, Font font, PdfTemplate template) {
        // 获取总页码
        int pageS = writer.getPageNumber();

        PdfContentByte cb = writer.getDirectContent();

        if (StringUtil.hasText(footerLeftText)) {
            Phrase footer1 = new Phrase(footerLeftText, font);

            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer1, (document.left() + 10), document.bottom() - 20, 0);
        }

        if (StringUtil.hasText(footerRightText)) {
            Phrase footer2 = new Phrase(footerRightText + "    " + pageS + "/", font);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer2, (document.right() - 30), document.bottom() - 20, 0);
        }
        // 设置模板位置
        cb.addTemplate(template, document.right() - 30, document.bottom() - 20);
    }

    /**
     * 页头、页眉设置的模板替换类
     *
     * @param writer   PDF编写类
     * @param document PDF文档对象
     */
    @Override
    public String getReplaceOfTemplate(PdfWriter writer, Document document) {
        return String.valueOf(writer.getPageNumber());
    }

}
