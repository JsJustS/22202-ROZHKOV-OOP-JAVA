package task5.util.network.c2s;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerAbilityUseC2SPacket extends Packet {
    private int playerId;

    public PlayerAbilityUseC2SPacket(int id) {
        this.playerId = id;
    }

    public PlayerAbilityUseC2SPacket(PacketBuf buf) throws IOException {
        this.playerId = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketC2SType.PlayerAbilityUse.ordinal());
        buf.writeInt(playerId);
        return buf;
    }

    public int getPlayerId() {
        return playerId;
    }
}
