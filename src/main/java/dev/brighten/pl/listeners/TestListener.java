package dev.brighten.pl.listeners;

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

            wrapper.setMessage(wrapper.getMessage() + " Arrived");
            wrapper.updateObject();
        }
    }

    @EventHandler
    public void onSend(PacketSendEvent event) {
        if(event.getPacket() instanceof WrappedOutChatPacket) {
            WrappedOutChatPacket wrapper = (WrappedOutChatPacket) event.getPacket();

            wrapper.component.addExtra("Sent");

            wrapper.updateObject();
        }
    }
}
