package io.github.kuugasky;

import java.util.Arrays;

/**
 * MinDiff
 * <p>
 * 最小值差异
 *
 * @author kuuga
 * @since 2023/9/26-09-26 12:34
 */
public class MinDiff {

    /**
     * 有一个包含N个整数的数组，请编写一个算法，找到其中的两个元素，使它们之差最小。时间复杂度必须为O(n)。
     * <p>
     * 这个问题，最简单的思路是把数组排序，然后直接把最小的两个值加到一起返回就行了，
     * 但是，常见排序算法中，时间复杂度基本都是O(nlogn)，想要实现O(n)就需要考虑**桶排序**。
     */
    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 5, 9, 2, 6, 8, 7};
        int minDiff = findMinDiff(arr);
        System.out.println("最小差为：" + minDiff);
    }

    /**
     * 可以使用桶排序来实现时间复杂度为O(n)的算法。
     *
     * <li>1.先扫描一遍数组，找到数组中的最大值和最小值。</li>
     * <li>2.根据最大值和最小值计算桶的个数和桶的宽度，桶的个数为(maxValue - minValue + 1)，桶的宽度为1。</li>
     * <li>3.将每个元素放到对应的桶中。</li>
     * <li>4.从第一个桶开始，计算该桶内的最大值和下一个非空桶的最小值之差，记录最小值。</li>
     * <li>5.返回最小值即为所求。</li>
     */
    private static int findMinDiff(int[] arr) {
        int n = arr.length;

        if (n < 2) {
            return -1;
        }

        // 找到最大值和最小值
        int minVal = Integer.MAX_VALUE, maxVal = Integer.MIN_VALUE;
        for (int j : arr) {
            if (j < minVal) {
                minVal = j;
            }
            if (j > maxVal) {
                maxVal = j;
            }
        }

        // 计算桶的个数和宽度
        int bucketWidth = 1;
        int bucketCount = maxVal - minVal + 1;

        // 初始化桶
        int[][] buckets = new int[bucketCount][n];
        int[] bucketSizes = new int[bucketCount];

        // 将元素放到对应的桶中
        for (int j : arr) {
            // 桶下标=元素-最小值/桶宽度1
            int index = (j - minVal) / bucketWidth;
            buckets[index][bucketSizes[index]++] = j;
        }

        // 对每个桶进行排序
        for (int i = 0; i < bucketCount; i++) {
            if (bucketSizes[i] > 0) {
                Arrays.sort(buckets[i], 0, bucketSizes[i]);
            }
        }

        // 计算相邻桶的最小差值
        int minDiff = Integer.MAX_VALUE;
        int prevMax = buckets[0][0];
        for (int i = 1; i < bucketCount; i++) {
            if (bucketSizes[i] == 0) {
                continue;
            }
            int currMin = buckets[i][0];
            int diff = currMin - prevMax;
            if (diff < minDiff) {
                minDiff = diff;
            }
            prevMax = buckets[i][bucketSizes[i] - 1];
        }

        return minDiff;
    }

}
