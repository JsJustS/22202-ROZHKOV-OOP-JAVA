package task3.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.controller.NetworkS2CController;
import task3.engine.ability.AbstractAbilityInstance;
import task3.engine.block.BlockRegistry;
import task3.engine.entity.Entity;
import task3.engine.entity.EntityRegistry;
import task3.engine.entity.PlayerEntity;
import task3.model.GameModel;
import task3.util.Config;
import task3.util.pubsub.ISubscriber;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Sophisticated controller with logic for GameModel.
 * Basically ticks the game and updates GameModel to render.
 * */
public class GameEngine implements ISubscriber {
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
        gameModel.subscribe(this);
        this.networkS2CController = networkS2CController;
    }

    public void start() {
        long last = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - last < 50) {
                continue;
            }
            if (gameModel.isGameRunning()) this.tick();
            last = System.currentTimeMillis();
        }
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
        //todo: remove all blocks and entities

        this.generateField();

        PlayerEntity playerEntity = new PlayerEntity(2.5, 2.5);
        playerEntity.setId(gameModel.getLastEntityId()+1);
        gameModel.setLastEntityId(playerEntity.getId());
        gameModel.spawnEntity(playerEntity);

        networkS2CController.execute(
                NetworkS2CController.PacketType.ENTITY_SPAWNED,
                new double[]{EntityRegistry.Entities.PLAYER.ordinal(), playerEntity.getX(), playerEntity.getY(), playerEntity.getId()}
        );
        networkS2CController.execute(
                NetworkS2CController.PacketType.BIND_PLAYER,
                playerEntity.getId()
        );
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
        gameModel.setGameRunning(true);
    }

    public void stopGame() {
        gameModel.setGameRunning(false);
    }

    private void tick() {
        // ###################
        // Abilities (in-game event system)
        Set<AbstractAbilityInstance> abilitiesToBeRemoved = new HashSet<>();
        for (AbstractAbilityInstance abilityInstance : gameModel.getAbilityInstances()) {
            abilityInstance.execute(gameModel, networkS2CController);
            abilitiesToBeRemoved.add(abilityInstance);
        }
        for (AbstractAbilityInstance abilityInstance : abilitiesToBeRemoved) {
            gameModel.removeAbilityInstance(abilityInstance);
        }
        //gameModel.clearAbilityInstances();

        // ###################
        // Entities
        Set<Entity> entitiesToBeRemoved = new HashSet<>();
        for (Entity entity : gameModel.getEntities()) {
            double x = entity.getX();
            double y = entity.getY();
            entity.tick(gameModel, networkS2CController);
            if (x != entity.getX() || y != entity.getY()) {
                networkS2CController.execute(
                        NetworkS2CController.PacketType.ENTITY_MOVED,
                        new double[]{entity.getId(), entity.getX(), entity.getY(), entity.getVelocity(), entity.getDirection().ordinal()}
                );
            }
            if (!entity.isAlive()) entitiesToBeRemoved.add(entity);
        }
        for (Entity entity : entitiesToBeRemoved) {
            gameModel.removeEntity(entity);
            networkS2CController.execute(
                    NetworkS2CController.PacketType.ENTITY_DESPAWNED,
                    entity.getId()
            );
        }
    }

    private void generateField() {
        FieldGenerator generator = new FieldGenerator(
                this.seed,
                gameModel.getFieldWidthInBlock(),
                gameModel.getFieldHeightInBlocks()
        );
        byte[][] field = generator.generateField();

        this.random.setSeed(this.seed);
        for (int i = 0; i < gameModel.getFieldWidthInBlock(); ++i) {
            for (int j = 0; j < gameModel.getFieldHeightInBlocks(); ++j) {
                if (field[i][j] > 0) {
                    BlockRegistry.Blocks blockType = BlockRegistry.Blocks.values()[field[i][j]];
                    gameModel.addBlock(i, j, blockType);
                    this.networkS2CController.execute(
                            NetworkS2CController.PacketType.BLOCK_PLACED,
                            new int[]{i, j, blockType.ordinal()}
                    );
                }
            }
        }
    }

    @Override
    public void onNotification() {
        if (gameModel.hasPlayer()) {
            resetGame();
            startGame();
        } else {
            stopGame();
        }
    }
}
