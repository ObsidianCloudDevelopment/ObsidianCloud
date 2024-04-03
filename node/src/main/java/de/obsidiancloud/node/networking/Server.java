package de.obsidiancloud.node.networking;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class Server {
    private final ServerSocketChannel serverSocket;

    public Server(String bindAddress, int port) throws IOException {
        this.serverSocket = ServerSocketChannel.open();
    }
}