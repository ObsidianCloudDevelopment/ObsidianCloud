package de.obsidiancloud.node;

import de.obsidiancloud.common.OCNode;
import de.obsidiancloud.common.OCServer;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocalOCNode extends OCNode {
    private final List<LocalOCServer> servers;

    public LocalOCNode(
            @NotNull String name,
            @NotNull String host,
            int port,
            @NotNull List<LocalOCServer> servers) {
        super(name, host, port);
        this.servers = servers;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable List<OCServer> getServers() {
        return (List<OCServer>) (List<?>) servers;
    }
}
