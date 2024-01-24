package de.obsidiancloud.common.command.impl;

import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
        setDescription("Shows help");
        setUsage("help");
        addAlias("?");
    }

    @Override
    public void execute(@NotNull CommandExecutor executor, @NotNull String[] args) {
        executor.sendMessage("Help:");
        for (Command command : Command.getAllCommands()) {
            executor.sendMessage("  " + command.getName() + " - " + command.getDescription());
        }
    }
}
