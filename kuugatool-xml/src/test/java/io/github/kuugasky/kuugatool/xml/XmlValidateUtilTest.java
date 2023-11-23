package io.github.kuugasky.kuugatool.xml;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

class XmlValidateUtilTest {

    private static String xml;

    @BeforeAll
    static void before() {
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset><url><loc>https://m.sbwl.com/shenzhen/</loc><lastmod>2020-01-09</lastmod><changefreq>weekly</changefreq><priority>1.0</priority></url><url><loc>https://m.sbwl.com/shenzhen/sitemap_office_detail_0.xml</loc><lastmod>2020-01-09</lastmod><changefreq>weekly</changefreq><priority>0.8</priority></url></urlset>";
    }

    @Test
    void isValid() {
        boolean valid = XmlValidateUtil.isValid(xml);
        System.out.println(valid);
    }

    @Test
    void isValid2() {
        ByteArrayInputStream targetStream = IoUtil.toInputStream(xml, Charset.defaultCharset());
        System.out.println(XmlValidateUtil.isValid(targetStream));
    }

    @Test
    void isValid3() throws IOException {
        String path = "/Users/kuuga/Downloads/test.xml";
        File file = FileUtil.file(path);
        FileUtil.delete(file);
        FileUtil.writeStringToFile(file, xml, Charset.defaultCharset());
        System.out.println(XmlValidateUtil.isValidFile(file));
    }

}