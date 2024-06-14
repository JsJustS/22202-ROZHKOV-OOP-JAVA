package task5.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.model.abilityInstance.DespawnPlayerAbilityInstanceModel;
import task5.model.abilityInstance.SpawnPlayerAbilityInstanceModel;
import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.model.entity.PlayerEntityModel;
import task5.model.entity.blockentity.BlockEntityModel;
import task5.util.ThreadPool;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;
import task5.util.network.SocketConnection;
import task5.util.network.c2s.PacketC2SType;
import task5.util.network.c2s.PlayerJoinC2SPacket;
import task5.util.network.c2s.PlayerLeaveC2SPacket;
import task5.util.network.s2c.ClientApproveS2CPacket;
import task5.util.network.s2c.EntitySpawnS2CPacket;
import task5.util.network.s2c.RoundDataS2CPacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
    private final Map<String, SocketConnection> connections;
    private final ServerSocket socket;
    private final GameModel serverModel;
    private ThreadPool threadPoolC2SPackets;

    public SocketServer(GameModel serverModel, int port) throws IOException {
        this.socket = new ServerSocket(port);
        LOGGER.info("Started server on " + this.socket.getInetAddress().getHostAddress() + ":" + port);
        this.connections = new HashMap<>();
        this.serverModel = serverModel;
        this.threadPoolC2SPackets = new ThreadPool(5, "Server-C2S");
    }

    public void start() {
        this.threadPoolC2SPackets.addTask(new NewConnectionHandlerTask(socket, connections, threadPoolC2SPackets));
        this.threadPoolC2SPackets.start();
    }

    public void broadcast(Packet packet) {
        for (SocketConnection client : connections.values()) {
            try {
                client.write(packet.serialize());
            } catch (IOException ignored) {}
        }
    }

    public void send(PlayerEntityModel player, Packet packet) {
        send(player.getClientUUID(), packet);
    }

    public void send(String clientUUID, Packet packet) {
        if (!connections.containsKey(clientUUID)) {
            LOGGER.error("No connection with specified player found (" + clientUUID + ")");
            return;
        }
        try {
            connections.get(clientUUID).write(packet.serialize());
        } catch (IOException ignored) {}
    }

    private void applyPacket(PacketBuf packetBuf) {
        try {
            int packetTypeOrdinal = packetBuf.readInt();
            PacketC2SType packetC2SType = PacketC2SType.values()[packetTypeOrdinal];

            switch (packetC2SType) {

                case PlayerJoin: {
                    PlayerJoinC2SPacket packet = new PlayerJoinC2SPacket(packetBuf);
                    serverModel.addAbilityInstance(
                            new SpawnPlayerAbilityInstanceModel(packet.getClientUUID())
                    );
                    serverModel.setPlayerJoined(true);
                    LOGGER.info(packet.getClientUUID() + " joined as a Player");
                    return;
                }

                case PlayerLeave: {
                    PlayerLeaveC2SPacket packet = new PlayerLeaveC2SPacket(packetBuf);
                    connections.remove(packet.getClientUUID());
                    boolean hasPlayers = false;
                    PlayerEntityModel player = null;
                    for (EntityModel entity : serverModel.getEntities()) {
                        if (entity instanceof PlayerEntityModel) {
                            hasPlayers = true;
                            if (((PlayerEntityModel)entity).getClientUUID().equals(packet.getClientUUID())) {
                                player = (PlayerEntityModel) entity;
                            }
                        }
                    }
                    serverModel.setPlayerJoined(hasPlayers);
                    if (player != null) {
                        serverModel.addAbilityInstance(
                                new DespawnPlayerAbilityInstanceModel(player)
                        );
                    }
                    return;
                }

            }
        } catch (IOException e) {
            LOGGER.error("Could not apply C2S packet: " + e.getMessage());
        }
    }

    public void broadcastGameMeta() {
        for (String clientUUID : connections.keySet()) {
            sendGameMeta(clientUUID);
        }
    }

    public void sendGameMeta(String clientUUID) {
        if (serverModel.isMapReady()) {
            send(
                    clientUUID,
                    new RoundDataS2CPacket(
                            serverModel.getFieldWidthInBlocks(),
                            serverModel.getFieldHeightInBlocks()
                    )
            );
        }
        for (EntityModel entity : serverModel.getEntities()) {
            EntitySpawnS2CPacket packet;
            if (entity instanceof BlockEntityModel) {
                BlockEntityModel blockEntity = (BlockEntityModel)entity;
                packet = new EntitySpawnS2CPacket(
                        EntityType.Block,
                        blockEntity.getId(),
                        blockEntity.getX(),
                        blockEntity.getY(),
                        blockEntity.getType()
                );
            } else {
                packet = new EntitySpawnS2CPacket(
                        entity.getEntityType(),
                        entity.getId(),
                        entity.getX(),
                        entity.getY()
                );
            }
            send(clientUUID, packet);
        }
    }

    private class NewConnectionHandlerTask implements Runnable {
        private final ServerSocket serverSocket;
        private final Map<String, SocketConnection> connections;
        private final ThreadPool parentPool;

        private NewConnectionHandlerTask(ServerSocket serverSocket, Map<String, SocketConnection> connections, ThreadPool parentPool) {
            this.serverSocket = serverSocket;
            this.connections = connections;
            this.parentPool = parentPool;
        }

        @Override
        public void run() {
            while (Thread.currentThread().isAlive()) {
                if (connections.keySet().size() >= 4) {
                    continue;
                }
                try {
                    LOGGER.info("waiting for connection");
                    Socket clientSocket = serverSocket.accept();
                    LOGGER.info("accepted " + clientSocket.toString());
                    String clientUUID = String.valueOf(UUID.randomUUID());
                    SocketConnection client = new SocketConnection(clientSocket);
                    LOGGER.info("established connection");
                    connections.put(clientUUID, client);

                    parentPool.addTask(new ServerC2SHandlerTask(client, clientUUID));
                } catch (IOException ignored) {}
            }
        }
    }

    private class ServerC2SHandlerTask implements Runnable {
        private final SocketConnection client;
        private final String clientUUID;

        private ServerC2SHandlerTask(SocketConnection client, String clientUUID) {
            this.client = client;
            this.clientUUID = clientUUID;
        }

        @Override
        public void run() {
            SocketServer.this.send(clientUUID, new ClientApproveS2CPacket(clientUUID));
            SocketServer.this.sendGameMeta(clientUUID);
            while (connections.containsKey(clientUUID)) {
                try {
                    PacketBuf packetBuf = client.read();
                    SocketServer.this.applyPacket(packetBuf);
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.error("Error while handling C2S packet: " + e.getMessage() + " " + clientUUID);
                    Packet packet = new PlayerLeaveC2SPacket(clientUUID);
                    SocketServer.this.applyPacket(packet.serialize());
                }
            }
        }
    }
}
