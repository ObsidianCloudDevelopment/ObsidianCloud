package de.obsidiancloud.common;

import de.obsidiancloud.common.command.CommandExecutor;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OCPlayer implements CommandExecutor {
    private final UUID uuid;
    private final String name;
    protected String proxy;
    protected String server;

    public OCPlayer(
            @NotNull UUID uuid,
            @NotNull String name,
            @Nullable String proxy,
            @Nullable String server) {
        this.uuid = uuid;
        this.name = name;
        this.proxy = proxy;
        this.server = server;
    }

    /**
     * Gets the proxy of this player.
     *
     * @return Returns the proxy of this player.
     */
    public abstract @Nullable OCServer getProxy();

    /**
     * Gets the server of this player.
     *
     * @return Returns the server of this player.
     */
    public abstract @Nullable OCServer getSerer();

    /**
     * Connects the player to the given server.
     *
     * @param server The server to connect to
     */
    public abstract void connect(OCServer server);

    /**
     * Kicks the player from the network.
     *
     * @param message The kick reason
     */
    public abstract void disconnect(Component message);

    /**
     * Gets the uuid of this player.
     *
     * @return Returns the uuid of this player.
     */
    public @NotNull UUID getUUID() {
        return uuid;
    }

    /**
     * Gets the name of this player.
     *
     * @return Returns the name of this player.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Updates the player.
     *
     * @param player The player to update
     * @param proxy The new proxy of the player
     * @param server The new server of the player
     */
    public static void updatePlayer(
            @NotNull OCPlayer player, @Nullable String proxy, @Nullable String server) {
        player.proxy = proxy;
        player.server = server;
    }
}
