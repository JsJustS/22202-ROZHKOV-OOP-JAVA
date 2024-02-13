package task1.input;

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

    // flags
    private boolean exitFlag;
    private boolean clueFlag;
    private boolean inputFlag;

    public ConsoleInputImpl() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.inputSequence = new ArrayList<>(4);

        // flags
        this.exitFlag = false;
        this.clueFlag = false;
        this.inputFlag = false;
    }

    @Override
    public void tick() {
        this.inputFlag = false;

        HashSet<Character> bytes = new HashSet<>();
        String badInput = "Bad input! Enter 4 different digits side by side, then press New Line.";

        try {
            String userInput = this.reader.readLine();
            if (this.parseCommands(userInput)) {
                return; // if any command found, tick is skipped.
            }

            this.inputSequence.clear();
            for (char c : userInput.toCharArray()) {
                if (!Character.isDigit(c) || bytes.contains(c) || this.inputSequence.size() >= 4) {
                    System.out.println(badInput);
                    this.inputSequence.clear();
                    return;
                }

                bytes.add(c);
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
