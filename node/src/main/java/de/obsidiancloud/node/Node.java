package de.obsidiancloud.node;

import de.obsidiancloud.common.OCNode;
import de.obsidiancloud.common.OCServer;
import de.obsidiancloud.common.command.BaseCommandProvider;
import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.impl.HelpCommand;
import de.obsidiancloud.common.config.Config;
import de.obsidiancloud.common.config.ConfigSection;
import de.obsidiancloud.common.console.Console;
import de.obsidiancloud.common.console.ConsoleCommandExecutor;
import de.obsidiancloud.node.command.ShutdownCommand;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class Node extends BaseCommandProvider {
    private static Node instance;
    private final Logger logger = Logger.getLogger("main");
    private final Config config = new Config(Path.of("config.json"), Config.Type.JSON);
    private final Config serversConfig = new Config(Path.of("servers.json"), Config.Type.JSON);
    private final ConsoleCommandExecutor executor = new ConsoleCommandExecutor(logger);
    private Console console;
    private final List<RemoteOCNode> remoteNodes = new ArrayList<>();
    private LocalOCNode localNode;

    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("logs"));
            LogManager.getLogManager()
                    .readConfiguration(ClassLoader.getSystemResourceAsStream("logging.properties"));
        } catch (Throwable error) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to setup logging", error);
            return;
        }
        instance = new Node();
    }

    public Node() {
        try {
            console = new Console(logger, executor);
            console.start();
        } catch (Throwable error) {
            logger.log(Level.SEVERE, "Failed to create console", error);
        }
        Command.registerProvider(this);
        registerCommand(new HelpCommand());
        registerCommand(new ShutdownCommand());
        setupLocalNodeConfig();
        try {
            List<LocalOCServer> servers = loadServers();
            localNode = loadLocalNode(servers);
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "Failed loading local node data", throwable);
            shutdown();
        }
    }

    private void setupLocalNodeConfig() {
        if (config.getSection("local_node") == null) {
            config.set("local_node", new HashMap<>());
            config.save();
        }
        ConfigSection localNode = config.getSection("local_node");
        assert localNode != null;
        if (localNode.getString("name") == null) {
            localNode.set("name", "Node-1");
            config.save();
        }
        if (localNode.getString("host") == null) {
            localNode.set("host", "127.0.0.1");
            config.save();
        }
        if (localNode.getInt("port") == 0) {
            localNode.set("port", 1405);
            config.save();
        }
    }

    private List<LocalOCServer> loadServers() {
        List<LocalOCServer> servers = new ArrayList<>();

        if (Files.notExists(serversConfig.getFile())) {
            serversConfig.save();
        }

        for (String name : serversConfig.getData().keySet()) {
            try {
                ConfigSection section = serversConfig.getSection(name);
                assert section != null;

                OCServer.Type type = OCServer.Type.valueOf(section.getString("type"));

                assert section.contains("port");
                int port = section.getInt("port");

                assert section.contains("max_players");
                int maxPlayers = section.getInt("max_players");

                assert section.contains("auto_start");
                boolean autoStart = section.getBoolean("auto_start");

                assert section.contains("delete_on_stop");
                boolean deleteOnStop = section.getBoolean("delete_on_stop");

                assert section.contains("maintenance");
                boolean maintenance = section.getBoolean("maintenance");

                servers.add(
                        new LocalOCServer(
                                name,
                                type,
                                port,
                                maxPlayers,
                                autoStart,
                                deleteOnStop,
                                maintenance));
            } catch (Throwable ignored) {
            }
        }

        return servers;
    }

    private LocalOCNode loadLocalNode(List<LocalOCServer> servers) {
        ConfigSection localNode = config.getSection("local_node");
        assert localNode != null;
        String name = localNode.getString("name");
        assert name != null;
        String host = localNode.getString("host");
        assert host != null;
        int port = localNode.getInt("port");
        assert 1024 < port && port < 65535;
        return new LocalOCNode(name, host, port, servers);
    }

    /** Shuts down the node. */
    public void shutdown() {
        if (console != null) {
            console.stop();
        }
        System.exit(0);
    }

    /**
     * Gets the logger of the node.
     *
     * @return The logger of the node.
     */
    public @NotNull Logger getLogger() {
        return logger;
    }

    /**
     * Gets the console command executor of the node.
     *
     * @return The console command executor of the node.
     */
    public @NotNull ConsoleCommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Gets the console of the node.
     *
     * @return The console of the node.
     */
    public @NotNull Console getConsole() {
        return console;
    }

    /**
     * Gets the remote nodes.
     *
     * @return A {@code List<RemoteOCNode>} of the remote nodes.
     */
    public @NotNull List<RemoteOCNode> getRemoteNodes() {
        return remoteNodes;
    }

    /**
     * Gets the local node of the node.
     *
     * @return The local node of the node.
     */
    public @NotNull LocalOCNode getLocalNode() {
        return localNode;
    }

    /**
     * Gets all connected nodes.
     *
     * @return A {@code List<OCNode>} of all connected nodes.
     */
    public @NotNull List<OCNode> getConnectedNodes() {
        List<OCNode> nodes = new ArrayList<>();
        nodes.add(localNode);
        nodes.addAll(remoteNodes.stream().filter(OCNode::isConnected).toList());
        return nodes;
    }

    /**
     * Gets the config of the node.
     *
     * @return The config of the node.
     */
    public @NotNull Config getConfig() {
        return config;
    }

    /**
     * Gets the servers config of the node.
     *
     * @return The servers config of the node.
     */
    public @NotNull Config getServersConfig() {
        return serversConfig;
    }

    /**
     * Gets the console of the node.
     *
     * @return The console of the node.
     */
    public static @NotNull Node getInstance() {
        return instance;
    }
}
