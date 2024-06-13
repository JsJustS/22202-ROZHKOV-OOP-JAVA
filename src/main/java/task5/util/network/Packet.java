package task5.util.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Packet {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Packet.class);

    public PacketBuf serialize() {
        return new PacketBuf();
    }

    public void deserialize(PacketBuf buf) {

    }
}
