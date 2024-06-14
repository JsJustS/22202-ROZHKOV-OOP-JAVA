package task5.util.network.s2c;

import task5.model.entity.EntityType;
import task5.model.entity.blockentity.Block;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class EntitySpawnS2CPacket extends Packet {
    private final EntityType entityType;
    private final int entityId;
    private final double x;
    private final double y;
    private final Block blockType;

    public EntitySpawnS2CPacket(EntityType entityType, int entityId, double x, double y, Block blockType) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.blockType = blockType;
    }

    public EntitySpawnS2CPacket(EntityType entityType, int entityId, double x, double y) {
        this(entityType, entityId, x, y, null);
    }

    public EntitySpawnS2CPacket(PacketBuf buf) throws IOException {
        this.entityType = EntityType.values()[buf.readInt()];
        this.entityId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.blockType = (this.entityType == EntityType.Block) ? Block.values()[buf.readInt()] : null;
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf =  super.serialize();
        buf.writeInt(PacketS2CType.EntitySpawn.ordinal());
        buf.writeInt(this.entityType.ordinal());
        buf.writeInt(this.entityId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        if (this.entityType == EntityType.Block) {
            buf.writeInt(this.blockType.ordinal());
        }
        return buf;
    }

    public EntityType getEntityType() {
        return entityType;
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

    public Block getBlockType() {
        return blockType;
    }
}
