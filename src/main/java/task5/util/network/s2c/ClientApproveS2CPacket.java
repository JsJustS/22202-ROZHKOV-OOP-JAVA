package task5.util.network.s2c;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class ClientApproveS2CPacket extends Packet {
    private final String clientUUID;

    public ClientApproveS2CPacket(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    public ClientApproveS2CPacket(PacketBuf buf) throws IOException {
        this.clientUUID = buf.readString();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketS2CType.ClientApprove.ordinal());
        buf.writeString(clientUUID);
        return buf;
    }

    public String getClientUUID() {
        return clientUUID;
    }
}
