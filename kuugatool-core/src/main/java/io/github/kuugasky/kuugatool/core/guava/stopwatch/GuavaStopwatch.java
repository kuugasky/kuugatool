package io.github.kuugasky.kuugatool.core.guava.stopwatch;

import com.google.common.base.Stopwatch;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.concurrent.TimeUnit;

/**
 * GuavaStopwatch
 * <p>
 * {@link TimeInterval#TimeInterval()}
 *
 * @author kuuga
 * @since 2022/8/1 17:18
 */
public class GuavaStopwatch {

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100000; i++) {
            // do some thing
            System.out.print(StringUtil.EMPTY);
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("逻辑代码运行耗时：" + nanos);

        stopwatch.reset();
        stopwatch.start();
        ThreadUtil.sleep(1000);
        System.out.println("逻辑代码运行耗时：" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println("逻辑代码运行耗时：" + stopwatch.elapsed(TimeUnit.SECONDS));
        System.out.println("逻辑代码运行耗时：" + stopwatch.elapsed());
    }

}
