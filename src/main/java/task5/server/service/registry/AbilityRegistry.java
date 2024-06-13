package task5.server.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.abilityInstance.AbstractAbilityInstanceModel;
import task5.model.abilityInstance.SpawnBombAbilityInstanceModel;
import task5.model.abilityInstance.SpawnExplosionAbilityInstanceModel;
import task5.model.abilityInstance.SpawnSuperBombAbilityInstanceModel;
import task5.server.service.engine.ability.AbstractAbilityExecutor;
import task5.server.service.engine.ability.SpawnBombAbilityExecutor;
import task5.server.service.engine.ability.SpawnExplosionAbilityExecutor;
import task5.server.service.engine.ability.SpawnSuperBombAbilityExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AbilityRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbilityRegistry.class);
    private final static Map<Class<? extends AbstractAbilityInstanceModel>, Class<? extends AbstractAbilityExecutor>> registeredAbilityExecutors = new HashMap<>();

    static {
        register(SpawnBombAbilityInstanceModel.class, SpawnBombAbilityExecutor.class);
        register(SpawnExplosionAbilityInstanceModel.class, SpawnExplosionAbilityExecutor.class);
        register(SpawnSuperBombAbilityInstanceModel.class, SpawnSuperBombAbilityExecutor.class);
    }

    private static void register(Class<? extends AbstractAbilityInstanceModel> abilityClass, Class<? extends AbstractAbilityExecutor> executorClass) {
        registeredAbilityExecutors.put(abilityClass, executorClass);
    }
    public static AbstractAbilityExecutor getExecutor(AbstractAbilityInstanceModel model) {
        Class<? extends AbstractAbilityExecutor> executorClass = registeredAbilityExecutors.get(model.getClass());
        if (executorClass == null) return null;
        try {
            return executorClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.toString());
        }
        return null;
    }
}