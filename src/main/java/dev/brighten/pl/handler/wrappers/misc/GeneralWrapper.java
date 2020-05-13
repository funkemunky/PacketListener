package dev.brighten.pl.handler.wrappers.misc;

import dev.brighten.pl.handler.wrappers.Wrapper;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import dev.brighten.pl.utils.reflection.types.WrappedField;
import dev.brighten.pl.utils.reflection.types.WrappedMethod;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GeneralWrapper extends Wrapper {

    public GeneralWrapper(Object object) {
        super(object);
    }

    @Override
    public void updateObject() {

    }

    @Getter
    private WrappedClass wrapped;

    @Override
    protected void onProcess(Object object, Player player) {
        wrapped = new WrappedClass(object.getClass());
    }
}
