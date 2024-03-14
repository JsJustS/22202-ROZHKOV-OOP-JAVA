package task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task2.command.Command;
import task2.error.BadArgumentCommandException;
import task2.error.CommandCreationException;
import task2.error.ConfigException;
import task2.error.RuntimeCommandException;
import task2.factory.CommandFactory;
import task2.util.CommandParser;
import task2.util.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Calculator {
    public final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);
    private final String filename;
    public Calculator() {
        this.filename = null;
    }

    public Calculator(String filename) {
        this.filename = filename;
    }

    public void start() throws ConfigException {
        Stream<String> commandStream;
        if (this.filename == null) {
            commandStream = new BufferedReader(new InputStreamReader(System.in)).lines();
        } else {
            try{
                commandStream = Files.lines(Paths.get(this.filename));
            } catch (IOException e) {
                throw new ConfigException("Could not open " + this.filename);
            }
        }

        CommandFactory commandFactory = new CommandFactory();
        commandFactory.init();
        Context ctx = new Context();

        commandStream.forEach(commandLine -> updateState(commandLine, commandFactory, ctx));
    }

    private void updateState(String commandLine, CommandFactory factory, Context ctx) {
        try {
            CommandParser parser = new CommandParser(commandLine);
            parser.parse();
            if (parser.shouldSkip()) return;

            Command command = factory.create(parser.getCommandName());
            command.run(ctx, parser.getArgs());
            LOGGER.debug(command + " was executed.");
        } catch (CommandCreationException | BadArgumentCommandException | RuntimeCommandException e) {
            LOGGER.warn("Skipped \"" + commandLine + "\" due to an error: " + e.getMessage());
        }
    }
}
