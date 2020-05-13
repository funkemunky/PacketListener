package dev.brighten.pl;

import dev.brighten.pl.handler.ChannelListener;
import dev.brighten.pl.listeners.JoinListeners;
import dev.brighten.pl.listeners.TestListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketListener extends JavaPlugin {

    public static PacketListener INSTANCE;

    public void onEnable() {
        System.out.println("Starting packet listener...");
        INSTANCE = this;

        registerListeners();

        System.out.println("Injecting handle for online players...");
        Bukkit.getOnlinePlayers().forEach(JoinListeners.clistener::inject);

        System.out.println("Enabled PacketListener (" + getDescription().getVersion() + ").");
    }

    public void onDisable() {
        System.out.println("Uninjecting all players...");
        Bukkit.getOnlinePlayers().forEach(JoinListeners.clistener::uninject);

        System.out.println("Unregistering listeners...");
        HandlerList.unregisterAll(this);

        System.out.println("Removing instances...");
        INSTANCE = null;

        System.out.println("Disabled.");
    }

    private void registerListeners() {
        System.out.println("Registering listeners...");
        Bukkit.getPluginManager().registerEvents(new JoinListeners(), this);
        Bukkit.getPluginManager().registerEvents(new TestListener(), this);
    }

    public void debug(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&cDebug&7] &f" + message));
    }
}
