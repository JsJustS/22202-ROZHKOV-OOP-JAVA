package task5.server.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.entity.*;
import task5.model.entity.blockentity.Block;
import task5.model.entity.blockentity.BlockEntityModel;
import task5.server.service.engine.entity.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityRegistry.class);
    private static final Map<Block, BlockSettings> registeredBlocks = new HashMap<>();
    private static final Map<Class<? extends EntityModel>, Class<? extends EntityService>> registeredEntityServices = new HashMap<>();
    private static final Map<EntityType, Class<? extends EntityModel>> registeredEntities = new HashMap<>();

    static {
        registerEntity(EntityType.Block, BlockEntityModel.class);
        registerEntity(EntityType.Player, PlayerEntityModel.class);
        registerEntity(EntityType.Bomb, BombEntityModel.class);
        registerEntity(EntityType.SuperBomb, SuperBombEntityModel.class);
        registerEntity(EntityType.Explosion, ExplosionEntityModel.class);
        registerEntity(EntityType.Bot, BotEntityModel.class);

        registerBlock(Block.AIR, 0, 0, false, null);
        registerBlock(Block.BEDROCK, 100, 0, true, "task5/img/entity/block/bedrock.png");
        registerBlock(Block.BRICK, 5, 3, true, "task5/img/entity/block/brick.png");
        registerBlock(Block.STONE, 2, 2, true, "task5/img/entity/block/stone.png");
        registerBlock(Block.COBBLESTONE, 1,  1,true, "task5/img/entity/block/cobblestone.png");
        registerBlock(Block.GOLD, 1, 5,true, "task5/img/entity/block/gold.png");
        registerBlock(Block.DIAMOND, 1, 10, true, "task5/img/entity/block/diamond.png");

        registerEntityService(EntityModel.class, EntityService.class);
        registerEntityService(PlayerEntityModel.class, PlayerService.class);
        registerEntityService(BotEntityModel.class, BotService.class);
        registerEntityService(BombEntityModel.class, BombService.class);
        registerEntityService(SuperBombEntityModel.class, BombService.class);
        registerEntityService(ExplosionEntityModel.class, ExplosionService.class);
        registerEntityService(BlockEntityModel.class, BlockService.class);
    }

    private static void registerEntity(EntityType entityType, Class<? extends EntityModel> modelClass) {
        registeredEntities.put(entityType, modelClass);
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

    public static BlockEntityModel getBlock(Block blockType) {
        BlockSettings settings = registeredBlocks.get(blockType);
        BlockEntityModel block = new BlockEntityModel();
        block.setBlastResistance(settings.getBlastResistance());
        block.setCollidable(settings.isCollidable());
        block.setSpritePath(settings.getSpritePath());
        block.setPoints(settings.getPoints());
        block.setType(blockType);
        return block;
    }

    public static EntityModel getEntity(EntityType type) {
        Class<? extends EntityModel> modelClass = registeredEntities.get(type);
        if (modelClass == null) return null;
        try {
            EntityModel entity = modelClass.getDeclaredConstructor().newInstance();
            entity.setEntityType(type);
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.toString());
        }
        return null;
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
