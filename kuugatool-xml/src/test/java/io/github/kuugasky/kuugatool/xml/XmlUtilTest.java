package io.github.kuugasky.kuugatool.xml;

import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.Charset;

class XmlUtilTest {

    private static String xml;

    @BeforeAll
    static void before() {
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset><url><loc>https://m.sbwl.com/shenzhen/</loc><lastmod>2020-01-09</lastmod><changefreq>weekly</changefreq><priority>1.0</priority></url><url><loc>https://m.sbwl.com/shenzhen/sitemap_office_detail_0.xml</loc><lastmod>2020-01-09</lastmod><changefreq>weekly</changefreq><priority>0.8</priority></url></urlset>";
    }

    @Test
    void testReadFile() throws Exception {
        String path = "/Users/kuuga/Downloads/test.xml";
        File file = FileUtil.file(path);
        FileUtil.writeStringToFile(file, xml, Charset.defaultCharset());
        String readXmlFile = XmlUtil.readXmlFile(path);
        System.out.println(readXmlFile);
    }

    @Test
    void testXml2Json() throws DocumentException {
        JSONObject jsonObject = XmlUtil.toJson(xml);
        System.out.println(jsonObject);
    }

    @Test
    void testIsEmpty() {
        System.out.println(XmlUtil.isEmpty(xml));
        System.out.println(XmlUtil.isEmpty("null"));
        System.out.println(XmlUtil.isEmpty(StringUtil.EMPTY));
        System.out.println(XmlUtil.isEmpty(null));
    }

}