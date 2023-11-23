package io.github.kuugasky.kuugatool.xml;

import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * xml格式验证工具类
 *
 * @author kuuga
 * @date 2017-11-28
 */
public final class XmlValidateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidateUtil.class);

    private XmlValidateUtil() {
    }

    /**
     * 是否正确格式的xml内容
     *
     * @param xmlInputStream xml输入流
     * @return boolean
     */
    public static boolean isValid(InputStream xmlInputStream) {
        if (ObjectUtil.isNull(xmlInputStream)) {
            return false;
        }
        boolean flag = true;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            builder.parse(xmlInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("xml文件格式错误[{}]", e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 是否正确格式的xml内容
     *
     * @param xmlStr xmlStr
     * @return boolean
     */
    public static boolean isValid(String xmlStr) {
        if (StringUtil.isEmpty(xmlStr)) {
            return false;
        }
        boolean flag = true;
        try {
            ByteArrayInputStream targetStream = IoUtil.toInputStream(xmlStr, Charset.defaultCharset());
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            builder.parse(targetStream);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("xml文件[{}]格式错误[{}]", xmlStr, e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 是否正确格式的xml内容文件
     *
     * @param file 文件
     * @return boolean
     */
    public static boolean isValidFile(File file) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        boolean flag = true;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            builder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("xml文件[{}]格式错误[{}]", file.getPath(), e.getMessage());
            flag = false;
        }
        return flag;
    }

}
