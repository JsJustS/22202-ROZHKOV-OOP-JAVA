package task5.util.network;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class PacketBuf {
    private ByteBuffer buffer;

    public PacketBuf() {
        this.buffer = null;
    }

    private void expand(int size) {
        if (buffer == null) {
            this.buffer = ByteBuffer.allocate(size);
        } else {
            ByteBuffer old = this.buffer;
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

    public byte readByte() throws IOException {
        try {
            return this.buffer.get();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public char readChar() throws IOException {
        try {
            return this.buffer.getChar();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public short readShort() throws IOException {
        try {
            return this.buffer.getShort();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public int readInt() throws IOException {
        try {
            return this.buffer.getInt();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public long readLong() throws IOException {
        try {
            return this.buffer.getLong();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public float readFloat() throws IOException {
        try {
            return this.buffer.getFloat();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public double readDouble() throws IOException {
        try {
            return this.buffer.getDouble();
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }

    public boolean readBoolean() throws IOException {
        try {
            return (this.buffer.get() != 0);
        } catch (BufferUnderflowException e) {
            throw new IOException("No data to read.");
        }
    }
}
