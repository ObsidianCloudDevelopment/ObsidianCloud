package de.obsidiancloud.common;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public abstract class OCServer {
    private final String name;
    private final Type type;
    private final int port;
    private final List<OCPlayer> players;
    private final int maxPlayers;
    private final boolean autoStart;
    private final boolean deleteOnStop;
    private final boolean maintenance;

    /**
     * Constructs a new OCServer with the specified parameters.
     *
     * @param name The name of the server
     * @param type The type of the server
     * @param port The port number on which the server is running
     * @param players The list of players currently connected to the server
     * @param maxPlayers The maximum number of players that can connect to the server
     * @param autoStart Whether the server should automatically start
     * @param deleteOnStop Whether the server should be deleted when it is stopped
     * @param maintenance Whether the server is currently in maintenance mode
     */
    public OCServer(
            @NotNull String name,
            @NotNull Type type,
            int port,
            @NotNull List<OCPlayer> players,
            int maxPlayers,
            boolean autoStart,
            boolean deleteOnStop,
            boolean maintenance) {
        this.name = name;
        this.type = type;
        this.port = port;
        this.players = players;
        this.maxPlayers = maxPlayers;
        this.autoStart = autoStart;
        this.deleteOnStop = deleteOnStop;
        this.maintenance = maintenance;
    }

    /** Starts the server. */
    public abstract void start();

    /** Stops the server. */
    public abstract void stop();

    /**
     * Gets the node of the server.
     *
     * @return Returns the node of the server.
     */
    public abstract OCNode getNode();

    /**
     * Gets the name of the server.
     *
     * @return Returns the name of the server.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Gets the type of the server.
     *
     * @return Returns the type of the server.
     */
    public @NotNull Type getType() {
        return type;
    }

    /**
     * Gets the port number on which the server is running.
     *
     * @return Returns the port number on which the server is running.
     */
    public int getPort() {
        return port;
    }

    /**
     * Checks whether the server is online.
     *
     * @return Returns whether the server is online.
     */
    public boolean isOnline() {
        return port != -1;
    }

    /**
     * Gets the list of players currently connected to the server.
     *
     * @return Returns the list of players currently connected to the server.
     */
    public @NotNull List<OCPlayer> getPlayers() {
        return players;
    }

    /**
     * Gets the maximum number of players that can connect to the server.
     *
     * @return Returns the maximum number of players that can connect to the server.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Checks whether the server should automatically start.
     *
     * @return Returns whether the server should automatically start.
     */
    public boolean isAutoStart() {
        return autoStart;
    }

    /**
     * Checks whether the server should be deleted when it is stopped.
     *
     * @return Returns whether the server should be deleted when it is stopped.
     */
    public boolean isDeleteOnStop() {
        return deleteOnStop;
    }

    /**
     * Checks whether the server is currently in maintenance mode.
     *
     * @return Returns whether the server is currently in maintenance mode.
     */
    public boolean isMaintenance() {
        return maintenance;
    }

    public static enum Type {
        BUKKIT(false),
        FABRIC(false),
        BUNGEECORD(true),
        VELOCITY(true);

        private final boolean proxy;

        Type(boolean proxy) {
            this.proxy = proxy;
        }

        public boolean isProxy() {
            return proxy;
        }
    }
}
