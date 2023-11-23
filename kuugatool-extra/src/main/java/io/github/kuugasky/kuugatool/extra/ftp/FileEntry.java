package io.github.kuugasky.kuugatool.extra.ftp;

import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.date.TimestampType;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import lombok.Data;

import java.util.Date;

/**
 * FileEntry
 *
 * @author kuuga
 * @since 2021/7/15
 */
@Data
public class FileEntry {

    private boolean isDir;

    private long updateTimestamp;

    private Date updateTime;

    private String name;

    private long size;

    private String longName;

    public void setUpdateTimestamp(long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
        if (updateTimestamp > 0) {
            this.updateTime = DateUtil.parseTimestamp(updateTimestamp, TimestampType.SECOND);
        }
    }

    public String getUpdateTimeStr() {
        return DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, updateTime);
    }

    public String getSizeStr() {
        if (size > 0) {
            return ByteSizeConvert.model().format(size);
        }
        return null;
    }

}
