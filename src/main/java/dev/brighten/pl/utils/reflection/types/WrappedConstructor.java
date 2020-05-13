package dev.brighten.pl.utils.reflection.types;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class WrappedConstructor {

    private Constructor constructor;

    public WrappedConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    @SneakyThrows
    public <T> T newInstance(Object... args) {
        return (T) constructor.newInstance(args);
    }
}
