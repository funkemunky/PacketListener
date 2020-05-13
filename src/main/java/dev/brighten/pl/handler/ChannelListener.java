package dev.brighten.pl.handler;

import org.bukkit.entity.Player;

public abstract class ChannelListener {

    public static String handle = "custom_packet_listener";

    public abstract void inject(Player player);

    public abstract void uninject(Player player);

    public abstract Object onReceive(Player player, Object packet);

    public abstract Object onSend(Player player, Object packet);
}
