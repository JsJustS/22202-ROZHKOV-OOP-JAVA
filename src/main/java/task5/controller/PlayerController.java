package task5.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.abilityInstance.Ability;
import task5.model.entity.Direction;
import task5.model.entity.EntityModel;
import task5.model.entity.PlayerEntityModel;
import task5.service.engine.entity.PlayerService;

public class PlayerController implements IController<PlayerController.OP, GameModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    public enum OP {
        JOIN,
        USE_ABILITY,
        CHANGE_ABILITY,
        MOVE,
        LEAVE
    }

    @Override
    public <T> void execute(OP operation, GameModel model, T value) {
        switch (operation) {
            case JOIN: {
                model.setPlayerJoined(true);
                break;
            }
            case LEAVE: {
                model.setPlayerJoined(false);
                break;
            }
            case MOVE: {
                int[] packet = (int[]) value;
                PlayerService service = new PlayerService();
                EntityModel entity = service.getEntityById(packet[0], model);
                if(!(entity instanceof PlayerEntityModel)) {
                    LOGGER.error("Wrong entity id");
                    break;
                }
                PlayerEntityModel playerEntity = (PlayerEntityModel) entity;
                playerEntity.setMoving(packet[1] != -1);
                if (packet[1] != -1) {
                    playerEntity.setDirection(Direction.values()[packet[1]]);
                }
                break;
            }
            case USE_ABILITY: {
                int entityId = (int) value;
                PlayerService service = new PlayerService();
                EntityModel entity = service.getEntityById(entityId, model);
                if(!(entity instanceof PlayerEntityModel)) {
                    LOGGER.error("Wrong entity id");
                    break;
                }
                service.useAbility(entity, model);
                break;
            }

            case CHANGE_ABILITY: {
                int[] packet = (int[]) value;
                PlayerService service = new PlayerService();
                EntityModel entity = service.getEntityById(packet[0], model);
                if(!(entity instanceof PlayerEntityModel)) {
                    LOGGER.error("Wrong entity id");
                    break;
                }
                entity.setAbility(Ability.values()[packet[1]]);
                break;
            }
        }
    }
}
