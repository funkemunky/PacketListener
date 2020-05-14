package dev.brighten.pl.handler;

public class Packet {

    public static class Client {
        private static final String path = "PacketPlayIn";

        public static final String CHAT = path + "Chat";
    }

    public static class Server {
        private static final String path = "PacketPlayOut";

        public static final String CHAT = path + "Chat";
    }
}
