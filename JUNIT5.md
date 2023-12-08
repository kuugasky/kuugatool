# JUnit5

## JUnit5注解

| 注解                 | 	说明                                                                                                  |
|--------------------|------------------------------------------------------------------------------------------------------|
| @Test              | 	表示方法是测试方法。与JUnit 4的@Test注释不同，这个注释不声明任何属性，因为JUnit Jupiter中的测试扩展基于它们自己的专用注释进行操作。                      |
| @ParameterizedTest | 	表示方法是参数化测试。                                                                                         |
| @RepeatedTest      | 	表示方法是重复测试的测试模板。                                                                                     |
| @TestFactory       | 	表示方法是动态测试的测试工厂。                                                                                     |
| @TestInstance      | 	用于为带注释的测试类配置测试实例生命周期。                                                                               |
| @TestTemplate      | 	表示方法是为测试用例设计的模板，根据注册提供程序返回的调用上下文的数量进行多次调用。                                                          |
| @DisplayName       | 	声明测试类或测试方法的自定义显示名称。                                                                                 |
| @BeforeEach        | 	表示在当前类中每个@Test、@RepeatedTest、@ParameterizedTest或@TestFactory方法之前执行注释的方法；类似于JUnit 4的@Before。         |
| @AfterEach         | 	表示在当前类中的每个@Test、@RepeatedTest、@ParameterizedTest或@TestFactory方法之后，都应该执行带注释的方法；类似于JUnit 4的@After。    |
| @BeforeAll         | 	表示应在当前类中的所有@Test、@RepeatedTest、@ParameterizedTest和@TestFactory方法之前执行带注释的方法；类似于JUnit 4的@BeforeClass。 |
| @AfterAll          | 	表示在当前类中，所有@Test、@RepeatedTest、@ParameterizedTest和@TestFactory方法都应该执行注释的方法；类似于JUnit 4的@AfterClass。   |
| @Nested            | 	表示带注释的类是一个嵌套的、非静态的测试类。@BeforeAll和@AfterAll方法不能直接在 @Nested 测试类中使用，除非使用“每个类”测试实例生命周期。                 |
| @Tag               | 	用于在类或方法级别声明过滤测试的标记；类似于TestNG中的测试组或JUnit 4中的类别。                                                      |
| @Disabled          | 	用于禁用测试类或测试方法；类似于JUnit 4的@Ignore。                                                                    |
| @ExtendWith        | 	用于注册自定义扩展。                                                                                          |

## JUnit5与JUnit4注释比较

| 特征                | 	JUNIT4       | 	JUNIT5            |
|-------------------|---------------|--------------------|
| 声明一种测试方法。         | 	@Test        | 	@Test             |
| 在当前类中的所有测试方法之前执行。 | 	@BeforeClass | 	@BeforeAll        |
| 在当前类中的所有测试方法之后执行。 | 	@AfterClass  | 	@AfterAll         |
| 在每个测试方法之前执行。      | 	@Before      | 	@BeforeEach       |
| 每种测试方法后执行。        | 	@After       | 	@AfterEach        |
| 禁用测试方法            | /类。           | 	@Ignore	@Disabled |
| 测试工厂进行动态测试。       | 	NA           | 	@TestFactory      |
| 嵌套测试。             | 	NA           | 	@Nested           |
| 标记和过滤。            | 	@Category    | 	@Tag              |
| 注册自定义扩展。          | 	NA           | 	@ExtendWith       |

## JUnit5断言（Assertions类）

> JUnit Jupiter附带了许多JUnit 4拥有的断言方法，并添加了一些可以很好地用于Java 8 lambdas的断言方法。
> 所有JUnit5断言都是 org.junit.jupiter.api.Assertions 中的静态方法断言类。

Asser类中主要方法如下：

| 方法名称             | 	方法描述                   |
|------------------|-------------------------|
| assertEquals     | 	断言传入的预期值与实际值是相等的。      |
| assertNotEquals  | 	断言传入的预期值与实际值是不相等的。     |
| assertArayEquals | 	断言传入的预期数组与实际数组是相等的。    |
| assertNull       | 	断言传入的对象是为空。            |
| assertNotNull    | 	断言传入的对象是不为空。           |
| assertTrue       | 	断言条件为真。                |
| assertFalse      | 	断言条件为假。                |
| assertSame       | 	断言两个对象引用同一个对象，相当于"==”。 |
| assertNotSame    | 	断言两个对象引用不同的对象，相当于"!=”。 |
| assertThat       | 	断言实际值是否满足指定的条件。        |