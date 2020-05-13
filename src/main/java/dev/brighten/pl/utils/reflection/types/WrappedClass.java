package dev.brighten.pl.utils.reflection.types;

import dev.brighten.pl.handler.Packet;
import dev.brighten.pl.utils.MiscUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WrappedClass {
    @Getter
    private final Class<?> parent;

    @SneakyThrows
    public WrappedClass(String name) {
        parent = Class.forName(name);
    }

    public WrappedClass(Packet.Client packet) {
        this(packet.getName());
    }

    public WrappedClass(Packet.Server packet) {
        this(packet.getName());
    }
    //Used to grab method by its name and parameters.
    @SneakyThrows
    public WrappedMethod getMethodByName(String name, Class<?>... params) {
        return new WrappedMethod(parent.getMethod(name, params));
    }

    //Used to grab method by its return type and parameters.
    public WrappedMethod getMethodByReturnType(Class<?> type, Class<?>... params) {
        List<Method> methodsList = new ArrayList<>();

        return new WrappedMethod(Arrays.stream(parent.getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(type)
                        && (params.length == 0 || MiscUtils.isArrayEqual(method.getParameterTypes(), params)))
                .findFirst()
                .orElseThrow(NullPointerException::new));
    }

    public List<WrappedMethod> getMethods() {
        return Arrays.stream(parent.getMethods()).map(WrappedMethod::new).collect(Collectors.toList());
    }

    public List<WrappedMethod> getDeclaredMethods() {
        return Arrays.stream(parent.getDeclaredMethods()).map(WrappedMethod::new).collect(Collectors.toList());
    }

    public List<WrappedField> getFields() {
        return Arrays.stream(parent.getFields()).map(WrappedField::new).collect(Collectors.toList());
    }

    public List<WrappedField> getDeclaredFields() {
        return Arrays.stream(parent.getDeclaredFields()).map(WrappedField::new).collect(Collectors.toList());
    }

    //Used to grab the method by only its return type and position in class.
    @SneakyThrows
    public WrappedMethod getMethodByReturnType(Class<?> type, int index) {
        val methodList = Arrays.stream(parent.getDeclaredMethods()) //Not the lightest on RAM but it should be
                .filter(method -> method.getReturnType().equals(type)) //the easiest on the CPU.
                .collect(Collectors.toList());

        if(methodList.size() == 0) throw new NoSuchMethodException("There are no methods in "
                + parent.getSimpleName() + " with return type " + type.getSimpleName());

        if(index > methodList.size() - 1) throw new IndexOutOfBoundsException("There are no methods at index " + index
                + " for class " + type.getSimpleName() + "!");

        Method method = methodList.get(index);

        methodList.clear(); //Clearing objects so the garbage collector doesn't have to.
        return new WrappedMethod(method);
    }

    //Used to grab a field by its name.
    @SneakyThrows
    public WrappedField getFieldByName(String name) {
        return new WrappedField(parent.getField(name));
    }

    //Used to grab a field by its type and its position in the class.
    @SneakyThrows
    public WrappedField getFieldByType(Class<?> type, int index) {
        val fieldList = Arrays.stream(parent.getDeclaredFields()) //Not the lightest on RAM but should be the easiest
                .filter(field -> field.getType().equals(type)) //on the CPU.
                .collect(Collectors.toList());

        if(fieldList.size() == 0) throw new NoSuchFieldException("There are no fields in "
                + parent.getSimpleName() + " with return type " + type.getSimpleName());

        if(index > fieldList.size() - 1) throw new IndexOutOfBoundsException("There are no fields at index " + index
                + " for class " + type.getSimpleName() + "!");

        Field field = fieldList.get(index);

        fieldList.clear(); //Clearing objects so the garbage collector doesn't have to.
        return new WrappedField(field);
    }

    @SneakyThrows
    public WrappedConstructor getConstructor(Class<?>... types) {
        return new WrappedConstructor(parent.getDeclaredConstructor(types));
    }
}
