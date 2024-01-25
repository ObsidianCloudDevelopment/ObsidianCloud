package de.obsidiancloud.master;

import de.obsidiancloud.common.command.BaseCommandProvider;
import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.impl.HelpCommand;
import de.obsidiancloud.common.console.Console;
import de.obsidiancloud.common.console.ConsoleCommandExecutor;
import de.obsidiancloud.master.command.ShutdownCommand;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class OCMaster extends BaseCommandProvider {
    private static OCMaster instance;
    private Logger logger;
    private ConsoleCommandExecutor executor;
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
        instance = new OCMaster();
    }

    public OCMaster() {
        logger = Logger.getLogger("main");
        executor = new ConsoleCommandExecutor(logger);
        Command.registerProvider(this);
        registerCommand(new HelpCommand());
        registerCommand(new ShutdownCommand());
        try {
            console = new Console(logger, executor);
            console.start();
        } catch (Throwable error) {
            logger.log(Level.SEVERE, "Failed to create console", error);
        }
    }

    public void shutdown() {
        console.stop();
        System.exit(0);
    }

    /**
     * Gets the logger of the master.
     *
     * @return The logger of the master.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the console command executor of the master.
     *
     * @return The console command executor of the master.
     */
    public ConsoleCommandExecutor getExecutor() {
        return executor;
    }

    /**
     * Gets the console of the master.
     *
     * @return The console of the master.
     */
    public Console getConsole() {
        return console;
    }

    /**
     * Gets the console of the master.
     *
     * @return The console of the master.
     */
    public static OCMaster getInstance() {
        return instance;
    }
}
