package task3.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.entity.BotEntityModel;
import task3.model.entity.EntityModel;
import task3.model.entity.PlayerEntityModel;
import task3.model.entity.blockentity.Block;
import task3.model.entity.blockentity.BlockEntityModel;
import task3.service.engine.entity.BotService;
import task3.service.engine.entity.EntityService;
import task3.service.engine.entity.PlayerService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityRegistry.class);
    private static final Map<Block, BlockSettings> registeredBlocks = new HashMap<>();
    private final static Map<Class<? extends EntityModel>, Class<? extends EntityService>> registeredEntityServices = new HashMap<>();

    static {
        registerBlock(Block.AIR, 0, 0, false, null);
        registerBlock(Block.BEDROCK, 100, 0, true, "img/entity/block/bedrock.png");
        registerBlock(Block.BRICK, 5, 3, true, "img/entity/block/brick.png");
        registerBlock(Block.STONE, 2, 2, true, "img/entity/block/stone.png");
        registerBlock(Block.COBBLESTONE, 1,  1,true, "img/entity/block/cobblestone.png");
        registerBlock(Block.GOLD, 1, 5,true, "img/entity/block/gold.png");
        registerBlock(Block.DIAMOND, 1, 10, true, "img/entity/block/diamond.png");

        registerEntityService(EntityModel.class, EntityService.class);
        registerEntityService(PlayerEntityModel.class, PlayerService.class);
        registerEntityService(BotEntityModel.class, BotService.class);
    }

    private static void registerEntityService(Class<? extends EntityModel> modelClass, Class<? extends EntityService> serviceClass) {
        registeredEntityServices.put(modelClass, serviceClass);
    }

    private static void registerBlock(Block id, int blastResistance, int points, boolean isCollidable, String spritePath) {
        registeredBlocks.put(
                id,
                new BlockSettings(blastResistance, points, isCollidable, spritePath)
        );
    }

    public static EntityService getService(EntityModel model) {
        Class<? extends EntityService> serviceClass = registeredEntityServices.get(model.getClass());
        if (serviceClass == null) return null;
        try {
            return serviceClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    public static BlockEntityModel getBlock(Block blockId) {
        BlockSettings settings = registeredBlocks.get(blockId);
        BlockEntityModel block = new BlockEntityModel();
        block.setBlastResistance(settings.getBlastResistance());
        block.setCollidable(settings.isCollidable());
        block.setSpritePath(settings.getSpritePath());
        return block;
    }

    private static final class BlockSettings {
        private final int blastResistance;
        private final int points;
        private final boolean isCollidable;
        private final String spritePath;

        BlockSettings(int blastResistance, int points, boolean isCollidable, String spritePath) {
            this.blastResistance = blastResistance;
            this.points = points;
            this.isCollidable = isCollidable;
            this.spritePath = spritePath;
        }

        public int getBlastResistance() {
            return blastResistance;
        }

        public int getPoints() {
            return points;
        }

        public boolean isCollidable() {
            return isCollidable;
        }

        public String getSpritePath() {
            return spritePath;
        }
    }
}
