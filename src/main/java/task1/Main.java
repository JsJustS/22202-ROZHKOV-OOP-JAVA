package task1;

import task1.input.ConsoleInputImpl;
import task1.output.ConsoleScreen;
import task1.util.Config;
import task1.util.DigitSequenceGen;

public class Main {
    public static void main(String[] args) {
        Config cfg = new Config(0L, 5);
        //Config cfg = Config.GENERAL;
        Engine engine = new Engine();
        engine.setOutputHandler(new ConsoleScreen());
        engine.setConfig(cfg);
        engine.setInputHandler(new ConsoleInputImpl(cfg));

        try {
            engine.start(new DigitSequenceGen());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
