package io.github.kuugasky.kuugatool.core.guava.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

class RateLimiterUtilTest {

    @Test
    void create() {
        // 每秒5个令牌
        // 创建一个具有指定的稳定吞吐量的{@code RateLimiter}，给出为“每秒许可”(通常称为<i>QPS<i>，每秒查询)。
        RateLimiter rateLimiter = RateLimiter.create(5);
        while (true) {
            // 平均每个0.2秒左右，很均匀
            System.out.println("time:" + rateLimiter.acquire() + "s");
        }
    }

    @Test
    void create1() throws InterruptedException {
        // 每秒5个令牌
        RateLimiter rateLimiter = RateLimiter.create(5);
        while (true) {
            System.out.println("time:" + rateLimiter.acquire() + "s");
            // 线程休眠，给足够的时间产生令牌
            Thread.sleep(1000);
        }
    }

    /**
     * 由于令牌可以积累，所以我一次可以取多个令牌，只要令牌充足，可以快速响应
     */
    @Test
    void create2() {
        // 每秒5个令牌
        RateLimiter rateLimiter = RateLimiter.create(5);
        while (true) {
            // 一次取出5个令牌也可以快速响应
            System.out.println("time:" + rateLimiter.acquire(5) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println(StringUtil.repeatNormal());
        }
    }

    /**
     * 平滑预热限流
     * 平滑预热限流带有预热期的平滑限流，它启动后会有一段预热期，逐步将令牌产生的频率提升到配置的速率
     * 这种方式适用于系统启动后需要一段时间来进行预热的场景
     * 比如，我设置的是每秒5个令牌，预热期为5秒，那么它就不会是0.2左右产生一个令牌。在前5秒钟它不是一个均匀的速率，5秒后恢复均匀的速率
     */
    @Test
    void create3() {
        // 每秒5个令牌，预热期为5秒
        RateLimiter rateLimiter = RateLimiter.create(5, 5, TimeUnit.SECONDS);
        while (true) {
            System.out.println(DateUtil.todayTime());
            // 一次取出5个令牌也可以快速响应
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println("time:" + rateLimiter.acquire(1) + "s");
            System.out.println(DateUtil.todayTime());
            System.out.println("-----------");
        }
    }

    /**
     * 场景一：举例来说明如何使用RateLimiter，想象下我们需要处理一个任务列表，但我们不希望每秒的任务提交超过两个：
     */
    @Test
    void scenario1() {
        // 速率是每秒两个许可
        final RateLimiter rateLimiter = RateLimiter.create(2.0);

        List<Integer> tasks = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            tasks.add(i + 1);
        }

        for (Integer i : tasks) {
            rateLimiter.acquire(); // 也许需要等待
            System.out.println(DateUtil.todayTime() + String.format(" executor.execute(%s);", i));
        }
    }

    /**
     * 场景二：再举另外一个例子，想象下我们制造了一个数据流，并希望以每秒5kb的速率处理它。
     * 可以通过要求每个字节代表一个许可，然后指定每秒5000个许可来完成
     */
    @Test
    void scenario2() throws IOException {
        // 每秒5000个许可
        final RateLimiter rateLimiter = RateLimiter.create(5000.0);

        String fileContent = FileUtil.readFileToString(FileUtil.file("/log/kfang/services/agent/service-agent-house/bootstrap.log"));

        byte[] packet = fileContent.getBytes();
        System.out.println(packet.length);
        rateLimiter.acquire(packet.length);

        System.out.println(DateUtil.todayTime() + " networkService.send(packet);");
    }


}