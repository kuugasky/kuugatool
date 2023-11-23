package io.github.kuugasky.kuugatool.pdf;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.pdf.component.PDFHeaderFooter;
import io.github.kuugasky.kuugatool.pdf.util.FreeMarkerUtil;
import io.github.kuugasky.kuugatool.pdf.util.PdfUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * FileTypeConvertUtil
 *
 * @author kuuga
 * @since 2023/6/13-06-13 17:04
 */
public class FileTypeConvertTest {

    public static String getXmlString() {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Hello World</title>\n" +
                "\t<style>\n" +
                "\t  table.table-separate th{\n" +
                "    font-weight:bold;\n" +
                "    font-size:14px;\n" +
                "    border-top:1px solid #F3EDE9 !important;\n" +
                "  }\n" +
                "  table.table-separate td{\n" +
                "    padding: 13px 0;\n" +
                "    font-weight:100;\n" +
                "  }\n" +
                "  .table-separate td.tit{\n" +
                "    background-color: #f4f9fe;\n" +
                "    font-weight:normal;\n" +
                "    padding:22px 0;\n" +
                "    width:15%;\n" +
                "  }\n" +
                "  .table-separate td.cont{\n" +
                "    text-align: left;\n" +
                "    padding:16px 22px;\n" +
                "    width:85%;\n" +
                "    line-height:175%;\n" +
                "  }\n" +
                "  .table-separate.no-border th{\n" +
                "    border:none;\n" +
                "    text-align: left;\n" +
                "  }\n" +
                "  .table-separate.no-border td{\n" +
                "    text-align: left;\n" +
                "    border:none;\n" +
                "  }\n" +
                "\t@page {\n" +
                "\tsize:210mm 297mm;//纸张大小A4\n" +
                "\tmargin: 0.25in;\n" +
                "\t-fs-flow-bottom: \"footer\";\n" +
                "\t-fs-flow-left: \"left\";\n" +
                "\t-fs-flow-right: \"right\";\n" +
                "\tborder: thin solid black;\n" +
                "\tpadding: 1em;\n" +
                "\t}\n" +
                "\t#footer {\n" +
                "\tfont-size: 90%; font-style: italic;\n" +
                "\tposition: absolute; top: 0; left: 0;\n" +
                "\t-fs-move-to-flow: \"footer\";\n" +
                "\t}\n" +
                "\t#pagenumber:before {\n" +
                "\tcontent: counter(page);\n" +
                "\t}\n" +
                "\t#pagecount:before {content: counter(pages);\n" +
                "\t}\n" +
                "\ttable {\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t\ttable-layout: fixed;\n" +
                "\t\t\tword-break:break-all;\n" +
                "\t\t\tfont-size: 10px;\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t}\n" +
                "\ttd {\n" +
                "\t\tword-break:break-all;\n" +
                "\t\tword-wrap : break-word;\n" +
                "\t}\n" +
                "\t</style>\n" +
                "\t</head>\n" +
                "<body style = \"font-family: SimSun;\">\n" +
                "<div id=\"footer\" style=\"\">  Page <span id=\"pagenumber\"/> of <span id=\"pagecount\"/> </div>\n" +
                "<div id=\"main\">\n" +
                "    <div style=\"max-width:600px;margin:0 auto;padding:10px;\">\n" +
                "        <div style=\"text-align: center; padding: 5mm 0;\">\n" +
                "            <div style=\"font-weight: bold; font-size: 30px;\"> HI Fudi&amp;More</div>\n" +
                "            <div> THANK YOU FOR SHOPPING WITH Fudi&amp;More!</div>\n" +
                "        </div>\n" +
                "        <div style=\"border: 1px solid black; background-color: #f8f8f8; padding: 4mm;\">\n" +
                "            <div style=\"font-size: 17px; font-weight: bold; border-bottom: 1px solid black; padding-bottom: 5mm;\"> ORDER DETAILS</div>\n" +
                "            <div style=\"padding-top: 10px;\">\n" +
                "                <div><strong>Order:&nbsp;</strong>D-8C2Y Placed on 29/09/2019 10:04</div>\n" +
                "                <div><strong>Carrier:&nbsp;</strong>Delivery</div>\n" +
                "                <div><strong>Payment:&nbsp;</strong>Cash Payment</div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"margin-top: 4mm;\">\n" +
                "            <table class=\"table-separate\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px;margin:0 auto;padding:10px;\">\n" +
                "                <thead>\n" +
                "                <tr style=\"text-align: center; height: 40px;\">\n" +
                "                    <th style=\"width: 90px; background-color: #f8f8f8; border-top: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Reference\n" +
                "                    </th>\n" +
                "                    <th colspan=\"2\" style=\"background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Product</th>\n" +
                "                    <th style=\"width: 110px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Unit price</th>\n" +
                "                    <th style=\"width: 80px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Quantity</th>\n" +
                "                    <th style=\"width: 90px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Total price</th>\n" +
                "                </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Main\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        SweetSour Chicken\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 7.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 7.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Main\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        Black Bean Stir Fry\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 9.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 9.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Pizzas\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        Test Design Your Own 8\" Pizza\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 6.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 6.00</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "                <tfoot>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Item:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">3</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Subtotal:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro;24.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Deliver Fee:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">+&euro;2.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Discount:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">-&euro;0.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Total:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro;24.00</td>\n" +
                "                </tr>\n" +
                "                </tfoot>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <div style=\"border: 1px solid black; background-color: #f8f8f8; padding:5mm; margin-top: 5mm;\">\n" +
                "                <div style=\"font-size: 17px; font-weight: bold; border-bottom: 1px solid black; padding-bottom: 15px;\"> DELIVERY ADDRESS</div>\n" +
                "                <div style=\"padding-top: 10px;\">\n" +
                "                    <div><strong>guan</strong> &#9742; <strong>13656690321</strong></div>\n" +
                "                    <div> 1024/ Edenhall,ModelFarmRd,Cork,爱尔兰,A 2048</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"font-size: 13px;\"><p>You can review your order and download your invoice from the \"<a target=\"_blank\"\n" +
                "                                                                                                          href=\"http://www.fudiandmore.ie/#/FudiIndex/Order1\">Order\n" +
                "            history</a>\"section of your customer account by clicking \"<a target=\"_blank\" href=\"http://www.fudiandmore.ie/#/FudiIndex/Personalcenter1\">My\n" +
                "            account</a>\" on ourshop.</p></div>\n" +
                "        <hr style=\"border-width: 5px;\"/>\n" +
                "        <div> Fudi,More powered by <a target=\"_blank\" href=\"http://www.fudiandmore.ie\">A2BLiving</a></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static void main(String[] args) throws IOException {
        String pdfFile = "/Users/kuuga/Downloads/pdf/invoice-1.pdf";

        String htmlPath = "/Users/kuuga/Downloads/pdf/invoice.html";
        String content = FileUtil.readFileToString(FileUtil.file(htmlPath));
        try {
            PdfUtils.html2pdf(content, pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成功例子
     */
    @Test
    public void sss() throws Exception {
        File htmlFile = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/main/resources/templates/table.ftl");
        String readFileToString = FreeMarkerUtil.packTemplateData(htmlFile, MapUtil.newHashMap("companyName", "大秦皇朝"));
        String pdfFile = "/Users/kuuga/Downloads/pdf/invoice-2.pdf";
        PdfUtils.html2pdf(readFileToString, pdfFile);
    }

    @Test
    public void sss2() throws Exception {
        File htmlFile = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/main/resources/templates/invoice.ftl");
        // String readFileToString = FileUtil.readFileToString(htmlFile);
        String readFileToString = FreeMarkerUtil.packTemplateData(htmlFile, null);
        String pdfFile = "/Users/kuuga/Downloads/pdf/invoice-3.pdf";
        PdfUtils.html2pdf(readFileToString, pdfFile);
    }

    @Test
    public void sss1() {
        File htmlFile = FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/main/resources/templates/table.ftl");
        String pdfFile = "/Users/kuuga/Downloads/pdf/invoice-333.pdf";
        PDFHeaderFooter headerFooter = new PDFHeaderFooter("页眉kuuga", null, "页脚kuuga");

        KuugaPdf kuugaPdf = KuugaPdf.builder()
                .templateFile(htmlFile)
                .templateData(MapUtil.newHashMap("companyName", "大秦皇朝"))
                .outputFile(FileUtil.file(pdfFile))
                .headerFooterBuilder(headerFooter)
                .overwriteIfExist(true)
                .build();
        kuugaPdf.exportToFile();
    }
}
