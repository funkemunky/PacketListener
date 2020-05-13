package dev.brighten.pl.events;

import dev.brighten.pl.handler.Packet;
import dev.brighten.pl.handler.wrappers.Wrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@Getter
public class PacketSendEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Setter
    private boolean cancelled;

    private final Player player;
    private final Packet.Server type;
    private final Wrapper packet;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
