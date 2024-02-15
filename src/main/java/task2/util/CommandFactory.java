package task2.util;

import task2.command.Command;
import task2.error.CommandCreationException;
import task2.error.ConfigException;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

public class CommandFactory {
    private final String configFilename = "config.txt";
    private final HashMap<String, Class<Command>> COMMANDS;

    public CommandFactory() throws ConfigException {
        this.COMMANDS = new HashMap<>();

        InputStream stream = CommandFactory.class.getResourceAsStream("/" + configFilename);
        if (stream == null) {
            throw new ConfigException("Can't open configuration file: " + configFilename);
        }

        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            String[] pair = scanner.nextLine().split("\\s*=\\s*");
            if (pair.length < 2)
                throw new ConfigException("Bad formatting for " + configFilename);
            try {
                COMMANDS.put(pair[0], (Class<Command>) Class.forName(pair[1]));
            } catch (ClassNotFoundException | ClassCastException e) {
                throw new ConfigException("Bad formatting for " + configFilename);
            }
        }
    }

    public Command create(String name) throws CommandCreationException {
        try {
            return COMMANDS.get(name).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CommandCreationException(e.getMessage());
        }
    }
}
