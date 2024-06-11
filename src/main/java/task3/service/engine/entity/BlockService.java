package task3.service.engine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.model.GameModel;
import task3.model.entity.EntityModel;
import task3.model.entity.blockentity.BlockEntityModel;
import task3.service.registry.EntityRegistry;

public class BlockService extends EntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockService.class);

    @Override
    public void tick(EntityModel entity, GameModel model) {
        if (!(entity instanceof BlockEntityModel)) {
            LOGGER.warn("Trying to use BlockService.useAbility() for other entity");
            return;
        }

        BlockEntityModel block = (BlockEntityModel) entity;
        if (block.getReceivedDamage() > 0) {
            if (block.getAttacker() != null) {
                EntityService service = EntityRegistry.getService(block.getAttacker());
                if (service != null) {
                    service.onKill(block, block.getAttacker());
                }
            }
            block.setReceivedDamage(0);
            kill(block);
        }
    }
}
