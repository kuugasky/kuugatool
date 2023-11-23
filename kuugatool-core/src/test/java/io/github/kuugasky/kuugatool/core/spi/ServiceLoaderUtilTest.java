package io.github.kuugasky.kuugatool.core.spi;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ServiceLoader;

class ServiceLoaderUtilTest {

    @Before
    void before() {

    }

    @Test
    void loadFirstAvailable() {
        KuugaModel kuugaModel = SpiServiceLoaderUtil.loadFirstAvailable(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void loadFirst() {
        KuugaModel kuugaModel = SpiServiceLoaderUtil.loadFirst(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void load() {
        // 服务加载器
        ServiceLoader<KuugaModel> serviceLoader = SpiServiceLoaderUtil.load(KuugaModel.class);
        System.out.println(StringUtil.formatString(serviceLoader));
    }

    @Test
    void testLoad() {
        ServiceLoader<KuugaModel> serviceLoader = SpiServiceLoaderUtil.load(KuugaModel.class, ClassLoader.getSystemClassLoader());
        System.out.println(StringUtil.formatString(serviceLoader));
        ServiceLoader<KuugaModel> serviceLoader1 = SpiServiceLoaderUtil.load(KuugaModel.class, ClassLoader.getPlatformClassLoader());
        System.out.println(StringUtil.formatString(serviceLoader1));
    }

    @Test
    void loadList() {
        List<KuugaModel> kuugaModels = SpiServiceLoaderUtil.loadList(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModels));
    }

    @Test
    void testLoadList() {
        List<KuugaModel> kuugaModels = SpiServiceLoaderUtil.loadList(KuugaModel.class, ClassLoader.getSystemClassLoader());
        System.out.println(StringUtil.formatString(kuugaModels));
    }

}