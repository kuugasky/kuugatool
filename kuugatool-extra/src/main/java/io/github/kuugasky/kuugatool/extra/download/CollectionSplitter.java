package io.github.kuugasky.kuugatool.extra.download;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 集合分流器
 *
 * @author kuuga
 */
public final class CollectionSplitter {

    /**
     * 将list按splitTimes等分拆分成多个子集list,再放入list返回
     *
     * @param list       list
     * @param splitTimes splitTimes
     * @param <T>        <T>
     * @return List
     */
    public static <T> List<List<T>> split(List<T> list, int splitTimes) {
        if (ListUtil.isEmpty(list)) {
            return ListUtil.emptyList();
        }

        List<List<T>> result = ListUtil.newArrayList();
        if (splitTimes <= 1) {
            result.add(list);
            return result;
        }

        int groupCount = list.size() / splitTimes + 1;
        int start = 0;
        int end = groupCount;
        for (int i = 0; i < splitTimes; i++) {
            if (start > list.size()) {
                continue;
            }
            List<T> listView = list.subList(start, end);
            start = start + groupCount;
            end = Math.min(start + groupCount, list.size());
            result.add(listView);
        }
        return result.stream().filter(ListUtil::hasItem).collect(Collectors.toList());
    }

    /**
     * 将map按splitTimes等分拆分成多个子集map,再放入list返回
     *
     * @param map        map
     * @param splitTimes splitTimes
     * @return List
     */
    public static <K, V> List<Map<K, V>> split(Map<K, V> map, int splitTimes) {
        if (MapUtil.isEmpty(map)) {
            return ListUtil.emptyList();
        }

        List<Map<K, V>> result = ListUtil.newArrayList();
        if (splitTimes <= 1) {
            result.add(map);
            return result;
        }

        List<K> fileUrls = ListUtil.newArrayList(map.keySet());
        List<List<K>> splits = split(fileUrls, splitTimes);
        splits.forEach(fileUr -> {
            Map<K, V> fillMap = MapUtil.newHashMap();
            for (K urlSplit : fileUr) {
                fillMap.put(urlSplit, map.get(urlSplit));
            }
            result.add(fillMap);
        });
        return result.stream().filter(MapUtil::hasItem).collect(Collectors.toList());
    }

    /**
     * 根据networkFileUrl按splitTimes拆分出断点区间
     * 100size/2splitTimes = map(0,49) + map(50,99)
     *
     * @param networkFileUrl 网络文件url
     * @param splitTimes     拆分成几份
     * @return Map
     */
    public static TreeMap<Long, Long> getBreakpointInterval(String networkFileUrl, long splitTimes) {
        long fileUrlSize = FileUtil.getFileSizeForNetworkUrl(networkFileUrl);
        return getBreakpointInterval(fileUrlSize, splitTimes);
    }

    /**
     * 根据networkFileUrl按splitTimes拆分出断点区间
     * 100size/2splitTimes = map(0,49) + map(50,99)
     *
     * @param networkFileUrl 网络文件url
     * @param splitTimes     拆分成几份
     * @param referer        referer
     * @param useProxy       是否使用代理
     * @return Map
     */
    public static Map<Long, Long> getBreakpointInterval(String networkFileUrl, long splitTimes, String referer, boolean useProxy) {
        if (useProxy) {
            long fileUrlSize = FileUtil.getFileSizeForNetworkUrl(networkFileUrl, referer);
            return getBreakpointInterval(fileUrlSize, splitTimes);
        } else {
            return getBreakpointInterval(networkFileUrl, splitTimes);
        }
    }

    /**
     * 根据fileUrl文件大小按splitTimes拆分出断点区间
     * 100size/2splitTimes = map(0,49) + map(50,99)
     *
     * @param fileSize   文件大小
     * @param splitTimes 拆分成几份
     * @return Map
     */
    public static TreeMap<Long, Long> getBreakpointInterval(long fileSize, long splitTimes) {
        if (fileSize <= 0 || splitTimes <= 0) {
            return MapUtil.newTreeMap();
        }
        TreeMap<Long, Long> result = new TreeMap<>();
        long blockSize = fileSize / splitTimes;
        long startIndex = 0;
        long endIndex = 0;
        while (endIndex < fileSize) {
            if (startIndex == 0) {
                endIndex = blockSize;
            } else {
                endIndex = startIndex + endIndex;
            }
            if (endIndex > fileSize) {
                endIndex = fileSize;
            }
            result.put(startIndex, endIndex);
            startIndex = endIndex + 1;
        }
        return result;
    }

}
