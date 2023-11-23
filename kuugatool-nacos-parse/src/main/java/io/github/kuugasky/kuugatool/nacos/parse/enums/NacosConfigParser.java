package io.github.kuugasky.kuugatool.nacos.parse.enums;

import com.alibaba.nacos.api.exception.NacosException;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.concurrent.SleuthThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.BiConsumer;

/**
 * NacosConfig解析器
 * <p>
 * 继承{@link AbstractNacosConfigParser},重写读取到nacos配置文件后的后续操作
 *
 * @author 彭清龙
 * @since 2022/02/16
 */
@Slf4j
public class NacosConfigParser<R> extends AbstractNacosConfigParser {

    /**
     * 自定义解析函数方法
     */
    private final BiConsumer<String, R> biConsumer;
    /**
     * 自定义泛型结果对象，用于接收自定义biConsumer.accept()结果集
     */
    private final R result;

    /**
     * 初始化是否完成 默认false
     */
    private final boolean initComplete;

    /**
     * 用于延迟加载数据源缓存
     */
    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(2, () -> log.info("Nacos Config Load And Parse Complete."));

    /**
     * nacos配置加载完成或变更完成后置处理逻辑
     * <p>
     * 加载顺序：
     * - NacosConfigInitParse，第一个CYCLIC_BARRIER.await();
     * - 等待super(dataId, groupId);->NacosConfigParse(dataId, groupId);->initConfig()->initAfter(nacosConfig)->await();
     * - 第一个栅栏完成，读取到了nacos配置，并且执行了this.biConsumer.accept(config, this.result);完成了配置解析
     * <p>
     * - 第二个栅栏开始（以下两步也有可能互调）：
     * - initAfter(String config)，此时initComplete为false，触发
     * - NacosConfigInitParse，第二个CYCLIC_BARRIER.await();触发
     * - 结束
     */
    private NacosConfigParser(String dataId, String groupId, BiConsumer<String, R> biConsumer, R result) throws NacosException, BrokenBarrierException, InterruptedException {
        super(dataId, groupId);
        this.biConsumer = biConsumer;
        this.result = result;

        // 第一次等待biConsumer初始化完成 让initAfter开始执行
        CYCLIC_BARRIER.await();
        // 第二次等待initAfter执行完成后初始化才成功
        CYCLIC_BARRIER.await();

        initComplete = true;
    }

    public static <R> NacosConfigParser<R> start(String dataId, String groupId, BiConsumer<String, R> biConsumer, R result) {
        try {
            return new NacosConfigParser<>(dataId, groupId, biConsumer, result);
        } catch (NacosException | BrokenBarrierException | InterruptedException e) {
            log.error("NacosConfigParser start exception.\n" + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    /**
     * 未初始化完成时需要阻塞等待
     */
    private void await() throws BrokenBarrierException, InterruptedException {
        if (!initComplete) {
            CYCLIC_BARRIER.await();
        }
    }

    @Override
    protected void initAfter(String config) {
        SleuthThreadPool.execute(() -> {
            try {
                // 在biConsumer未初始化完成时阻塞
                await();
                if (ObjectUtil.isNull(this.biConsumer) || ObjectUtil.isNull(this.result)) {
                    return;
                }
                this.biConsumer.accept(config, this.result);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("NacosConfigParser.initAfter error:{}", e.getMessage(), e);
            } finally {
                try {
                    await();
                } catch (Exception e) {
                    log.error("NacosConfigParser.initAfter await error:{}", e.getMessage(), e);
                }
            }
        });
    }

}