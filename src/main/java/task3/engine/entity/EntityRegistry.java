package task3.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class EntityRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityRegistry.class);
    private static final HashMap<Integer, Class<? extends Entity>> registeredEntities = new HashMap<>();

    public enum Entities {
        PLAYER,
        BOMB,
        EXPLOSION,
        POWER_UP
    }

    static {
        register(Entities.PLAYER, PlayerEntity.class);
        register(Entities.BOMB, BombEntity.class);
        register(Entities.EXPLOSION, ExplosionEntity.class);
    }

    public static Entity getEntityById(EntityRegistry.Entities id) {
        return getEntityById(id.ordinal());
    }

    public static Entity getEntityById(int id) {
        Class<? extends Entity> entityClass = registeredEntities.get(id);
        if (entityClass == null) return null;
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private static void register(EntityRegistry.Entities id, Class<? extends Entity> entityClass) {
        registeredEntities.put(id.ordinal(), entityClass);
    }
}
