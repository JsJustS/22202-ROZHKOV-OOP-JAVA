package task1;

import task1.input.ConsoleInputImpl;
import task1.output.ConsoleScreen;
import task1.util.Config;
import task1.util.DigitSequenceGen;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.setInputHandler(new ConsoleInputImpl());
        engine.setOutputHandler(new ConsoleScreen());
        engine.setConfig(Config.GENERAL);

        try {
            engine.start(new DigitSequenceGen());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
