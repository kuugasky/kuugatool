package io.github.kuugasky.kuugatool.extra.img;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.regex.RegexConstants;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.List;

/**
 * ImgCheckUtil
 *
 * @author kuuga
 * @since 2022/8/19 20:08
 */
public final class ImgCheckUtil {

    /**
     * 提取文本中的图片地址
     *
     * @param str 文本内容
     * @return 图片urls
     */
    public static List<String> extractImageUrls(String str) {
        if (StringUtil.isEmpty(str)) {
            return ListUtil.emptyList();
        }
        return RegexUtil.getMatches(RegexConstants.REGEX_IMAGE_URL, str);
    }

    public record urlSize(String url, long size) {
    }

    /**
     * 提取文本中的图片地址后，获取图片大小
     *
     * @param str 文本内容
     * @return url<- ->size
     */
    public static List<urlSize> getImgSizeAfterExtractImageUrls(String str) {
        List<String> imageUrls = extractImageUrls(str);

        List<urlSize> results = ListUtil.newArrayList();
        ListUtil.optimize(imageUrls).forEach(url -> {
            long fileSizeForNetworkUrl = FileUtil.getFileSizeForNetworkUrl(url);
            results.add(new urlSize(url, fileSizeForNetworkUrl));
        });
        return results;
    }

}
