package io.github.kuugasky.kuugatool.thirdparty.request.context.config;

/**
 * 第三方配置类
 * <p>
 * 第三方配置类需要实现该接口，用于提取第三方API请求URL前缀
 *
 * @author pengqinglong
 * @since 2022/3/14
 */
public interface RequestConfig {

    /**
     * 当前第三方请求URL前缀
     *
     * @return the url
     */
    String getUrl();

}