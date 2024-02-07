package de.obsidiancloud.node;

import de.obsidiancloud.common.OCNode;
import de.obsidiancloud.common.OCServer;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class LocalOCServer extends OCServer {
    public LocalOCServer(
            @NotNull String name,
            @NotNull Type type,
            int port,
            int maxPlayers,
            boolean autoStart,
            boolean deleteOnStop,
            boolean maintenance) {
        super(
                name,
                type,
                port,
                new ArrayList<>(),
                maxPlayers,
                autoStart,
                deleteOnStop,
                maintenance);
    }

    @Override
    public void start() {
        // TODO: Start server
    }

    @Override
    public void stop() {
        // TODO: Stop server
    }

    @Override
    public OCNode getNode() {
        return Node.getInstance().getLocalNode();
    }
}
