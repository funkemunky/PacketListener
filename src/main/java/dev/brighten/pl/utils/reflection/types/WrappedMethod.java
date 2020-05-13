package dev.brighten.pl.utils.reflection.types;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class WrappedMethod {
    @Getter
    private final Method method;

    public WrappedMethod(Method method) {
        this.method = method;
        method.setAccessible(true);
    }

    @SneakyThrows
    public <T> T invoke(Object instance, Object... params) {
        return (T) method.invoke(instance, params);
    }
}
