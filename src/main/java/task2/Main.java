package task2;

import task2.error.ConfigException;
import task2.util.ArgParser;

public class Main {
    public static void main(String[] strings) {
        ArgParser argp = new ArgParser(strings);
        if (!argp.isOk()) {
            System.out.println(argp.getMessage());
        }

        Calculator instance;
        if (argp.hasFileName()) {
            instance = new Calculator(argp.getFileName());
        } else {
            instance = new Calculator();
        }

        try {
            instance.start();
        } catch (ConfigException e) {
            // todo: logging
            System.err.println("Could not start calculations: " + e.getMessage());
        }
    }
}
