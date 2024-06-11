package task3.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.GameModel;
import task3.model.abilityInstance.SpawnBombAbilityInstanceModel;
import task3.model.abilityInstance.SpawnSuperBombAbilityInstanceModel;
import task3.model.entity.EntityModel;
import task3.model.entity.PlayerEntityModel;

import java.util.Set;

public class PlayerService extends EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Override
    public void useAbility(EntityModel entity, GameModel model) {
        super.useAbility(entity, model);
        if (!(entity instanceof PlayerEntityModel)) {
            LOGGER.warn("Trying to use PlayerService.useAbility() for other entity");
            return;
        }

        PlayerEntityModel player = (PlayerEntityModel) entity;
        if (player.getBombsLeft() <= 0) {
            return;
        }

        Set<EntityModel> collided =this.getCollidingEntities(player, model);
        if (!collided.isEmpty()) {
            return;
        }

        switch (player.getAbility()) {
            case SIMPLE_BOMB:
                model.addAbilityInstance(
                        new SpawnBombAbilityInstanceModel((int)player.getX()+.5, (int)player.getY()+.5, player)
                );
                player.setBombsLeft(player.getBombsLeft() - 1);
                break;
            case SUPER_BOMB:
                if (player.getBombsLeft() != player.getBombsMax()) {
                    break;
                }
                model.addAbilityInstance(
                        new SpawnSuperBombAbilityInstanceModel((int)player.getX()+.5, (int)player.getY()+.5, player)
                );
                player.setBombsLeft(0);
                break;
            default:
                LOGGER.warn("Trying to generate ability not applicable to player");
        }
    }
}
