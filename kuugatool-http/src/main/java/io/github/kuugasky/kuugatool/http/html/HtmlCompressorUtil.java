package io.github.kuugasky.kuugatool.http.html;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 压缩jsp,html中的代码，去掉所有空白符、换行符
 *
 * @author bearrui(ak - 47)
 * @version 0.1
 */
public final class HtmlCompressorUtil {

    private final static String TEMP_PRE_BLOCK = "%%%HTMLCOMPRESS~PRE&&&";
    private final static String TEMP_TEXT_AREA_BLOCK = "%%%HTMLCOMPRESS~TEXTAREA&&&";
    private final static String TEMP_SCRIPT_BLOCK = "%%%HTMLCOMPRESS~SCRIPT&&&";
    private final static String TEMP_STYLE_BLOCK = "%%%HTMLCOMPRESS~STYLE&&&";
    private final static String TEMP_JSP_BLOCK = "%%%HTMLCOMPRESS~JSP&&&";

    private final static Pattern COMMENT_PATTERN = Pattern.compile("<!--\\s*[^\\[].*?-->", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern ITS_PATTERN = Pattern.compile(">\\s+?<", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern PRE_PATTERN = Pattern.compile("<pre[^>]*?>.*?</pre>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern TA_PATTERN = Pattern.compile("<textarea[^>]*?>.*?</textarea>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern JSP_PATTERN = Pattern.compile("<%([^-@][\\w\\W]*?)%>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern SCRIPT_PATTERN = Pattern.compile("(?:<script\\s*>|<script type=['\"]text/javascript['\"]\\s*>)(.*?)</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern STYLE_PATTERN = Pattern.compile("<style[^>()]*?>(.+)</style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    /**
     * 单行注释
     */
    private final static Pattern SINGLE_COMMENT_PATTERN = Pattern.compile("//.*");
    /**
     * 字符串匹配
     */
    private final static Pattern STRING_PATTERN = Pattern.compile("(\"[^\"\\n]*?\"|'[^'\\n]*?')");
    /**
     * trim去空格和换行符
     */
    private final static Pattern TRIM_PATTERN = Pattern.compile("\\n\\s*", Pattern.MULTILINE);
    private final static Pattern TRIM_PATTERN_2 = Pattern.compile("\\s*\\r", Pattern.MULTILINE);
    /**
     * 多行注释
     */
    private final static Pattern MULTI_COMMENT_PATTERN = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    /**
     * //占位符
     */
    private final static String TEMP_SINGLE_COMMENT_BLOCK = "%%%HTMLCOMPRESS~SINGLECOMMENT&&&";
    /**
     * /*占位符
     */
    private final static String TEMP_MULIT_COMMENT_BLOCK1 = "%%%HTMLCOMPRESS~MULITCOMMENT1&&&";
    /**
     * * /占位符
     */
    private final static String TEMP_MULIT_COMMENT_BLOCK2 = "%%%HTMLCOMPRESS~MULITCOMMENT2&&&";

    /**
     * 压缩html
     *
     * @param html html
     * @return String
     */
    public static String compress(String html) {
        if (html == null || html.length() == 0) {
            return html;
        }

        List<String> preBlocks = ListUtil.newArrayList();
        List<String> taBlocks = ListUtil.newArrayList();
        List<String> scriptBlocks = ListUtil.newArrayList();
        List<String> styleBlocks = ListUtil.newArrayList();
        List<String> jspBlocks = ListUtil.newArrayList();

        String result = html;

        // preserve inline java code
        Matcher jspMatcher = JSP_PATTERN.matcher(result);
        while (jspMatcher.find()) {
            jspBlocks.add(jspMatcher.group(0));
        }
        result = jspMatcher.replaceAll(TEMP_JSP_BLOCK);

        // preserve PRE tags
        Matcher preMatcher = PRE_PATTERN.matcher(result);
        while (preMatcher.find()) {
            preBlocks.add(preMatcher.group(0));
        }
        result = preMatcher.replaceAll(TEMP_PRE_BLOCK);

        // preserve TEXTAREA tags
        Matcher taMatcher = TA_PATTERN.matcher(result);
        while (taMatcher.find()) {
            taBlocks.add(taMatcher.group(0));
        }
        result = taMatcher.replaceAll(TEMP_TEXT_AREA_BLOCK);

        // preserve SCRIPT tags
        Matcher scriptMatcher = SCRIPT_PATTERN.matcher(result);
        while (scriptMatcher.find()) {
            scriptBlocks.add(scriptMatcher.group(0));
        }
        result = scriptMatcher.replaceAll(TEMP_SCRIPT_BLOCK);

        // don't process inline css
        Matcher styleMatcher = STYLE_PATTERN.matcher(result);
        while (styleMatcher.find()) {
            styleBlocks.add(styleMatcher.group(0));
        }
        result = styleMatcher.replaceAll(TEMP_STYLE_BLOCK);

        // process pure html
        result = processHtml(result);

        // process preserved blocks
        result = processPreBlocks(result, preBlocks);
        result = processTextareaBlocks(result, taBlocks);
        result = processScriptBlocks(result, scriptBlocks);
        result = processStyleBlocks(result, styleBlocks);
        result = processJspBlocks(result, jspBlocks);

        return result.trim();
    }

    private static String processHtml(String html) {
        String result = html;

        // remove comments
        result = COMMENT_PATTERN.matcher(result).replaceAll(StringUtil.EMPTY);

        // remove inter-tag spaces
        result = ITS_PATTERN.matcher(result).replaceAll("><");

        // remove multi whitespace characters
        result = result.replaceAll("\\s{2,}", " ");

        return result;
    }

    private static String processJspBlocks(String html, List<String> blocks) {
        String result = html;
        for (int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressJsp(blocks.get(i)));
        }
        // put preserved blocks back
        while (result.contains(TEMP_JSP_BLOCK)) {
            result = result.replaceFirst(TEMP_JSP_BLOCK, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processPreBlocks(String html, List<String> blocks) {
        String result = html;

        // put preserved blocks back
        while (result.contains(TEMP_PRE_BLOCK)) {
            result = result.replaceFirst(TEMP_PRE_BLOCK, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processTextareaBlocks(String html, List<String> blocks) {
        String result = html;

        // put preserved blocks back
        while (result.contains(TEMP_TEXT_AREA_BLOCK)) {
            result = result.replaceFirst(TEMP_TEXT_AREA_BLOCK, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processScriptBlocks(String html, List<String> blocks) {
        String result = html;

        for (int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressJavaScript(blocks.get(i)));
        }

        // put preserved blocks back
        while (result.contains(TEMP_SCRIPT_BLOCK)) {
            result = result.replaceFirst(TEMP_SCRIPT_BLOCK, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String processStyleBlocks(String html, List<String> blocks) {
        String result = html;

        for (int i = 0; i < blocks.size(); i++) {
            blocks.set(i, compressCssStyles(blocks.get(i)));
        }

        // put preserved blocks back
        while (result.contains(TEMP_SCRIPT_BLOCK)) {
            result = result.replaceFirst(TEMP_SCRIPT_BLOCK, Matcher.quoteReplacement(blocks.remove(0)));
        }

        return result;
    }

    private static String compressJsp(String source) {
        // check if block is not empty
        Matcher jspMatcher = JSP_PATTERN.matcher(source);
        if (jspMatcher.find()) {
            String result = compressJspJs(jspMatcher.group(1));
            return source.substring(0, jspMatcher.start(1)) + result + source.substring(jspMatcher.end(1));
        } else {
            return source;
        }
    }

    private static String compressJavaScript(String source) {
        // check if block is not empty
        Matcher scriptMatcher = SCRIPT_PATTERN.matcher(source);
        if (scriptMatcher.find()) {
            String result = compressJspJs(scriptMatcher.group(1));
            return source.substring(0, scriptMatcher.start(1)) + result + source.substring(scriptMatcher.end(1));
        } else {
            return source;
        }
    }

    private static String compressCssStyles(String source) {
        // check if block is not empty
        Matcher styleMatcher = STYLE_PATTERN.matcher(source);
        if (styleMatcher.find()) {
            // 去掉注释，换行
            String result = MULTI_COMMENT_PATTERN.matcher(styleMatcher.group(1)).replaceAll(StringUtil.EMPTY);
            result = TRIM_PATTERN.matcher(result).replaceAll(StringUtil.EMPTY);
            result = TRIM_PATTERN_2.matcher(result).replaceAll(StringUtil.EMPTY);
            return source.substring(0, styleMatcher.start(1)) + result + source.substring(styleMatcher.end(1));
        } else {
            return source;
        }
    }

    private static String compressJspJs(String source) {
        String result = source;
        // 因注释符合有可能出现在字符串中，所以要先把字符串中的特殊符好去掉
        Matcher stringMatcher = STRING_PATTERN.matcher(result);
        while (stringMatcher.find()) {
            String tmpStr = stringMatcher.group(0);

            if ((tmpStr.contains("//")) || (tmpStr.contains("/*")) || (tmpStr.contains("*/"))) {
                String blockStr = tmpStr
                        .replaceAll("//", TEMP_SINGLE_COMMENT_BLOCK)
                        .replaceAll("/\\*", TEMP_MULIT_COMMENT_BLOCK1)
                        .replaceAll("\\*/", TEMP_MULIT_COMMENT_BLOCK2);
                result = result.replace(tmpStr, blockStr);
            }
        }
        // 去掉注释
        result = SINGLE_COMMENT_PATTERN.matcher(result).replaceAll(StringUtil.EMPTY);
        result = MULTI_COMMENT_PATTERN.matcher(result).replaceAll(StringUtil.EMPTY);
        result = TRIM_PATTERN_2.matcher(result).replaceAll(StringUtil.EMPTY);
        result = TRIM_PATTERN.matcher(result).replaceAll(" ");
        // 恢复替换掉的字符串
        result = result
                .replaceAll(TEMP_SINGLE_COMMENT_BLOCK, "//")
                .replaceAll(TEMP_MULIT_COMMENT_BLOCK1, "/*")
                .replaceAll(TEMP_MULIT_COMMENT_BLOCK2, "*/");
        return result;
    }

}
