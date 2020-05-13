package dev.brighten.pl.utils.reflection.types;

import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

@Getter
public class WrappedField {
    private final Field field;

    //Cached field information
    private String name;
    private Class<?> type;

    public WrappedField(Field field) {
        this.field = field;

        field.setAccessible(true);
        name = field.getName();
        type = field.getType();
    }

    @SneakyThrows
    public void set(Object instance, Object value) {
        field.set(instance, value);
    }

    @SneakyThrows
    public <T> T get(Object instance) {
        return (T) field.get(instance);
    }
}
