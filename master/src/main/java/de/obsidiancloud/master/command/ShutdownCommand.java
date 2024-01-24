package de.obsidiancloud.master.command;

import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.CommandExecutor;
import de.obsidiancloud.master.OCMaster;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShutdownCommand extends Command {
    private final Map<CommandExecutor, Long> lastUsed = new HashMap<>();

    public ShutdownCommand() {
        super("shutdown");
        setDescription("Shuts down the master");
        setUsage("shutdown");
        addAlias("stop");
        addAlias("exit");
    }

    @Override
    public void execute(@NotNull CommandExecutor executor, @NotNull String[] args) {
        if (lastUsed.getOrDefault(executor, 0L) + 10000 > System.currentTimeMillis()) {
            lastUsed.remove(executor);
            executor.sendMessage("§cShutting down...");
            OCMaster.getInstance().shutdown();
        } else {
            lastUsed.put(executor, System.currentTimeMillis());
            executor.sendMessage("§cAre you sure? Type the command again in the next §e10 seconds §cto confirm.");
        }
    }
}