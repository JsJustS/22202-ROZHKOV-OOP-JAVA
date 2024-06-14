package task5.util.network.s2c;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class BindPlayerS2CPacket extends Packet {
    private final int playerId;

    public BindPlayerS2CPacket(int playerId) {
        this.playerId = playerId;
    }

    public BindPlayerS2CPacket(PacketBuf buf) throws IOException {
        this.playerId = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketS2CType.BindPlayer.ordinal());
        buf.writeInt(this.playerId);
        return buf;
    }

    public int getPlayerId() {
        return playerId;
    }
}
