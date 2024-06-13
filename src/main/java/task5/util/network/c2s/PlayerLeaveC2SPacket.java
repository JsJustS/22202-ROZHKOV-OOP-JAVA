package task5.util.network.c2s;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerLeaveC2SPacket extends Packet {
    private int playerId;

    public PlayerLeaveC2SPacket(int id) {
        this.playerId = id;
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(playerId);
        return buf;
    }

    @Override
    public void deserialize(PacketBuf buf) {
        super.deserialize(buf);
        try {
            this.playerId = buf.readInt();
        } catch (IOException e) {
            LOGGER.error("Could not deserialize" + PlayerAbilityUseC2SPacket.class.getSimpleName() + " packet due to and error: " + e.getMessage());
        }
    }

    public int getPlayerId() {
        return playerId;
    }
}
