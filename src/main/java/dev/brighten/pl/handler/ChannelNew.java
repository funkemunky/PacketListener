package dev.brighten.pl.handler;

import dev.brighten.pl.events.PacketReceiveEvent;
import dev.brighten.pl.events.PacketSendEvent;
import dev.brighten.pl.handler.wrappers.in.WrappedInChatPacket;
import dev.brighten.pl.handler.wrappers.misc.GeneralWrapper;
import dev.brighten.pl.handler.wrappers.out.WrappedOutChatPacket;
import dev.brighten.pl.utils.reflection.impl.MinecraftReflection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static dev.brighten.pl.handler.Packet.Client.CHAT;

public class ChannelNew extends ChannelListener {

    private static Map<String, Channel> cachedChannels = new HashMap<>();

    @Override
    public void inject(Player player) {
        Channel channel = getChannel(player);

        Listen listen = (Listen) channel.pipeline().get(ChannelListener.handle);

        if(listen == null) {
            listen = new Listen(player);

            ChannelHandlerContext context = channel.pipeline().context("packet_handler");

            if(context != null) {
                channel.pipeline().addBefore("packet_handler", ChannelListener.handle, listen);
            }
        }
    }

    @Override
    public void uninject(Player player) {
        Channel channel = getChannel(player);

        channel.pipeline().remove(ChannelListener.handle);
    }

    private Channel getChannel(Player player) {
        return cachedChannels.computeIfAbsent(player.getUniqueId().toString(), key -> {
            Channel channel = MinecraftReflection.getChannel(player);

            cachedChannels.put(key, channel);

            return channel;
        });
    }

    @Override
    public Object onReceive(Player player, Object packet) {
        PacketReceiveEvent event;
        Packet.Client type = Packet.Client.getByName(packet.getClass().getSimpleName());
        switch(type) {
            case CHAT:
                event = new PacketReceiveEvent(player, type, new WrappedInChatPacket(packet, player));
                break;
            default:
                event = new PacketReceiveEvent(player, type, new GeneralWrapper(packet));
                break;
        }
        Bukkit.getPluginManager().callEvent(event);

        return event.isCancelled() ? null : event.getPacket().getObject();
    }

    @Override
    public Object onSend(Player player, Object packet) {
        PacketSendEvent event;
        Packet.Server type = Packet.Server.getByName(packet.getClass().getSimpleName());
        switch(type) {
            case CHAT:
                event = new PacketSendEvent(player, type, new WrappedOutChatPacket(packet, player));
                break;
            default:
                event = new PacketSendEvent(player, type, new GeneralWrapper(packet));
                break;
        }
        Bukkit.getPluginManager().callEvent(event);

        return event.isCancelled() ? null : event.getPacket().getObject();
    }

    @RequiredArgsConstructor
    public class Listen extends ChannelDuplexHandler {
        final Player player;
        @Override
        public void channelRead(ChannelHandlerContext context, Object o) throws Exception {
            Object object = o;

            object = onReceive(player, object);

            if(object != null) {
                super.channelRead(context, object);
            }
        }

        @Override
        public void write(ChannelHandlerContext context, Object o, ChannelPromise promise) throws Exception {
            Object object = o;

            object = onSend(player, object);

            if(object != null) {
                super.write(context, object, promise);
            }
        }
    }
}
