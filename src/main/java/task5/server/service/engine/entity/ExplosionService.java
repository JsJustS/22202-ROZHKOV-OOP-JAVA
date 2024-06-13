package task5.server.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.entity.EntityModel;
import task5.model.entity.ExplosionEntityModel;

public class ExplosionService extends EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExplosionService.class);
    @Override
    public void tick(EntityModel entity, GameModel model) {
        super.tick(entity, model);
        if (!(entity instanceof ExplosionEntityModel)) {
            LOGGER.warn("Trying to use ExplosionService.tick() for other entity");
            return;
        }
        ExplosionEntityModel explosion = (ExplosionEntityModel) entity;
        explosion.setTimeToLive(explosion.getTimeToLive()-1);
        if (explosion.getTimeToLive() <= 0) {
            kill(explosion);
        }
    }
}
