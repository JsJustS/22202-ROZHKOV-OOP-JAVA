package task3.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.engine.ability.SpawnBombAbilityInstance;
import task3.engine.entity.Entity;
import task3.engine.entity.PlayerEntity;
import task3.model.GameModel;

public class NetworkC2SController implements IController<NetworkC2SController.PacketType, GameModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkC2SController.class);
    private final GameModel server;

    public NetworkC2SController(GameModel gameModel) {
        this.server = gameModel;
    }

    public enum PacketType {
        PLAYER_JOINED,
        PLAYER_LEFT,
        PLAYER_MOVED,
        PLAYER_ABILITY_USED
    }

    public <T> void execute(NetworkC2SController.PacketType packetType, T value) {
        synchronized (server) {
            this.execute(packetType, server, value);
        }
    }

    @Override
    public <T> void execute(NetworkC2SController.PacketType packetType, GameModel model, T value) {
        switch (packetType) {
            case PLAYER_JOINED: {
                model.setHasPlayer(true);
                break;
            }
            case PLAYER_LEFT: {
                model.setHasPlayer(false);
                break;
            }
            case PLAYER_MOVED: {
                int[] packet = (int[]) value;
                Entity entity = model.getEntity(packet[0]);
                if(!(entity instanceof PlayerEntity)) {
                    LOGGER.error("Wrong entity id");
                    break;
                }
                //LOGGER.info(packet[0]+"|"+packet[1]+"|"+packet[2]);
//                if (entity.getVelocityX() == 0 && entity.getVelocityY() == 0) {
//                    entity.setVelocityX(entity.getVelocityX() + entity.getSpeedX()*packet[1]);
//                    entity.setVelocityY(entity.getVelocityY() + entity.getSpeedY()*packet[2]);
//                }
                PlayerEntity playerEntity = (PlayerEntity) entity;
                playerEntity.setMoving(packet[1] != 0);
                if (packet[1] != 0) {
                    playerEntity.setDirection(PlayerEntity.Direction.values()[packet[1]-1]);
                }
                break;
            }
            case PLAYER_ABILITY_USED: {
                double[] packet = (double[]) value;
                // todo: ability registry?
                Entity playerEntity = model.getEntity((int)packet[0]);
                if(!(playerEntity instanceof PlayerEntity)) {
                    LOGGER.error("Wrong entity id");
                    break;
                }
                for (Entity entity : model.getEntities()) {
                    if (entity.equals(playerEntity)) {
                        continue;
                    }
                    if ((int)packet[1] > entity.getX() + entity.getHitboxWidth()/2 || entity.getX() - entity.getHitboxWidth()/2 > (int)packet[1] + 1) {
                        continue;
                    }
                    if ((int)packet[2] > entity.getY() + entity.getHitboxHeight()/2 || entity.getY() - entity.getHitboxHeight()/2 > (int)packet[2] + 1) {
                        continue;
                    }
                    return;
                }
                model.addAbilityInstance(
                        new SpawnBombAbilityInstance((int)packet[1], (int)packet[2], playerEntity)
                );
                break;
            }
        }
    }
}
