package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionSplitterTest {

    @Test
    public void split() {
        List<String> todoList = new ArrayList<>(10) {{
            add("https://i.pximg.net/img-original/img/2017/08/31/11/02/23/64709947_p0.jpg");
            add("https://i.pximg.net/img-original/img/2016/05/22/12/23/47/57003517_p0.jpg");
            add("https://i.pximg.net/img-original/img/2018/04/27/00/42/19/68420926_p0.jpg");
            add("https://i.pximg.net/img-original/img/2017/01/02/10/51/11/60718057_p0.jpg");
            add("https://i.pximg.net/img-original/img/2017/10/05/23/04/24/62288832_p0.jpg");
            add("https://i.pximg.net/img-original/img/2018/04/30/00/00/02/68476297_p0.jpg");
            add("https://i.pximg.net/img-original/img/2016/05/03/23/09/00/56688320_p0.jpg");
            add("https://i.pximg.net/img-original/img/2017/08/17/09/54/42/64463656_p0.jpg");
            add("https://i.pximg.net/img-original/img/2018/03/08/20/19/04/67553106_p0.jpg");
            add("https://i.pximg.net/img-original/img/2016/12/25/13/39/24/60546670_p0.jpg");
        }};
        // 拆分成4份
        List<List<String>> lists = CollectionSplitter.split(todoList, 4);
        lists.forEach(list -> {
            list.forEach(System.out::println);
            System.out.println();
        });
    }

    @Test
    public void testSplit() {
        Map<String, String> todoMap = new HashMap<>(11) {{
            put("https://i.pximg.net/img-original/img/2017/08/31/11/02/23/64709947_p0.jpg", "1.jpg");
            put("https://i.pximg.net/img-original/img/2016/05/22/12/23/47/57003517_p0.jpg", "2.jpg");
            put("https://i.pximg.net/img-original/img/2018/04/27/00/42/19/68420926_p0.jpg", "3.jpg");
            put("https://i.pximg.net/img-original/img/2017/01/02/10/51/11/60718057_p0.jpg", "4.jpg");
            put("https://i.pximg.net/img-original/img/2017/10/05/23/04/24/62288832_p0.jpg", "5.jpg");
            put("https://i.pximg.net/img-original/img/2018/04/30/00/00/02/68476297_p0.jpg", "6.jpg");
            put("https://i.pximg.net/img-original/img/2016/05/03/23/09/00/56688320_p0.jpg", "7.jpg");
            put("https://i.pximg.net/img-original/img/2017/08/17/09/54/42/64463656_p0.jpg", "8.jpg");
            put("https://i.pximg.net/img-original/img/2018/03/08/20/19/04/67553106_p0.jpg", "9.jpg");
            put("https://i.pximg.net/img-original/img/2016/12/25/13/39/24/60546670_p0.jpg", "10.jpg");
        }};
        // 拆分成6份
        List<Map<String, String>> mapList = CollectionSplitter.split(todoMap, 6);
        mapList.forEach(list -> {
            list.forEach((key, value) -> System.out.println(key + "=" + value + "     "));
            System.out.println();
        });

    }

    @Test
    public void getBreakpointInterval() {
        String networkFileUrl = "https://attach.bbs.miui.com/forum/201507/01/101040rpwggmf7ptz2wepi.png.thumb.jpg";
        long fileUrlSize = FileUtil.getFileSizeForNetworkUrl(networkFileUrl);
        System.err.println(fileUrlSize);
        Map<Long, Long> breakpointInterval =
                CollectionSplitter.getBreakpointInterval(networkFileUrl, 5);
        breakpointInterval.forEach((key, value) -> System.out.println(key + "===" + value));
    }

    @Test
    public void testGetBreakpointInterval() {
        Map<Long, Long> breakpointInterval =
                CollectionSplitter.getBreakpointInterval(180541, 5);
        breakpointInterval.forEach((key, value) -> System.out.println(key + "===" + value));
    }

    @Test
    public void testGetBreakpointInterval1() {
        Map<Long, Long> breakpointInterval =
                CollectionSplitter.getBreakpointInterval("https://attach.bbs.miui.com/forum/201507/01/101040rpwggmf7ptz2wepi.png.thumb.jpg", 5, "https://www.pixiv.net/", false);
        breakpointInterval.forEach((key, value) -> System.out.println(key + "===" + value));
    }
}