package dev.brighten.pl.utils.reflection;

import dev.brighten.pl.utils.ProtocolVersion;
import dev.brighten.pl.utils.reflection.types.WrappedClass;
import org.bukkit.Bukkit;

public class Reflection {

    public static String VERSION;
    private static String cbPath, nmsPath, utilPath;
    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        cbPath = "org.bukkit.craftbukkit." + VERSION + ".";
        nmsPath = "net.minecraft.server." + VERSION + ".";

        if(ProtocolVersion.VERSION.isAbove(ProtocolVersion.v1_7_10)) {
            utilPath = "";
        } else utilPath = "net.minecraft.util.";
    }

    public static WrappedClass getClass(String name) {
        return new WrappedClass(name);
    }

    public static WrappedClass getNMSClass(String name) {
        return new WrappedClass(nmsPath + name);
    }

    public static WrappedClass getCBClass(String name) {
        return new WrappedClass(cbPath + name);
    }

    public static WrappedClass getMinecraftUtil(String name) {
        return new WrappedClass(utilPath + name);
    }
}
