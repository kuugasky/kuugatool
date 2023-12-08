package io.github.kuugasky.kuugatool.aop;

import io.github.kuugasky.kuugatool.aop.aspects.TimeIntervalAspect;
import io.github.kuugasky.kuugatool.aop.interceptor.JdkInterceptor;
import io.github.kuugasky.kuugatool.aop.proxy.CglibProxyFactory;
import io.github.kuugasky.kuugatool.aop.proxy.JdkProxyFactory;
import io.github.kuugasky.kuugatool.aop.proxy.SpringCglibProxyFactory;
import io.github.kuugasky.kuugatool.aop.test.entity.Animal;
import io.github.kuugasky.kuugatool.aop.test.entity.Cat;
import io.github.kuugasky.kuugatool.aop.test.entity.Dog;
import io.github.kuugasky.kuugatool.aop.test.entity.TagObj;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpiProxyUtilTest {

    @Test
    void aopTest() {
        // 创建cat类，并反射创建TimeIntervalAspect类，再通过SPI获取到代理实现，以此创建代理对象
        Cat cat = SpiProxyUtil.proxy(new Cat(), TimeIntervalAspect.class);
        // 代理对象调用原对象方法时，会执行TimeIntervalAspect
        String result = cat.eat();
        Assertions.assertEquals("猫吃鱼", result);
        cat.seize();
    }

    @Test
    void aopByAutoCglibTest() {
        Animal dog = SpiProxyUtil.proxy(new Dog(), TimeIntervalAspect.class);
        String result = dog.eat();
        Assertions.assertEquals("狗吃肉", result);
        dog.seize();
    }

    @Test
    void testCGLIBProxy() {
        TagObj target = new TagObj();
        // 目标类设置标记
        target.setTag("tag");

        TagObj proxy = SpiProxyUtil.proxy(target, TimeIntervalAspect.class);
        // 代理类获取标记tag (断言错误)
        Assertions.assertEquals("tag", proxy.getTag());
    }

    @Test
    void testSpringCglibProxy() {
        TagObj target = new TagObj();
        // 目标类设置标记
        target.setTag("tag");

        SpringCglibProxyFactory springCglibProxyFactory = new SpringCglibProxyFactory();
        TagObj proxy = springCglibProxyFactory.proxy(target, TimeIntervalAspect.class);
        Assertions.assertEquals("tag", proxy.getTag());
    }

    /**
     * # 警告信息：
     * WARNING: An illegal reflective access operation has occurred
     * WARNING: Illegal reflective access by net.sf.cglib.core.ReflectUtils$1 (file:/Users/kuuga/.m2/repository/cglib/cglib/3.3.0/cglib-3.3.0.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
     * WARNING: Please consider reporting this to the maintainers of net.sf.cglib.core.ReflectUtils$1
     * WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
     * WARNING: All illegal access operations will be denied in a future release
     * <p>
     * # 原因分析
     * 错误提示：com.google.inject.internal.cglib.core.$ReflectUtils$1访问了java.lang.ClassLoader类。
     * 因为JDK9的新特性模块系统(module system)对jdk中的模块进行了反射检查，对于反射了jdk中的类的操作进行警告。
     * <p>
     * # 处理办法
     * 方法一：降低jdk版本
     * 方法二：开放访问权限
     * JDK9+可以使用启动参数中增加“--add-opens <package>/<class>=ALL-UNNAMED”的方法允许其他模块访问。
     * 在RUN>Edit Configurations>VM options 中增加参数，如：
     * <p>
     * --add-opens java.base/java.lang=ALL-UNNAMED  --add-opens java.base/java.util=ALL-UNNAMED  --add-opens java.desktop/java.awt.font=ALL-UNNAMED
     * </p>
     */
    @Test
    void testCglibProxy() {
        TagObj target = new TagObj();
        // 目标类设置标记
        target.setTag("tag");

        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory();
        TagObj proxy = cglibProxyFactory.proxy(target, TimeIntervalAspect.class);
        Assertions.assertEquals("tag", proxy.getTag());
    }

    @Test
    void testJdkProxy() {
        TagObj target = new TagObj();
        // 目标类设置标记
        target.setTag("tag");

        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory();
        TagObj proxy = jdkProxyFactory.proxy(target, TimeIntervalAspect.class);
        Assertions.assertEquals("tag", proxy.getTag());
    }

    /**
     * jdk代理只能用接口去承接具体服务对象
     */
    @Test
    void testJdkProxy2() {
        Cat animal = new Cat();
        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory();
        Animal proxy = jdkProxyFactory.proxy(animal, TimeIntervalAspect.class);
        Assertions.assertEquals("猫吃鱼", proxy.eat());
    }


    @Test
    void newProxyInstance() {
        TagObj target = new TagObj();
        // 目标类设置标记
        target.setTag("tag");
        TimeIntervalAspect timeIntervalAspect = new TimeIntervalAspect();
        JdkInterceptor invocationHandler = new JdkInterceptor(target, timeIntervalAspect);
        Object o = SpiProxyUtil.newProxyInstance(invocationHandler);
        System.out.println(StringUtil.formatString(o));
    }
}