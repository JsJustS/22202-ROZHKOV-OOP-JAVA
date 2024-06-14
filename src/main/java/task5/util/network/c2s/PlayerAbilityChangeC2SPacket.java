package task5.util.network.c2s;

import task5.model.abilityInstance.Ability;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerAbilityChangeC2SPacket extends Packet {
    private final int playerId;
    private final Ability ability;

    public PlayerAbilityChangeC2SPacket(int playerId, Ability ability) {
        this.playerId = playerId;
        this.ability = ability;
    }

    public PlayerAbilityChangeC2SPacket(PacketBuf buf) throws IOException {
        this.playerId = buf.readInt();
        this.ability = Ability.values()[buf.readInt()];
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketC2SType.PlayerAbilityChange.ordinal());
        buf.writeInt(playerId);
        buf.writeInt(ability.ordinal());
        return buf;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Ability getAbility() {
        return ability;
    }
}
