package io.github.kuugasky.kuugatool.core.io.unit;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Arrays;

/**
 * 数据单位封装<p>
 * 此类来自于：Spring-framework
 *
 * <pre>
 *     BYTES      1B      2^0     1
 *     KILOBYTES  1KB     2^10    1,024
 *     MEGABYTES  1MB     2^20    1,048,576
 *     GIGABYTES  1GB     2^30    1,073,741,824
 *     TERABYTES  1TB     2^40    1,099,511,627,776
 * </pre>
 *
 * @author Sam Brannen，Stephane Nicoll
 * @since 5.3.10
 */
public enum DataUnit {

    /**
     * Bytes, 后缀表示为： {@code B}.
     */
    BYTES(Constants.B, DataSize.ofBytes(1)),

    /**
     * Kilobytes, 后缀表示为： {@code KB}.
     */
    KILOBYTES(Constants.KB, DataSize.ofKilobytes(1)),

    /**
     * Megabytes, 后缀表示为： {@code MB}.
     */
    MEGABYTES(Constants.MB, DataSize.ofMegabytes(1)),

    /**
     * Gigabytes, 后缀表示为： {@code GB}.
     */
    GIGABYTES(Constants.GB, DataSize.ofGigabytes(1)),

    /**
     * Terabytes, 后缀表示为： {@code TB}.
     */
    TERABYTES(Constants.TB, DataSize.ofTerabytes(1));

    public static final String[] UNIT_NAMES = new String[]{
            Constants.B, Constants.KB, Constants.MB, Constants.GB, Constants.TB, Constants.EB
    };

    /**
     * 数据后缀单位
     */
    private final String suffix;
    /**
     * 数据大小
     */
    private final DataSize size;

    DataUnit(String suffix, DataSize size) {
        this.suffix = suffix;
        this.size = size;
    }

    public DataSize size() {
        return this.size;
    }

    /**
     * 通过后缀返回对应的 {@link DataUnit}
     *
     * @param suffix 单位后缀
     * @return 匹配到的{@link DataUnit}
     * @throws IllegalArgumentException 后缀无法识别报错
     */
    public static DataUnit fromSuffix(String suffix) {
        // 支持类似于 3MB，3M，3m等写法
        DataUnit dataUnit = Arrays.stream(values())
                .filter(dataUnitItem -> StringUtil.startsWithIgnoreCase(dataUnitItem.suffix, suffix))
                .findFirst()
                .orElse(null);
        if (ObjectUtil.isNull(dataUnit)) {
            throw new IllegalArgumentException("Unknown data unit suffix '" + suffix + "'");
        }
        return dataUnit;
    }

    private static class Constants {
        public static final String B = "B";
        public static final String KB = "KB";
        public static final String MB = "MB";
        public static final String GB = "GB";
        public static final String TB = "TB";
        public static final String EB = "EB";
    }

}