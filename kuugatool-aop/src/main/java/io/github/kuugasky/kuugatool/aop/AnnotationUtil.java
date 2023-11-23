package io.github.kuugasky.kuugatool.aop;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 注解工具类
 *
 * @author kuuga
 * @since 2021-12-22 11:37:28
 */
public class AnnotationUtil {

    // 常规模式 ===============================================================================================================

    /**
     * 先从切入点的方法上尝试读取声明的注解，如果读取不到，则从类上读取
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @return 注解
     */
    public static <T extends Annotation> T getDeclaredAnnotation(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        Annotation annotation;
        annotation = getAnnotationOfMethod(joinPoint, clazz);
        if (ObjectUtil.isNull(annotation)) {
            annotation = getAnnotationOfClass(joinPoint, clazz);
        }
        return ObjectUtil.cast(annotation);
    }

    /**
     * 从切入点读取方法上声明的注解
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @param <T>       T
     * @return 注解
     */
    @SuppressWarnings("all")
    public static <T extends Annotation> T getAnnotationOfMethod(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        method.isAnnotationPresent(clazz);
        return ObjectUtil.cast(method.getAnnotation(clazz));
    }

    /**
     * 从切入点读取类上声明的注解
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @param <T>       T
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotationOfClass(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Annotation classAnnotation = AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(), clazz);
        return ObjectUtil.cast(classAnnotation);
    }

    // around环绕模式 ===============================================================================================================

    /**
     * 先从切入点的方法上尝试读取声明的注解，如果读取不到，则从类上读取（环绕方式）
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @return 注解
     */
    public static <T extends Annotation> T getDeclaredAnnotation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        Annotation annotationOfMethod = getAnnotationOfMethod(joinPoint, clazz);
        if (ObjectUtil.isNull(annotationOfMethod)) {
            return getAnnotationOfClass(joinPoint, clazz);
        }
        return ObjectUtil.cast(annotationOfMethod);
    }

    /**
     * 从切入点读取方法上声明的注解（环绕方式）
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @param <T>       T
     * @return 注解
     */
    @SuppressWarnings("all")
    public static <T extends Annotation> T getAnnotationOfMethod(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        method.isAnnotationPresent(clazz);
        return ObjectUtil.cast(method.getAnnotation(clazz));
    }

    /**
     * 从切入点读取类上声明的注解（环绕方式）
     *
     * @param joinPoint 切入点
     * @param clazz     注解类
     * @param <T>       T
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotationOfClass(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Annotation classAnnotation = AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(), clazz);
        return ObjectUtil.cast(classAnnotation);
    }

    // =====================================================================================================================

    /**
     * 提取tClass的annotationClazz注解
     *
     * @param tClass          tCLass
     * @param annotationClazz annotationClazz
     * @return Annotation
     */
    public static <A extends Annotation, T> A getAnnotation(Class<T> tClass, Class<A> annotationClazz) {
        return tClass.getAnnotation(annotationClazz);
    }

}
