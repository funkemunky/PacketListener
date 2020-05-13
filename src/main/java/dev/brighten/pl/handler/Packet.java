package dev.brighten.pl.handler;

import dev.brighten.pl.utils.reflection.Reflection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

public class Packet {

    @RequiredArgsConstructor
    public enum Client {

        CHAT("Chat"),
        UNKNOWN("Unknown");

        private static String path = "net.minecraft.server." + Reflection.VERSION + ".PacketPlayIn";
        @Getter
        private final String name;

        public static Client getByName(String name) {
            return Arrays.stream(values()).filter(val -> val.name.equals(name)).findFirst().orElse(UNKNOWN);
        }
    }

    @RequiredArgsConstructor
    public enum Server {

        CHAT("Chat"),
        UNKNOWN("Unknown");

        private static String path = "net.minecraft.server." + Reflection.VERSION + ".PacketPlayOut";
        @Getter
        private final String name;

        public static Server getByName(String name) {
            return Arrays.stream(values()).filter(val -> val.name.equals(name)).findFirst().orElse(UNKNOWN);
        }
    }
}