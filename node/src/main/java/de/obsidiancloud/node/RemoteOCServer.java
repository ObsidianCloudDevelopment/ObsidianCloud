package de.obsidiancloud.node;

import de.obsidiancloud.common.OCNode;
import de.obsidiancloud.common.OCPlayer;
import de.obsidiancloud.common.OCServer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RemoteOCServer extends OCServer {
    private final RemoteOCNode node;

    public RemoteOCServer(
            @NotNull String name,
            @NotNull Type type,
            int port,
            List<OCPlayer> players,
            int maxPlayers,
            boolean autoStart,
            boolean deleteOnStop,
            boolean maintenance,
            @NotNull RemoteOCNode node) {
        super(name, type, port, players, maxPlayers, autoStart, deleteOnStop, maintenance);
        this.node = node;
    }

    @Override
    public void start() {
        // TODO: Send start server packet to node
    }

    @Override
    public void stop() {
        // TODO: Send stop server packet to node
    }

    @Override
    public OCNode getNode() {
        return node;
    }
}
