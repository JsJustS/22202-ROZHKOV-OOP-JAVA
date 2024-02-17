package task1.input;

import task1.util.Config;
import task1.util.DigitValidator;
import task1.util.IValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Specified realisation of input for Task-1:
 * Input from console with 4-digit sequence.
 * */
public class ConsoleInputImpl implements IInputHandler {
    private final List<Integer> inputSequence;
    private final BufferedReader reader;

    private final IValidator validator;
    private final int inputLength;

    // flags
    private boolean exitFlag;
    private boolean clueFlag;
    private boolean inputFlag;

    public ConsoleInputImpl(Config cfg) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.inputSequence = new ArrayList<>();
        this.validator = new DigitValidator(cfg.length());
        this.inputLength = cfg.length();

        // flags
        this.exitFlag = false;
        this.clueFlag = false;
        this.inputFlag = false;
    }

    @Override
    public void tick() {
        this.inputFlag = false;

        HashSet<Character> bytes = new HashSet<>();
        String badInput = "Bad input! Enter "+this.inputLength+" different digits side by side, then press New Line.";

        try {
            String userInput = this.reader.readLine();
            if (this.parseCommands(userInput)) {
                return; // if any command found, tick is skipped.
            }

            if (!validator.isOk(userInput)) {return;}

            this.inputSequence.clear();
            for (char c : userInput.toCharArray()) {
                this.inputSequence.add(Integer.parseInt(String.valueOf(c), 10));
            }
            this.inputFlag = true;
        } catch (IOException ignored) {}
    }

    private boolean parseCommands(String userInput) {
        this.exitFlag = userInput.contains("exit");
        this.clueFlag = userInput.contains("clue");
        return this.exitFlag || this.clueFlag;
    }

    @Override
    public boolean hasInput() {
        return this.inputFlag;
    }

    @Override
    public List<Integer> getInput() {
        return this.inputSequence;
    }

    @Override
    public boolean exited() {
        return this.exitFlag;
    }

    @Override
    public boolean askedClue() {
        if (this.clueFlag) {
            this.clueFlag = false;
            return true;
        }
        return false;
    }
}
