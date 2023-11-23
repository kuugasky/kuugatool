package io.github.kuugasky.design;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class SingletonUtilTest {

    @Test
    void testGetSingletonInstance() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Singleton singletonInstance = SingletonUtil.getSingletonInstance(Singleton.class, () -> new Singleton());
        // System.out.println(singletonInstance);
        // Singleton singleton = new Singleton();
        // System.out.println(singleton.hashCode() == singletonInstance.hashCode());
    }

    static class Singleton {
        private Singleton() {
        }

        @Setter
        @Getter
        private String name;
    }

}