package io.github.kuugasky.kuugatool.pdf;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.pdf.component.PDFHeaderFooter;
import org.junit.jupiter.api.Test;

/**
 * KuugaPdfInvoiceTest
 *
 * @author kuuga
 * @since 2023/6/13-06-13 09:42
 */
class KuugaPdfInvoiceTest {

    @Test
    void test() {
        PDFHeaderFooter headerFooter = new PDFHeaderFooter("kuuga", null, "Thank you for reading.");
        KuugaPdf kuugaPdf = KuugaPdf.builder()
                .headerFooterBuilder(headerFooter)
                .templateFile(FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/main/resources/templates/invoice.ftl"))
                // .templateFile(FileUtil.file("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/test/resources/templates/kuuga.ftl"))
                .templateData(MapUtil.newHashMap("payee", "KUUGA"))
                .outputFile(FileUtil.file("/Users/kuuga/Downloads/pdf/invoice1.pdf"))
                .overwriteIfExist(true)
                .build();
        kuugaPdf.exportToFile();
    }

}
