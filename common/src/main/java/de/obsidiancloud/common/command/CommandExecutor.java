package de.obsidiancloud.common.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public abstract class CommandExecutor {
    /**
     * Executes the command
     *
     * @param line The command line
     */
    public void execute(@NotNull String line) {
        String[] parts = line.split(" ");
        Command command = Command.getCommand(parts[0]);
        if (command == null) {
            sendMessage("§cCommand \"" + command + "\" was not found");
        } else {
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);
            command.execute(this, parts);
        }
    }

    /**
     * Sends a message to the command executor
     *
     * @param message The message to send
     */
    public abstract void sendMessage(@NotNull Component message);

    /**
     * Sends a message to the command executor
     *
     * @param message The message to send
     */
    public void sendMessage(@NotNull String message) {
        sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
    }
}