package task5.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task5.model.GameModel;
import task5.util.network.Packet;
import task5.util.network.SocketConnection;
import task5.util.pubsub.ISubscriber;

import java.io.IOException;
import java.net.Socket;

public class SocketClient implements ISubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);
    private final GameModel clientModel;
    private SocketConnection connection;
    private Thread threadS2CPackets;

    public SocketClient(GameModel clientModel) {
        this.clientModel = clientModel;
    }

    public void connect() throws IOException {
        Socket socket = new Socket(clientModel.getHostName(), clientModel.getPort());
        this.connection = new SocketConnection(socket);
        this.threadS2CPackets = new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                try {
                    Packet packet = this.connection.read();
                    this.applyPacket(packet);
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.error("Error while handling S2C packet: " + e.getMessage());
                }
            }
        }, "Client-S2C");
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
            this.connection.write(packet);
        } catch (IOException e) {
            LOGGER.error("Error while sending C2S packet: " + e.getMessage());
        }
    }

    @Override
    public void onNotification() {
        if (!clientModel.getPressedKeys().isEmpty()) {
            //todo: send pressed keys
        }

        if (!clientModel.getReleasedKeys().isEmpty()) {
            //todo: send released keys
        }
    }

    /**
     * Applies S2C packets on client-sided GameModel instance
     * */
    private void applyPacket(Packet packetS2C) {

    }
}
