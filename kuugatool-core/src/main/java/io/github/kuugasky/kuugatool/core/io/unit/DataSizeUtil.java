package io.github.kuugasky.kuugatool.core.io.unit;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

import java.text.DecimalFormat;

/**
 * 数据大小工具类
 *
 * @author looly
 * @since 5.3.10
 */
public class DataSizeUtil {

    private static final String DATA_SIZE_PATTERN = "#,##0.##";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private DataSizeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 解析数据大小字符串，转换为bytes大小
     *
     * @param text 数据大小字符串，类似于：12KB, 5MB等
     * @return bytes大小
     */
    public static long parse(String text) {
        return DataSize.parse(text).toBytes();
    }

    /**
     * 可读的文件大小<br>
     * 参考 <a href="http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc">...</a>
     *
     * @param size Long类型大小
     * @return 大小
     */
    public static String format(long size) {
        if (size <= 0) {
            return String.valueOf(KuugaConstants.ZERO);
        }

        int digitGroups = Math.min(DataUnit.UNIT_NAMES.length - 1, (int) (Math.log10(size) / Math.log10(1024)));

        String unitName = DataUnit.UNIT_NAMES[digitGroups];

        // 十进制格式
        DecimalFormat decimalFormat = new DecimalFormat(DATA_SIZE_PATTERN);

        return decimalFormat
                .format(size / Math.pow(1024, digitGroups)) + " " + unitName;
    }

}
