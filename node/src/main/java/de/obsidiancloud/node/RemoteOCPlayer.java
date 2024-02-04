package de.obsidiancloud.node;

import de.obsidiancloud.common.OCPlayer;
import de.obsidiancloud.common.OCServer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class RemoteOCPlayer extends OCPlayer {
    private final RemoteOCNode node;

    public RemoteOCPlayer(@NotNull UUID uuid, @NotNull String name, @NotNull RemoteOCNode node) {
        super(uuid, name);
        this.node = node;
    }

    @Override
    public @Nullable OCServer getProxy() {
        List<OCServer> servers = node.getServers();
        if (servers != null) {
            for (OCServer server : servers) {
                if (server.getType().isProxy() && server.getPlayers().contains(this)) {
                    return server;
                }
            }
        }
        return null;
    }

    @Override
    public @Nullable OCServer getSerer() {
        List<OCServer> servers = node.getServers();
        if (servers != null) {
            for (OCServer server : servers) {
                if (!server.getType().isProxy() && server.getPlayers().contains(this)) {
                    return server;
                }
            }
        }
        return null;
    }

    @Override
    public void connect(OCServer server) {
        // TODO: Send packet to node to connect player to server
    }

    @Override
    public void disconnect(Component message) {
        // TODO: Send packet to node to disconnect player
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        // TODO: Send packet to node to send message to player
    }

    @Override
    public String getCommandPrefix() {
        return "/cloud ";
    }
}