package io.github.kuugasky.kuugatool.core.collection;

import java.util.Arrays;

/**
 * BitmapOffsetUtil
 *
 * @author kuuga
 * @since 2023/6/18 10:48
 */
public final class BitmapOffsetUtil {

    /**
     * 根据位偏移量计算出位偏移量所在范围
     *
     * @param bitOffset bitOffset
     * @return bit offset range
     */
    public static long[] bitOffsetRange(long bitOffset) {
        long offsetRangeStart;
        long offsetRangeEnd;
        if (bitOffset == 0) {
            offsetRangeStart = 0;
            offsetRangeEnd = 7;
        } else {
            offsetRangeStart = ((bitOffset / 8) * 8);
            offsetRangeEnd = offsetRangeStart + 7;
        }
        return new long[]{offsetRangeStart, offsetRangeEnd};
    }

    /**
     * 根据bit偏移量计算出其所在的字节范围
     * <p>
     * 1byte=8bit
     *
     * @param bitOffset bitOffset
     * @return byte range
     */
    public static long[] byteRange(long bitOffset) {
        long byteRangeStart;
        long byteRangeEnd;
        if (bitOffset == 0) {
            // byte 0-0 代表bit从 0-7
            byteRangeStart = 0;
            byteRangeEnd = 0;
        } else {
            byteRangeStart = bitOffset / 8;
            byteRangeEnd = byteRangeStart;
        }
        return new long[]{byteRangeStart, byteRangeEnd};
    }

    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        while (i <= 120) {
            System.out.printf("[%s] [%s - %s]%n", j++, (i == 0 ? 0 : i + 1), (i = (i == 0 ? i + 7 : i + 8)));
        }
        System.out.println(Arrays.toString(byteRange(121)));
        System.out.println(Arrays.toString(byteRange(87)));
        System.out.println(Arrays.toString(byteRange(88)));
        System.out.println(Arrays.toString(byteRange(99)));
        System.out.println(Arrays.toString(byteRange(0)));

        System.out.println(Arrays.toString(bitOffsetRange(0)));
        System.out.println(Arrays.toString(bitOffsetRange(10)));
        System.out.println(Arrays.toString(bitOffsetRange(110)));
        System.out.println(Arrays.toString(bitOffsetRange(122)));
    }

}
