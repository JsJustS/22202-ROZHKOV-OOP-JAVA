package task1.output;

public class ConsoleScreen implements IOutputHandler {
    private int cows;
    private int bulls;
    private boolean isGuessed;
    private String clue;
    private boolean clueFlag;

    public ConsoleScreen() {
        this.cows = 0;
        this.bulls = 0;
        this.isGuessed = false;
    }

    @Override
    public void setResponse(int cows, int bulls, boolean isGuessed) {
        this.cows = cows;
        this.bulls = bulls;
        this.isGuessed = isGuessed;
    }

    @Override
    public void tick(boolean showCowsAndBulls) {

        if (this.isGuessed) {
            System.out.println("You guessed the sequence! Congratulation!");
        } else if (this.clueFlag) {
            this.clueFlag = false;
            System.out.println("Clue: " + this.clue);
        } else if (showCowsAndBulls) {
            System.out.printf("Cows: %d\n", this.cows);
            System.out.printf("Bulls: %d\n", this.bulls);
        }
    }

    @Override
    public void showClue(boolean askedClue) {
        this.clueFlag = askedClue;
    }

    @Override
    public void setClue(String clue) {
        this.clue = clue;
    }
}
