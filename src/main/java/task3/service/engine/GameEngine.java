package task3.service.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.GameModel;
import task3.model.abilityInstance.AbstractAbilityInstanceModel;
import task3.model.entity.BotEntityModel;
import task3.model.entity.EntityModel;
import task3.model.entity.PlayerEntityModel;
import task3.model.entity.blockentity.Block;
import task3.model.entity.blockentity.BlockEntityModel;
import task3.service.engine.ability.AbstractAbilityExecutor;
import task3.service.engine.entity.EntityService;
import task3.service.registry.AbilityRegistry;
import task3.service.registry.EntityRegistry;
import task3.util.Config;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEngine.class);
    private final Config config;
    private final GameModel model;

    private final Random random = new Random();
    private long seed;

    public GameEngine(Config config, GameModel model) {
        this.config = config;
        this.model = model;

        this.seed = (config.getSeed() != 0) ? config.getSeed() : random.nextLong();
        this.random.setSeed(this.seed);
    }

    public void start() {
        long last = System.currentTimeMillis();
        boolean hadPlayer = false;
        while (true) {
            if (System.currentTimeMillis() - last < 50) {
                continue;
            }

            if (model.hasPlayer() && !hadPlayer) {
                resetGame();
                startGame();
                hadPlayer = true;
            } else if (!model.hasPlayer() && hadPlayer) {
                stopGame();
                hadPlayer = false;
            }

            if (model.isGameRunning()) {
                this.tick();
            }
            last = System.currentTimeMillis();
        }
    }

    public void startGame() {
        model.setGameRunning(true);
    }

    public void stopGame() {
        model.setGameRunning(false);
    }

    public void resetGame(long seed) {
        this.seed = seed;
        this.random.setSeed(this.seed);
        resetGame();
    }

    public void resetGame() {
        model.setFieldWidthInBlocks(this.config.getFieldWidth());
        model.setFieldHeightInBlocks(this.config.getFieldHeight());

        model.clearEntities();
        model.clearAbilityInstances();

        int pointsForWin = this.generateField();

        PlayerEntityModel playerEntity = new PlayerEntityModel();
        playerEntity.setX(2.5);
        playerEntity.setY(2.5);

        playerEntity.setId(model.getLastEntityId()+1);
        model.setLastEntityId(playerEntity.getId());
        model.addEntity(playerEntity);

        model.setMainPlayer(playerEntity);

        this.populateMapWithBots(config.getBots());
        pointsForWin += 100 * config.getBots();

        pointsForWin  = (int)(pointsForWin * config.getDifficultyModifier());
        model.setPointsForWin(pointsForWin);
    }

    public void tick() {
        // ###################
        // Abilities (in-game event system)
        tickAbilityInstances();

        // ###################
        // Entities (live, die, etc)
        tickEntities();

        if (model.getMainPlayer().getPoints() >= model.getPointsForWin()) {
            this.stopGame();
        }
    }

    private void tickAbilityInstances() {
        Set<AbstractAbilityInstanceModel> abilitiesToBeRemoved = new HashSet<>();
        for (AbstractAbilityInstanceModel abilityInstance : model.getAbilityInstances()) {
            AbstractAbilityExecutor executor = AbilityRegistry.getExecutor(abilityInstance);
            abilitiesToBeRemoved.add(abilityInstance);
            if (executor == null) {
                LOGGER.warn(String.format("Could not generate executor for \"%s\"", abilityInstance.getClass().getName()));
                continue;
            }
            executor.execute(abilityInstance, model);
        }
        for (AbstractAbilityInstanceModel abilityInstance : abilitiesToBeRemoved) {
            model.removeAbilityInstance(abilityInstance);
        }
    }

    private void tickEntities() {
        Set<EntityModel> entitiesToBeRemoved = new HashSet<>();
        for (EntityModel entity : model.getEntities()) {
            if (!entity.isAlive()) {
                entitiesToBeRemoved.add(entity);
                continue;
            }
            
            EntityService entityService = EntityRegistry.getService(entity);
            if (entityService == null) {
                //LOGGER.warn(String.format("Could not generate service for \"%s\"", entity.getClass().getName()));
                continue;
            }
            entityService.tick(entity, model);
        }
        for (EntityModel entity : entitiesToBeRemoved) {
            model.removeEntity(entity);
        }
    }

    private int generateField() {
        FieldGenerator generator = new FieldGenerator(
                this.seed,
                model.getFieldWidthInBlocks(),
                model.getFieldHeightInBlocks()
        );

        model.setBotMap(new byte[model.getFieldWidthInBlocks()][model.getFieldHeightInBlocks()]);
        byte[][] field = generator.generateField(model.getBotMap());

        int maxPointsOnField = 0;
        this.random.setSeed(this.seed);
        for (int i = 0; i < model.getFieldWidthInBlocks(); ++i) {
            for (int j = 0; j < model.getFieldHeightInBlocks(); ++j) {
                if (field[i][j] > 0) {
                    Block blockType = Block.values()[field[i][j]];
                    BlockEntityModel block = EntityRegistry.getBlock(blockType);
                    block.setX(i + .5);
                    block.setY(j + .5);
                    model.addEntity(block);
                    maxPointsOnField += block.getPoints();
                }
            }
        }
        model.setMapReady(true);
        return maxPointsOnField;
    }

    private void populateMapWithBots(int bots) {
        for (int i = 0; i < bots; ++i) {
            double x;
            double y;
            switch (i) {
                case 0:
                    x = model.getBotMap().length - 2.5;
                    y = 2.5;
                    break;
                case 1:
                    x = 2.5;
                    y = model.getBotMap()[0].length - 2.5;
                    break;
                default:
                    x = model.getBotMap().length - 2.5;
                    y = model.getBotMap()[0].length - 2.5;
            }
            BotEntityModel bot = new BotEntityModel();
            bot.setX(x);
            bot.setY(y);
            bot.setId(model.getLastEntityId()+1);
            model.setLastEntityId(bot.getId());
            model.addEntity(bot);
        }
    }
}
