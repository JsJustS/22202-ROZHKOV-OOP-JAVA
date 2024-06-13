package task5.server.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.abilityInstance.SpawnBombAbilityInstanceModel;
import task5.model.abilityInstance.SpawnSuperBombAbilityInstanceModel;
import task5.model.entity.BombEntityModel;
import task5.model.entity.BotEntityModel;
import task5.model.entity.EntityModel;
import task5.model.entity.PlayerEntityModel;
import task5.model.entity.blockentity.BlockEntityModel;

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

    @Override
    public void tick(EntityModel entity, GameModel model) {
        super.tick(entity, model);
        if (!(entity instanceof PlayerEntityModel)) {
            LOGGER.warn("Trying to use PlayerService.useAbility() for other entity");
            return;
        }

        PlayerEntityModel player = (PlayerEntityModel) entity;
        if (player.getBombsLeft() < player.getBombsMax()) {
            if (player.getTicksUntilNextBombGranted() > 0) {
                player.setTicksUntilNextBombGranted(player.getTicksUntilNextBombGranted() - 1);
            } else {
                player.setTicksUntilNextBombGranted(player.getTicksMaxUntilNextBombGranted());
                player.setBombsLeft(player.getBombsLeft()+1);
            }
        }

        if (player.getReceivedDamage() > 0) {
            player.setPoints(player.getPoints() - 100);
            if (player.getPoints() < 0) {
                kill(player);
            }
            player.setReceivedDamage(0);
        }
    }

    @Override
    public boolean shouldCollideWith(EntityModel entity, EntityModel other) {
        if (other instanceof BombEntityModel && ((BombEntityModel)other).getParent().equals(entity)) {
            return false;
        }
        return super.shouldCollideWith(entity, other);
    }

    @Override
    public void tickMovement(EntityModel entity, GameModel model) {
        if (!(entity instanceof PlayerEntityModel)) {
            LOGGER.warn("Trying to use PlayerService.tickMovement() for other entity");
            return;
        }

        PlayerEntityModel player = (PlayerEntityModel) entity;
        if (player.isMoving() && player.getVelocity() == 0) {
            player.setVelocity(player.getSpeed());
        }

        super.tickMovement(entity, model);
    }

    @Override
    public void onKill(EntityModel attacked, EntityModel attacker) {
        super.onKill(attacked, attacker);
        if (!(attacker instanceof PlayerEntityModel)) return;
        PlayerEntityModel player = (PlayerEntityModel) attacker;
        if (attacked instanceof BotEntityModel) {
            player.setPoints(player.getPoints() + 100);
        } else if (attacked instanceof BlockEntityModel) {
            player.setPoints(player.getPoints() + ((BlockEntityModel)attacked).getPoints());
        }
    }
}
