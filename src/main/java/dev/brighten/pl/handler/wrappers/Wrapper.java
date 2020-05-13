package dev.brighten.pl.handler.wrappers;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class Wrapper {

    private Object object;
    private Player player;

    public Wrapper(Object object) {
        this.object = object;

        onProcess(object, null);
    }

    public Wrapper(Object object, Player player) {
        this.object = object;
        this.player = player;

        onProcess(object, player);
    }

    public abstract void updateObject();

    protected void onProcess(Object object, Player player) {
        //Empty method.
    }
}
