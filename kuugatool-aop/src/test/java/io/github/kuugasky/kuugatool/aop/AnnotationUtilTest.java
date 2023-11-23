package io.github.kuugasky.kuugatool.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

class AnnotationUtilTest {

    @Test
    void getDeclaredAnnotation(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getDeclaredAnnotation(joinPoint, clazz);
    }

    @Test
    void getAnnotationOfMethod(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getAnnotationOfMethod(joinPoint, clazz);
    }

    @Test
    void testGetAnnotationOfClass(JoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getAnnotationOfClass(joinPoint, clazz);
    }

    @Test
    void testGetDeclaredAnnotation(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getDeclaredAnnotation(joinPoint, clazz);
    }

    @Test
    void testGetAnnotationOfMethod(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getAnnotationOfMethod(joinPoint, clazz);
    }

    @Test
    void getAnnotationOfClass(ProceedingJoinPoint joinPoint, Class<? extends Annotation> clazz) {
        AnnotationUtil.getAnnotationOfClass(joinPoint, clazz);
    }

}