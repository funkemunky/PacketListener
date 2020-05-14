package dev.brighten.pl.handler;

import dev.brighten.pl.PacketListener;
import dev.brighten.pl.handler.wrappers.Wrapper;
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

public class ChannelNew extends ChannelListener {

    @Override
    public void inject(Player player) {
        ChannelListener.executor.execute(() -> {
            Channel channel = getChannel(player);

            if(channel == null) return;

            Listen listen = (Listen) channel.pipeline().get(ChannelListener.handle);

            if(listen == null) {
                listen = new Listen(player);

                if(channel.pipeline().get(ChannelListener.handle) != null) {
                    channel.pipeline().remove(ChannelListener.handle);
                }
                channel.pipeline().addBefore("packet_handler", ChannelListener.handle, listen);
            }
        });
    }

    @Override
    public void uninject(Player player) {
        ChannelListener.executor.execute(() -> {
            Channel channel = getChannel(player);

            channel.eventLoop().execute(() -> {
                if(channel.pipeline().get(ChannelListener.handle) != null) {
                    channel.pipeline().remove(ChannelListener.handle);
                }
            });
        });
    }

    private Channel getChannel(Player player) {
        return MinecraftReflection.getChannel(player);
    }

    @Override
    public Object onReceive(Player player, Object packet) {
        String type = packet.getClass().getSimpleName();
        Wrapper wrapped;

        switch(type) {
            case Packet.Client.CHAT:
                wrapped = new WrappedInChatPacket(packet, player);
                break;
            default:
                wrapped = new GeneralWrapper(packet);
                break;
        }

        boolean cancelled = PacketListener.INSTANCE.packetProcessor.call(wrapped, type);

        return cancelled ? null : wrapped.getObject();
    }

    @Override
    public Object onSend(Player player, Object packet) {
        String type = packet.getClass().getSimpleName();
        Wrapper wrapped;

        switch(type) {
            case Packet.Server.CHAT:
                wrapped = new WrappedOutChatPacket(packet, player);
                break;
            default:
                wrapped = new GeneralWrapper(packet);
                break;
        }

        boolean cancelled = PacketListener.INSTANCE.packetProcessor.call(wrapped, type);

        return cancelled ? null : wrapped.getObject();
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
