package dev.brighten.pl.utils.reflection.impl;

import dev.brighten.pl.utils.reflection.Reflection;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import dev.brighten.pl.utils.reflection.types.WrappedMethod;
import org.bukkit.entity.Player;

public class CraftReflection {

    //Cached craft classes
    public static WrappedClass craftPlayer = Reflection.getCBClass("entity.CraftPlayer"),
            craftChatMessage = Reflection.getCBClass("util.CraftChatMessage");

    //Bukkit object instances
    private static WrappedMethod entityPlayerInstance = craftPlayer.getMethodByName("getHandle");

    //static methods
    private static WrappedMethod fromComponent = craftChatMessage.getMethodByName("fromComponent",
            MinecraftReflection.iChatComponent.getParent(), MinecraftReflection.enumChatFormat.getParent());

    public static <T> T getEntityPlayer(Player player) {
        return entityPlayerInstance.invoke(player);
    }

    public static String getMessageFromComp(Object ichatcomp, String defaultColor) {
        return fromComponent.invoke(null, ichatcomp, MinecraftReflection.enumChatFormat.getEnum(defaultColor));
    }
}
