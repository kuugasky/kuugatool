# 什么是SPI

> SPI ，全称为 Service Provider Interface，是一种服务发现机制。它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类。

> 这一机制为很多框架扩展提供了可能，比如在Dubbo、JDBC中都使用到了SPI机制。我们先通过一个很简单的例子来看下它是怎么用的。

# 源码分析

## ServiceLoader

> 首先，我们先来了解下ServiceLoader，看看它的类结构。

```java
public final class ServiceLoader<S> implements Iterable<S> {
    //配置文件的路径
        private static final String PREFIX = "META-INF/services/";
        //加载的服务类或接口
        private final Class<S> service;
        //已加载的服务类集合
        private LinkedHashMap<String, S> providers = new LinkedHashMap<>();
        //类加载器
        private final ClassLoader loader;
        //内部类，真正加载服务类
        private LazyIterator lookupIterator;
}
```

## Load

> load方法创建了一些属性，重要的是实例化了内部类，LazyIterator。最后返回ServiceLoader的实例。

```java
public final class ServiceLoader<S> implements Iterable<S> {

    private ServiceLoader(Class<S> svc, ClassLoader cl) {
            //要加载的接口
            service = Objects.requireNonNull(svc, "Service interface cannot be null");
            //类加载器
            loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
            //访问控制器 JDK17已删除SecurityManager
            acc = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
            //先清空
            providers.clear();
            //实例化内部类 
            LazyIterator lookupIterator = new LazyIterator(service, loader);
        }
}
```

# 查找实现类

> 查找实现类和创建实现类的过程，都在LazyIterator完成。当我们调用iterator.hasNext和iterator.next方法的时候，实际上调用的都是LazyIterator的相应方法。

> 所以，我们重点关注lookupIterator.hasNext()方法，它最终会调用到hasNextService。

```java
private class LazyIterator implements Iterator<S> {
    Class<S> service;
    ClassLoader loader;
    Enumeration<URL> configs = null;
    Iterator<String> pending = null;
    String nextName = null;

    private boolean hasNextService() {
        //第二次调用的时候，已经解析完成了，直接返回
        if (nextName != null) {
            return true;
        }
        if (configs == null) {
            //META-INF/services/ 加上接口的全限定类名，就是文件服务类的文件
            //META-INF/services/com.viewscenes.netsupervisor.spi.SPIService
            String fullName = PREFIX + service.getName();
            //将文件路径转成URL对象
            configs = loader.getResources(fullName);
        }
        while ((pending == null) || !pending.hasNext()) {
            //解析URL文件对象，读取内容，最后返回
            pending = parse(service, configs.nextElement());
        }
        //拿到第一个实现类的类名
        nextName = pending.next();
        return true;
    }
}
```

## 创建实例

> 当然，调用next方法的时候，实际调用到的是，lookupIterator.nextService。它通过反射的方式，创建实现类的实例并返回。

```java
private class LazyIterator implements Iterator<S> {
    private S nextService() {
        //全限定类名
        String cn = nextName;
        nextName = null;
        //创建类的Class对象
        Class<?> c = Class.forName(cn, false, loader);
        //通过newInstance实例化
        S p = service.cast(c.newInstance());
        //放入集合，返回实例
        providers.put(cn, p);
        return p;
    }
}
```

看到这儿，我想已经很清楚了。获取到类的实例，我们自然就可以对它为所欲为了！

# JDBC中的应用

我们开头说，SPI机制为很多框架的扩展提供了可能，其实JDBC就应用到了这一机制。回忆一下JDBC获取数据库连接的过程。在早期版本中，需要先设置数据库驱动的连接，再通过DriverManager.getConnection获取一个Connection。

```java
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///consult?serverTimezone=UTC";
        String user = "root";
        String password = "root";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
    }
}
```

在较新版本中(具体哪个版本，笔者没有验证)，设置数据库驱动连接，这一步骤就不再需要，那么它是怎么分辨是哪种数据库的呢？答案就在SPI。

## 加载

我们把目光回到DriverManager类，它在静态代码块里面做了一件比较重要的事。很明显，它已经通过SPI机制， 把数据库驱动连接初始化了。

