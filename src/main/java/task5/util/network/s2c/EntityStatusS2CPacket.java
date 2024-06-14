package task5.util.network.s2c;

import task5.model.abilityInstance.Ability;
import task5.model.entity.Direction;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class EntityStatusS2CPacket extends Packet {
    private final int entityId;
    private final double x;
    private final double y;
    private final Direction direction;
    private final Ability ability;
    private final int animationStep;

    public EntityStatusS2CPacket(int entityId, double x, double y, Direction direction, Ability ability, int animationStep) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.ability = ability;
        this.animationStep = animationStep;
    }

    public EntityStatusS2CPacket(PacketBuf buf) throws IOException {
        this.entityId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.direction = Direction.values()[buf.readInt()];
        this.ability = Ability.values()[buf.readInt()];
        this.animationStep = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketS2CType.EntityStatus.ordinal());
        buf.writeInt(this.entityId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeInt(this.direction.ordinal());
        buf.writeInt(this.ability.ordinal());
        buf.writeInt(this.animationStep);
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

    public Direction getDirection() {
        return direction;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getAnimationStep() {
        return animationStep;
    }
}
