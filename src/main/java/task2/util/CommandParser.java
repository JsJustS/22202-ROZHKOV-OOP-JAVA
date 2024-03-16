package task2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private final String commandLine;
    private String commandName;
    private List<Object> commandArgs;
    private boolean skipFlag;

    public CommandParser(String commandLine) {
        this.commandLine = commandLine;
        this.commandName = null;
        this.commandArgs = new ArrayList<>();
        this.skipFlag = true;
    }

    public void parse() {
        if (commandLine == null || commandLine.isEmpty()) return;

        String trimmedCommandLine = commandLine.trim();
        String[] splitLine = trimmedCommandLine.split("\\s+");
        if (splitLine[0].charAt(0) == '#') return;

        List<Object> args = new ArrayList<>(Arrays.asList(splitLine));

        this.commandName = (String) args.get(0);
        args.remove(0);
        this.commandArgs = args;
        this.skipFlag = false;

        args.replaceAll(el -> {
            try {
                return Double.parseDouble((String) el);
            } catch (NumberFormatException | ClassCastException e) {
                return el;
            }
        });
    }

    public boolean shouldSkip() {
        return this.skipFlag;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public List<Object> getArgs() {
        return new ArrayList<>(this.commandArgs);
    }
}
