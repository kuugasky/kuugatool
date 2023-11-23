package io.github.kuugasky.kuugatool.core.guava.ratelimiter;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.RateLimiter;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Map;

/**
 * GuavaRateLimiterTest3
 * 模拟限速下载器
 * <p>
 * RateLimiter没有Release方法，不需要手动进行令牌的回收释放；
 * <p>
 * RateLimiter默认按照设定的参数，每秒固定生成令牌数量，不光可以简单控制并发总量，更重要能控制访问的速率，粒度更细；
 * <p>
 * RateLimiter的Acquire可以一次返回多个令牌，对业务的弹性来讲丰富了很多场景；
 *
 * @author kuuga
 * @since 2022/7/29 18:14
 */
public class GuavaRateLimiterTest3 {

    static int limit = 10;
    static RateLimiter limiter = RateLimiter.create(limit);

    public static void main(String[] args) {
        // 利用RateLimiter定时生成令牌的特性，做一个速率控制器（限速器），模拟某盘的下载控制功能
        // 当然实际的限速器要比这个复杂的多，这里只是开阔下思维，同时与JDK自带的Semaphore进行下比较
        // Semaphore用来控制并发总量，而RateLimiter用来控制并发速率

        String inputStr = "阿富汗战争，即2001年阿富汗战争，是以 美国 为首的联军在2001年10月7日起对 “基地”组织 和塔利班的一场战争，该战争是美国对 9·11事件 的报复。";

        System.out.println("文件内容:" + inputStr);
        System.out.println("文件长度:" + inputStr.length());
        System.out.println("--------------------------");

        long startTime = System.currentTimeMillis();
        String downloadStr = download(inputStr);
        long stopTime = System.currentTimeMillis();

        System.out.println("下载开始时间:" + startTime);
        System.out.println("下载结束时间:" + stopTime);
        System.out.println("下载耗时(毫秒):" + (stopTime - startTime));
        System.out.println("下载的文件内容:" + downloadStr);
        System.out.println("下载的文件长度:" + downloadStr.length());
        System.out.println("文件内容是否相等:" + downloadStr.equals(inputStr));

//    文件内容:阿富汗战争，即2001年阿富汗战争，是以 美国 为首的联军在2001年10月7日起对 “基地”组织 和塔利班的一场战争，该战争是美国对 9·11事件 的报复。
//    文件长度:79
//          --------------------------
//    下载开始时间:1649750529917
//    下载结束时间:1649750555917
//    下载耗时(毫秒):26000
//    下载的文件内容:阿富汗战争，即2001年阿富汗战争，是以 美国 为首的联军在2001年10月7日起对 “基地”组织 和塔利班的一场战争，该战争是美国对 9·11事件 的报复。
//    下载的文件长度:79
//    文件内容是否相等:true
    }

    public static String download(String inputStr) {

        StringBuffer sb = new StringBuffer();
        char[] inputChars = inputStr.toCharArray();
        final int str_length = inputStr.length();

        int start_index = 0;
        int len = limit;

        Multiset<Object> objects = SetUtil.newMultiset();
        Map<String, Integer> xx = MapUtil.newHashMap();

        while (start_index < str_length) {
            // 每秒最多下载3个字节
            limiter.acquire(limit);
            String element = DateUtil.todayTime();
            objects.add(element);
            if (xx.keySet().contains(element)) {
                xx.put(element, xx.get(element) + 1);
            } else {
                xx.put(element, 1);
            }

            if ((start_index + limit) >= str_length) {
                len = str_length - start_index;
            }

            String s = new String(inputChars, start_index, len);
            // System.out.println("s:" + s + ",start_index:" + start_index + ",len:" + len);
            sb.append(inputChars, start_index, len);

            start_index += limit;
        }

        System.out.println(StringUtil.repeatNormal());
        objects.forEach(x -> System.out.println(x + "-->" + objects.count(x)));
        objects.forEach(System.out::println);
        System.out.println(StringUtil.repeatNormal());
        System.out.println(MapUtil.toString(xx, true));
        System.out.println(StringUtil.repeatNormal());

        return sb.toString();
    }

}
