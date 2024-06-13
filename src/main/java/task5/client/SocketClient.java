package task5.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;
import task5.util.network.SocketConnection;
import task5.util.network.c2s.PlayerJoinC2SPacket;
import task5.util.network.s2c.ClientApproveS2CPacket;
import task5.util.network.s2c.PacketS2CType;

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
                    this.sendPacket(new PlayerJoinC2SPacket(clientModel.getClientUUID()));
                }

            }
        } catch (IOException e) {
            LOGGER.error("Could not apply S2C packet: " + e.getMessage());
        }
    }
}
