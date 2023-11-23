package io.github.kuugasky.kuugatool.core.spi;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import junit.framework.TestCase;

import java.util.List;
import java.util.ServiceLoader;

public class SpiServiceLoaderUtilTest extends TestCase {

    public void testLoadFirstAvailable() {
        ISpi iSpi = SpiServiceLoaderUtil.loadFirstAvailable(ISpi.class);
        System.out.println(StringUtil.formatString(iSpi));
        assert iSpi != null;
        iSpi.test();
    }

    public void testLoadFirst() {
        KuugaInterface iSpi = SpiServiceLoaderUtil.loadFirstAvailable(KuugaInterface.class);
        System.out.println(StringUtil.formatString(iSpi));
        assert iSpi != null;
        System.out.println(iSpi.test());
    }

    public void testLoad() {
        ServiceLoader<KuugaInterface> serviceLoader = SpiServiceLoaderUtil.load(KuugaInterface.class);
        System.out.println(StringUtil.formatString(serviceLoader));
    }

    public void testTestLoad() {
        // 读取到两个服务加载程序 KuugaSpi和KuugaSpi2
        ServiceLoader<KuugaInterface> serviceLoader = SpiServiceLoaderUtil.load(KuugaInterface.class);
        for (KuugaInterface kuugaInterface : serviceLoader) {
            System.out.println("服务加载程序：" + StringUtil.formatString(kuugaInterface));

            // KuugaSpi和KuugaSpi2 的类加载器是一样的
            ClassLoader classLoader = kuugaInterface.getClass().getClassLoader();
            System.out.println("类加载器--->" + classLoader.toString());

            // 重新加载KuugaInterface.class，带上实体类的类加载器，依旧可以查出两个，
            ServiceLoader<KuugaInterface> load = SpiServiceLoaderUtil.load(KuugaInterface.class, classLoader);

            for (KuugaInterface anInterface : load) {
                System.out.println("服务加载程序+类加载器：" + StringUtil.formatString(anInterface));
            }

            System.out.println(StringUtil.repeatNormal());
        }
    }

    public void testLoadList() {
        // 加载服务 并以list列表返回
        List<KuugaInterface> kuugaInterfaces = SpiServiceLoaderUtil.loadList(KuugaInterface.class);
        for (KuugaInterface kuugaInterface : kuugaInterfaces) {
            System.out.println(kuugaInterface.toString());
        }
    }

    public void testTestLoadList() {
        ServiceLoader<KuugaInterface> serviceLoader = SpiServiceLoaderUtil.load(KuugaInterface.class);
        // 加载服务 并以list列表返回
        List<KuugaInterface> kuugaInterfaces = SpiServiceLoaderUtil.loadList(KuugaInterface.class, serviceLoader.getClass().getClassLoader());
        for (KuugaInterface kuugaInterface : kuugaInterfaces) {
            System.out.println(kuugaInterface.toString());
        }
    }

}