package dev.brighten.pl.handler.wrappers.misc;

import dev.brighten.pl.handler.wrappers.Wrapper;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import lombok.Getter;
import org.bukkit.entity.Player;

public class GeneralWrapper extends Wrapper {

    public GeneralWrapper(Object object) {
        super(object);
    }

    public GeneralWrapper(Object object, Player player) {
        super(object, player);
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
