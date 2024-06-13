package task5.server.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.entity.EntityModel;
import task5.model.GameModel;
import task5.model.entity.blockentity.BlockEntityModel;

import java.util.HashSet;
import java.util.Set;

public class EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityService.class);

    public EntityModel getEntityById(int id, GameModel model) {
        for (EntityModel entity : model.getEntities()) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public void kill(EntityModel entity) {
        entity.setAlive(false);
    }

    public void tick(EntityModel entity, GameModel model) {
        tickMovement(entity, model);
        entity.setAnimationTick(entity.getAnimationTick()+1);
        if (entity.getAnimationTick() % entity.getAnimationPerTick() == 0) {
            entity.setAnimationStep(entity.getAnimationStep()+1);
        }
    }

    public void tickMovement(EntityModel entity, GameModel model) {
        if (Math.abs(entity.getVelocity()) > entity.getMaxVelocity()) {
            entity.setVelocity(entity.getMaxVelocity()*Math.signum(entity.getVelocity()));
        }

        double prevX = entity.getX();
        double prevY = entity.getY();

        switch (entity.getDirection()) {
            case UP:
                entity.setY(entity.getY()-entity.getVelocity());
                break;
            case LEFT:
                entity.setX(entity.getX()-entity.getVelocity());
                break;
            case DOWN:
                entity.setY(entity.getY()+entity.getVelocity());
                break;
            case RIGHT:
                entity.setX(entity.getX()+entity.getVelocity());
                break;
        }

        if (!this.getCollidingEntities(entity, model).isEmpty()) {
            entity.setX(prevX);
            entity.setY(prevY);
            entity.setVelocity(entity.getVelocity() * entity.getFriction());
        }

        entity.setVelocity(entity.getVelocity() * entity.getFriction());
        if (entity.getVelocity() < 0.001 && entity.getVelocity() > -0.001) {
            entity.setVelocity(0);
            entity.setX((int)entity.getX()+0.5);
            entity.setY((int)entity.getY()+0.5);
        }
    }

    public void useAbility(EntityModel entity, GameModel model) {

    }

    /**
     * Used when entity kills someone
     * */
    public void onKill(EntityModel attacked, EntityModel attacker) {

    }

    public boolean shouldCollideWith(EntityModel entity, EntityModel other) {
        return other.isCollidable();
    }

    public Set<EntityModel> getCollidingEntities(EntityModel entity, GameModel model) {
        Set<EntityModel> collidingEntities = new HashSet<>();
        for (EntityModel other : model.getEntities()) {
            if (other.equals(entity)) {
                continue;
            }

            if (!shouldCollideWith(entity, other)) {
                continue;
            }

            if (entity.getX() - entity.getHitboxWidth()/2 > other.getX() + other.getHitboxWidth()/2 ||
                    other.getX() - other.getHitboxWidth()/2 > entity.getX() + entity.getHitboxWidth()/2) {
                continue;
            }
            if (entity.getY() - entity.getHitboxHeight()/2 > other.getY() + other.getHitboxHeight()/2 ||
                    other.getY() - other.getHitboxHeight()/2 > entity.getY() + entity.getHitboxHeight()/2) {
                continue;
            }

            collidingEntities.add(other);
        }
        return collidingEntities;
    }

    public static BlockEntityModel getBlockEntityByCoordinates(int x, int y, GameModel model) {
        for (EntityModel entity : model.getEntities()) {
            if (!(entity instanceof BlockEntityModel)) {
                continue;
            }

            if ((int)entity.getX() == x && (int)entity.getY() == y) {
                return (BlockEntityModel) entity;
            }
        }
        return null;
    }
}
