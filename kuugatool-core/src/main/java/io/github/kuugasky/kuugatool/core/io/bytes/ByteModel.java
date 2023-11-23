package io.github.kuugasky.kuugatool.core.io.bytes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ByteModelEnum
 *
 * @author kuuga
 * @since 2022/7/13 17:49
 */
@Getter
@AllArgsConstructor
public enum ByteModel {

    /**
     *
     */
    NORMAL(1024, "存储模式"),
    STORAGE(1024, "存储模式"),
    MAC(1000, "传输模式"),
    TRANSMISSION(1000, "传输模式");

    private final long KB;
    private final String desc;

    private static final long ONE_B = 1;

    @SuppressWarnings("unchecked")
    public long B() {
        return ONE_B;
    }

    public long KB() {
        return this.KB;
    }

    public long MB() {
        return this.KB * this.KB;
    }

    public long GB() {
        return this.KB * this.KB * this.KB;
    }

    public long TB() {
        return this.KB * this.KB * this.KB * this.KB;
    }

    public long EB() {
        return this.KB * this.KB * this.KB * this.KB * this.KB;
    }

}
