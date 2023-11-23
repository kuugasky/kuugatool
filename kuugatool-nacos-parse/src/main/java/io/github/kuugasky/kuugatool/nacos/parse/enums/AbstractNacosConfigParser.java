package io.github.kuugasky.kuugatool.nacos.parse.enums;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * AbstractNacosConfigParse
 *
 * @author pengqinglong
 * @since 2022/2/16
 */
@Slf4j
public abstract class AbstractNacosConfigParser {

    /**
     * 原始配置数据 缓存原始数据 子类可以读取
     */
    protected String nacosConfig;

    /**
     * nacos配置数据源
     */
    private static ConfigService CONFIG_SERVICE;
    /**
     * Spring cloud nacos配置服务器地址
     */
    private static final String SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR = "spring.cloud.nacos.config.server-addr";
    /**
     * Spring cloud nacos配置命名空间
     */
    private static final String SPRING_CLOUD_NACOS_CONFIG_NAMESPACE = "spring.cloud.nacos.config.namespace";

    static {
        /*
         * 类第一次被加载进jvm时触发
         * 初始化nacos配置数据源
         */
        try {
            // 获取上下文
            AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
            // 获取配置环境
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            // 配置nacos地址和命名空间
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, environment.getProperty(SPRING_CLOUD_NACOS_CONFIG_SERVER_ADDR));
            properties.setProperty(PropertyKeyConst.NAMESPACE, environment.getProperty(SPRING_CLOUD_NACOS_CONFIG_NAMESPACE));
            // 创建nacos配置服务
            CONFIG_SERVICE = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nacos配置解析，监听配置变更
     *
     * @param dataId  dataId
     * @param groupId groupId
     * @throws NacosException Nacos异常
     */
    public AbstractNacosConfigParser(String dataId, String groupId) throws NacosException {
        // 初始化nacos配置
        this.initConfig(dataId, groupId);

        // nacos配置添加监听
        CONFIG_SERVICE.addListener(dataId, groupId, new Listener() {
            /**
             * 获取执行器
             *
             * @return Executor
             */
            @Override
            public Executor getExecutor() {
                return null;
            }

            /**
             * 配置信息变更时触发
             * @param configInfo config info
             */
            @Override
            public void receiveConfigInfo(String configInfo) {
                // 重新初始化配置，并回调给子类去处理
                initConfig(dataId, groupId);
            }
        });
    }

    /**
     * 初始化原始数据
     *
     * @param dataId  dataId
     * @param groupId groupId
     */
    public final void initConfig(String dataId, String groupId) {
        try {
            // 读取nacos配置信息
            nacosConfig = CONFIG_SERVICE.getConfig(dataId, groupId, 10000);
            // 模版方法模式 子类实现
            this.initAfter(nacosConfig);
        } catch (NacosException e) {
            log.error("nacos获取配置异常", e);
        }
    }

    /**
     * 获取到config的后置操作 钩子函数 丢给子类实现
     * (模版方法模式)
     *
     * @param config config
     */
    protected abstract void initAfter(String config);

}