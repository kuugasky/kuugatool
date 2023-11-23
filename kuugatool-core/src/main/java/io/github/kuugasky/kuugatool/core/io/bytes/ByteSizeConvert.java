package io.github.kuugasky.kuugatool.core.io.bytes;

import io.github.kuugasky.kuugatool.core.enums.ByteType;
import io.github.kuugasky.kuugatool.core.numerical.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ByteSizeConvert
 *
 * @author kuuga
 * @since 2022/7/13 17:48
 */
public final class ByteSizeConvert {

    public ByteSizeConvert(ByteModel byteModel) {
        this.byteModel = byteModel;
    }

    /**
     * 字节模式
     */
    private final ByteModel byteModel;
    /**
     * 标度
     */
    private int scale = 0;
    /**
     * 舍入模式
     */
    private RoundingMode roundingMode = RoundingMode.UP;

    public static ByteSizeConvert model() {
        return new ByteSizeConvert(ByteModel.NORMAL);
    }

    public static ByteSizeConvert model(ByteModel byteModel) {
        return new ByteSizeConvert(byteModel);
    }

    public ByteSizeConvert setScale(int scale) {
        this.scale = scale;
        return this;
    }

    public ByteSizeConvert setScale(int scale, RoundingMode roundingMode) {
        this.scale = scale;
        this.roundingMode = roundingMode;
        return this;
    }

    public ByteSizeConvert setScale(RoundingMode roundingMode) {
        this.scale = 0;
        this.roundingMode = roundingMode;
        return this;
    }

    /**
     * byte字节大小转成对应单位的大小
     *
     * @param byteSize   byte字节大小
     * @param targetType 指定单位
     * @return 转换后的单位大小
     */
    public double convertTo(long byteSize, ByteType targetType) {
        return convertTo(new BigDecimal(byteSize), targetType);
    }

    /**
     * byte字节大小转成对应单位的大小
     *
     * @param byteSize   byte字节大小
     * @param targetType 指定单位
     * @return 转换后的单位大小
     */
    public double convertTo(int byteSize, ByteType targetType) {
        return convertTo(new BigDecimal(byteSize), targetType);
    }

    /**
     * byte字节大小转成对应单位的大小
     *
     * @param byteSize   byte字节大小
     * @param targetType 指定单位
     * @return 转换后的单位大小
     */
    public double convertTo(BigDecimal byteSize, ByteType targetType) {
        long bytesSize = byteSize.longValue();
        if (bytesSize <= 0) {
            return 0;
        }
        return switch (targetType) {
            case B -> byteSize.setScale(this.scale, this.roundingMode).doubleValue();
            case KB ->
                    BigDecimalUtil.divide(bytesSize, this.byteModel.KB()).setScale(this.scale, this.roundingMode).doubleValue();
            case MB ->
                    BigDecimalUtil.divide(bytesSize, this.byteModel.MB()).setScale(this.scale, this.roundingMode).doubleValue();
            case GB ->
                    BigDecimalUtil.divide(bytesSize, this.byteModel.GB()).setScale(this.scale, this.roundingMode).doubleValue();
            case TB ->
                    BigDecimalUtil.divide(bytesSize, this.byteModel.TB()).setScale(this.scale, this.roundingMode).doubleValue();
            case EB ->
                    BigDecimalUtil.divide(bytesSize, this.byteModel.EB()).setScale(this.scale, this.roundingMode).doubleValue();
        };
    }

    /**
     * 将byte_b转换成对应的类型
     *
     * @param byteSize byte字节大小
     * @return 转换后的字节码大小和类型
     */
    public String format(int byteSize) {
        BigDecimal byteSizeValue = BigDecimal.valueOf(byteSize);
        return format(byteSizeValue);
    }

    /**
     * 将byte_b转换成对应的类型
     *
     * @param byteSize byte字节大小
     * @return 转换后的字节码大小和类型
     */
    public String format(long byteSize) {
        BigDecimal byteSizeValue = BigDecimal.valueOf(byteSize);
        return format(byteSizeValue);

    }

    /**
     * 将byte_b转换成对应的类型
     *
     * @param byteSize byte字节大小
     * @return 转换后的字节码大小和类型
     */
    public String format(BigDecimal byteSize) {
        long bytesSize = byteSize.longValue();
        if (bytesSize >= this.byteModel.EB()) {
            return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.EB()) + ByteType.EB.name();
        }
        if (bytesSize >= this.byteModel.TB()) {
            return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.TB()) + ByteType.TB.name();
        }
        if (bytesSize >= this.byteModel.GB()) {
            return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.GB()) + ByteType.GB.name();
        }
        if (bytesSize >= this.byteModel.MB()) {
            return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.MB()) + ByteType.MB.name();
        }
        if (bytesSize >= this.byteModel.KB()) {
            return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.KB()) + ByteType.KB.name();
        }
        return BigDecimalUtil.divide(this.scale, this.roundingMode, bytesSize, this.byteModel.B()) + ByteType.B.name();
    }

    /* ------------------------------------------------------------------------------------------------------------------------------------- */

}
