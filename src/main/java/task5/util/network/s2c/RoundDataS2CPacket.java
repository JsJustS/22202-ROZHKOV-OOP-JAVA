package task5.util.network.s2c;

import task5.util.network.Packet;
import task5.util.network.PacketBuf;

import java.io.IOException;

public class RoundDataS2CPacket extends Packet {
    private final int fieldWidthInBlocks;
    private final int fieldHeightInBlocks;

    public RoundDataS2CPacket(int fieldWidthInBlocks, int fieldHeightInBlocks) {
        this.fieldWidthInBlocks = fieldWidthInBlocks;
        this.fieldHeightInBlocks = fieldHeightInBlocks;
    }

    public RoundDataS2CPacket(PacketBuf buf) throws IOException {
        this.fieldWidthInBlocks = buf.readInt();
        this.fieldHeightInBlocks = buf.readInt();
    }

    @Override
    public PacketBuf serialize() {
        PacketBuf buf = super.serialize();
        buf.writeInt(PacketS2CType.RoundData.ordinal());
        buf.writeInt(fieldWidthInBlocks);
        buf.writeInt(fieldHeightInBlocks);
        return buf;
    }

    public int getFieldWidthInBlocks() {
        return fieldWidthInBlocks;
    }

    public int getFieldHeightInBlocks() {
        return fieldHeightInBlocks;
    }
}
