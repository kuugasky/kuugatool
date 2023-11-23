package io.github.kuugasky.kuugatool.http.html;

import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.KuugaHttp;
import io.github.kuugasky.kuugatool.http.KuugaHttpSingleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JAVA去掉HTMl以及CSS样式
 *
 * @author kuuga
 * ******************************************
 * <script>
 * //替换掉所有的 html标签，得到Html标签中的内容
 * var s="<p><font face=宋体 color=#000000>北京中航宇飞科技有限公司是一家致力于为中国国防工业服务的高新企业</font></p>";
 * var dd=s.replace(/<\/?.+?>/g,"");
 * var dds=dd.replace(/ /g,"");//dds为得到后的内容
 * alert(dds);
 * </script>
 *******************************************/
public final class HtmlUtil {
    /**
     * 定义script的正则表达式
     */
    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REG_EX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REG_EX_SPACE = "\\s*|\t|\r|\n";

    /**
     * 删除Html标签
     *
     * @param htmlStr htmlStr
     * @return str
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern pScript = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        // 过滤script标签
        htmlStr = mScript.replaceAll(StringUtil.EMPTY);

        Pattern pStyle = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        // 过滤style标签
        htmlStr = mStyle.replaceAll(StringUtil.EMPTY);

        Pattern pHtml = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        // 过滤html标签
        htmlStr = mHtml.replaceAll(StringUtil.EMPTY);

        Pattern pSpace = Pattern.compile(REG_EX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher mSpace = pSpace.matcher(htmlStr);
        // 过滤空格回车标签
        htmlStr = mSpace.replaceAll(StringUtil.EMPTY);
        // 返回文本字符串
        return htmlStr.trim();
    }

    /**
     * 从html中获取无标签文本
     *
     * @param htmlStr htmlStr
     * @return String
     */
    public static String getTextFromHtml(String htmlStr) {
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("\n\r\t", StringUtil.EMPTY);
        return htmlStr;
    }

    /**
     * 将转义符替换成html标签
     *
     * @param text text
     * @return String
     */
    public static String processBrNbsp(String text) {
        if (text == null) {
            return null;
        }
        text = text.replaceAll("\r\n", "<br>");
        text = text.replaceAll("\n", "<br>");
        text = text.replaceAll("\r", "<br>");
        text = text.replaceAll(" ", "&nbsp;");
        return text;
    }

    /**
     * 将html标签和转义符替换成转义符
     *
     * @param target target
     * @return String
     */
    public static String filterHtmlElement(String target) {
        if (target == null) {
            return null;
        }
        target = target.trim();
        target = target.replace("<", "&lt;");
        target = target.replace(">", "&gt;");
        target = target.replace("\n", "<br>");
        target = target.replace("\"", "&quot;");
        target = target.replace("\'", "&#039;");
        return target.replaceAll("'|exec |;|--|\\/\\*|\\*\\/", StringUtil.EMPTY);
    }

    /**
     * 从html中获取无标签文本（同getTextFromHtml）
     *
     * @param target target
     * @return String
     */
    public static String clearHtmlElement(String target) {
        if (target == null) {
            return null;
        }
        return target.replaceAll("</?[^>]+>", StringUtil.EMPTY).replaceAll("'|exec |;|--|\\/\\*|\\*\\/", StringUtil.EMPTY).trim();
    }

    /**
     * 去除a标签  和 img 标签
     *
     * @param target target
     * @return String
     */
    public static String cleanAandIMG(String target) {
        if (StringUtil.hasText(target)) {
            target = target.replaceAll("<a.*?href[^>]*>", StringUtil.EMPTY);
            target = target.replaceAll("</a>", StringUtil.EMPTY);
            target = target.replaceAll("<img[^>]*/>", " ");
            target = target.replaceAll("<script[^>]*/>", " ");
            target = target.replaceAll("script", StringUtil.EMPTY);
            target = target.replaceAll("www", "4w");
            target = target.replaceAll("http://", "tp://");
            target = target.replaceAll("<script[^>]*/>", " ");
            target = target.replaceAll("script", StringUtil.EMPTY);
        }
        return target;
    }

    /**
     * 将富文本中的图片地址顺序提取出来
     *
     * @param htmlStr htmlStr
     * @return list
     */
    public static List<String> getImagesSrc(String htmlStr) {
        List<String> list = Lists.newArrayList();
        String img;
        Pattern pimage;
        Matcher mimage;
        String regexImg = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        String compileImg = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
        pimage = Pattern.compile(regexImg, Pattern.CASE_INSENSITIVE);
        mimage = pimage.matcher(htmlStr);
        while (mimage.find()) {
            // 得到<img />数据
            img = mimage.group();
            // 匹配<img>中的src数据
            Matcher matcher = Pattern.compile(compileImg).matcher(img);
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
        }
        return list;
    }

    /**
     * 根据id从htmlStr中提取href
     *
     * @param htmlStr htmlStr
     * @param id      id
     * @return href
     */
    public static String getHrefById(String htmlStr, String id) {
        Element elementById = Jsoup.parse(htmlStr).getElementById(id);
        assert elementById != null;
        return elementById.attr("href");
    }

    public static void main(String[] args) {
        KuugaHttp.HttpResult httpResult = KuugaHttpSingleton.init().get("https://qr.chinaums.com/netpay-portal/alipay2/h5Pay.do?billDate=2022-08-02&expireTime=2022-08-02+18%3A29%3A36&goodsList=%5B%7B%22body%22%3A%22%E6%B5%81%E6%B0%B4%E5%8F%B7%3A1017555955186966528%22%2C%22goodsCategory%22%3A%22Auto%22%2C%22goodsId%22%3A%22202208000208%22%2C%22goodsName%22%3A%22order%22%2C%22price%22%3A0.02%2C%22quantity%22%3A1%7D%5D&instMid=H5DEFAULT&merOrderId=310X202208021819367338262136&mid=8984403701397D2&notifyUrl=https%3A%2F%2Fpay-api.kfang.com%2Fipay%2Fchannel%2Fnotify%2Funiversal%2Fh5%2Ffinish&requestTimestamp=2022-08-02+18%3A19%3A36&returnUrl=https%3A%2F%2Ftest-h5.kfang.com%2Fh5-fkb-main%2Fmall-orders&tid=97D20001&totalAmount=2&walletOption=SINGLE&openAppId=8a81c1be7bea6e9c017c62db214d021e&msgSrc=OPENPLATFORM&msgType=trade.h5Pay&sign=4C487531FB445C0D1F4A2728ADE59256");
        String content = httpResult.getContent();
        String httpResult1 = getHrefById(content, "httpResult");
        System.out.println(httpResult1);
    }


}
