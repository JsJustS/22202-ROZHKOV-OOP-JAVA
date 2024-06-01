package task3.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.NetworkS2CController;
import task3.engine.block.Block;
import task3.engine.block.BlockRegistry;
import task3.model.GameModel;
import task3.util.Config;

import java.util.Random;

/**
 * Sophisticated controller with logic for GameModel.
 * Basically ticks the game and updates GameModel to render.
 * */
public class GameEngine {
    private final Logger LOGGER = LoggerFactory.getLogger(GameEngine.class);
    private final GameModel gameModel;
    private final Config config;
    private final NetworkS2CController networkS2CController;

    private final Random random = new Random();
    private long seed;

    public GameEngine(Config cfg, GameModel gameModel, NetworkS2CController networkS2CController) {
        this.config = cfg;
        this.seed = (cfg.getSeed() != 0) ? cfg.getSeed() : random.nextLong();
        this.random.setSeed(this.seed);

        this.gameModel = gameModel;
        this.networkS2CController = networkS2CController;
    }

    /**
     * Reset in-game state
     * */
    public void resetGame() {
        gameModel.setFieldWidthInBlock(this.config.getFieldWidth());
        gameModel.setFieldHeightInBlocks(this.config.getFieldHeight());
        this.networkS2CController.execute(
                NetworkS2CController.PacketType.MAP_DIM_CHANGED,
                new int[]{gameModel.getFieldWidthInBlock(), gameModel.getFieldHeightInBlocks()}
        );

        this.generateField();
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

    private void generateField() {
        this.random.setSeed(this.seed);

        for (int i = 0; i < gameModel.getFieldWidthInBlock(); ++i) {
            for (int j = 0; j < gameModel.getFieldHeightInBlocks(); ++j) {
                if (this.random.nextDouble() < 0.5d) {
                    BlockRegistry.Blocks blockType = BlockRegistry.Blocks.values()[this.random.nextInt(BlockRegistry.Blocks.values().length)];
                    gameModel.addBlock(i, j, blockType);
                    this.networkS2CController.execute(
                            NetworkS2CController.PacketType.BLOCK_PLACED,
                            new int[]{i, j, blockType.ordinal()}
                    );
                }
            }
        }
    }
}
