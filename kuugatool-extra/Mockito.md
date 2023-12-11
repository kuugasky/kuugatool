# Mockito

## 什么是 Mockito

> Mockito 是一个强大的用于 Java 开发的模拟测试框架, 通过 Mockito 我们可以创建和配置 Mock 对象, 进而简化有外部依赖的类的测试.

## 使用 Mockito流程

- 创建外部依赖的 Mock 对象, 然后将此 Mock 对象注入到测试类中.
- 执行测试代码.
- 校验测试代码是否执行正确.

## 为什么使用 Mockito

当我们需要测试 BankService 服务时, 该真么办呢?

一种方法是构建真实的 BankDao, DB, AccountService 和 AuthService 实例, 然后注入到 BankService 中.

不用我说, 读者们也肯定明白, 这是一种既笨重又繁琐的方法, 完全不符合单元测试的精神. 那么还有一种更加优雅的方法吗? 自然是有的,
那就是我们今天的主角 Mock Object. 下面来看一下使用 Mock 对象后的框架图:

我们看到, BankDao, AccountService 和 AuthService 都被我们使用了虚拟的对象(Mock 对象) 来替换了, 因此我们就可以对
BankService 进行测试, 而不需要关注它的复杂的依赖了.

## Mockito 基本使用

### Maven 依赖

```xml

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.0.111-beta</version>
</dependency>
```

### 创建 Mock 对象

```java
@Test
public void createMockObject(){
        // 使用 mock 静态方法创建 Mock 对象.
        List mockedList=mock(List.class);
        Assert.assertTrue(mockedList instanceof List);

        // mock 方法不仅可以 Mock 接口类, 还可以 Mock 具体的类型.
        ArrayList mockedArrayList=mock(ArrayList.class);
        Assert.assertTrue(mockedArrayList instanceof List);
        Assert.assertTrue(mockedArrayList instanceof ArrayList);
        }
```

## 常用方法

| 方法名                                                                                                                     | 描述                          |
|-------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| Mockito.mock(classToMock)                                                                                               | 模拟对象                        |
| Mockito.mock(classToMock, defaultAnswer)                                                                                | 使用默认Answer模拟对象              |
| Mockito.verify(mock)                                                                                                    | 验证行为是否发生                    |
| Mockito.when(methodCall).thenReturn(value)                                                                              | 设置方法预期返回值                   |
| Mockito.when(methodCall).thenReturn(value1).thenReturn(value2) //等价于Mockito.when(methodCall).thenReturn(value1, value2) | 触发时第一次返回value1，第n次都返回value2 |
| Mockito.when(methodCall).thenAnswer(answer))                                                                            | 预期回调接口生成期望值                 |
| Mockito.doThrow(toBeThrown).when(mock).[method]                                                                         | 模拟抛出异常。                     |
| Mockito.doReturn(toBeReturned).when(mock).[method]                                                                      | 设置方法预期返回值（直接执行不判断）          |
| Mockito.doAnswer(answer).when(methodCall).[method]                                                                      | 预期回调接口生成期望值（直接执行不判断）        |
| Mockito.doNothing().when(mock).[method]                                                                                 | 不做任何返回                      |
| Mockito.doCallRealMethod().when(mock).[method] //等价于Mockito.when(mock.[method] ).thenCallRealMethod();                  | 调用真实的方法                     |
| Mockito.spy(Object)                                                                                                     | 用spy监控真实对象,设置真实对象行为         |
| Mockito.inOrder(Object… mocks)                                                                                          | 创建顺序验证器                     |
| Mockito.reset(mock)                                                                                                     | 重置mock                      |