package task1;

import task1.util.Config;
import task1.util.ISequenceGen;

import java.util.List;
import java.util.Random;

public class BotPlayer <T> {
    private final List<T> generatedSeq;

    private int cows;
    private int bulls;

    public BotPlayer(Config cfg, ISequenceGen<T> generator) {
        this.generatedSeq = generator.generate(
                cfg.length(),
                (cfg.seed() == null) ?
                        new Random() :
                        new Random(cfg.seed())
        );
    }

    public int getCows() { return this.cows; }

    public int getBulls() { return this.bulls; }

    public String getClue() {
        return generatedSeq.toString();
    }

    /**
     * Checks provided sequence against generated one.
     * Also sets bulls and cows accordingly.
     * */
    public void checkSequence(List<T> sequence) {
        this.bulls = 0;
        this.cows = 0;

        if (sequence.size() != this.generatedSeq.size()) return;
        for (int i = 0; i < sequence.size(); ++i) {
            if (sequence.get(i).equals(this.generatedSeq.get(i))) {
                this.bulls++;
            } else if (this.generatedSeq.contains(sequence.get(i))) {
                this.cows++;
            }
        }
    }

    public boolean isSequenceGuessed() {
        return this.generatedSeq.size() == this.bulls;
    }
}
