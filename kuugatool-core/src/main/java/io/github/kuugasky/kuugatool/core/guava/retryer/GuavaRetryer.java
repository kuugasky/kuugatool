package io.github.kuugasky.kuugatool.core.guava.retryer;

import com.github.rholder.retry.*;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * GuavaRetryer
 * <p>
 * https://juejin.cn/post/6974202216768864264
 *
 * @author kuuga
 * @since 2022/8/1 16:39
 */
public class GuavaRetryer {

    public static void main(String[] args) throws Exception {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Objects::isNull)    // 设置自定义段元重试源
                .retryIfExceptionOfType(Exception.class)        // 设置异常重试源
                .retryIfRuntimeException()                      // 设置异常重试源
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))   // 设置重试次数    设置重试超时时间？？？？
                .withWaitStrategy(WaitStrategies.fixedWait(5L, TimeUnit.SECONDS)) // 设置每次重试间隔
                .build();

        Callable<Boolean> task = new Callable<>() {
            int i = 0;

            @Override
            public Boolean call() throws Exception {
                i++;
                System.out.println(StringUtil.format("第{}次执行！", i));
                if (i < 3) {
                    System.out.println("模拟执行失败");
                    throw new IOException("异常");
                }
                return true;
            }
        };

        try {
            retryer.call(task);
        } catch (ExecutionException | RetryException e) {
            System.out.println("error:" + e.getMessage());
        }

        Boolean result = task.call();
        System.out.println("成功输出结果:" + result);
    }

}
