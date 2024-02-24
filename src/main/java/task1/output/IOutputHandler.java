package task1.output;

public interface IOutputHandler {
    public void setResponse(int cows, int bulls, boolean isGuessed);

    public void tick(boolean showCowsAndBulls);

    public void showClue(boolean askedClue);
    public void setClue(String clue);
}
