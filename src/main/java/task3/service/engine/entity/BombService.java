package task3.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.GameModel;
import task3.model.abilityInstance.SpawnExplosionAbilityInstanceModel;
import task3.model.entity.BombEntityModel;
import task3.model.entity.EntityModel;
import task3.service.registry.EntityRegistry;

public class BombService extends EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BombService.class);

    @Override
    public void tick(EntityModel entity, GameModel model) {
        super.tick(entity, model);
        if (!(entity instanceof BombEntityModel)) {
            LOGGER.warn("Trying to use BombService.tick() for other entity");
            return;
        }
        BombEntityModel bomb = (BombEntityModel) entity;
        bomb.setTimeToLive(bomb.getTimeToLive()-1);
        if (bomb.getTimeToLive() < 10) {
            bomb.setAnimationPerTick(1);
        }
        if (bomb.getTimeToLive() <= 0) {
            useAbility(bomb, model);
            kill(bomb);
            return;
        }

        if (bomb.getReceivedDamage() > 0) {
            bomb.setReceivedDamage(0);
            useAbility(bomb, model);
            kill(bomb);
        }
    }

    @Override
    public void useAbility(EntityModel entity, GameModel model) {
        super.useAbility(entity, model);
        if (!(entity instanceof BombEntityModel)) {
            LOGGER.warn("Trying to use BombService.tick() for other entity");
            return;
        }
        BombEntityModel bomb = (BombEntityModel) entity;

        model.addAbilityInstance(
                new SpawnExplosionAbilityInstanceModel(bomb.getPower(), bomb.getX(), bomb.getY(), bomb)
        );
    }

    @Override
    public void onKill(EntityModel attacked, EntityModel attacker) {
        super.onKill(attacked, attacker);
        if (!(attacker instanceof BombEntityModel)) return;
        BombEntityModel bomb = (BombEntityModel)attacker;

        if (bomb.getParent() == null) return;
        EntityService service = EntityRegistry.getService(bomb.getParent());

        if (service == null) return;
        service.onKill(attacked, bomb.getParent());
    }
}
