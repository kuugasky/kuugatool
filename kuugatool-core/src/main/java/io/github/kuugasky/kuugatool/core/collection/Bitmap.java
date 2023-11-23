package io.github.kuugasky.kuugatool.core.collection;

import lombok.Getter;

/**
 * Bitmap
 * <p>
 * 位图是一种常用的数据结构，可以用一个 bit 位来标记某个元素是否存在。
 * bitmap经常用在大数据的题中，比如10亿个int类型的数，如果用int数组存储的话，那么需要大约4G内存，浪费内存。而使用位图可以更加节省空间，并且可以快速判断某个元素是否存在。
 *
 * @author kuuga
 * @since 2023/6/11 16:03
 */
public final class Bitmap {

    /**
     *
     */
    private final byte[] bitmap;
    /**
     * 位图长度
     */
    @Getter
    private final int length;

    /**
     * 构造函数，创建一个指定长度的位图。
     *
     * @param length 位图长度
     */
    public Bitmap(int length) {
        this.length = length;
        bitmap = new byte[length >>> 3];
    }

    /**
     * 获取指定位置的值，即获取指定位置的 bit 是否为 1。
     */
    public boolean get(int number) {
        // 获取位置
        // 等价于 site=number/8
        int site = number >>> 3;
        // 获取该字节
        byte temp = bitmap[site];

        // 获取该字节的第几个
        // 等价于 i=number%8
        int i = number & 7;

        // 将这个字节数右移(7-i)位（也就是把要查找的位移动到最右侧），然后与 0000 0001相与
        return ((temp >>> (7 - i)) & 1) != 0;
    }

    /**
     * 设置指定位置的值，即将指定位置的 bit 设置为 1 或 0。
     */
    private void set(int number, boolean bool) {
        // 获取位置
        // 等价于 site=number/8
        // number >>> 3  是一个位运算操作，它的作用是将  number  右移三位，相当于将  number  除以 8，但比除法更快。
        // 这个操作是在 Bitmap 类中的 get 和 set 方法中用来计算需要操作的字节的位置，因为一个 byte 类型占 8 位，所以将位置除以 8 可以得到对应的字节位置。
        int site = number >>> 3;
        // 获取该字节
        byte temp = bitmap[site];

        // 获取该字节的第几个
        // 等价于 i=number%8
        // number & 7  是一个位运算操作，它的作用是将  number  与 7 进行按位与操作，相当于对  number  取模 8，但比取模运算更快。
        // 这个操作是在 Bitmap 类中的 get 和 set 方法中用来计算需要操作的字节中的某一位，因为一个 byte 类型占 8 位，所以对 8 取模可以得到需要操作的位在字节中的位置。
        int i = number & 7;
        // 将0000 0001 左移(7-i)
        byte comp = (byte) (1 << (7 - i));

        // 设置为1
        if (bool) {
            // 取或(0.. 1 0..)
            // bitmap[site] = (byte) (comp | temp); 是一个位运算操作，它的作用是将 comp 和 temp 进行按位或操作，然后将结果转换成 byte 类型，并将其赋值给 bitmap 数组中的 site 位置。
            // 按位或操作的结果是将两个操作数的每一位进行或运算，只要其中有一个为 1，结果就为 1，否则为 0。
            // 这个操作是在 Bitmap 类中的 add 和 set 方法中用来将指定位置的 bit 设置为 1。
            // 因为一个 byte 类型占 8 位，所以需要将两个 byte 类型的值进行按位或操作，然后将结果存储在 bitmap 数组的对应位置。
            bitmap[site] = (byte) (comp | temp);
        } else {
            // 设置为0
            // 取反
            comp = (byte) ~comp;
            // 相与(1.. 0 1..)
            // bitmap[site] = (byte) (comp & temp); 是一个位运算操作，它的作用是将 comp 和 temp 进行按位与操作，然后将结果转换成 byte 类型，并将其赋值给 bitmap 数组中的 site 位置。
            // 按位与操作的结果是将两个操作数的每一位进行与运算，只有两个操作数的对应位都为 1，结果才为 1，否则为 0。
            // 这个操作是在 Bitmap 类中的 delete 方法中用来将指定位置的 bit 设置为 0。因为一个 byte 类型占 8 位，所以需要将两个 byte 类型的值进行按位与操作，然后将结果存储在 bitmap 数组的对应位置。
            bitmap[site] = (byte) (comp & temp);
        }
    }

    /**
     * 将指定位置的 bit 设置为 1。
     */
    public void add(int index) {
        set(index, true);
    }

    /**
     * 将指定位置的 bit 设置为 0。
     */
    public void delete(int index) {
        set(index, false);
    }

    public static void main(String[] args) {
        // 创建了一个长度为 100000 的位图
        Bitmap bitmap = new Bitmap(1000000000);
        // 然后将第 100 个位置设置为 1
        bitmap.add(10000);
        // 获取查看第 100 个位置的值
        System.out.println(bitmap.get(10000));
        // 再将第 100 个位置的值删除，也就是改成0，改成false
        bitmap.delete(10000);
        // 获取查看第 100 个位置的值
        System.out.println(bitmap.get(10000));
    }

}
