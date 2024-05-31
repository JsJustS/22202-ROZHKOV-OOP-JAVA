package task3.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.util.Config;

import java.util.Random;

/**
 * Sophisticated controller with logic for GameModel.
 * Basically ticks the game and updates GameModel to render.
 * */
public class GameEngine {
    private final Logger LOGGER = LoggerFactory.getLogger(GameEngine.class);
    private Random random = new Random();
    private long seed;

    public GameEngine(Config cfg) {
        this.seed = (cfg.getSeed() != 0) ? cfg.getSeed() : random.nextLong();
        this.random.setSeed(this.seed);
    }

    /**
     * Reset in-game state
     * */
    public void resetGame() {
    }

    public void resetGame(long seed) {
        this.seed = seed;
        this.random.setSeed(this.seed);
        resetGame();
    }

    /**
     * Start game from stored in-game state
     * */
    public void startGame() {

    }

    public void stopGame() {

    }

    private void tick() {

    }
}
