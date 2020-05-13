package dev.brighten.pl.listeners;

import dev.brighten.pl.PacketListener;
import dev.brighten.pl.events.PacketReceiveEvent;
import dev.brighten.pl.events.PacketSendEvent;
import dev.brighten.pl.handler.wrappers.in.WrappedInChatPacket;
import dev.brighten.pl.handler.wrappers.out.WrappedOutChatPacket;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TestListener implements Listener {

    @EventHandler
    public void onReceive(PacketReceiveEvent event) {
        if(event.getPacket() instanceof WrappedInChatPacket) {
            WrappedInChatPacket wrapper = (WrappedInChatPacket) event.getPacket();

            PacketListener.INSTANCE.debug("IN-" + event.getPlayer().getName() + ": " + wrapper.getMessage());
            wrapper.setMessage("edited msg " + wrapper.getMessage());
            wrapper.updateObject();
        }
    }

    @EventHandler
    public void onSend(PacketSendEvent event) {
        if(event.getPacket() instanceof WrappedOutChatPacket) {
            WrappedOutChatPacket wrapper = (WrappedOutChatPacket) event.getPacket();

            PacketListener.INSTANCE.debug("OUT-" + event.getPlayer().getName() + ": " + wrapper.component.getText());

            wrapper.component.addExtra("this has been modified");

            wrapper.updateObject();
        }
    }
}
