package io.github.kuugasky.kuugatool.core.spi;

import io.github.kuugasky.kuugatool.core.annotations.Remark;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ServiceLoader;

/**
 * SpiTest
 *
 * @author kuuga
 * @since 2022-05-17 17:08:48
 */
public class SpiTest {

    @Test
    @Remark(remark = "从JRT ServiceLoader服务加载中，使用类加载读取该class对应的ServiceLoad，迭代获取第一个可用到服务对象")
    void loadFirstAvailable() {
        KuugaInterface kuugaInterface = SpiServiceLoaderUtil.loadFirstAvailable(KuugaInterface.class);
        if (ObjectUtil.nonNull(kuugaInterface)) {
            System.out.println(kuugaInterface.test());
        }
    }

    @Test
    @Remark(remark = "从JRT ServiceLoader服务加载中，使用类加载读取该class对应的ServiceLoad，加载第一个服务，如果用户定义了多个接口实现类，只获取第一个。")
    void loadFirst() {
        KuugaInterface kuugaInterface = SpiServiceLoaderUtil.loadFirst(KuugaInterface.class);
        if (ObjectUtil.nonNull(kuugaInterface)) {
            System.out.println(kuugaInterface.test());
        }
    }

    @Test
    @Remark(remark = "从JRT中指定系统类加载器读取服务对象到ServiceLoader")
    void loadAndClassLoader() {
        ServiceLoader<KuugaInterface> serviceLoader = SpiServiceLoaderUtil.load(KuugaInterface.class, ClassLoader.getSystemClassLoader());
        for (KuugaInterface kuugaInterface : serviceLoader) {
            System.out.println(kuugaInterface.test());
        }
    }

    @Test
    @Remark(remark = "从JRT ServiceLoader服务加载中，使用类加载读取该class对应的ServiceLoad，加载多个服务")
    void loadList() {
        List<KuugaInterface> kuugaInterfaces = SpiServiceLoaderUtil.loadList(KuugaInterface.class);
        Console.redLog(kuugaInterfaces.size());
        ListUtil.optimize(kuugaInterfaces).forEach(kuugaInterface -> System.out.println(StringUtil.formatString(kuugaInterface.test())));
    }

    @Test
    @Remark(remark = "从JRT ServiceLoader服务加载中，使用指定类加载读取该class对应的ServiceLoad，加载多个服务")
    void loadListAndClassLoader() {
        List<KuugaInterface> kuugaInterfaces = SpiServiceLoaderUtil.loadList(KuugaInterface.class, ClassLoader.getPlatformClassLoader());
        Console.redLog(kuugaInterfaces.size());
        ListUtil.optimize(kuugaInterfaces).forEach(kuugaInterface -> System.out.println(StringUtil.formatString(kuugaInterface.test())));
    }

}
