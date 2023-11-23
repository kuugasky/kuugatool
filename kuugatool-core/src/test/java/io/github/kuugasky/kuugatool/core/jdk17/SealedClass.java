package io.github.kuugasky.kuugatool.core.jdk17;

/**
 * SealedClass
 * sealed:
 * 封闭类（预览特性），可以是封闭类和或者封闭接口，用来增强 Java 编程语言，防止其他类或接口扩展或实现它们。
 * <p>
 * 有了这个特性，意味着以后不是你想继承就继承，想实现就实现了，你得经过允许才行。
 * 类 SealedClass 被 sealed 修饰，说明它是一个封闭类，并且只允许指定的 3 个子类继承。
 *
 * @author kuuga
 * @since 2022/8/8 12:08
 */
public abstract sealed class SealedClass permits ZhangSan, LiSi, ZhaoLiu {


}

final class ZhangSan extends SealedClass {

}

final class LiSi extends SealedClass {

}

final class ZhaoLiu extends SealedClass {

}
