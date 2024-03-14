package task2.factory;

import task2.command.Command;
import task2.error.CommandCreationException;
import task2.error.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class CommandFactory {
    private final String configFilename = "config.txt";
    private final HashMap<String, Class<Command>> COMMANDS;

    public CommandFactory() {
        this.COMMANDS = new HashMap<>();
    }

    public void init() throws ConfigException {
        InputStream stream = CommandFactory.class.getResourceAsStream("/" + configFilename);
        if (stream == null) {
            throw new ConfigException("Can't open configuration file: " + configFilename);
        }

        try {
            Properties properties = new Properties();
            properties.load(stream);
            for (Object commandNameObj : properties.keySet()) {
                String commandName = (String) commandNameObj;
                COMMANDS.put(commandName, (Class<Command>) Class.forName((String) properties.get(commandNameObj)));
            }
        } catch (IOException e) {
            throw new ConfigException(e.getMessage());
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new ConfigException("Bad formatting for " + configFilename);
        }
    }

    public Command create(String name) throws CommandCreationException {
        try {
            if (name == null || name.length() == 0 || !COMMANDS.containsKey(name)) {
                throw new CommandCreationException("Invalid name provided.");
            }
            return COMMANDS.get(name).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CommandCreationException(e.getMessage());
        }
    }
}
