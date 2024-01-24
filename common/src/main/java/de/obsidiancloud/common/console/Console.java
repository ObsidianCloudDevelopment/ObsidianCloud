package de.obsidiancloud.common.console;

import de.obsidiancloud.common.command.Command;
import de.obsidiancloud.common.command.CommandExecutor;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Log;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

public class Console implements Runnable {
    private final Logger logger;
    private final ConsoleCommandExecutor executor;
    private final Terminal terminal;
    private final LineReader reader;
    private final Thread thread;

    public Console(Logger logger, ConsoleCommandExecutor executor) throws IOException {
        this.logger = logger;
        this.executor = executor;
        try (Terminal terminal = TerminalBuilder.builder()
                                                .color(true)
                                                .system(true)
                                                .encoding(StandardCharsets.UTF_8)
                                                .build()) {
            this.terminal = terminal;
        }
        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new CommandCompleter())
                .parser(new DefaultParser())
                .history(new DefaultHistory())
                .build();
        reader.setAutosuggestion(LineReader.SuggestionType.COMPLETER);
        this.thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        thread.stop();
        try {
            terminal.close();
        } catch (IOException e) {
            logger.throwing(Console.class.getName(), "stop", e);
        }
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = reader.readLine("> ")) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 0) {
                    Command command = null;
                    for (Command cmd : Command.getAllCommands()) {
                        if (cmd.getName().equalsIgnoreCase(parts[0]) || Arrays.asList(cmd.getAliases()).contains(parts[0])) {
                            command = cmd;
                        }
                    }
                    if (command != null) {
                        command.execute(executor, Arrays.copyOfRange(parts, 1, parts.length));
                    } else {
                        executor.sendMessage("§cCommand not found!");
                    }
                }
            }
        } catch (Throwable error) {
            logger.throwing(Console.class.getName(), "run", error);
        }
    }
}