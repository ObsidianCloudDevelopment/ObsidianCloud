package de.obsidiancloud.node;

import de.obsidiancloud.common.OCNode;
import de.obsidiancloud.common.OCServer;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RemoteOCNode extends OCNode {
    private final List<OCServer> servers;

    public RemoteOCNode(
            @NotNull String name, @NotNull String host, int port, @NotNull List<OCServer> servers) {
        super(name, host, port);
        this.servers = servers;
    }

    @Override
    public boolean isConnected() {
        // TODO: Check connection
        return false;
    }

    @Override
    public @Nullable List<OCServer> getServers() {
        return isConnected() ? servers : null;
    }
}
