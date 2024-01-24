package de.obsidiancloud.common.console;

import de.obsidiancloud.common.command.Command;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public class CommandCompleter implements Completer {
    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> list) {
        for (Command command : Command.getAllCommands()) {
            list.add(new Candidate(command.getName()));
            for (String alias : command.getAliases()) {
                list.add(new Candidate(alias));
            }
        }
    }
}