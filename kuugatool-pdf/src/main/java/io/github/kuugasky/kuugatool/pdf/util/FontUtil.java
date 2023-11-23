package io.github.kuugasky.kuugatool.pdf.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 字体工具类
 *
 * @author kuuga
 * @since 2023-06-12
 */
@Slf4j
public class FontUtil {

    private static final Map<String, Font> FONT_CACHE = Maps.newConcurrentMap();
    private static final String SIMLI_TTF = "simsun.ttf";
    // private static final String SIMLI_TTF = "SIMLI.TTF";

    public static String getFontPath(String fontName) {
        String classPath = Objects.requireNonNull(FontUtil.class.getClassLoader().getResource("")).getPath();
        return classPath + "/fonts/" + fontName;
    }

    /**
     * 加载自定义字体
     *
     * @param fontFileName 字体文件名
     * @param style        字体样式
     * @param fontSize     字体大小
     */
    public static Font loadFont(String fontFileName, int style, float fontSize) throws IOException {
        FileInputStream fis = null;
        String key = fontFileName + "|" + style;
        Font dynamicFont = FONT_CACHE.get(key);
        if (dynamicFont == null) {
            try {
                File file = new File(fontFileName);
                fis = new FileInputStream(file);
                dynamicFont = Font.createFont(style, fis);
                FONT_CACHE.put(key, dynamicFont);

            } catch (Exception e) {
                return new Font("宋体", Font.PLAIN, 14);
            } finally {
                if (null != fis) {
                    fis.close();
                }
            }
        }
        return dynamicFont.deriveFont(fontSize);
    }

    /**
     * 获取字体
     *
     * @param style    字体样式
     * @param fontSize 字体大小
     * @return 字体
     */
    public static Font getFont(int style, float fontSize) {
        try {
            String fontPath = FontUtil.getFontPath(SIMLI_TTF);
            return loadFont(fontPath, style, fontSize);
        } catch (IOException e) {
            log.error("字体加载异常{}", ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

}
