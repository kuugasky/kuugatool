package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * HighConcurrentResult
 *
 * @author kuuga
 * @since 2022/11/1-11-01 20:00
 */
@Builder
@Getter
public class HighConcurrentResult {

    /**
     * 时间间隔对象
     */
    private TimeInterval timeInterval;
    /**
     * 时间间隔
     */
    private long interval;
    /**
     * 总任务数
     */
    private long taskCount;
    /**
     * 成功任务数
     */
    private long taskSuccessCount;
    /**
     * 失败任务数
     */
    private long taskErrorCount;

    private List<String> errorMessages;

}