```java
public class DriverManager {
    static {
        loadInitialDrivers();
        println("JDBC DriverManager initialized");
    }
}
```

具体过程还得看loadInitialDrivers，它在里面查找的是Driver接口的服务类，所以它的文件路径就是：META-INF/services/java.sql.Driver。

```java
public class DriverManager {
    private static void loadInitialDrivers() {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
//很明显，它要加载Driver接口的服务类，Driver接口的包为:java.sql.Driver
//所以它要找的就是META-INF/services/java.sql.Driver文件
                ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
                Iterator<Driver> driversIterator = loadedDrivers.iterator();
                try {
//查到之后创建对象
                    while (driversIterator.hasNext()) {
                        driversIterator.next();
                    }
                } catch (Throwable t) {
// Do nothing
                }
                return null;
            }
        });
    }
}
```

那么，这个文件哪里有呢？我们来看MySQL的jar包，就是这个文件，文件内容为：com.mysql.cj.jdbc.Driver。 MySQL SPI文件

## 创建实例

上一步已经找到了MySQL中的com.mysql.cj.jdbc.Driver全限定类名，当调用next方法时，就会创建这个类的实例。它就完成了一件事，向DriverManager注册自身的实例。

```java
public class Driver extends NonRegisteringDriver implements java.sql.Driver {
    static {
        try {
//注册
//调用DriverManager类的注册方法
//往registeredDrivers集合中加入实例
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    public Driver() throws SQLException {
// Required for Class.forName().newInstance()
    }
}
```

## 创建Connection

在DriverManager.getConnection()方法就是创建连接的地方，它通过循环已注册的数据库驱动程序，调用其connect方法，获取连接并返回。

```java
public class Main {
    private static Connection getConnection(String url, java.util.Properties info, Class<?> caller) {
        // registeredDrivers中就包含com.mysql.cj.jdbc.Driver实例
        for (DriverInfo aDriver : registeredDrivers) {
            if (isDriverAllowed(aDriver.driver, callerCL)) {
                try {
                    // 调用connect方法创建连接
                    Connection con = aDriver.driver.connect(url, info);
                    if (con != null) {
                        return (con);
                    }
                } catch (SQLException ex) {
                    if (reason == null) {
                        reason = ex;
                    }
                }
            } else {
                println("skipping: " + aDriver.getClass().getName());
            }
        }
    }
}
```

## 再扩展

既然我们知道JDBC是这样创建数据库连接的，我们能不能再扩展一下呢？如果我们自己也创建一个java.sql.Driver文件，自定义实现类MyDriver，那么，在获取连接的前后就可以动态修改一些信息。

还是先在项目ClassPath下创建文件，文件内容为自定义驱动类com.viewscenes.netsupervisor.spi.MyDriver

自定义数据库驱动程序
我们的MyDriver实现类，继承自MySQL中的NonRegisteringDriver，还要实现java.sql.Driver接口。这样，在调用connect方法的时候，就会调用到此类，但实际创建的过程还靠MySQL完成。

```java
package com.viewscenes.netsupervisor.spi;

public class MyDriver extends NonRegisteringDriver implements Driver {
    static {
        try {
            java.sql.DriverManager.registerDriver(new MyDriver());
        } catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    public MyDriver() throws SQLException {
    }

    public Connection connect(String url, Properties info) throws SQLException {
        System.out.println("准备创建数据库连接.url:" + url);
        System.out.println("JDBC配置信息：" + info);
        info.setProperty("user", "root");
        Connection connection = super.connect(url, info);
        System.out.println("数据库连接创建完成!" + connection.toString());
        return connection;
    }
}
```

--------------------输出结果---------------------

```
准备创建数据库连接.url:jdbc:mysql:///consult?serverTimezone=UTC
JDBC配置信息：{user=root, password=root}
数据库连接创建完成!com.mysql.cj.jdbc.ConnectionImpl@7cf10a6f
```

作者：清幽之地 链接：https://www.jianshu.com/p/3a3edbcd8f24
来源：简书 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
