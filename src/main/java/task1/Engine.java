package task1;

import task1.input.IInputHandler;
import task1.output.IOutputHandler;
import task1.util.Config;
import task1.util.ISequenceGen;

import java.util.List;

public class Engine {
    private IInputHandler input;
    private IOutputHandler output;
    private Config config;
    private boolean running;

    public Engine() {
        this.running = false;
    }

    /**
     * Set instance of input handling class
     * Provided instance should implement IInputHandler.java
     * */
    public void setInputHandler(IInputHandler inp) {
        this.input = inp;
    }

    /**
     * Set instance of output handling class
     * Provided instance should implement IOutputHandler.java
     * */
    public void setOutputHandler(IOutputHandler out) {
        this.output = out;
    }

    /**
     * Set instance of config class
     * */
    public void setConfig(Config cfg) {
        this.config = cfg;
    }

    public boolean isRunning() {
        return this.running;
    }

    /**
     * Start main game
     * */
    public <T> void start(ISequenceGen<T> generator) throws RuntimeException {
        if (this.input == null) throw new RuntimeException("Input handler undefined");
        if (this.output == null) throw new RuntimeException("Output handler undefined");
        if (this.config == null) throw new RuntimeException("Config undefined");

        BotPlayer<T> botPlayer = new BotPlayer<>(this.config, generator);
        this.output.setClue(botPlayer.getClue());
        this.running = true;

        while (this.isRunning()) {
            this.input.tick();
            if (input.hasInput()) {

                List<T> inputSequence;
                try {
                    // IMPORTANT! Chosen <T> should be castable to user input
                    inputSequence = (List<T>) this.input.getInput();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                botPlayer.checkSequence(inputSequence);

                int cows = botPlayer.getCows();
                int bulls = botPlayer.getBulls();

                this.output.setResponse(cows, bulls, botPlayer.isSequenceGuessed());
            }
            this.output.showClue(this.input.askedClue());
            this.output.tick(this.input.hasInput());
            this.checkExitCondition(botPlayer);
        }
    }

    /**
     * Checks for any condition resulting in program end.
     * */
    private void checkExitCondition(BotPlayer bot) {
        // We ARE RUNNING if
        // 1. User has NOT inputted EXIT COMMAND;
        // 2. User has NOT guessed sequence.
        this.running = !this.input.exited() && !bot.isSequenceGuessed();
    }
}
