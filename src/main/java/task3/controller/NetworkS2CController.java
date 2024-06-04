package task3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task3.engine.entity.Entity;
import task3.engine.entity.EntityRegistry;
import task3.engine.entity.PlayerEntity;
import task3.model.ClientModel;

import javax.swing.*;

public class NetworkS2CController implements IController<NetworkS2CController.PacketType, ClientModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkS2CController.class);
    private final ClientModel client;

    public NetworkS2CController(ClientModel clientModel) {
        this.client = clientModel;
    }

    public enum PacketType {
        BIND_PLAYER,
        MAP_DIM_CHANGED,
        ENTITY_SPAWNED,
        ENTITY_DESPAWNED,
        ENTITY_MOVED,
        ENTITY_PLAYED_ANIMATION,
        BLOCK_PLACED,
        BLOCK_REMOVED,
        PLAYER_STATUS
    }

    public <T> void execute(PacketType packetType, T value) {
        // Execute on client Thread
        SwingUtilities.invokeLater(() -> {
            this.execute(packetType, client, value);
        });
    }

    @Override
    public <T> void execute(PacketType packetType, ClientModel model, T value) {
        switch (packetType) {
            case BIND_PLAYER: {
                int id = (int) value;
                Entity entity = model.getEntity(id);
                if (!(entity instanceof PlayerEntity)) {
                    LOGGER.error("Wrong Entity id");
                    break;
                }
                model.setMainPlayer((PlayerEntity)entity);
                break;
            }
            case MAP_DIM_CHANGED: {
                int[] packet = (int[]) value;
                model.setFieldWidthInBlocks(packet[0]);
                model.setFieldHeightInBlocks(packet[1]);
                model.setMapReady(true);
                break;
            }
            case BLOCK_PLACED: {
                int[] packet = (int[]) value;
                model.addBlock(packet[0], packet[1], packet[2]);
                break;
            }
            case BLOCK_REMOVED: {
                int[] packet = (int[]) value;
                model.removeBlock(packet[0], packet[1]);
                break;
            }
            case ENTITY_SPAWNED: {
                double[] packet = (double[]) value;
                Entity entity = EntityRegistry.getEntityById((int)packet[0]);
                if (entity == null) {
                    LOGGER.error("Wrong Entity id");
                    break;
                }
                entity.setX(packet[1]);
                entity.setY(packet[2]);
                entity.setId((int)packet[3]);
                model.spawnEntity(entity);
                break;
            }

            case ENTITY_DESPAWNED: {
                int id = (int) value;
                model.removeEntity(id);
                break;
            }

            case ENTITY_MOVED: {
                double[] packet = (double[]) value;
                int id = (int) packet[0];
                Entity entity = model.getEntity(id);
                if (entity == null) {
                    LOGGER.error("Wrong Entity id");
                    break;
                }
                entity.setX(packet[1]);
                entity.setY(packet[2]);
                entity.setVelocity(packet[3]);
                entity.setDirection(Entity.Direction.values()[(int)packet[4]]);
                break;
            }
            case PLAYER_STATUS: {
                int[] packet = (int[]) value;
                int id = packet[0];
                Entity entity = model.getEntity(id);
                if (!(entity instanceof PlayerEntity)) {
                    LOGGER.error("Wrong Entity id");
                    break;
                }
                ((PlayerEntity)entity).setPoints(packet[1]);
                ((PlayerEntity)entity).setBombsLeft(packet[2]);
                break;
            }
        }
    }
}
