package task1.input;

import java.util.List;

public interface IInputHandler {
    public void tick();

    public boolean hasInput();

    public List<?> getInput();

    public boolean exited();

    public boolean askedClue();
}
