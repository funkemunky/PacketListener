package dev.brighten.pl.test;

import dev.brighten.pl.PacketListener;
import dev.brighten.pl.handler.Packet;
import dev.brighten.pl.handler.wrappers.in.WrappedInChatPacket;
import dev.brighten.pl.handler.wrappers.out.WrappedOutChatPacket;

public class TestListener {

    public TestListener() {
        PacketListener.INSTANCE.packetProcessor.process((packet, type) -> {
            if(packet instanceof WrappedInChatPacket) {
                WrappedInChatPacket wrapper = (WrappedInChatPacket) packet;

                wrapper.setMessage(wrapper.getMessage() + " Arrived");
                wrapper.updateObject();
            } else if(packet instanceof WrappedOutChatPacket) {
                WrappedOutChatPacket wrapper = (WrappedOutChatPacket) packet;

                if(wrapper.component.getText().contains("Arrived")) {
                    wrapper.component.addExtra(" Sent");

                    wrapper.updateObject();
                }
            }
            return false;
        });
    }
}
