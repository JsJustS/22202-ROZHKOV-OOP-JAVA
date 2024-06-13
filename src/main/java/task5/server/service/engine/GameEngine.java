package task5.server.service.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.entity.BotEntityModel;
import task5.model.entity.EntityModel;
import task5.model.entity.blockentity.Block;
import task5.model.entity.blockentity.BlockEntityModel;
import task5.server.SocketServer;
import task5.server.service.engine.ability.AbstractAbilityExecutor;
import task5.server.service.engine.entity.EntityService;
import task5.server.service.registry.AbilityRegistry;
import task5.server.service.registry.EntityRegistry;
import task5.util.Config;
import task5.util.pubsub.ISubscriber;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameEngine implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEngine.class);
    private final Config config;
    private final GameModel serverModel;
    private final SocketServer serverNetwork;

    private final Random random = new Random();

    public GameEngine(Config config, GameModel serverModel, int port) throws IOException {
        this.config = config;
        this.serverModel = serverModel;
        serverModel.setTicksPerSecond(20);
        serverModel.subscribe(this);

        this.serverNetwork = new SocketServer(serverModel, port);
        this.serverNetwork.start();
    }

    public void start() {
        long last = System.currentTimeMillis();
        boolean hadPlayer = false;
        while (true) {
            if (System.currentTimeMillis() - last < 1000 / serverModel.getTicksPerSecond()) {
                continue;
            }

            if (serverModel.hasPlayer() && !hadPlayer) {
                if (serverModel.getCurrentSeed() == 0) {
                    resetGame(random.nextLong());
                } else {
                    resetGame();
                }
                startGame();
                hadPlayer = true;
            } else if (!serverModel.hasPlayer() && hadPlayer) {
                stopGame();
                hadPlayer = false;
            }

            if (serverModel.isGameRunning()) {
                this.tick();
            }
            last = System.currentTimeMillis();
        }
    }

    public void startGame() {
        serverModel.setGameRunning(true);
    }

    public void stopGame() {
        serverModel.setGameRunning(false);
    }

    public void resetGame(long seed) {
        serverModel.setCurrentSeed(seed);
        this.random.setSeed(serverModel.getCurrentSeed());
        resetGame();
    }

    public void resetGame() {
        serverModel.setFieldWidthInBlocks(this.config.getFieldWidth());
        serverModel.setFieldHeightInBlocks(this.config.getFieldHeight());

        serverModel.clearEntities();
        serverModel.clearAbilityInstances();

        this.generateField();

        int maxPlayer = 4;
        this.populateMapWithBots(maxPlayer);

        //model.setRoundTicksLeft(config.getRoundSeconds() * model.getTicksPerSecond());

        //model.setMainPlayer(playerEntity);


        //pointsForWin += 100 * config.getBots();

        //pointsForWin  = (int)(pointsForWin * config.getDifficultyModifier());
        //model.setPointsForWin(pointsForWin);
    }

    public void tick() {
        // ###################
        // Abilities (in-game event system)
        tickAbilityInstances();

        // ###################
        // Entities (live, die, etc)
        tickEntities();

        /*if (model.getMainPlayer().getPoints() >= model.getPointsForWin()) {
            this.stopGame();
        }*/

        /*model.setRoundTicksLeft(model.getRoundTicksLeft() - 1);
        if (model.getRoundTicksLeft() <= 0) {
            model.getMainPlayer().setAlive(false);
            stopGame();
        }*/
    }

    private void tickAbilityInstances() {
        Set<AbstractAbilityInstanceModel> abilitiesToBeRemoved = new HashSet<>();
        for (AbstractAbilityInstanceModel abilityInstance : serverModel.getAbilityInstances()) {
            AbstractAbilityExecutor executor = AbilityRegistry.getExecutor(abilityInstance);
            abilitiesToBeRemoved.add(abilityInstance);
            if (executor == null) {
                LOGGER.warn(String.format("Could not generate executor for \"%s\"", abilityInstance.getClass().getName()));
                continue;
            }
            executor.execute(abilityInstance, serverModel);
        }
        for (AbstractAbilityInstanceModel abilityInstance : abilitiesToBeRemoved) {
            serverModel.removeAbilityInstance(abilityInstance);
        }
    }

    private void tickEntities() {
        Set<EntityModel> entitiesToBeRemoved = new HashSet<>();
        for (EntityModel entity : serverModel.getEntities()) {
            if (!entity.isAlive()) {
                entitiesToBeRemoved.add(entity);
                continue;
            }
            
            EntityService entityService = EntityRegistry.getService(entity);
            if (entityService == null) {
                //LOGGER.warn(String.format("Could not generate service for \"%s\"", entity.getClass().getName()));
                continue;
            }
            entityService.tick(entity, serverModel);
        }
        for (EntityModel entity : entitiesToBeRemoved) {
            serverModel.removeEntity(entity);
        }
    }

    private void generateField() {
        FieldGenerator generator = new FieldGenerator(
                serverModel.getCurrentSeed(),
                serverModel.getFieldWidthInBlocks(),
                serverModel.getFieldHeightInBlocks()
        );

        serverModel.setBotMap(new byte[serverModel.getFieldWidthInBlocks()][serverModel.getFieldHeightInBlocks()]);
        byte[][] field = generator.generateField(serverModel.getBotMap());

        this.random.setSeed(serverModel.getCurrentSeed());
        for (int i = 0; i < serverModel.getFieldWidthInBlocks(); ++i) {
            for (int j = 0; j < serverModel.getFieldHeightInBlocks(); ++j) {
                if (field[i][j] > 0) {
                    Block blockType = Block.values()[field[i][j]];
                    BlockEntityModel block = EntityRegistry.getBlock(blockType);
                    block.setX(i + .5);
                    block.setY(j + .5);
                    serverModel.addEntity(block);
                }
            }
        }
        serverModel.setMapReady(true);
    }

    private void populateMapWithBots(int bots) {
        for (int i = 0; i < bots; ++i) {
            double x;
            double y;
            switch (i) {
                case 0:
                    x = 2.5;
                    y = 2.5;
                    break;
                case 1:
                    x = serverModel.getBotMap().length - 2.5;
                    y = 2.5;
                    break;
                case 2:
                    x = 2.5;
                    y = serverModel.getBotMap()[0].length - 2.5;
                    break;
                case 3:
                default:
                    x = serverModel.getBotMap().length - 2.5;
                    y = serverModel.getBotMap()[0].length - 2.5;
            }
            BotEntityModel bot = new BotEntityModel();
            bot.setX(x);
            bot.setY(y);
            bot.setId(serverModel.getLastEntityId()+1);
            serverModel.setLastEntityId(bot.getId());
            serverModel.addEntity(bot);
        }
    }

    @Override
    public void onNotification() {

    }
}
