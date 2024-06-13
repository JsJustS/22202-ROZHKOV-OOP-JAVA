package task5.util.network;

import java.io.IOException;
import java.io.Serializable;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class PacketBuf implements Serializable {
    private ByteBuffer buffer;
    private int rd;

    public PacketBuf() {
        this.buffer = null;
        this.rd = 0;
    }

    public PacketBuf(byte[] data) {
        this.buffer = ByteBuffer.wrap(data);
        this.rd = 0;
    }

    public byte[] asArray() {
        return buffer.array();
    }

    private void expand(int size) {
        if (buffer == null) {
            this.buffer = ByteBuffer.allocate(size);
        } else {
            byte[] old = this.buffer.array();
            this.buffer = ByteBuffer.allocate(this.buffer.capacity() + size);
            this.buffer.put(old);
        }
    }

    public void writeByte(byte value) {
        expand(Byte.BYTES);
        this.buffer.put(value);
    }

    public void writeChar(char value) {
        expand(Character.BYTES);
        this.buffer.putChar(value);
    }

    public void writeShort(short value) {
        expand(Short.BYTES);
        this.buffer.putShort(value);
    }

    public void writeInt(int value) {
        expand(Integer.BYTES);
        this.buffer.putInt(value);
    }

    public void writeLong(long value) {
        expand(Long.BYTES);
        this.buffer.putLong(value);
    }

    public void writeFloat(float value) {
        expand(Float.BYTES);
        this.buffer.putFloat(value);
    }

    public void writeDouble(double value) {
        expand(Double.BYTES);
        this.buffer.putDouble(value);
    }

    public void writeBoolean(boolean value) {
        expand(Byte.BYTES);
        this.buffer.put((byte)((value) ? 1 : 0));
    }

    public void writeString(String value) {
        expand(Integer.BYTES + value.length() * Character.BYTES);
        this.buffer.putInt(value.length());
        for (char c : value.toCharArray()) {
            this.buffer.putChar(c);
        }
    }

    public byte readByte() throws IOException {
        try {
            byte value = this.buffer.get(this.rd);
            this.rd += Byte.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public char readChar() throws IOException {
        try {
            char value = this.buffer.getChar(this.rd);
            this.rd += Character.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public short readShort() throws IOException {
        try {
            short value = this.buffer.getShort(this.rd);
            this.rd += Short.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public int readInt() throws IOException {
        try {
            int value = this.buffer.getInt(this.rd);
            this.rd += Integer.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public long readLong() throws IOException {
        try {
            long value = this.buffer.getLong(this.rd);
            this.rd += Long.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public float readFloat() throws IOException {
        try {
            float value = this.buffer.getFloat(this.rd);
            this.rd += Float.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public double readDouble() throws IOException {
        try {
            double value = this.buffer.getDouble(this.rd);
            this.rd += Double.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public boolean readBoolean() throws IOException {
        try {
            boolean value = (this.buffer.get(this.rd) != 0);
            this.rd += Byte.BYTES;
            return value;
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public String readString() throws IOException {
        int length = readInt();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(readChar());
        }
        return builder.toString();
    }
}
