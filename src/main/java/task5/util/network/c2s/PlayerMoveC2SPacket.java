package task5.util.network.c2s;

import task5.model.entity.Direction;
import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class PlayerMoveC2SPacket extends Packet {
    private int playerId;
    private Direction direction;
    private boolean isMoving;

    public PlayerMoveC2SPacket(int playerId, Direction direction, boolean isMoving) {
        this.playerId = playerId;
        this.direction = direction;
        this.isMoving = isMoving;
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(playerId);
        buf.writeInt(direction.ordinal());
        buf.writeBoolean(isMoving);
        return buf;
    }

    @Override
    public void deserialize(PacketBuf buf) {
        super.deserialize(buf);
        try {
            this.playerId = buf.readInt();
            this.direction = Direction.values()[buf.readInt()];
            this.isMoving = buf.readBoolean();
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.error("Could not deserialize" + PlayerMoveC2SPacket.class.getSimpleName() + " packet due to and error: " + e.getMessage());
        }
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
