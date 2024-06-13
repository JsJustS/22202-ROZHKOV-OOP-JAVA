package task5.util.network.c2s;

import task5.model.entity.Direction;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerMoveC2SPacket extends Packet {
    private final int playerId;
    private final Direction direction;
    private final boolean isMoving;

    public PlayerMoveC2SPacket(int playerId, Direction direction, boolean isMoving) {
        this.playerId = playerId;
        this.direction = direction;
        this.isMoving = isMoving;
    }

    public PlayerMoveC2SPacket(PacketBuf buf) throws IOException {
        this.playerId = buf.readInt();
        this.direction = Direction.values()[buf.readInt()];
        this.isMoving = buf.readBoolean();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketC2SType.PlayerMove.ordinal());
        buf.writeInt(playerId);
        buf.writeInt(direction.ordinal());
        buf.writeBoolean(isMoving);
        return buf;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
