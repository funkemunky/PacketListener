package dev.brighten.pl.utils.reflection.impl;

import dev.brighten.pl.utils.reflection.Reflection;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import dev.brighten.pl.utils.reflection.types.WrappedField;
import dev.brighten.pl.utils.reflection.types.WrappedMethod;
import org.bukkit.entity.Player;

public class MinecraftReflection {

    //Cached classes
    public static WrappedClass playerConnection = Reflection.getNMSClass("PlayerConnection"),
            entityPlayer =  Reflection.getNMSClass("EntityPlayer"),
            networkManager = Reflection.getNMSClass("NetworkManager"),
            iChatComponent = Reflection.getNMSClass("IChatBaseComponent"),
            channel = Reflection.getMinecraftUtil("io.netty.channel.Channel");

    //Fields to grab from EntityPlayer
    private static WrappedField fieldPlayerConn = entityPlayer.getFieldByType(playerConnection.getParent(), 0),
            fieldNetworkManager = playerConnection.getFieldByType(networkManager.getParent(), 0),
            fieldChannel = networkManager.getFieldByType(channel.getParent(), 0);

    //Cached methods from chatComponent
    private static WrappedMethod getText = iChatComponent.getMethodByReturnType(String.class, 0),
            toPlainText = iChatComponent.getMethodByReturnType(String.class, 1);

    public static <T> T getPlayerConnection(Object entityPlayer) {
        return fieldPlayerConn.get(entityPlayer);
    }

    public static <T> T getNetworkManager(Object playerConnection) {
        return fieldNetworkManager.get(playerConnection);
    }

    public static <T> T getChannel(Object networkManager) {
        return fieldChannel.get(networkManager);
    }

    public static <T> T getChannel(Player player) {
        Object entityPlayer = CraftReflection.getEntityPlayer(player);

        return getChannel(
                getNetworkManager(
                        getPlayerConnection(entityPlayer)));
    }

    public static String getText(Object chatComponent) {
        return getText.invoke(chatComponent);
    }
}
