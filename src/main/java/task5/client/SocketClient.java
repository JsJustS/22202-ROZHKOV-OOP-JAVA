package task5.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.model.entity.PlayerEntityModel;
import task5.server.service.engine.entity.EntityService;
import task5.server.service.registry.EntityRegistry;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;
import task5.util.network.SocketConnection;
import task5.util.network.c2s.PlayerJoinC2SPacket;
import task5.util.network.s2c.*;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);
    private final GameModel clientModel;
    private SocketConnection connection;
    private Thread threadS2CPackets;

    public SocketClient(GameModel clientModel) {
        this.clientModel = clientModel;
    }

    public void connect() throws IOException {
        Socket socket = new Socket(clientModel.getHostAddress(), clientModel.getPort());
        this.connection = new SocketConnection(socket);
        this.threadS2CPackets = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    PacketBuf buf = this.connection.read();
                    this.applyPacket(buf);
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.error("Error while handling S2C packet: " + e.getMessage());
                    this.disconnect();
                }
            }
        }, "Client-S2C");
    }

    public void disconnect() {
        if (this.connection == null) return;
        this.threadS2CPackets.interrupt();
        try {
            this.connection.stop();
        } catch (IOException e) {
            LOGGER.warn("Caught error while closing socket: " + e.getMessage());
        }
        this.connection = null;
    }

    public void startS2CHandler() {
        if (this.connection == null) {
            LOGGER.error("Tried to start S2C Handler without establishing connection.");
            return;
        }
        if (this.threadS2CPackets == null) {
            LOGGER.error("Tried to start S2C Handler without new Thread instance.");
            return;
        }
        this.threadS2CPackets.start();
    }

    public void sendPacket(Packet packet) {
        try {
            this.connection.write(packet.serialize());
        } catch (IOException e) {
            LOGGER.error("Error while sending C2S packet: " + e.getMessage());
        }
    }

    /**
     * Applies S2C packets on client-sided GameModel instance
     * */
    private void applyPacket(PacketBuf packetBuf) {
        try {
            int packetTypeOrdinal = packetBuf.readInt();
            PacketS2CType packetC2SType = PacketS2CType.values()[packetTypeOrdinal];

            switch (packetC2SType) {

                case ClientApprove: {
                    ClientApproveS2CPacket packet = new ClientApproveS2CPacket(packetBuf);
                    clientModel.setClientUUID(packet.getClientUUID());
                    LOGGER.info("Client approved! UUID: " + clientModel.getClientUUID());
                    this.sendPacket(new PlayerJoinC2SPacket(clientModel.getClientUUID()));
                    return;
                }

                case RoundData: {
                    RoundDataS2CPacket packet = new RoundDataS2CPacket(packetBuf);
                    clientModel.setFieldWidthInBlocks(packet.getFieldWidthInBlocks());
                    clientModel.setFieldHeightInBlocks(packet.getFieldHeightInBlocks());
                    LOGGER.info("Got Round Data: width " + clientModel.getFieldWidthInBlocks() + " height " + clientModel.getFieldHeightInBlocks());
                    clientModel.setMapReady(true);
                    return;
                }

                case EntitySpawn: {
                    EntitySpawnS2CPacket packet = new EntitySpawnS2CPacket(packetBuf);
                    EntityModel entity;
                    if (packet.getEntityType() == EntityType.Block) {
                        entity = EntityRegistry.getBlock(packet.getBlockType());
                    } else {
                        entity = EntityRegistry.getEntity(packet.getEntityType());
                    }
                    if (entity == null) {
                        LOGGER.warn("Wrong entity type: " + packet.getEntityType().name());
                        return;
                    }
                    entity.setX(packet.getX());
                    entity.setY(packet.getY());
                    entity.setId(packet.getEntityId());
                    clientModel.addEntity(entity);
                    LOGGER.info("Spawned entity " + packet.getEntityType().name() + " on (" + entity.getX() + ";" + entity.getY() + ") with id " + entity.getId());
                    return;
                }

                case BindPlayer: {
                    BindPlayerS2CPacket packet = new BindPlayerS2CPacket(packetBuf);
                    EntityModel entity = EntityService.getEntityById(clientModel, packet.getPlayerId());
                    if (!(entity instanceof PlayerEntityModel)) {
                        LOGGER.warn("Wrong entity id for binding");
                        return;
                    }

                    PlayerEntityModel player = (PlayerEntityModel) entity;
                    clientModel.setMainPlayer(player);
                    LOGGER.info("Bound client to entity with id " + player.getId());
                    return;
                }

                case EntityDespawn: {
                    EntityDespawnS2CPacket packet = new EntityDespawnS2CPacket(packetBuf);
                    EntityModel entity = EntityService.getEntityById(clientModel, packet.getEntityId());
                    if (entity == null) {
                        LOGGER.warn("Wrong entity id for despawning");
                        return;
                    }

                    clientModel.removeEntity(entity);
                    return;
                }

                case EntityStatus: {
                    EntityStatusS2CPacket packet = new EntityStatusS2CPacket(packetBuf);
                    EntityModel entity = EntityService.getEntityById(clientModel, packet.getEntityId());
                    if (entity == null) {
                        LOGGER.warn("Wrong entity id for status");
                        return;
                    }

                    entity.setX(packet.getX());
                    entity.setY(packet.getY());
                    entity.setDirection(packet.getDirection());
                    entity.setAbility(packet.getAbility());
                    entity.setAnimationStep(packet.getAnimationStep());
                    return;
                }

            }
        } catch (IOException e) {
            LOGGER.error("Could not apply S2C packet: " + e.getMessage());
        }
    }
}
