package task3.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.entity.EntityModel;
import task3.model.GameModel;

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

    public void tick(EntityModel entity, GameModel model) {

    }

    public void useAbility(EntityModel entity, GameModel model) {

    }

    public Set<EntityModel> getCollidingEntities(EntityModel entity, GameModel model) {
        Set<EntityModel> collidingEntities = new HashSet<>();
        for (EntityModel other : model.getEntities()) {
            if (other.equals(entity)) {
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
}
