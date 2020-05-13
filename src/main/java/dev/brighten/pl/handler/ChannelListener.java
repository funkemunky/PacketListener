package dev.brighten.pl.handler;

import org.bukkit.entity.Player;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ChannelListener {

    public static String handle = "custom_packet_listener";
    public static ExecutorService executor = Executors.newSingleThreadExecutor();

    public abstract void inject(Player player);

    public abstract void uninject(Player player);

    public abstract Object onReceive(Player player, Object packet);

    public abstract Object onSend(Player player, Object packet);
}
