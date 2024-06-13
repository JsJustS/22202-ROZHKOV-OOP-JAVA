package task5.util.network.c2s;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerAbilityChangeC2SPacket extends Packet {
    private final int playerId;
    private final int abilityOrdinal;

    public PlayerAbilityChangeC2SPacket(int playerId, int abilityOrdinal) {
        this.playerId = playerId;
        this.abilityOrdinal = abilityOrdinal;
    }

    public PlayerAbilityChangeC2SPacket(PacketBuf buf) throws IOException {
        this.playerId = buf.readInt();
        this.abilityOrdinal = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketC2SType.PlayerAbilityChange.ordinal());
        buf.writeInt(playerId);
        buf.writeInt(abilityOrdinal);
        return buf;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAbilityOrdinal() {
        return abilityOrdinal;
    }
}
