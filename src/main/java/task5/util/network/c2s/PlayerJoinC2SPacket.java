package task5.util.network.c2s;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerJoinC2SPacket extends Packet {
    private final String clientUUID;

    public PlayerJoinC2SPacket(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    public PlayerJoinC2SPacket(PacketBuf buf) throws IOException {
        this.clientUUID = buf.readString();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketC2SType.PlayerJoin.ordinal());
        buf.writeString(clientUUID);
        return buf;
    }

    public String getClientUUID() {
        return clientUUID;
    }
}
