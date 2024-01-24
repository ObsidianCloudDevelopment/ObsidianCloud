package de.obsidiancloud.common.addon;

import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.CommandProvider;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Addon implements CommandProvider {
    private final List<Command> commands = new ArrayList<>();
    private AddonManifest manifest;

    public void onEnable() {}

    public void onDisable() {}

    @Override
    public void registerCommand(@NotNull Command command) {
        if (commands.contains(command)) {
            throw new IllegalArgumentException(
                    "Command \"" + command.getName() + "\" is already registered");
        } else {
            commands.add(command);
        }
    }

    @Override
    public void unregisterCommand(@NotNull String name) {
        Command command = getCommand(name);
        if (command == null) {
            throw new IllegalArgumentException("Command \"" + name + "\" is not registered");
        } else {
            commands.remove(command);
        }
    }

    @Override
    public @NotNull List<Command> getCommands() {
        return commands;
    }

    @Override
    public Command getCommand(@NotNull String name) {
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    /**
     * Gets the manifest of the addon
     *
     * @return The manifest of the addon
     */
    public AddonManifest getManifest() {
        return manifest;
    }
}
