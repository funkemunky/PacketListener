package dev.brighten.pl.listeners;

import dev.brighten.pl.handler.ChannelListener;
import dev.brighten.pl.handler.ChannelNew;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListeners implements Listener {

    public static ChannelListener clistener;

    public JoinListeners() {
        //Could implement a version for 1.7.10 but not necessary for this purpose.
        clistener = new ChannelNew();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        clistener.inject(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        clistener.uninject(event.getPlayer());
    }
}
