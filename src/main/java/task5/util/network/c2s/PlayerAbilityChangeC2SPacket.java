package task5.util.network.c2s;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerAbilityChangeC2SPacket extends Packet {
    private int playerId;
    private int abilityOrdinal;

    public PlayerAbilityChangeC2SPacket(int playerId, int abilityOrdinal) {
        this.playerId = playerId;
        this.abilityOrdinal = abilityOrdinal;
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(playerId);
        buf.writeInt(abilityOrdinal);
        return buf;
    }

    @Override
    public void deserialize(PacketBuf buf) {
        super.deserialize(buf);
        try {
            this.playerId = buf.readInt();
            this.abilityOrdinal = buf.readInt();
        } catch (IOException e) {
            LOGGER.error("Could not deserialize" + PlayerAbilityChangeC2SPacket.class.getSimpleName() + " packet due to and error: " + e.getMessage());
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAbilityOrdinal() {
        return abilityOrdinal;
    }
}
