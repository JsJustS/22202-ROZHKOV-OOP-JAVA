package task5.util.network.s2c;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class EntityDespawnS2CPacket extends Packet {
    private final int entityId;

    public EntityDespawnS2CPacket(int entityId) {
        this.entityId = entityId;
    }

    public EntityDespawnS2CPacket(PacketBuf buf) throws IOException {
        this.entityId = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketS2CType.EntityDespawn.ordinal());
        buf.writeInt(entityId);
        return buf;
    }

    public int getEntityId() {
        return entityId;
    }
}
