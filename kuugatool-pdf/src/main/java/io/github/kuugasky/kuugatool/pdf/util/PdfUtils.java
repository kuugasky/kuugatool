package io.github.kuugasky.kuugatool.pdf.util;

import com.itextpdf.text.pdf.BaseFont;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * PdfUtils
 *
 * @author kuuga
 * @since 2023/6/13-06-13 16:19
 */
public class PdfUtils {

    public static void main(String[] args) throws Exception {
        String htmlPath = "/Users/kuuga/Downloads/pdf/invoice.html";
        String pdfPath = "/Users/kuuga/Downloads/pdf/invoice-pdfUtil1.pdf";
        String htmlContent = FileUtil.readFileToString(FileUtil.file(htmlPath));

        htmlToPdf(htmlContent, pdfPath);

        pdfPath = "/Users/kuuga/Downloads/pdf/invoice-pdfUtil2.pdf";

        html2pdf(htmlContent, pdfPath);
    }

    public static void htmlToPdf(String htmlContent, String filePath) throws Exception {
        File file = FileUtil.file(filePath);
        if (file.exists()) {
            boolean delete = file.delete();
            System.out.println(delete);
        }
        FileUtil.createFile(filePath);
        try (OutputStream fileStream = new FileOutputStream(filePath)) {
            ITextRenderer textRenderer = new ITextRenderer();
            ITextFontResolver fontResolver = textRenderer.getFontResolver();

            String agreementBody = htmlContent;
            agreementBody = agreementBody.replace("&nbsp;", " ");
            agreementBody = agreementBody.replace("&ndash;", "–");
            agreementBody = agreementBody.replace("&mdash;", "—");
            // left single quotation mark
            agreementBody = agreementBody.replace("&lsquo;", "‘");
            // right single quotation mark
            agreementBody = agreementBody.replace("&rsquo;", "’");
            // single low-9 quotation mark
            agreementBody = agreementBody.replace("&sbquo;", "‚");
            // left double quotation mark
            agreementBody = agreementBody.replace("&ldquo;", "“");
            // right double quotation mark
            agreementBody = agreementBody.replace("&rdquo;", "”");
            // double low-9 quotation mark
            agreementBody = agreementBody.replace("&bdquo;", "„");
            // minutes
            agreementBody = agreementBody.replace("&prime;", "′");
            // seconds
            agreementBody = agreementBody.replace("&Prime;", "″");
            // single left angle quotation
            agreementBody = agreementBody.replace("&lsaquo;", "‹");
            // single right angle quotation
            agreementBody = agreementBody.replace("&rsaquo;", "›");
            // overline
            agreementBody = agreementBody.replace("&oline;", "‾");

            fontResolver.addFont("/fonts/simsun.ttc", com.itextpdf.kernel.pdf.PdfName.IdentityH.getValue(), false);
            textRenderer.setDocumentFromString(agreementBody, null);
            textRenderer.layout();
            textRenderer.createPDF(fileStream, true);
        }
    }

    /**
     * 将HTML转成PD格式的文件。html文件的格式比较严格
     *
     * @param htmlContent   html内容
     * @param outputPdfFile 输出pdf文件
     * @throws Exception Exception
     */
    public static void html2pdf(String htmlContent, String outputPdfFile) throws Exception {
        File file = FileUtil.file(outputPdfFile);
        if (file.exists()) {
            boolean delete = file.delete();
            System.out.println(delete);
        }
        try (OutputStream os = new FileOutputStream(outputPdfFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);

            // step 3 解决中文支持
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("/fonts/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        }

        System.out.println("create pdf done!!");
    }

}
