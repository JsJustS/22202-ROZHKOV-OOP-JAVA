package task2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private final String commandName;
    private final List<Object> commandArgs;
    private final boolean skipFlag;

    public CommandParser(String commandLine) {
        if (commandLine == null || commandLine.equals("")) {
            this.commandName = null;
            this.commandArgs = new ArrayList<>();
            this.skipFlag = true;
            return;
        }

        commandLine = commandLine.trim();
        String[] splitLine = commandLine.split("\\s+");
        if (splitLine[0].charAt(0) == '#') {
            this.commandName = null;
            this.commandArgs = new ArrayList<>();
            this.skipFlag = true;
            return;
        }
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

    public List<Object> getArgs(Context ctx) {
        List<Object> copy = new ArrayList<>(this.commandArgs);
        copy.replaceAll(el -> (el instanceof String && ctx.hasVar((String)el)?ctx.getVar((String)el):el));
        return copy;
    }
}
