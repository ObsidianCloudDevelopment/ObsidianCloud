package de.obsidiancloud.node;

import de.obsidiancloud.common.command.BaseCommandProvider;
import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.impl.HelpCommand;
import de.obsidiancloud.common.config.Config;
import de.obsidiancloud.common.console.Console;
import de.obsidiancloud.common.console.ConsoleCommandExecutor;
import de.obsidiancloud.node.command.ShutdownCommand;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Node extends BaseCommandProvider {
    private static Node instance;
    private final Logger logger = Logger.getLogger("main");
    private final Config config = new Config(Path.of("config.json"), Config.Type.JSON);
    private final ConsoleCommandExecutor executor = new ConsoleCommandExecutor(logger);
    private Console console;

    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("logs"));
            LogManager.getLogManager()
                    .readConfiguration(ClassLoader.getSystemResourceAsStream("logging.properties"));
        } catch (Throwable error) {
            Logger.getGlobal().log(Level.SEVERE, "Filed to setup logging", error);
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
    }

    public void shutdown() {
        console.stop();
        System.exit(0);
    }

    /**
     * Gets the logger of the node.
     *
     * @return The logger of the node.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the console command executor of the node.
     *
     * @return The console command executor of the node.
     */
    public ConsoleCommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Gets the console of the node.
     *
     * @return The console of the node.
     */
    public Console getConsole() {
        return console;
    }

    /**
     * Gets the console of the node.
     *
     * @return The console of the node.
     */
    public static Node getInstance() {
        return instance;
    }
}
