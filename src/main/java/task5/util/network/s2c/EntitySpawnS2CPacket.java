package task5.util.network.s2c;

import task5.model.entity.EntityModel;
import task5.model.entity.EntityType;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class EntitySpawnS2CPacket extends Packet {
    private final EntityType entityType;
    private final int entityId;
    private final double x;
    private final double y;

    public EntitySpawnS2CPacket(EntityType entityType, int entityId, double x, double y) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.x = x;
        this.y = y;
    }

    public EntitySpawnS2CPacket(PacketBuf buf) throws IOException {
        this.entityType = EntityType.values()[buf.readInt()];
        this.entityId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf =  super.serialize();
        buf.writeInt(PacketS2CType.EntitySpawn.ordinal());
        buf.writeInt(this.entityType.ordinal());
        buf.writeInt(this.entityId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        return buf;
    }

    public int getEntityId() {
        return entityId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
